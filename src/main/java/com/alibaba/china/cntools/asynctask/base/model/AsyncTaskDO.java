/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.alibaba.china.cntools.asynctask.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhengpc
 * @version $Id: AsyncTaskDO.java, v 0.1 2018/4/13 下午3:59 zhengpc Exp $
 * @date 2018/04/13
 */
public class AsyncTaskDO implements Serializable {

    private static final long serialVersionUID = -6917364286730208766L;

    /**
     * ID
     */
    private String id;

    /**
     * 任务类型
     */
    private String taskType;

    /**
     * 外部业务数据编号
     */
    private String businessNo;

    /**
     * 全局唯一的traceId
     */
    private String traceId;

    /**
     * 状态
     **/
    private String status;

    /**
     * 重试次数
     */
    private int executeTimes = 0;

    /**
     * 下次执行时间
     */
    private Date nextExecuteTime;

    /**
     * 执行上下文
     */
    private String context;

    /**
     * 生产者主机名
     */
    private String host;

    /**
     * 生产者所处环境
     */
    private String env;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>taskType</tt>.
     *
     * @return property value of taskType
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Setter method for property <tt>taskType</tt>.
     *
     * @param taskType value to be assigned to property taskType
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Getter method for property <tt>traceId</tt>.
     *
     * @return property value of traceId
     */
    public String getTraceId() {
        return traceId;
    }

    /**
     * Setter method for property <tt>traceId</tt>.
     *
     * @param traceId value to be assigned to property traceId
     */
    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>executeTimes</tt>.
     *
     * @return property value of executeTimes
     */
    public int getExecuteTimes() {
        return executeTimes;
    }

    /**
     * Setter method for property <tt>executeTimes</tt>.
     *
     * @param executeTimes value to be assigned to property executeTimes
     */
    public void setExecuteTimes(int executeTimes) {
        this.executeTimes = executeTimes;
    }

    /**
     * Getter method for property <tt>nextExecuteTime</tt>.
     *
     * @return property value of nextExecuteTime
     */
    public Date getNextExecuteTime() {
        return nextExecuteTime;
    }

    /**
     * Setter method for property <tt>nextExecuteTime</tt>.
     *
     * @param nextExecuteTime value to be assigned to property nextExecuteTime
     */
    public void setNextExecuteTime(Date nextExecuteTime) {
        this.nextExecuteTime = nextExecuteTime;
    }

    /**
     * Getter method for property <tt>businessNo</tt>.
     *
     * @return property value of businessNo
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * Setter method for property <tt>businessNo</tt>.
     *
     * @param businessNo value to be assigned to property businessNo
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    /**
     * Getter method for property <tt>context</tt>.
     *
     * @return property value of context
     */
    public String getContext() {
        return context;
    }

    /**
     * Setter method for property <tt>context</tt>.
     *
     * @param context value to be assigned to property context
     */
    public void setContext(String context) {
        this.context = context;
    }

    /**
     * Getter method for property <tt>host</tt>.
     *
     * @return property value of host
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter method for property <tt>host</tt>.
     *
     * @param host value to be assigned to property host
     */
    public void setHost(String host) {
        this.host = host;
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
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>gmtCreate</tt>.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModify</tt>.
     *
     * @return property value of gmtModify
     */
    public Date getGmtModify() {
        return gmtModify;
    }

    /**
     * Setter method for property <tt>gmtModify</tt>.
     *
     * @param gmtModify value to be assigned to property gmtModify
     */
    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}
