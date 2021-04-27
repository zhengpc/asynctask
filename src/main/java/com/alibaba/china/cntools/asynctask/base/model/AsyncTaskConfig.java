/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.model;

/**
 * 异步任务配置模型
 *
 * @author zhengpc
 * @version $Id: AsyncTaskConfig.java, v 0.1 2018/4/12 下午9:50 zhengpc Exp $
 * @date 2018/04/12
 */
public class AsyncTaskConfig {

    /**
     * 是否持久化，默认：false
     */
    private boolean persistent = false;

    /**
     * 环境标识，持久化任务有效
     */
    private String env;

    /**
     * 重试间隔，持久化任务有效
     */
    private String retryInterval;

    /**
     * 最多可重试次数，持久化任务有效
     */
    private Integer retryLimit;

    /**
     * 任务超时时间，单位：毫秒
     */
    private Integer timeOutInMillis;

    /**
     * Getter method for property <tt>persistent</tt>.
     *
     * @return property value of persistent
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * Setter method for property <tt>persistent</tt>.
     *
     * @param persistent value to be assigned to property persistent
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * Getter method for property <tt>env</tt>.
     *
     * @return property value of env
     */
    public String getEnv() {
        return env;
    }

    /**
     * Setter method for property <tt>env</tt>.
     *
     * @param env value to be assigned to property env
     */
    public void setEnv(String env) {
        this.env = env;
    }

    /**
     * Getter method for property <tt>retryInterval</tt>.
     *
     * @return property value of retryInterval
     */
    public String getRetryInterval() {
        return retryInterval;
    }

    /**
     * Setter method for property <tt>retryInterval</tt>.
     *
     * @param retryInterval value to be assigned to property retryInterval
     */
    public void setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
    }

    /**
     * Getter method for property <tt>retryLimit</tt>.
     *
     * @return property value of retryLimit
     */
    public Integer getRetryLimit() {
        return retryLimit;
    }

    /**
     * Setter method for property <tt>retryLimit</tt>.
     *
     * @param retryLimit value to be assigned to property retryLimit
     */
    public void setRetryLimit(Integer retryLimit) {
        this.retryLimit = retryLimit;
    }

    /**
     * Getter method for property <tt>timeOutInMillis</tt>.
     *
     * @return property value of timeOutInMillis
     */
    public Integer getTimeOutInMillis() {
        return timeOutInMillis;
    }

    /**
     * Setter method for property <tt>timeOutInMillis</tt>.
     *
     * @param timeOutInMillis value to be assigned to property timeOutInMillis
     */
    public void setTimeOutInMillis(Integer timeOutInMillis) {
        this.timeOutInMillis = timeOutInMillis;
    }

}
