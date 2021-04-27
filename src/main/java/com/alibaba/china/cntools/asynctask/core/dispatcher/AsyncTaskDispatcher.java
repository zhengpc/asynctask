/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.dispatcher;

import java.util.List;
import java.util.Map;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskConfig;
import com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskProcessor;
import com.alibaba.china.cntools.asynctask.facade.AsyncTaskBSFacade;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskConfig;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskProcessor;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.getTaskTypeList;
import static com.alibaba.china.cntools.asynctask.core.processor.AsyncTaskRegistry.isPersistent;

/**
 * 异步命令分发
 *
 * @author zhengpc
 * @version $Id: AsyncTaskDispatcher.java, v 0.1 2017/8/10 上午9:19 zhengpc Exp $
 * @date 2017/08/10
 */
public class AsyncTaskDispatcher {

    /**
     *
     */
    private static AsyncTaskBSFacade asyncTaskBSFacade;

    /**
     * 一层分发，基于分库分表规则进行拆分
     *
     * @return
     */
    public static List<String> level_1() {
        return null;
    }

    /**
     * 二层分发，基于异步任务的类型进行二次拆分
     *
     * @return
     */
    public static List<String> level_2(String businessKey) {
        List<String> stringList = Lists.newArrayList();
        for (String taskType : getTaskTypeList()) {
            // 过滤掉非持久化的任务
            if (!isPersistent(taskType)) {
                continue;
            }

            Map<String, String> map = Maps.newHashMap();
            map.put("taskType", taskType);
            // TODO add db&table info

            stringList.add(Joiner.on("&").withKeyValueSeparator("=").useForNull("").join(map));
        }
        return stringList;
    }

    /**
     * 三层分发，执行拆分后的子任务
     *
     * @param businessKey
     * @return
     */
    public static void level_3(String businessKey) {
        Map<String, String> attrs = Splitter.on("&").withKeyValueSeparator("=").split(businessKey);

        String dbIndex = attrs.get("");
        String logicTable = attrs.get("");
        String table = "";
        String taskType = "";

        AsyncTaskProcessor taskProcessor = getTaskProcessor(taskType);
        if (taskProcessor == null) {
            // TODO add log
            return;
        }

        AsyncTaskConfig taskConfig = getTaskConfig(taskType);
        if (taskConfig == null || !taskConfig.isPersistent()) {
            // TODO add log
            return;
        }

        if (StringUtils.isAnyBlank(dbIndex, logicTable, table, taskType)) {
            // TODO add log
            return;
        }

        asyncTaskBSFacade.fetchTaskToProcess(dbIndex, logicTable, table, taskType);
    }

    /**
     * Setter method for property <tt>asyncTaskBSFacade</tt>.
     *
     * @param asyncTaskBSFacade value to be assigned to property asyncTaskBSFacade
     */
    public void setAsyncTaskBSFacade(AsyncTaskBSFacade asyncTaskBSFacade) {
        AsyncTaskDispatcher.asyncTaskBSFacade = asyncTaskBSFacade;
    }

}
