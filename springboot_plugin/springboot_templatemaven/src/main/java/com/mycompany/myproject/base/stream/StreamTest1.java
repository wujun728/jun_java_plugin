package com.mycompany.myproject.base.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class StreamTest1 {

    public static void main(String[] args){

        //List<String> list = Arrays.asList("America", "China", "Japan", "India", "Germany");
        List<String> countries = new ArrayList<>();
        countries.add("America");
        countries.add("China");
        countries.add("Japan");
        countries.add("India");
        countries.add("Germany");
        countries.forEach(System.out::println);

        OptionalInt longestStringLengthStartingWithA
                = countries.stream()
                .filter(s -> s.startsWith("A"))
                .mapToInt(String::length)
                .sorted()
                .max();

        if(longestStringLengthStartingWithA.isPresent()){
            System.out.println(longestStringLengthStartingWithA.getAsInt());
        }


        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Barry", "China", 33));
        persons.add(new Person("Jonas", "China", 32));
        persons.add(new Person("Charlie", "China", 33));
        persons.add(new Person("Jansie", "India", 38));
        persons.add(new Person("Siemens", "Germany", 40));
        persons.add(new Person("XiaoZe", "Japan", 25));

        List<Person> realPersons = persons.stream()
                .filter(c-> !"Japan".equals(c.getCountry()))
                .collect(Collectors.toList());

        realPersons.forEach(System.out::println);

        //Stream.Builder
        //IntStream.Builder
        //LongStream.Builder
        //DoubleStream.Builder
    }

    public static class Person {

        private String name ;
        private String country ;
        private int old;

        public Person(String name, String country , int old){
            this.name = name;
            this.country = country;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getOld() {
            return old;
        }

        public void setOld(int old) {
            this.old = old;
        }

        @Override
        public String toString(){
            return this.name + "-" + this.country + "-" + this.old ;
        }
    }

}
