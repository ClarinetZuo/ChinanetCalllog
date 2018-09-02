package com.oracle.mapper;

import com.oracle.vo.ComboBean;
import com.oracle.vo.ContactBean;
import com.oracle.vo.DateBean;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Clarinet
 * @since JDK8
 */
public class CalllogMapper extends TableMapper<ComboBean, Text> {
    //经过CalllogMapper出去的key样式:
    //18504608809_2016
    //18504608809_201608
    //18504608809_20160816
    private ComboBean outKey = new ComboBean();

    private Text outValue = new Text();

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

            //得到RowKey
            //样式为:01_18504608809_ts_18846822591_duration_flag
            String rowKey = Bytes.toString(value.getRow());
            //将RowKey切分开得到我想要的数据
            String[] results = rowKey.split("_");
            //time样式:20180816
            String time = simpleDateFormat.format(Long.parseLong(results[2]));

            outKey.setContactBean(new ContactBean(results[1]));

            outKey.setDateBean(new DateBean(Integer.parseInt(time.substring(0,4))));

            outValue.set(results[4]);
            //18504608809   2018
            context.write(outKey,outValue);

            outKey.setDateBean(new DateBean(Integer.parseInt(time.substring(0,4)),
                    Integer.parseInt(time.substring(4,6))));
            //18504608809   201808
            context.write(outKey,outValue);

            outKey.setDateBean(new DateBean(Integer.parseInt(time.substring(0,4)),
                    Integer.parseInt(time.substring(4,6)),
                    Integer.parseInt(time.substring(6))));
            //18504608809   20180816
            context.write(outKey,outValue);

    }
}
