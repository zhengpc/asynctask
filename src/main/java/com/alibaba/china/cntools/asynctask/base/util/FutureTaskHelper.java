/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */
package com.alibaba.china.cntools.asynctask.base.util;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhengpc
 * @version $Id: FutureTaskHelper.java, v 0.1 2018/7/26 上午10:25 zhengpc Exp $
 * @date 2018/07/26
 */
public class FutureTaskHelper {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger("ASYNC_TASK");

    /**
     * 获取任务执行结果，等待所有任务执行结束后返回所有任务的执行结果
     *
     * @param futureTaskList
     * @param <R>
     * @return
     */
    public static <R> List<R> getDoneAsList(List<ListenableFutureTask<R>> futureTaskList) {
        return getDoneAsList(futureTaskList, null);
    }

    /**
     * 获取任务执行结果，如果超时，则仅返回已成功的执行结果
     *
     * @param futureTaskList
     * @param timeOutInMillis
     * @param <R>
     * @return
     */
    public static <R> List<R> getDoneAsList(List<ListenableFutureTask<R>> futureTaskList, Integer timeOutInMillis) {
        if (futureTaskList == null || futureTaskList.isEmpty()) {
            return null;
        }

        final List<R> resultAsList = Lists.newArrayList();

        // 遍历所有futureTask，增加回调
        futureTaskList.stream().filter(Objects::nonNull).forEach(task -> {
            Futures.addCallback(task, new FutureCallback<R>() {
                @Override
                public void onSuccess(R result) {
                    resultAsList.add(result);
                }

                @Override
                public void onFailure(Throwable t) {
                }
            });
        });

        // 调Futures的静态方法，返回一个ListenableFuture ，该Future的结果包含所有成功的Future
        ListenableFuture<List<R>> successfulAsList = Futures.successfulAsList(futureTaskList);

        // 等待任务处理结果，如果超时，则取消任务的执行
        try {
            if (timeOutInMillis == null || timeOutInMillis <= 0) {
                return successfulAsList.get();
            } else {
                return successfulAsList.get(timeOutInMillis, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            logger.error("error while waiting for callable", e);

            // 超时，任务取消
            successfulAsList.cancel(true);
        }

        return resultAsList;
    }
}
