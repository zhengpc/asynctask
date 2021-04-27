/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.repository;

import java.util.List;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;
import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskDO;
import com.alibaba.china.cntools.asynctask.base.repository.dao.AsyncTaskDAO;

import com.google.common.collect.Lists;

/**
 * @author zhengpc
 * @version $Id: AsyncTaskRepositoryImpl.java, v 0.1 2018/4/13 下午6:54 zhengpc Exp $
 * @date 2018/04/13
 */
public class AsyncTaskRepositoryImpl implements AsyncTaskRepository {

    /**
     *
     */
    private AsyncTaskDAO asyncTaskDAO;

    @Override
    public List<AsyncTask> queryToDoListByTypeAndEnv(String taskType, String env, Integer limit) {
        List<AsyncTaskDO> asyncTaskDOList = asyncTaskDAO.queryToDoListByTypeAndEnv(taskType, env, limit);

        List<AsyncTask> asyncTaskList = Lists.newArrayList();
        if (asyncTaskDOList == null || asyncTaskDOList.isEmpty()) {
            return asyncTaskList;
        }

        for (AsyncTaskDO asyncTaskDO : asyncTaskDOList) {
            if (asyncTaskDO == null) {
                continue;
            }

            // TODO add data convert
        }

        return asyncTaskList;
    }

    /**
     * Setter method for property <tt>asyncTaskDAO</tt>.
     *
     * @param asyncTaskDAO value to be assigned to property asyncTaskDAO
     */
    public void setAsyncTaskDAO(AsyncTaskDAO asyncTaskDAO) {
        this.asyncTaskDAO = asyncTaskDAO;
    }

}
