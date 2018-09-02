package com.oracle.dao;

import com.oracle.bean.Calllog;
import com.oracle.util.HBaseUtil;
import com.oracle.util.PropertiesUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Clarinet
 * @since JDK8
 */
public class HBaseDao {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void createNamespace(){
        HBaseUtil.createNameSpace(PropertiesUtil.getProperty("hbase.namespace.name"));
    }



    /**
     *@author Clarinet
     *@create 2018/8/15 12:13
     * 创建一张表,底层调用HBaseUtil
    */
    public static void createTable(){
        HBaseUtil.createTable(PropertiesUtil.getProperty("hbase.namespace.name"),
                PropertiesUtil.getProperty("hbase.table.name"),
                PropertiesUtil.getProperty("hbase.columnFamily1"),
                PropertiesUtil.getProperty("hbase.columnFamily2"));
    }



    public static void putDatas(Calllog calllog) throws ParseException {

        //在put之前,需要确定分区边界,分区号,以及rowkey
        String phoneNumberandTime = calllog.getCallPhoneNumber()+calllog.getCallDate().substring(0,7);

        String phoneNumberandTime1 = calllog.getCalledPhoneNumber()+calllog.getCallDate().substring(0,7);

        Date date = simpleDateFormat.parse(calllog.getCallDate());

        long timeStamp = date.getTime();

        String str = calllog.getCallPhoneNumber()+"_"+timeStamp+"_"+calllog.getCalledPhoneNumber()+"_"+calllog.getDuration()+"_"+"1";

        String value = calllog.getCallName()+"\t"+calllog.getCallDate()+"\t"+calllog.getCalledName();


        String str1 = calllog.getCalledPhoneNumber()+"_"+timeStamp+"_"+calllog.getCallPhoneNumber()+"_"+calllog.getDuration()+"_"+"0";

        String value1 = calllog.getCalledName()+"_"+calllog.getCallDate()+"_"+calllog.getCallName();

        HBaseUtil.addSingleRowData(PropertiesUtil.getProperty("hbase.namespace.name"),
                PropertiesUtil.getProperty("hbase.table.name"),
                phoneNumberandTime,
                str,
                PropertiesUtil.getProperty("hbase.columnFamily1"),
                PropertiesUtil.getProperty("hbase.qualifierName"),
                value);

        HBaseUtil.addSingleRowData(PropertiesUtil.getProperty("hbase.namespace.name"),
                PropertiesUtil.getProperty("hbase.table.name"),
                phoneNumberandTime1,
                str1,
                PropertiesUtil.getProperty("hbase.columnFamily2"),
                PropertiesUtil.getProperty("hbase.qualifierName"),
                value1);



    }



}
