/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.processor;

import com.alibaba.china.cntools.asynctask.base.model.AsyncTask;

import com.google.common.util.concurrent.ListenableFutureTask;
import org.springframework.beans.factory.InitializingBean;

/**
 * 异步任务执行器
 *
 * @author zhengpc
 * @version $Id: AsyncTaskProcessor.java, v 0.1 2017/8/9 下午9:59 zhengpc Exp $
 * @date 2017/08/09
 */
public interface AsyncTaskProcessor<T extends AsyncTask, R> extends InitializingBean {

    /**
     * @param asyncTask
     */
    R process(T asyncTask) throws Exception;

    /**
     * @param asyncTask
     * @return
     */
    boolean submit(T asyncTask);

    /**
     * @param asyncTask
     * @return
     */
    ListenableFutureTask<R> submitListenable(T asyncTask);

}
