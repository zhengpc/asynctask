/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.scheduler;

import com.alibaba.china.cntools.asynctask.core.dispatcher.AsyncTaskDispatcher;
import com.alibaba.dts.client.executor.grid.processor.GridJobContext;
import com.alibaba.dts.client.executor.job.processor.GridJobProcessor;
import com.alibaba.dts.common.constants.Constants;
import com.alibaba.dts.common.domain.result.ProcessResult;

/**
 * 异步任务调度器
 *
 * @author zhengpc
 * @version $Id: AsyncTaskScheduler.java, v 0.1 2017/8/9 下午10:11 zhengpc Exp $
 * @date 2017/08/09
 */
public class AsyncTaskScheduler implements GridJobProcessor {

    /**
     * 常量定义
     */
    private static final String LEVEL_2 = "level_2", LEVEL_3 = "level_3";

    @Override
    public ProcessResult process(GridJobContext jobContext) throws Exception {
        String taskName = jobContext.getTaskName();
        if (Constants.DEFAULT_ROOT_LEVEL_TASK_NAME.equals(taskName)) {
            jobContext.dispatchTaskList(AsyncTaskDispatcher.level_1(), LEVEL_2);

            return new ProcessResult(true);
        } else if (LEVEL_2.equals(taskName)) {
            String businessKey = (String)jobContext.getTask();
            jobContext.dispatchTaskList(AsyncTaskDispatcher.level_2(businessKey), LEVEL_3);

            return new ProcessResult(true);
        } else if (LEVEL_3.equals(taskName)) {
            String businessKey = (String)jobContext.getTask();
            AsyncTaskDispatcher.level_3(businessKey);

            return new ProcessResult(true);
        }

        return new ProcessResult(true);
    }

}
