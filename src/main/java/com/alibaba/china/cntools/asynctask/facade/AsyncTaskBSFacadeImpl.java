/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */
package com.alibaba.china.cntools.asynctask.facade;

import java.util.List;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;
import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskConfig;
import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskStatusEnum;
import com.alibaba.china.cntools.asynctask.base.repository.AsyncTaskRepository;
import com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskProcessor;

import com.google.common.collect.Lists;
import com.taobao.tddl.util.RouteHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.alibaba.china.cntools.asynctask.base.util.AsyncTaskTypeUtils.getAsyncTaskType;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskConfig;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskExecutor;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskProcessor;

/**
 * @author zhengpc
 * @version $Id: AsyncTaskBSFacadeImpl.java, v 0.1 2018/7/31 下午5:39 zhengpc Exp $
 * @date 2018/07/31
 */
public class AsyncTaskBSFacadeImpl implements AsyncTaskBSFacade {

    /**
     *
     */
    private AsyncTaskRepository asyncTaskRepository;

    @Override
    public <T extends AsyncTask> void saveAsyncTask(T asyncTask) {
        // TODO validate argument
        // 获取任务类型
        String taskType = getAsyncTaskType(asyncTask);

        // 根据任务类型获取任务处理器
        AsyncTaskProcessor taskProcessor = getTaskProcessor(taskType);
        if (taskProcessor == null) {
            // TODO add log
            return;
        }

        // 根据任务类型获取任务配置
        AsyncTaskConfig taskConfig = getTaskConfig(taskType);
        // 根据任务类型判断是否是持久化的任务
        boolean isPersistent = taskConfig != null && taskConfig.isPersistent();
        if (isPersistent) {
            // TODO save callable，默认状态为init，如果支持蓄洪，则将任务状态置为init，如果运行模式是立即执行，则将状态设置为processsing

            // 持久化的任务状态默认为INIT
            AsyncTaskStatusEnum taskStatus = AsyncTaskStatusEnum.INIT;

            // 如果任务的运行模式是立即执行，则任务状态设置为PROCESSING
            //AsyncTaskRunModeEnum runMode = asyncTask.getRunMode();
            //if (runMode == AsyncTaskRunModeEnum.IMMEDIATE_EXECUTION) {
            //    taskStatus = AsyncTaskStatusEnum.PROCESSING;
            //}

            // TODO save asyncTask
            saveAsyncTask(asyncTask, taskStatus);

            // TODO if status not equals process,return
            if (taskStatus != AsyncTaskStatusEnum.PROCESSING) {
                return;
            }
        }

        boolean accepted = taskProcessor.submit(asyncTask);
        // TODO add log

        if (isPersistent && !accepted) {
            // 持久化的任务如果提交执行失败，则更新任务为初始化状态
            updateTaskStatus(Lists.<AsyncTask>newArrayList(asyncTask), AsyncTaskStatusEnum.INIT);
        }
    }

    @Override
    public void fetchTaskToProcess(String dbIndex, String logicTable, String table, String taskType) {
        // 绕开sql解析器和规则计算
        RouteHelper.executeByDBAndTab(dbIndex, logicTable, table);

        AsyncTaskProcessor taskProcessor = getTaskProcessor(taskType);
        if (taskProcessor == null) {
            // TODO add log
            return;
        }

        //
        List<AsyncTask> taskList = queryToDoListByTypeAndEnv(taskType);
        if (taskList == null || taskList.isEmpty()) {
            return;
        }

        List<AsyncTask> rejectedList = Lists.newArrayList();
        for (AsyncTask asyncTask : taskList) {
            if (asyncTask == null) {
                continue;
            }

            //
            boolean accepted = taskProcessor.submit(asyncTask);
            if (!accepted) {
                rejectedList.add(asyncTask);
            }
        }

        // TODO update status init
        updateTaskStatus(rejectedList, AsyncTaskStatusEnum.INIT);
    }

    /**
     * @param taskType
     * @return
     */
    private List<AsyncTask> queryToDoListByTypeAndEnv(String taskType) {
        AsyncTaskConfig taskConfig = getTaskConfig(taskType);
        if (taskConfig == null) {
            return null;
        }

        ThreadPoolTaskExecutor taskExecutor = getTaskExecutor(taskType);
        int fetchSize = taskExecutor.getMaxPoolSize() - taskExecutor.getActiveCount();

        String env = taskConfig.getEnv();

        // TODO add log

        return asyncTaskRepository.queryToDoListByTypeAndEnv(taskType, env, fetchSize);
    }

    /**
     * @param asyncTask
     * @param status
     */
    private void saveAsyncTask(AsyncTask asyncTask, AsyncTaskStatusEnum status) {

    }

    /**
     * @param taskList
     * @param status
     */
    private void updateTaskStatus(List<AsyncTask> taskList, AsyncTaskStatusEnum status) {

    }

    /**
     * Setter method for property <tt>asyncTaskRepository</tt>.
     *
     * @param asyncTaskRepository value to be assigned to property asyncTaskRepository
     */
    public void setAsyncTaskRepository(AsyncTaskRepository asyncTaskRepository) {
        this.asyncTaskRepository = asyncTaskRepository;
    }

}
