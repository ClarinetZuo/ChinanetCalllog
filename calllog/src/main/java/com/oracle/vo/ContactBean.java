package com.oracle.vo;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Clarinet
 * @since JDK8
 * 这个InfoBean对应数据库中Info那张表
 */
public class ContactBean implements WritableComparable<ContactBean> {
    //主键标识
    private int id;
    //电话号码
    private String telephoneNumber;


    public ContactBean(){

    }

    public ContactBean(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public ContactBean(int id, String telephoneNumber) {
        this.id = id;
        this.telephoneNumber = telephoneNumber;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.id+"\t"+this.telephoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }



    public int compareTo(ContactBean o) {
        int result = this.telephoneNumber.compareTo(o.telephoneNumber);

        return result;
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);

        out.writeUTF(this.telephoneNumber);

    }

    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();

        this.telephoneNumber = in.readUTF();

    }
}
