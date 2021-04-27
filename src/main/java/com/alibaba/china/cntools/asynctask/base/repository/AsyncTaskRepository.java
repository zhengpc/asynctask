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

/**
 * @author zhengpc
 * @version $Id: AsyncTaskRepository.java, v 0.1 2018/4/13 下午3:59 zhengpc Exp $
 * @date 2018/04/13
 */
public interface AsyncTaskRepository {

    /**
     * @param taskType
     * @param env
     * @param limit
     * @return
     */
    List<AsyncTask> queryToDoListByTypeAndEnv(String taskType, String env, Integer limit);

}
