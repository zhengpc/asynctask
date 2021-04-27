/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zhengpc
 * @version $Id: ReflectUtils.java, v 0.1 2018/4/15 下午9:13 zhengpc Exp $
 * @date 2018/04/15
 */
public class ReflectUtils {

    /**
     * @param object
     * @return
     */
    public static Type[] getParameterizedTypes(Object object) {
        if (object == null) {
            return null;
        }

        Type classType = object.getClass().getGenericSuperclass();

        if (!ParameterizedType.class.isAssignableFrom(classType.getClass())) {
            return null;
        }

        return ((ParameterizedType)classType).getActualTypeArguments();
    }

    /**
     * @param object
     * @param index
     * @return
     */
    public static <T> Class<T> getParameterizedType(Object object, int index) {
        Type[] parameterizedTypes = getParameterizedTypes(object);

        if (parameterizedTypes == null || parameterizedTypes.length < index + 1) {
            return null;
        }

        return (Class<T>)parameterizedTypes[index];
    }

}
