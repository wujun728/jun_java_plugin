package com.mycompany.myproject.patterns.creational.factory;

public class BmwFactory extends AbstractCarFactory {
    @Override
    public Car getCar() {
        return new X6();
    }
}
