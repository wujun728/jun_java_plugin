package com.mycompany.myproject.patterns.creational.abstractfactory;

import com.mycompany.myproject.patterns.creational.factory.*;

public class AudiFactory implements AbstractCarFactory {

    @Override
    public Suv getSuv() {
        return new Q7();
    }

    @Override
    public Wagon getWagon() {
        return new A6Wagon();
    }

    @Override
    public Motor getMotor() {
        return null;
    }
}
