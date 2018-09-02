package com.oracle.consumer;

import com.oracle.bean.Calllog;
import com.oracle.dao.HBaseDao;
import com.oracle.util.KafkaPropertiesUtil;
import com.oracle.util.PropertiesUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Clarinet
 * @since JDK8
 */
public class TeleDataConsumer {
    public static void main(String[] args) throws ParseException {

        Properties properties = KafkaPropertiesUtil.put();

        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        //消费者订阅telecom主题
        consumer.subscribe(Arrays.asList(PropertiesUtil.getProperty("kafka.topic")));
        //创建命名空间
        HBaseDao.createNamespace();
        //创建命名空间下的某张表
        HBaseDao.createTable();


        while(true){
            ConsumerRecords<String,String> records = consumer.poll(1000);
            for (ConsumerRecord<String,String> record:
                    records) {
                //得到所有记录中的值
                String value = record.value();
                //创建一个Calllog对象
                Calllog calllog = new Calllog();
                //传递数据
                String[] values = value.split("\t");

                calllog.setAllProperties(values[0],values[1],values[2],values[3],values[4],values[5]);

                HBaseDao.putDatas(calllog);






            }

        }
    }


}
