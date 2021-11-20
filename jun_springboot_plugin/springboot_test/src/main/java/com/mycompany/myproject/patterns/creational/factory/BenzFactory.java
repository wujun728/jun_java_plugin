package com.mycompany.myproject.patterns.creational.factory;

public class BenzFactory extends AbstractCarFactory {
    @Override
    public Car getCar() {
        return new Glk();
    }
}
