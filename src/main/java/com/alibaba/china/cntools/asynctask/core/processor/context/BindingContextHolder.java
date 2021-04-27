/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */
package com.alibaba.china.cntools.asynctask.core.processor.context;

/**
 * 持有线程上下文的容器
 *
 * @author zhengpc
 * @version $Id: BindingContextHolder.java, v 0.1 2018/6/14 下午7:33 zhengpc Exp $
 * @date 2018/06/14
 */
public final class BindingContextHolder {

    /**
     *
     */
    private static final ThreadLocal<BindingContext[]> threadLocal = new ThreadLocal<BindingContext[]>();

    /**
     * @param contexts
     */
    public static void set(BindingContext... contexts) {
        if (contexts != null) {
            threadLocal.set(contexts);
        }
    }

    /**
     * @return
     */
    public static BindingContext[] get() {
        return threadLocal.get();
    }

    /**
     *
     */
    public static void clear() {
        threadLocal.remove();
    }

}
