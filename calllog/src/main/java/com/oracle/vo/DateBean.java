package com.oracle.vo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Clarinet
 * @since JDK8
 */
public class DateBean implements WritableComparable<DateBean> {
    //主键标识id
    private int id;

    //年份
    private int year;
    //月份
    private int month;
    //天
    private int day;


    public DateBean(){


    }

    public DateBean(Integer year) {
        this.year = year;
    }

    public DateBean(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public DateBean(Integer year, Integer month, Integer day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public DateBean(int id, Integer year, Integer month, Integer day) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Integer getYear() {
        return year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public int compareTo(DateBean o) {
        StringBuffer buffer1 = new StringBuffer();
        buffer1.append(o.year).append(o.month).append(o.day);
        StringBuffer buffer2 = new StringBuffer();
        buffer2.append(this.year).append(this.month).append(this.day);




        return buffer2.toString().compareTo(buffer1.toString());

    }

    @Override
    public String toString() {
        return this.id+"\t"+this.year+"\t"+this.month+"\t"+this.day;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.year);
        out.writeInt(this.month);
        out.writeInt(this.day);
    }

    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.year = in.readInt();
        this.month = in.readInt();
        this.day = in.readInt();
    }
}
