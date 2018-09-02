package com.oracle.outputformat;

import com.oracle.util.DBTool;
import com.oracle.vo.ComboBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * @author Clarinet
 * @since JDK8
 */
public class MySQLRecordWriter extends RecordWriter<ComboBean, Text> {
    private Configuration configuration;
    DBTool dbTool = new DBTool();


//    public MySQLRecordWriter(TaskAttemptContext context){
//        configuration = context.getConfiguration();
//    }
    public void write(ComboBean key, Text value) {

        String str = value.toString();
        String[] values = str.split("\t");


        //这里有bug
        //将ComboBean切成若干个属性,由ContactBean和DateBean组成
        String[] results = key.toString().split("\t");

        String telephone = results[1];
        List<Map<String,Object>> result = dbTool.queryBySQL("SELECT id FROM tb_contacts WHERE telephone="+"'"+telephone+"'");
        //找出手机号对应的id

        int phoneNumber = (Integer) result.get(0).get("id");


        String year = results[3];
        String month = results[4];
        String day = results[5];


        DecimalFormat decimalFormat1 = new DecimalFormat();
        decimalFormat1.applyPattern("00");
        if(Integer.valueOf(month)==0 && Integer.valueOf(day)==0){
            String querySql = "SELECT id FROM date WHERE year='"+Integer.parseInt(year)
                    +"'AND month='-1' AND day='-1'";
            List<Map<String,Object>> result1 = dbTool.queryBySQL(querySql);
            //找出时间所对应的id
            int timeId = (Integer) result1.get(0).get("id");

            String insertSql = "INSERT INTO calllogtable (id,phoneNumber,timeRange,callCount,timeCount)" +
                    "VALUES ("+"'"+phoneNumber+"_"+timeId+"'"+","+phoneNumber+","+timeId+","+values[0]+","+values[1]+")";
            dbTool.updateBySQL(insertSql);

        }else if(!decimalFormat1.format(Integer.valueOf(month)).equals("00") &&
                decimalFormat1.format(Integer.valueOf(day)).equals("00")){

            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.applyPattern("00");


            List<Map<String,Object>> result1 = dbTool.queryBySQL("SELECT id FROM date WHERE year='"+Integer.parseInt(year)
                    +"'AND month='"+decimalFormat.format(Integer.parseInt(month))+"'AND day='-1'");
            //找出时间所对应的id
            int timeId = (Integer) result1.get(0).get("id");
            String insertSql = "INSERT INTO calllogtable (id,phoneNumber,timeRange,callCount,timeCount)" +
                    "VALUES ("+"'"+phoneNumber+"_"+timeId+"'"+","+phoneNumber+","+timeId+","+values[0]+","+values[1]+")";
            dbTool.updateBySQL(insertSql);
        }else{
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.applyPattern("00");
            List<Map<String,Object>> result1 = dbTool.queryBySQL("SELECT id FROM date WHERE year='"+Integer.parseInt(year)
                    +"'AND month='"+decimalFormat.format(Integer.parseInt(month))+"'AND day='"+decimalFormat.format(Integer.parseInt(day))+"'");
            //找出时间所对应的id
            int timeId = (Integer) result1.get(0).get("id");
            String insertSql = "INSERT INTO calllogtable (id,phoneNumber,timeRange,callCount,timeCount)" +
                    "VALUES ("+"'"+phoneNumber+"_"+timeId+"'"+","+phoneNumber+","+timeId+","+values[0]+","+values[1]+")";
            dbTool.updateBySQL(insertSql);
        }





    }

    public void close(TaskAttemptContext context) throws IOException, InterruptedException {


    }
}
