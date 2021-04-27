/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.processor;

import java.util.concurrent.Callable;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;
import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskConfig;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.taobao.eagleeye.EagleEye;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import static com.alibaba.china.cntools.asynctask.base.util.AsyncTaskTypeUtils.getAsyncTaskType;
import static com.alibaba.china.cntools.asynctask.base.util.EventLogUtils.logAccepted;
import static com.alibaba.china.cntools.asynctask.base.util.EventLogUtils.logError;
import static com.alibaba.china.cntools.asynctask.base.util.EventLogUtils.logFailure;
import static com.alibaba.china.cntools.asynctask.base.util.EventLogUtils.logRejected;
import static com.alibaba.china.cntools.asynctask.base.util.EventLogUtils.logSuccess;
import static com.alibaba.china.cntools.asynctask.base.util.EventLogUtils.logUnknown;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.registerTaskConfig;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.registerTaskExecutor;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.registerTaskProcessor;
import static com.alibaba.china.cntools.asynctask.core.processor.context.BindingContextHolder.get;
import static com.alibaba.china.cntools.asynctask.core.processor.context.ContextUtils.bindCtx;

/**
 * 异步任务执行器的抽象父类
 *
 * @author zhengpc
 * @version $Id: BaseAsyncTaskProcessor.java, v 0.1 2018/4/12 下午10:01 zhengpc Exp $
 * @date 2018/04/12
 */
public abstract class BaseAsyncTaskProcessor<T extends AsyncTask, R> implements AsyncTaskProcessor<T, R> {

    /**
     *
     */
    protected AsyncTaskConfig taskConfig;

    /**
     *
     */
    protected ThreadPoolTaskExecutor taskExecutor;

    @Override
    public boolean submit(final T asyncTask) {
        return submitListenable(asyncTask) != null;
    }

    @Override
    public ListenableFutureTask<R> submitListenable(final T asyncTask) {
        // 起始时间
        final long startTime = System.currentTimeMillis();
        final String traceId = EagleEye.getTraceId();

        // 任务类型
        final String taskType = getAsyncTaskType(asyncTask);
        try {
            // 如果调用方有设置上下文，则在此绑定线程上下文
            Callable<R> callable = bindCtx(() -> doProcess(asyncTask), get());

            // 创建ListenableFuture
            ListenableFutureTask<R> futureTask = ListenableFutureTask.create(callable);

            // 增加回调
            Futures.addCallback(futureTask, new FutureCallback<R>() {
                @Override
                public void onSuccess(R result) {
                    // 成功回调
                    successCallback(asyncTask, result);
                    // 处理成功
                    logSuccess(taskType, true, startTime, traceId);
                }

                @Override
                public void onFailure(Throwable t) {
                    // 失败回调
                    failureCallback(asyncTask, t);
                    // 处理失败
                    logFailure(taskType, true, startTime, traceId);
                    logError(taskType, t, traceId);
                }
            });

            // 提交任务
            taskExecutor.submit(futureTask);
            logAccepted(taskType, startTime, traceId);

            // 已受理
            return futureTask;
        } catch (TaskRejectedException e) {
            // 已拒绝
            logRejected(taskType, startTime, traceId);
            logError(taskType, e, traceId);
        } catch (Throwable t) {
            // 未知异常
            logUnknown(taskType, startTime, traceId);
            logError(taskType, t, traceId);
        }

        return null;
    }

    @Override
    public R process(T asyncTask) throws Exception {
        // 起始时间
        long startTime = System.currentTimeMillis();
        String traceId = EagleEye.getTraceId();

        // 任务类型
        String taskType = getAsyncTaskType(asyncTask);
        try {
            R result = doProcess(asyncTask);
            // 处理成功
            logSuccess(taskType, false, startTime, traceId);
            return result;
        } catch (Exception e) {
            logFailure(taskType, false, startTime, traceId);
            logError(taskType, e, traceId);
            throw e;
        }
    }

    /**
     * @param asyncTask
     * @return
     * @throws Exception
     */
    protected abstract R doProcess(T asyncTask) throws Exception;

    /**
     * @param asyncTask
     * @param result
     */
    protected void successCallback(T asyncTask, R result) {
        // 根据任务类型判断是否是持久化的任务
        final boolean isPersistent = taskConfig != null && taskConfig.isPersistent();
        if (isPersistent) {
            // TODO update status success
        }
    }

    /**
     * @param asyncTask
     * @param t
     */
    protected void failureCallback(T asyncTask, Throwable t) {
        // 根据任务类型判断是否是持久化的任务
        final boolean isPersistent = taskConfig != null && taskConfig.isPersistent();
        if (isPersistent) {
            // TODO update status init
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        register();
    }

    /**
     * 注册任务执行器
     *
     * @throws Exception
     */
    protected void register() throws Exception {
        String taskType = getAsyncTaskType(this);

        if (StringUtils.isBlank(taskType)) {
            throw new IllegalArgumentException("get AsyncTaskType failed from " + this.getClass());
        }

        // 注册任务处理器
        registerTaskProcessor(taskType, this);
        // 注册任务的配置
        registerTaskConfig(taskType, taskConfig);
        // 注册任务执行器
        registerTaskExecutor(taskType, taskExecutor);
    }

    /**
     * Setter method for property <tt>taskConfig</tt>.
     *
     * @param taskConfig value to be assigned to property taskConfig
     */
    public void setTaskConfig(AsyncTaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }

    /**
     * Setter method for property <tt>taskExecutor</tt>.
     *
     * @param taskExecutor value to be assigned to property taskExecutor
     */
    public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

}
