package com.mycompany.myproject.base.lambda;

import java.util.function.Function;

public class FunctionTest {


    public static int covert(String s, Function<String, Integer> function){
        
        return function.apply(s);
    }
    
    public static void main(String[] args){
       int ret =  covert("28", (x) -> Integer.valueOf(x));
       System.out.println(ret);
    }

}
