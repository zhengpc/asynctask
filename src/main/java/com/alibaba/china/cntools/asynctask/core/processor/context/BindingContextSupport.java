/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.processor.context;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Callable;

/**
 * 支持绑定上下文的基础类
 *
 * @author zhengpc
 * @version $Id: BindingContextSupport.java, v 0.1 2017/12/11 下午7:38 zhengpc Exp $
 * @date 2017/12/11
 */
public class BindingContextSupport<T> implements Callable<T> {

    /**
     * 调用者ID，即调用线程的线程ID
     */
    private final long callerId;

    /**
     * 任务回调，用于业务逻辑的处理
     */
    private final Callable<T> callable;

    /**
     * 需要绑定的上下文列表
     */
    private final BindingContext[] contexts;

    /**
     * 构造函数
     *
     * @param callable
     * @param contexts
     */
    public BindingContextSupport(Callable<T> callable, BindingContext... contexts) {
        this.callable = callable;
        this.contexts = contexts;
        callerId = Thread.currentThread().getId();
    }

    /**
     * 执行回调任务，供子类使用
     *
     * @return
     * @throws Exception
     */
    private T execInGivenCtx() throws Exception {
        if (callable == null) {
            return null;
        }

        if (contexts == null || isCallerRun() && !allowCallerRun()) {
            return callable.call();
        }

        return execInGivenCtx(Arrays.asList(contexts).iterator(), callable);
    }

    /**
     * 在给定的上下文中执行回调任务
     *
     * @param givenCtxs
     * @param callable
     * @return
     */
    private T execInGivenCtx(Iterator<BindingContext> givenCtxs, Callable<T> callable) throws Exception {
        // 递归调用
        while (givenCtxs != null && givenCtxs.hasNext()) {
            // 获取上下文
            BindingContext context = givenCtxs.next();
            try {
                // 绑定上下文
                context.bind();
                // 递归调用
                return execInGivenCtx(givenCtxs, callable);
            } finally {
                // 清理上下文
                context.unbind();
            }
        }

        // 执行回调任务
        return callable.call();
    }

    /**
     * 判断是否是调用线程在执行run方法
     *
     * @return
     */
    private boolean isCallerRun() {
        return callerId == Thread.currentThread().getId();
    }

    /**
     * 是否允许调用者执行，默认不支持，主要是针对采用CallerRunsPolicy这种拒绝策略的情况下，避免调用线程上下文丢失
     *
     * @return
     */
    protected boolean allowCallerRun() {
        return false;
    }

    @Override
    public T call() throws Exception {
        return execInGivenCtx();
    }

}