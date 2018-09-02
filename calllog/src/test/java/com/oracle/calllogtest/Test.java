package com.oracle.calllogtest;


import com.oracle.util.DBTool;

import java.util.List;
import java.util.Map;

/**
 * @author Clarinet
 * @since JDK8
 */
public class Test {
    public static void main(String[] args) throws Exception{
//        HBaseUtil.createNameSpace(PropertiesUtil.getProperty("hbase.namespace.name"));
//        String str = "111112017-01";
//        String str1 = str.replaceAll("\\D","");
//        System.out.println(str1);
//        String str = "01_18504608809_ts_18846822591_duration_flag";
//        String[] strs = str.split("_");
//        for (String s:
//             strs) {
//            System.out.println(s);
//        }
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        String str = simpleDateFormat.format(Long.parseLong("1534396520134"));
//        System.out.println(str);
//        String str = "01";
//        String str1 = "02";
//        System.out.println(Integer.parseInt(str)+Integer.parseInt(str1));
//        DBTool dbTool = new DBTool();
//        System.out.println(dbTool.queryBySQL("SELECT id FROM info WHERE telephone="+"'"+"13222648605"+"'"));
//        List<Map<String,Object>> result = dbTool.queryBySQL("SELECT id FROM info WHERE telephone="+"'"+"13222648605"+"'");
//        System.out.println(result);
//        System.out.println(result.get(0).get("id"));

//        ComboBean comboBean = new ComboBean();
//        ContactBean contactBean = new ContactBean();
//        contactBean.setId(1);
//        contactBean.setTelephoneNumber("1111");
//        DateBean dateBean = new DateBean();
//        dateBean.setYear(2018);
//        comboBean.setContactBean(contactBean);
//        comboBean.setDateBean(dateBean);
//        System.out.println(comboBean);
        String sql = "SELECT id FROM date WHERE year='"+Integer.parseInt("2017")
                +"'AND month='-1' AND day='-1'";
        DBTool dbTool = new DBTool();
        List<Map<String,Object>> result = dbTool.queryBySQL(sql);
        System.out.println(result);
        System.out.println(Integer.parseInt("00"));
    }
}
