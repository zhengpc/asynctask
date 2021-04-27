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
 * @version $Id: AsyncTaskDAO.java, v 0.1 2018/4/13 下午5:05 zhengpc Exp $
 * @date 2018/04/13
 */
public interface AsyncTaskDAO {

    /**
     * @param asyncTaskDO
     */
    void save(AsyncTaskDO asyncTaskDO);

    /**
     * @param asyncTaskDO
     */
    void update(AsyncTaskDO asyncTaskDO);

    /**
     * @param id
     */
    void deleteById(String id);

    /**
     * @param id
     * @return
     */
    AsyncTaskDO queryById(String id);

    /**
     * @param taskType
     * @param env
     * @param limit
     * @return
     */
    List<AsyncTaskDO> queryToDoListByTypeAndEnv(String taskType, String env, Integer limit);

}
