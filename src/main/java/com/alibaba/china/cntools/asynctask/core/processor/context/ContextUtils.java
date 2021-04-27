/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.core.processor.context;

import java.util.concurrent.Callable;

/**
 * 上下文绑定的工具类，简化调用、方便使用；
 *
 * @author zhengpc
 * @version $Id: ContextUtils.java, v 0.1 2017/12/26 下午8:10 zhengpc Exp $
 * @date 2017/12/26
 */
public final class ContextUtils {

    /**
     * 绑定线程上下文
     *
     * @param callable
     * @param contexts
     * @param <T>
     * @return
     */
    public static <T> Callable<T> bindCtx(final Callable<T> callable, final BindingContext... contexts) {
        if (callable == null || contexts == null) {
            return callable;
        }

        return new BindingContextSupport(callable, contexts);
    }

}
