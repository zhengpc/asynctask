/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.repository.dao;

import java.util.List;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTaskDO;

/**
 * @author zhengpc
 * @version $Id: AsyncTaskDAOImpl.java, v 0.1 2018/4/13 下午5:43 zhengpc Exp $
 * @date 2018/04/13
 */
public class AsyncTaskDAOImpl implements AsyncTaskDAO {

    @Override
    public void save(AsyncTaskDO asyncTaskDO) {

    }

    @Override
    public void update(AsyncTaskDO asyncTaskDO) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public AsyncTaskDO queryById(String id) {
        return null;
    }

    @Override
    public List<AsyncTaskDO> queryToDoListByTypeAndEnv(String taskType, String env, Integer limit) {
        return null;
    }

}
