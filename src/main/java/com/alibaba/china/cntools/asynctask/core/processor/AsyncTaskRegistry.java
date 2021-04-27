/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.processor;

import java.util.List;
import java.util.Map;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskConfig;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 任务注册表
 *
 * @author zhengpc
 * @version $Id: AsyncTaskRegistry.java, v 0.1 2017/8/9 下午10:16 zhengpc Exp $
 * @date 2017/08/09
 */
public class AsyncTaskRegistry {

    /**
     * 任务处理器的集合
     */
    private static final Map<String, AsyncTaskProcessor> ASYNC_TASK_PROCESSOR_MAP = Maps.newConcurrentMap();

    /**
     * 任务配置的集合
     */
    private static final Map<String, AsyncTaskConfig> ASYNC_TASK_CONFIG_MAP = Maps.newConcurrentMap();

    /**
     * 任务处理线程池的集合
     */
    private static final Map<String, ThreadPoolTaskExecutor> ASYNC_TASK_EXECUTOR_MAP = Maps.newConcurrentMap();

    /**
     * 注册任务处理器
     *
     * @param taskType
     * @param processor
     */
    public static void registerTaskProcessor(String taskType, AsyncTaskProcessor processor) {
        if (StringUtils.isBlank(taskType) || processor == null) {
            throw new IllegalArgumentException();
        }

        ASYNC_TASK_PROCESSOR_MAP.put(taskType, processor);
    }

    /**
     * 注册任务的配置
     *
     * @param taskType
     * @param taskConfig
     */
    public static void registerTaskConfig(String taskType, AsyncTaskConfig taskConfig) {
        if (StringUtils.isBlank(taskType) || taskConfig == null) {
            throw new IllegalArgumentException();
        }

        ASYNC_TASK_CONFIG_MAP.put(taskType, taskConfig);
    }

    /**
     * 注册任务的执行器
     *
     * @param taskType
     * @param taskExecutor
     */
    public static void registerTaskExecutor(String taskType, ThreadPoolTaskExecutor taskExecutor) {
        if (StringUtils.isBlank(taskType) || taskExecutor == null) {
            throw new IllegalArgumentException();
        }

        ASYNC_TASK_EXECUTOR_MAP.put(taskType, taskExecutor);
    }

    /**
     * 根据任务类型获取任务处理器
     *
     * @param taskType
     * @return
     */
    public static AsyncTaskProcessor getTaskProcessor(String taskType) {
        if (StringUtils.isBlank(taskType)) {
            return null;
        }

        return ASYNC_TASK_PROCESSOR_MAP.get(taskType);
    }

    /**
     * 根据任务类型获取任务配置
     *
     * @param taskType
     * @return
     */
    public static AsyncTaskConfig getTaskConfig(String taskType) {
        if (StringUtils.isBlank(taskType)) {
            return null;
        }

        return ASYNC_TASK_CONFIG_MAP.get(taskType);
    }

    /**
     * 根据任务类型获取对应的处理线程池
     *
     * @param taskType
     * @return
     */
    public static ThreadPoolTaskExecutor getTaskExecutor(String taskType) {
        if (StringUtils.isBlank(taskType)) {
            return null;
        }

        return ASYNC_TASK_EXECUTOR_MAP.get(taskType);
    }

    /**
     * 获取任务列表
     *
     * @return
     */
    public static List<String> getTaskTypeList() {
        return Lists.newArrayList(ASYNC_TASK_PROCESSOR_MAP.keySet());
    }

    /**
     * 根据任务类型判断该任务是否是持久化的任务
     *
     * @param taskType
     * @return
     */
    public static boolean isPersistent(String taskType) {
        AsyncTaskConfig taskConfig = getTaskConfig(taskType);
        return taskConfig != null && taskConfig.isPersistent();
    }

}
