package com.erp.test;

import java.util.ArrayList;
import java.util.List;

public class MyTest {
    public static int sum_add(int n,int sum){
        boolean is_end=true;
        sum+=n;
        is_end=(n>0) && ((sum=sum_add(sum,--n))>0);
        return sum;


    }


    public static void main(String[] args) {
       int sum =0;
      sum=  sum_add(5,sum);
        System.out.println(sum);
    }
}
