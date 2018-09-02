package com.oracle.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

/**
 * @author Clarinet
 * @since JDK8
 */
public class KafkaPropertiesUtil {

    private static Properties properties;

    static{
        properties = new Properties();
    }

    public static Properties put(){
        properties.put("bootstrap.servers", PropertiesUtil.getProperty("bootstrap.servers"));
        // 制定consumer group
        properties.put("group.id", PropertiesUtil.getProperty("group.id"));
        // 是否自动确认offset
        properties.put("enable.auto.commit", PropertiesUtil.getProperty("enable.auto.commit"));
        // 自动确认offset的时间间隔
        properties.put("auto.commit.interval.ms", PropertiesUtil.getProperty("auto.commit.interval.ms"));
        // key的序列化类
        properties.put("key.deserializer", PropertiesUtil.getProperty("key.deserializer"));
        // value的序列化类
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PropertiesUtil.getProperty("value.deserializer"));

        return properties;
    }
}
