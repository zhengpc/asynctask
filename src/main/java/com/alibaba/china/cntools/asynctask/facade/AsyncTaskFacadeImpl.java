/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.facade;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;
import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskConfig;
import com.alibaba.china.cntools.asynctask.base.util.AsyncTaskTypeUtils;
import com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskProcessor;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.alibaba.china.cntools.asynctask.base.util.AsyncTaskTypeUtils.getAsyncTaskType;
import static com.alibaba.china.cntools.asynctask.base.util.FutureTaskHelper.getDoneAsList;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskConfig;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskProcessor;

/**
 * 异步任务门面服务
 *
 * @author zhengpc
 * @version $Id: AsyncTaskFacadeImpl.java, v 0.1 2018/4/13 下午3:07 zhengpc Exp $
 * @date 2018/04/13
 */
public class AsyncTaskFacadeImpl implements AsyncTaskFacade {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger("ASYNC_TASK");

    @Override
    public <T extends AsyncTask, R> Future<R> submit(T asyncTask) {
        // 获取任务类型
        String taskType = getAsyncTaskType(asyncTask);

        // 根据任务类型获取任务处理器
        AsyncTaskProcessor<T, R> taskProcessor = getTaskProcessor(taskType);
        if (taskProcessor == null) {
            // TODO add log
            return null;
        }

        return taskProcessor.submitListenable(asyncTask);
    }

    @Override
    public <T extends AsyncTask, R> R process(T asyncTask) throws Exception {
        // 获取任务类型
        String taskType = getAsyncTaskType(asyncTask);

        // 根据任务类型获取任务处理器
        AsyncTaskProcessor<T, R> taskProcessor = getTaskProcessor(taskType);
        if (taskProcessor == null) {
            // TODO add log
            return null;
        }

        return taskProcessor.process(asyncTask);
    }

    @Override
    public <T extends AsyncTask, R> List<R> batchProcess(List<T> taskList) {
        if (taskList == null || taskList.isEmpty()) {
            logger.error("illegal arguments,taskList={0}", taskList);
            return null;
        }

        // 任务配置集合
        List<AsyncTaskConfig> taskConfigList = Lists.newArrayList();

        // 任务提交后返回的FutureTask集合
        List<ListenableFutureTask<R>> futureTaskList = Lists.newArrayList();

        // 对任务按类型分组，并遍历分组，提交执行
        taskList.stream().filter(Objects::nonNull).filter(task -> getAsyncTaskType(task) != null)
            .collect(Collectors.groupingBy(AsyncTaskTypeUtils::getAsyncTaskType, Collectors.toList()))
            .forEach(
                (taskType, subTaskList) -> {
                    // 根据任务类型获取任务处理器
                    AsyncTaskProcessor<T, R> taskProcessor = getTaskProcessor(taskType);
                    if (taskProcessor == null) {
                        logger.error("get processor by taskType={0} failed", taskType);
                        return;
                    }

                    // 根据任务类型获取任务配置
                    AsyncTaskConfig taskConfig = getTaskConfig(taskType);
                    if (taskConfig != null) {
                        taskConfigList.add(taskConfig);
                    }

                    // 遍历分组内的任务，提交执行
                    subTaskList.stream().filter(Objects::nonNull).forEach(task -> {
                            // 提交任务执行，并取得futureTask
                            ListenableFutureTask<R> futureTask = taskProcessor.submitListenable(task);
                            if (futureTask != null) {
                                futureTaskList.add(futureTask);
                            }
                        }
                    );
                });

        // 获取配置的超时等待时间，多个任务类型的情况下取最大值
        Integer timeOutInMillis = null;
        if (!taskConfigList.stream().filter(config -> config.getTimeOutInMillis() == null).findAny().isPresent()) {
            timeOutInMillis = taskConfigList.stream().filter(Objects::nonNull)
                .mapToInt(AsyncTaskConfig::getTimeOutInMillis)
                .max().orElse(0);
        }

        // 获取任务执行结果，如果超时，则仅返回已成功的执行结果
        return getDoneAsList(futureTaskList, timeOutInMillis);
    }

}
