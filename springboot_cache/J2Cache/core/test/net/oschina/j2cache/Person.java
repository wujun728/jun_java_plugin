package net.oschina.j2cache;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 测试 JSON 序列化的类
 */
public class Person implements Serializable {

    private String name;
    private int age;
    private BigInteger seconds = new BigInteger("100");
    private List<School> schoolList;
    private HashMap<String, Integer> jobs;

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    private Date create_time = new Date(System.currentTimeMillis());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BigInteger getSeconds() {
        return seconds;
    }

    public void setSeconds(BigInteger seconds) {
        this.seconds = seconds;
    }

    public List<School> getSchoolList() {
        return schoolList;
    }

    public void setSchoolList(List<School> schoolList) {
        this.schoolList = schoolList;
    }

    public HashMap<String, Integer> getJobs() {
        return jobs;
    }

    public void setJobs(HashMap<String, Integer> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        String str = String.format("NAME:%s,AGE:%d%n", name, age);
        for(School sch : schoolList) {
            str += "\t";
            str += sch;
        }

        for(String key : jobs.keySet())
            str += String.format("\tNAME:%s,YEARS:%d%n", key, jobs.get(key));

        return str;
    }

}