package com.mycompany.myproject.base.lambda;

import java.util.*;
import java.util.function.Supplier;

// 供给型接口  工厂方法
public class SupplierTest {

    public static Random random = new Random();


    public static List<Integer> supply(int count , Supplier<Integer> supplier) {
       
       List<Integer> list = new ArrayList<Integer>();
       while(count-- >0)
           list.add(supplier.get());
           
       return list;        
       
   }
   
   public static void main(String[] args){


       List list = SupplierTest.supply(10, ()-> random.nextInt(1000));
       list.forEach(System.out::println);

       list.sort(Comparator.naturalOrder());
       list.forEach(System.out::println);
   }

}
