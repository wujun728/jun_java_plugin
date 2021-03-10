package com.mycompany.myproject.base.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// 断言型接口  他还在吗
public class PredicateTest {

    public static void  filter(List<String> names , Predicate<String> predicate){
        names.forEach( name -> {
            if(predicate.test(name))
                System.out.println(name);
        });
    }

    public static void main(String[] args){

        List<String> names = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        filter(names, (name) -> name.length() > 4);


        Predicate<String> p1 = (t) -> t.length() > 3 ;
        Predicate<String> p2 = (t) -> t.length() < 6 ;

        names.stream().filter(p1.and(p2)).forEach(System.out::println);

    }
}
