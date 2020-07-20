package com.zccoder.java.book1.ch4.composing;

import java.io.Serializable;
import java.util.Objects;

/**
 * 标题：Person 实体<br>
 * 描述：POJO<br>
 * 时间：2018/10/25<br>
 *
 * @author zc
 **/
public class Person implements Serializable {

    private static final long serialVersionUID = -4959647045455650723L;
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
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
}
