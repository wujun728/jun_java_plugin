package com.mycompany.myproject.patterns.creational.abstractfactory;

import com.mycompany.myproject.patterns.creational.factory.*;

public class BenzFactory implements AbstractCarFactory {

    @Override
    public Suv getSuv() {
        return new Glk();
    }

    @Override
    public Wagon getWagon() {
        return new E63Wagon();
    }

    @Override
    public Motor getMotor() {
        return null;
    }
}
