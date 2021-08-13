package com.mycompany.myproject.patterns.creational.abstractfactory;

import com.mycompany.myproject.patterns.creational.factory.Car;
import com.mycompany.myproject.patterns.creational.factory.Motor;
import com.mycompany.myproject.patterns.creational.factory.Suv;
import com.mycompany.myproject.patterns.creational.factory.Wagon;

public class AbstractFactoryTest {

    public static void print(Car car){
        if(car == null){
            System.out.println(" --- 我不造啊亲  ---");
            return;
        }

        System.out.println(car);
    }

    
    public static void main(String[] args){

        AbstractCarFactory benzFactory = new BenzFactory();
        Suv benzSuv = benzFactory.getSuv();
        Wagon benzWagon = benzFactory.getWagon();
        Motor benzMotor = benzFactory.getMotor();
        print(benzSuv);
        print(benzWagon);
        print(benzMotor);

        AbstractCarFactory bmwFactory = new BmwFactory();
        Suv bmwSuv = bmwFactory.getSuv();
        Wagon bmwWagon = bmwFactory.getWagon();
        Motor bmwMotor = bmwFactory.getMotor();
        print(bmwSuv);
        print(bmwWagon);
        print(bmwMotor);

        AbstractCarFactory audiFactory = new AudiFactory();
        Suv audiSuv = audiFactory.getSuv();
        Wagon audiWagon = audiFactory.getWagon();
        Motor audiMotor = audiFactory.getMotor();
        print(audiSuv);
        print(audiWagon);
        print(audiMotor);

    }
}
