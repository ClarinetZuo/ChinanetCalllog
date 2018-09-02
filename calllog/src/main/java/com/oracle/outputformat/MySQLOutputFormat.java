package com.oracle.outputformat;

import com.oracle.vo.ComboBean;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author Clarinet
 * @since JDK8
 */

public class MySQLOutputFormat extends OutputFormat<ComboBean, Text> {
    OutputCommitter committer = null;
    public RecordWriter<ComboBean, Text> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        RecordWriter<ComboBean,Text> mySQLRecordWriter = new MySQLRecordWriter();
        return mySQLRecordWriter;
    }

    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }

    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if (committer == null) {
            committer = new FileOutputCommitter(null, context);
        }
        return committer;
    }
}
