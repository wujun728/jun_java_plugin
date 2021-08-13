package com.mycompany.myproject.patterns.creational.abstractfactory;

import com.mycompany.myproject.patterns.creational.factory.*;

public class BmwFactory implements AbstractCarFactory {

    @Override
    public Suv getSuv() {
        return new X6();
    }

    @Override
    public Wagon getWagon() {
        return new M2Wagon();
    }

    @Override
    public Motor getMotor() {
        return new S1000RR();
    }
}
