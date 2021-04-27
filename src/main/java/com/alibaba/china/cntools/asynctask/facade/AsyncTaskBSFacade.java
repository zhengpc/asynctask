/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */
package com.alibaba.china.cntools.asynctask.facade;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;

/**
 * @author zhengpc
 * @version $Id: AsyncTaskBSFacade.java, v 0.1 2018/7/31 下午5:33 zhengpc Exp $
 * @date 2018/07/31
 */
public interface AsyncTaskBSFacade {

    /**
     * 提交异步任务
     *
     * @param asyncTask
     * @param <T>
     */
    <T extends AsyncTask> void saveAsyncTask(T asyncTask);

    /**
     * 捞取待执行的任务并执行
     *
     * @param dbIndex
     * @param logicTable
     * @param table
     * @param taskType
     */
    void fetchTaskToProcess(String dbIndex, String logicTable, String table, String taskType);

}
