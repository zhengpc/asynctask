/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */
package com.alibaba.china.cntools.asynctask.core.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import com.alibaba.dts.client.executor.job.processor.SimpleJobProcessor;
import com.alibaba.dts.client.executor.simple.processor.SimpleJobContext;
import com.alibaba.dts.common.domain.result.ProcessResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskExecutor;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskTypeList;

/**
 * 线程池监控调度器
 *
 * @author zhengpc
 * @version $Id: ThreadPoolMonitorScheduler.java, v 0.1 2018/6/18 下午8:10 zhengpc Exp $
 * @date 2018/06/18
 */
public class ThreadPoolMonitorScheduler implements SimpleJobProcessor {

    /**
     * 任务监控日志
     */
    private static final Logger TASK_MONITOR_LOG = LoggerFactory.getLogger("ASYNC_TASK_MONITOR");

    /**
     * @param taskType
     */
    private static void monitor(String taskType) {
        ThreadPoolTaskExecutor taskExecutor = getTaskExecutor(taskType);
        if (taskExecutor == null) {
            return;
        }

        ThreadPoolExecutor threadPool = taskExecutor.getThreadPoolExecutor();
        BlockingQueue blockingQueue = threadPool.getQueue();

        StringBuilder sb = new StringBuilder();

        sb.append("task_type=").append(taskType).append(",");
        sb.append("active_threads=").append(threadPool.getActiveCount()).append(",");
        sb.append("pool_size=").append(threadPool.getPoolSize()).append(",");
        sb.append("maximum_pool_size=").append(threadPool.getMaximumPoolSize()).append(",");
        sb.append("queue_capacity=").append(blockingQueue.size()).append(",");
        sb.append("queue_remaining_capacity=").append(blockingQueue.remainingCapacity());

        TASK_MONITOR_LOG.info(sb.toString());
    }

    @Override
    public ProcessResult process(SimpleJobContext context) throws InterruptedException {
        for (String taskType : getTaskTypeList()) {
            monitor(taskType);
        }

        return new ProcessResult(true);
    }
}
