/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.util;

import java.util.Map;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;
import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskType;
import com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskProcessor;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

/**
 * 任务类型相关的工具类
 *
 * @author zhengpc
 * @version $Id: AsyncTaskTypeUtils.java, v 0.1 2018/4/15 下午9:17 zhengpc Exp $
 * @date 2018/04/15
 */
public class AsyncTaskTypeUtils {

    /**
     *
     */
    private static final Map<Class, String> CLASS_ASYNC_TASK_TYPE_MAP = Maps.newConcurrentMap();

    /**
     * 根据任务处理器获取任务类型
     *
     * @param processor
     * @return
     */
    public static String getAsyncTaskType(AsyncTaskProcessor processor) {
        if (processor == null) {
            return null;
        }

        Class processorClassType = processor.getClass();
        String taskType = CLASS_ASYNC_TASK_TYPE_MAP.get(processorClassType);
        if (StringUtils.isBlank(taskType)) {
            synchronized (processorClassType) {
                taskType = CLASS_ASYNC_TASK_TYPE_MAP.get(processorClassType);
                if (StringUtils.isBlank(taskType)) {
                    Class<AsyncTask> taskClassType = ReflectUtils.getParameterizedType(processor, 0);
                    if (taskClassType == null) {
                        return null;
                    }
                    AsyncTaskType asyncTaskType = taskClassType.getAnnotation(AsyncTaskType.class);
                    if (asyncTaskType == null) {
                        return null;
                    }
                    CLASS_ASYNC_TASK_TYPE_MAP.put(processorClassType, taskType = asyncTaskType.value());
                }
            }
        }

        return taskType;
    }

    /**
     * 根据任务实例获取任务类型
     *
     * @param asyncTask
     * @return
     */
    public static <T extends AsyncTask> String getAsyncTaskType(T asyncTask) {
        if (asyncTask == null) {
            return null;
        }

        Class taskClassType = asyncTask.getClass();
        String taskType = CLASS_ASYNC_TASK_TYPE_MAP.get(taskClassType);
        if (StringUtils.isBlank(taskType)) {
            synchronized (taskClassType) {
                taskType = CLASS_ASYNC_TASK_TYPE_MAP.get(taskClassType);
                if (StringUtils.isBlank(taskType)) {
                    AsyncTaskType asyncTaskType = asyncTask.getClass().getAnnotation(AsyncTaskType.class);
                    if (asyncTaskType == null) {
                        return null;
                    }
                    CLASS_ASYNC_TASK_TYPE_MAP.put(taskClassType, taskType = asyncTaskType.value());
                }
            }
        }

        return taskType;
    }

}
