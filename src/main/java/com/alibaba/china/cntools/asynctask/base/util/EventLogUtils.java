/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */
package com.alibaba.china.cntools.asynctask.base.util;

import com.alibaba.china.cntools.asynctask.base.model.EventCodeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhengpc
 * @version $Id: EventLogUtils.java, v 0.1 2018/6/19 上午10:43 zhengpc Exp $
 * @date 2018/06/18
 */
public class EventLogUtils {

    /**
     * 日志
     */
    private static final Logger TASK_INFO_LOG = LoggerFactory.getLogger("ASYNC_TASK");

    /**
     * 任务事件日志
     */
    private static final Logger TASK_EVENT_LOG = LoggerFactory.getLogger("ASYNC_TASK_EVENT");

    /**
     * 任务异常日志
     */
    private static final Logger TASK_ERROR_LOG = LoggerFactory.getLogger("ASYNC_TASK_ERROR");

    /**
     * @param taskType
     * @param startTime
     * @param traceId
     */
    public static void logAccepted(String taskType, long startTime, String traceId) {
        logEvent(taskType, true, EventCodeEnum.ACCEPTED, startTime, traceId);
    }

    /**
     * @param taskType
     * @param startTime
     * @param traceId
     */
    public static void logRejected(String taskType, long startTime, String traceId) {
        logEvent(taskType, true, EventCodeEnum.REJECTED, startTime, traceId);
    }

    /**
     * @param taskType
     * @param async
     * @param startTime
     * @param traceId
     */
    public static void logSuccess(String taskType, boolean async, long startTime, String traceId) {
        logEvent(taskType, async, EventCodeEnum.SUCCESS, startTime, traceId);
    }

    /**
     * @param taskType
     * @param async
     * @param startTime
     * @param traceId
     */
    public static void logFailure(String taskType, boolean async, long startTime, String traceId) {
        logEvent(taskType, async, EventCodeEnum.FAILURE, startTime, traceId);
    }

    /**
     * @param taskType
     * @param startTime
     * @param traceId
     */
    public static void logUnknown(String taskType, long startTime, String traceId) {
        logEvent(taskType, true, EventCodeEnum.UNKNOWN, startTime, traceId);
    }

    /**
     * @param taskType
     * @param eventCode
     * @param startTime
     * @param traceId
     */
    private static void logEvent(String taskType, boolean async, EventCodeEnum eventCode, long startTime,
                                 String traceId) {
        // 执行耗时
        long spendTime = System.currentTimeMillis() - startTime;

        StringBuilder sb = new StringBuilder();
        sb.append(taskType).append(",");
        sb.append(eventCode).append(",");
        sb.append(spendTime).append("ms").append(",");
        sb.append(async ? "ASYNC" : "SYNC").append(",");
        sb.append(traceId);

        TASK_EVENT_LOG.info(sb.toString());
    }

    /**
     * @param taskType
     * @param t
     * @param traceId
     */
    public static void logError(String taskType, Throwable t, String traceId) {
        if (t == null) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(taskType).append(",");
        sb.append(t.getClass().getSimpleName()).append(",");
        sb.append(traceId);
        String errorMsg = sb.toString();

        TASK_ERROR_LOG.info(errorMsg);
        TASK_INFO_LOG.error(errorMsg, t);
    }

}

