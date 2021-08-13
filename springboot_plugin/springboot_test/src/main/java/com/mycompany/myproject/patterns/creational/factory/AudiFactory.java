package com.mycompany.myproject.patterns.creational.factory;

public class AudiFactory extends AbstractCarFactory{

    @Override
    public Car getCar() {
        return new Q7();
    }
}
