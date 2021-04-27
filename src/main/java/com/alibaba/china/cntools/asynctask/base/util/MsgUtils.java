/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息工具类
 *
 * @author zhengpc
 * @version $Id: MsgUtils.java, v 0.1 2017/8/29 下午2:36 zhengpc Exp $
 * @date 2017/08/29
 */
public class MsgUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MsgUtils.class);

    /**
     * 格式化含占位符的字符串
     * <p>
     * 举例:
     *
     * <pre>
     * <ol><li>
     * 所有占位符参数都存在：
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:&quot;yimin.jiang&quot;, &quot;female&quot;
     *  return:My name is yimin.jiang,my sex is male
     * </li>
     *  <li>
     * 占位符参数数组不为null(含数组所有元素为null的情况，占位符都会被null取代):
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:null, &quot;female&quot;
     *  return:My name is null,my sex is male
     *
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:null, null
     *  return:My name is null,my sex is null
     * </li>
     * <li>
     * 占位符参数数组为null，占位符原样输出:
     *  msgTemplate: My name is {0},my sex is {1}.
     *  positionValues:null
     *  return:My name is {0},my sex is {1}
     * </li>
     *  </ol>
     * </pre>
     *
     * @param msgTemplate    含占位符的字符串消息模板
     * @param positionValues 消息占位符取代变量，按参数顺序依次取代{0},{1},{2}...等
     * @return 用positionValues替换占位符后的字符串
     * @see MessageFormat#format(String, Object...)
     */
    public static String formatMsg(String msgTemplate, Object... positionValues) {
        try {
            if (positionValues == null || positionValues.length == 0) {
                return msgTemplate;
            }

            return MessageFormat.format(msgTemplate, positionValues);
        } catch (Exception e) {
            StringBuilder buf = new StringBuilder("资源信息占位符替换异常，占位符参数信息：");
            for (int i = 0; i < positionValues.length; i++) {
                buf.append(" arg[" + i + "]=" + positionValues[i]);
            }

            LOGGER.warn(buf.toString(), e);
        }

        return msgTemplate;
    }

    /**
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }

        return ReflectionToStringBuilder.toString(object, new RecursiveToStringStyle() {
            {
                this.setUseShortClassName(true);
                this.setUseIdentityHashCode(false);
            }
        });
    }

}
