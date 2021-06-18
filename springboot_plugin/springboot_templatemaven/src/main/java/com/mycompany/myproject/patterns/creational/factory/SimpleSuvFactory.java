package com.mycompany.myproject.patterns.creational.factory;

public class SimpleSuvFactory {

    public static String AUDI = "AODI";
    public static String BMW = "BAOMA";
    public static String BENZ = "BENCHI";


    public static Car getCar(String brand){

        if(BENZ.equals(brand)){
            return new Glk();
        }else if(BMW.equals(brand)){
            return new X6();
        }else if(AUDI.equals(brand)){
            return new Q7();
        }

        return null;
    }


    public static void main(String[] args){
        Car car1 = SimpleSuvFactory.getCar(SimpleSuvFactory.BENZ);
        System.out.println(car1);

        Car car2 = SimpleSuvFactory.getCar(SimpleSuvFactory.BMW);
        System.out.println(car2);

        Car car3 = SimpleSuvFactory.getCar(SimpleSuvFactory.AUDI);
        System.out.println(car3);
    }
}
