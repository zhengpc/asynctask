/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.model;

/**
 * 任务状态枚举类
 *
 * @author zhengpc
 * @version $Id: AsyncTaskStatusEnum.java, v 0.1 2018/6/13 下午1:47 zhengpc Exp $
 * @date 2018/06/13
 */
public enum AsyncTaskStatusEnum {

    INIT("INIT", "初始化"),

    PROCESSING("PROCESSING", "处理中"),

    SUCCESS("SUCCESS", "成功"),

    FAILURE("FAILURE", "失败"),

    //
    ;

    /**
     * @param code
     * @param desc
     */
    AsyncTaskStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     *
     */
    private String code;

    /**
     *
     */
    private String desc;

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
