package com.mycompany.myproject.base.classType;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ClassTypeTest {


    public static void main(String[] args) throws Exception{


        String js = "[{\"guest_no\":\"5498409\",\"name\":\"Barbara Best Banquet Ever\",\"first_name\":\"\"}]";

        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType =  mapper.getTypeFactory().constructParametricType(ArrayList.class, Guest.class);
        Object  obj = mapper.readValue(js, javaType);


        Object  obj2 = mapper.readValue(js, ArrayList.class);

        System.out.println(obj);
    }


}
