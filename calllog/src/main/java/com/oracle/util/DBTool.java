package com.oracle.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.oracle.parameter.Global;

import java.beans.PropertyVetoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FunnyZuo
 * @since JDK8
 */
public class DBTool {
    private Connection connection;//连接
    private Statement statement;//处理对象
    private PreparedStatement preparedStatement;//编译预处理的处理对象
    private ResultSet resultSet;//查询的结果集
    /**
     *@author FunnyZuo
     *@create 2018/7/14 8:53
     * 在构造方法中,完成加载驱动,建立连接,生成处理对象
     */
    public DBTool(){



        //c3p0连接池组件用法
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        try {
            comboPooledDataSource.setDriverClass(MySQLPropertiesUtil.getProperty(Global.DRIVER_CLASS));
            comboPooledDataSource.setJdbcUrl(MySQLPropertiesUtil.getProperty(Global.URL));
            comboPooledDataSource.setUser(MySQLPropertiesUtil.getProperty(Global.USER_NAME));
            comboPooledDataSource.setPassword(MySQLPropertiesUtil.getProperty(Global.USER_PWD));
            connection = comboPooledDataSource.getConnection();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *@author FunnyZuo
     *@create 2018/7/13 19:33
     * 执行查询
     * @param sql:待执行的sql语句
     */
    public List<Map<String,Object>> queryBySQL(String sql) {
        List<Map<String,Object>> myList = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            myList = new ArrayList<Map<String,Object>>();
//            myList = new ArrayList<>();
            while(resultSet.next()){
                Map<String,Object> myMap = new HashMap<String,Object>();

                for (int index = 1; index <= metaData.getColumnCount() ; index++) {
                    String key = metaData.getColumnLabel(index);
                    Object value = resultSet.getObject(key);
                    myMap.put(key,value);
                }
                myList.add(myMap);
            }
        } catch (SQLException e) {
            System.err.println("看看你的查询语句写对了吗?");
            e.printStackTrace();
        }
        return myList;
    }

    /**
     *@author FunnyZuo
     *@create 2018/7/13 19:33
     * 执行增删改
     * @param sql:待执行的sql语句
     */
    public int updateBySQL(String sql) {
        int count = 0;
        try {
            statement = connection.createStatement();

            count = statement.executeUpdate(sql);
            System.out.println("影响了"+count+"条记录");

        } catch (SQLException e) {
            //出现异常,手动回滚事务
            System.err.println("看看insert,update,delete的sql语句写的对吗");
            e.printStackTrace();
        }
        return count;
    }





}
