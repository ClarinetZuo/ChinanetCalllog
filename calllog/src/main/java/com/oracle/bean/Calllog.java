package com.oracle.bean;

import java.util.Date;

/**
 * @author Clarinet
 * @since JDK8
 */
public class Calllog {
    private String callPhoneNumber;
    private String callName;
    private String calledPhoneNumber;
    private String calledName;
    private String callDate;
    private String duration;
    public Calllog(){

    }
    /**
     *@author Clarinet
     *@create 2018/8/15 17:09
     * 设置所有的属性,封装
    */
    public void setAllProperties(String callPhoneNumber,String callName,String calledPhoneNumber,
                                 String calledName,String callDate,String duration){
        setCallPhoneNumber(callPhoneNumber);
        setCallName(callName);
        setCalledPhoneNumber(calledPhoneNumber);
        setCalledName(calledName);
        setCallDate(callDate);
        setDuration(duration);
    }
    public void setCallPhoneNumber(String callPhoneNumber) {
        this.callPhoneNumber = callPhoneNumber;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public void setCalledPhoneNumber(String calledPhoneNumber) {
        this.calledPhoneNumber = calledPhoneNumber;
    }

    public void setCalledName(String calledName) {
        this.calledName = calledName;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCallPhoneNumber() {
        return callPhoneNumber;
    }

    public String getCallName() {
        return callName;
    }

    public String getCalledPhoneNumber() {
        return calledPhoneNumber;
    }

    public String getCalledName() {
        return calledName;
    }

    public String getCallDate() {
        return callDate;
    }

    public String getDuration() {
        return duration;
    }
}
