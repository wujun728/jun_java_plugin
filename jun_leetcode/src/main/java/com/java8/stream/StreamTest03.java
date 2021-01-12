package com.java8.stream;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author BaoZhou
 * @date 2018/7/31
 */
public class StreamTest03 {
    List<Transication> list;

    @BeforeEach
    public void before() {
        System.out.println("@Before");
        Trader A = new Trader("AERGC", "aa");
        Trader B = new Trader("BFaV", "bb");
        Trader C = new Trader("CVV", "cc");
        Trader D = new Trader("DSS", "cc");
        Trader E = new Trader("EaSAF", "ee");
        list = Arrays.asList(
                new Transication(A, 2011, 300)
                , new Transication(B, 2012, 1000)
                , new Transication(C, 2011, 400)
                , new Transication(D, 2012, 700)
                , new Transication(E, 2012, 950));
    }

    @Test
    void test1() {
        System.out.println("2011年的所有交易");
        list.stream().filter(t -> t.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transication::getNumber))
                .forEach(System.out::println);
    }

    @Test
    void test2() {
        System.out.println("@test2");
        list.stream()
                .map(t -> t.getTrader().getSchool())
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    void test3() {
        System.out.println("@test3");
        list.stream()
                .filter(t -> t.getTrader()
                        .getSchool()
                        .equals("cc"))
                .map(Transication::getTrader)
                .sorted(Comparator.comparing(Trader::getName))
                .forEach(System.out::println);
    }

    @Test
    void test4() {
        System.out.println("@test4");
        list.stream()
                .map(t->t.getTrader().getName())
                .sorted()
                .forEach(System.out::println);

        String reduce = list.stream()
                .map(t -> t.getTrader().getName())
                .sorted()
                .reduce("", String::concat);
        System.out.println(reduce);

        list.stream()
                .map(t -> t.getTrader().getName())
                .flatMap(StreamTest03::filterCharacter)
                .sorted((s1,s2)->s1.compareToIgnoreCase(s2))
                .forEach(System.out::print);

    }

    public static Stream<String> filterCharacter(String str)
    {
        List<String> list = new ArrayList<>();
        for(Character ch:str.toCharArray())
        {
            list.add(ch.toString());
        }
        return list.stream();
    }
    @Test
    void test5()
    {
        boolean a = list.stream().anyMatch(t -> t.getTrader().getSchool().equals("cc"));
        System.out.println(a);
    }

    @Test
    void test6()
    {
        Optional<Integer> sum = list.stream().filter(t -> t.getTrader().getSchool().equals("cc")).map(Transication::getNumber)
                .reduce(Integer::sum);
        System.out.println(sum.orElse(0));
    }

    @Test
    void test7()
    {
        Optional<Integer> max = list.stream().map(Transication::getNumber).max(Integer::compareTo);
        System.out.println(max.get());
    }

    @Test
    void test8()
    {
        Optional<Transication> min = list.stream().min(Comparator.comparingInt(Transication::getNumber));
        System.out.println(min);
    }
}
