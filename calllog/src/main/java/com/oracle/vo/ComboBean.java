package com.oracle.vo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Clarinet
 * @since JDK8
 */
public class ComboBean implements WritableComparable<ComboBean> {
    //联系人对象(Bean)
    private ContactBean contactBean;
    //日期对象(Bean)
    private DateBean dateBean;

    public ComboBean(){

    }

    public ContactBean getContactBean() {
        return contactBean;
    }

    public void setContactBean(ContactBean contactBean) {
        this.contactBean = contactBean;
    }

    public DateBean getDateBean() {
        return dateBean;
    }

    public void setDateBean(DateBean dateBean) {
        this.dateBean = dateBean;
    }

    public int compareTo(ComboBean o) {
        int result = this.getDateBean().compareTo(o.getDateBean());
        if(result == 0){
            return this.getContactBean().compareTo(o.getContactBean());
        }
        return result;
    }

    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.contactBean.toString());

        out.writeUTF(this.dateBean.toString());
    }
    //这里有bug
    public void readFields(DataInput in) throws IOException {
        String results = in.readUTF();
        String[] result = results.split("\t");
        this.contactBean = new ContactBean(Integer.parseInt(result[0]),result[1]);
        //id year month day
        String result1s = in.readUTF();
        String[] result1 = result1s.split("\t");
        if(result1[2].equals("null")){

            this.dateBean = new DateBean(Integer.parseInt(result1[0]),
                    Integer.parseInt(result1[1]));
        }else if(!result1[2].equals("null") && result1[3].equals("null")){

            this.dateBean = new DateBean(Integer.parseInt(result1[0]),
                    Integer.parseInt(result1[1]),
                    Integer.parseInt(result1[2])
                    );
        }else{

            this.dateBean = new DateBean(Integer.parseInt(result1[0]),
                    Integer.parseInt(result1[1]),
                    Integer.parseInt(result1[2]),
                    Integer.parseInt(result1[3]));
        }




    }

    @Override
    public String toString() {
        return this.contactBean.toString()+"\t"+this.dateBean.toString();
    }
}
