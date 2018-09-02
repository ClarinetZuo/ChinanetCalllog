package com.oracle.reducer;

import com.oracle.vo.ComboBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Clarinet
 * @since JDK8
 */
public class CalllogReducer extends Reducer<ComboBean,Text,ComboBean,Text> {

    private Text outValue = new Text();

    @Override
    protected void reduce(ComboBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //count用来统计通话次数
        int count = 0;
        //duration用来统计通话时长
        int duration = 0;
        for (Text value:
             values) {
            duration += Integer.parseInt(value.toString());
            count ++;
        }

        outValue.set(count+"\t"+duration);

        context.write(key,outValue);
    }
}
