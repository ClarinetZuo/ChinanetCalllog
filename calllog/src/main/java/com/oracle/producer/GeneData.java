package com.oracle.producer;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Clarinet
 * @since JDK8
 */
public class GeneData {
    public static void main(String[] args){
        //使用StringBuffer因为线程安全
        while(true) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(geneTelephoneNumberAndName()).append("\t")
                    .append(geneTime("2017-01-01", "2017-12-31")).append("\t")
                    .append(geneDuration()).append("\n");
            String str = buffer.toString();
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File("/home/bduser/teleproject/dataset/teledata.txt"), true);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
                bufferedWriter.write(str);
                bufferedWriter.flush();
                bufferedWriter.close();
                fileOutputStream.close();
                Thread.sleep(1000);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     *@author Clarinet
     *@create 2018/8/14 11:58
     * 随机生成一个2017-01-01——2017-12-31的时间
     */
    public static String geneTime(String beginTime,String endTime){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str1 = null;
        try {
            Date beginDate = simpleDateFormat.parse(beginTime);
            long beginTS = beginDate.getTime();
            Date endDate = simpleDateFormat.parse(endTime);
            long endTS = endDate.getTime();
            long minus = endTS-beginTS;
            double range = minus * Math.random();
//            System.out.println(range);
            double chooseTS = beginTS + range;

            String str = simpleDateFormat1.format(chooseTS);

            Date date = simpleDateFormat1.parse(str);

            str1 = simpleDateFormat1.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str1;
    }
    /**
     *@author Clarinet
     *@create 2018/8/14 12:05
     * 随机生成一个通话时长(0-7200)
     */
    public static String geneDuration(){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("0000");
        Random random = new Random();
        int duration = random.nextInt(7201);
        return decimalFormat.format(duration);
    }
    /**
     *@author Clarinet
     *@create 2018/8/14 12:20
     * 生成电话号码和名字的映射
     */
    public static String geneTelephoneNumberAndName(){
        List<String> myList = new ArrayList<String>();
        myList.add("13222648605");
        myList.add("13495809651");
        myList.add("16063914889");
        myList.add("17486142334");
        myList.add("14780982547");
        myList.add("15756086494");
        myList.add("13702639038");
        myList.add("18538144042");
        myList.add("15405773835");
        myList.add("13824993830");
        myList.add("13494045732");
        myList.add("17331861816");
        myList.add("13262028369");
        myList.add("15961126795");
        myList.add("14442888778");
        myList.add("18018026893");
        myList.add("15604803960");
        myList.add("13663622984");
        myList.add("16494016460");
        myList.add("18932127845");
        myList.add("13944763715");
        myList.add("13092350505");
        myList.add("18501010799");
        myList.add("18423374044");
        myList.add("13881947749");
        myList.add("18989862551");
        myList.add("13825029630");
        myList.add("13690577624");
        myList.add("13418897994");
        myList.add("18474236261");
        myList.add("16597782782");
        myList.add("16282437711");
        myList.add("15456373072");
        myList.add("17762560942");
        myList.add("17556723568");

        Map<String,String> myMap = new HashMap<String, String>();
        myMap.put("13222648605","赵光兰");
        myMap.put("13495809651","孙昭东");
        myMap.put("16063914889","华倩");
        myMap.put("17486142334","钱冬儿");
        myMap.put("14780982547","李秀芳");
        myMap.put("15756086494","朱乐");
        myMap.put("13702639038","许萍");
        myMap.put("18538144042","褚英");
        myMap.put("15405773835","张晶");
        myMap.put("13824993830","水蕊");
        myMap.put("13494045732","魏政群");
        myMap.put("17331861816","秦南莲");
        myMap.put("13262028369","冯眉");
        myMap.put("15961126795","金怜云");
        myMap.put("14442888778","陈政群");
        myMap.put("18018026893","乐贞");
        myMap.put("15604803960","邬冷安");
        myMap.put("13663622984","姜太群");
        myMap.put("16494016460","华娟");
        myMap.put("18932127845","朱明珠");
        myMap.put("13944763715","张春竹");
        myMap.put("13092350505","吕念");
        myMap.put("18501010799","孙晓");
        myMap.put("18423374044","金婕");
        myMap.put("13881947749","韩元琼");
        myMap.put("18989862551","何静");
        myMap.put("13825029630","赵开凤");
        myMap.put("13690577624","吕怜云");
        myMap.put("13418897994","施娣");
        myMap.put("18474236261","孔政群");
        myMap.put("16597782782","陈雪倩");
        myMap.put("16282437711","咸慧");
        myMap.put("15456373072","卫筠");
        myMap.put("17762560942","曹乐萱");
        myMap.put("17556723568","吕瑞进");
        Random random = new Random();
        int index = random.nextInt(myList.size());
        String callPhoneNumber = myList.get(index);
        String callName = myMap.get(callPhoneNumber);
        int index1 = random.nextInt(myList.size());
        if(index1 == index){
            index1 ++;
        }
        String calledPhoneNumber = myList.get(index1);
        String calledName = myMap.get(calledPhoneNumber);
        StringBuffer buffer = new StringBuffer();
        buffer.append(callPhoneNumber).append("\t")
                .append(callName).append("\t")
                .append(calledPhoneNumber).append("\t")
                .append(calledName);
        return buffer.toString();
    }
}




