package com.utils.pojo;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private String desc;


    private  Object obj;

    private List list;
    private Map map;

    public User(){

    }

    public User(String id, String name, Integer age, String desc, Object obj) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.desc = desc;
        this.obj = obj;
    }


    public User(String id, String name, Integer age, String desc, Object obj, List list, Map map) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.desc = desc;
        this.obj = obj;
        this.list = list;
        this.map = map;
    }
}
