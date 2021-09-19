package com.jun.plugin.json.gson.pojo;

import java.util.Date;
import java.util.Objects;

public class TestPojoSimple {
    private String name;
    private Integer age;
    private Date birth;

    public TestPojoSimple() {
    }

    public TestPojoSimple(String name, Integer age, Date birth) {
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return "TestPojoSimple{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestPojoSimple)) return false;
        TestPojoSimple that = (TestPojoSimple) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAge(), that.getAge()) &&
                Objects.equals(getBirth(), that.getBirth());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAge(), getBirth());
    }
}
