/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.facade;

import java.util.List;
import java.util.concurrent.Future;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;

/**
 * 异步任务门面服务
 *
 * @author zhengpc
 * @version $Id: AsyncTaskFacade.java, v 0.1 2017/8/9 下午10:13 zhengpc Exp $
 * @date 2017/08/09
 */
public interface AsyncTaskFacade {

    /**
     * 提交异步任务
     *
     * @param asyncTask
     * @return
     */
    <T extends AsyncTask, R> Future<R> submit(T asyncTask);

    /**
     * @param asyncTask
     * @param <T>
     * @param <R>
     * @return
     */
    <T extends AsyncTask, R> R process(T asyncTask) throws Exception;

    /**
     * 批量执行异步任务，并返回执行结果
     *
     * @param taskList
     * @param <T>
     * @param <R>
     * @return
     */
    <T extends AsyncTask, R> List<R> batchProcess(List<T> taskList);

}
