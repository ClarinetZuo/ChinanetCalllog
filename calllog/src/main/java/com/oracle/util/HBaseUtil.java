package com.oracle.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

/**
 * @author Clarinet
 * @since JDK8
 * HBase的数据访问对象
 */
public class HBaseUtil {
    private static Configuration configuration;
    private static Admin admin;
    private static Connection connection;
    private static Table hTable = null;
    /**
     *@author Clarinet
     *@create 2018/8/14 14:28
     * 初始化配置对象,连接以及管理员对象
    */
    static{

        configuration = HBaseConfiguration.create();
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *@author Clarinet
     *@create 2018/8/15 9:31
     * 创建命名空间
    */
    public static void createNameSpace(String nameSpace) {
        if(null == nameSpace || "".equals(nameSpace)){
            throw new RuntimeException("输入的参数有误");
        }

        try {
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();
            NamespaceDescriptor[] namespaceDescriptors = admin.listNamespaceDescriptors();
            for (NamespaceDescriptor namespaceDescriptor1:
                 namespaceDescriptors) {
                if((namespaceDescriptor1.getName()).equals(namespaceDescriptor.getName())){
                    throw new RuntimeException("该命名空间存在,请更换名字");
                }
            }
            admin.createNamespace(namespaceDescriptor);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
    /**
     *@author Clarinet
     *@create 2018/8/15 9:33
     * 生成预分区边界的方法(00| 01| 02| 03| 04| 05|)
     * 生成七个分区,最后一个分区用来保存异常数据
    */
    public static byte[][] genePartitioinBound(){
        byte[] byte1 = Bytes.toBytes("00|");
        byte[] byte2 = Bytes.toBytes("01|");
        byte[] byte3 = Bytes.toBytes("02|");
        byte[] byte4 = Bytes.toBytes("03|");
        byte[] byte5 = Bytes.toBytes("04|");
        byte[] byte6 = Bytes.toBytes("05|");
        byte[][] bytes = new byte[][]{byte1,byte2,byte3,byte4,byte5,byte6};



        return bytes;
    }

    /**
     *@author Clarinet
     *@create 2018/8/15 10:23
     * 生成分区号的方法
     * @param phoneNumberandTime:是手机号+年月
    */
    public static String genePartitionIndex(String phoneNumberandTime){
        if(null == phoneNumberandTime || "".equals(phoneNumberandTime)){
            throw new RuntimeException("输入的参数有误");
        }
        phoneNumberandTime.replaceAll("\\D","");
        Long l = Long.parseLong(phoneNumberandTime.replaceAll("\\D","")) ^
                Long.parseLong(phoneNumberandTime.substring(12).replaceAll("\\D",""));

        int index = Math.abs(l.hashCode()) % Integer.parseInt(PropertiesUtil.getProperty("hbase.regions.nums"));

        return "0"+index+"_";
    }

    /**
     *@author Clarinet
     *@create 2018/8/15 10:31
     * 生成RowKey的方法
    */
    public static String geneRowKey(String phoneNumberandTime,String str){
        if(null == phoneNumberandTime || null == str ||
        "".equals(phoneNumberandTime) || "".equals(str)){
            throw new RuntimeException("输入的参数有误,请重新输入");
        }

        return genePartitionIndex(phoneNumberandTime)+str;
    }






    /**
     *@author Clarinet
     *@create 2018/8/14 14:28
     * 在HBase中创建一张表,考虑预分区
    */
    public static void createTable(String namepace,String tableName,String...columnFamilies){
        if(null == tableName || null == columnFamilies ||
                "".equals(tableName) || columnFamilies.length == 0){
            throw new RuntimeException("输入的参数有误,请重新输入");
        }else {
            try {
                if(admin.tableExists(TableName.valueOf(tableName))){
                    throw new RuntimeException("表已经存在,请重新输入表名");
                }else{
                    HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(namepace+":"+tableName));
                    for (String columnFamily:
                         columnFamilies) {
                        hTableDescriptor.addFamily(new HColumnDescriptor(Bytes.toBytes(columnFamily)));
                    }
                    //生成RowKey预分区的表
                    admin.createTable(hTableDescriptor,genePartitioinBound());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    admin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     *@author Clarinet
     *@create 2018/8/14 14:36
     * 向HBase中加一条记录
    */
    public static void addSingleRowData(String namespcae,
                                        String tableName,
                                        String phoneNumberandTime,
                                        String str,
                                        String columnFamily,
                                        String columnName,
                                        String value) {
        if (null == tableName || null == columnFamily || null == columnFamily || null == value
                || "".equals(tableName) ||  "".equals(columnFamily) || "".equals(columnName) || "".equals(value)) {
            throw new RuntimeException("输入的参数有误,请重新输入");
        } else {
            try {
                hTable = connection.getTable(TableName.valueOf(namespcae+":"+tableName));

                Put put = new Put(Bytes.toBytes(geneRowKey(phoneNumberandTime,str)));

                put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(columnName), Bytes.toBytes(value));

                hTable.put(put);


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    public static void getAllRows(String namespace,String tableName){
        try {
            hTable = connection.getTable(TableName.valueOf(namespace+":"+tableName));

            Scan scan = new Scan();

            ResultScanner resultScanner = hTable.getScanner(scan);
            for (Result result:
                 resultScanner) {
                System.out.println(Bytes.toString(result.getRow()));
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hTable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
//        createTable("myns2","111","info");
//        addSingleRowData("myns2",
//                "111",
//                "000",
//                "info",
//                "name",
//                "Tom");
        getAllRows("myns2","calllog");
    }

}
