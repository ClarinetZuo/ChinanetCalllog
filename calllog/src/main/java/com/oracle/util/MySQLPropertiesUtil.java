package com.oracle.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Clarinet
 * @since JDK8
 */
public class MySQLPropertiesUtil {
    private static Properties properties;

    static{
        properties = new Properties();

        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conn.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key){

        return properties.getProperty(key);
    }
}
