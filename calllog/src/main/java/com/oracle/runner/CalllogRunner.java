package com.oracle.runner;

import com.oracle.mapper.CalllogMapper;
import com.oracle.outputformat.MySQLOutputFormat;
import com.oracle.reducer.CalllogReducer;
import com.oracle.vo.ComboBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author Clarinet
 * @since JDK8
 */
public class CalllogRunner implements Tool {

    private Configuration configuration;

    public int run(String[] args) throws Exception {

        Job job = Job.getInstance(this.getConf());

        job.setOutputKeyClass(ComboBean.class);

        job.setOutputValueClass(Text.class);
        job.setJarByClass(CalllogRunner.class);

        Scan scan = new Scan();

        TableMapReduceUtil.initTableMapperJob(args[0],
                scan,
                CalllogMapper.class,
                ComboBean.class,
                Text.class,
                job);

        job.setReducerClass(CalllogReducer.class);

        job.setOutputFormatClass(MySQLOutputFormat.class);

        boolean flag = job.waitForCompletion(true);

        return flag?0:1;





    }

    public void setConf(Configuration conf) {
        this.configuration = HBaseConfiguration.create(conf);
    }

    public Configuration getConf() {
        return this.configuration;
    }

    public static void main(String[] args) {
        try {
            int result = ToolRunner.run(new CalllogRunner(),args);

            System.exit(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
