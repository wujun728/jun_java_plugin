package com.mycompany.myproject.patterns.creational.abstractfactory;

import com.mycompany.myproject.patterns.creational.factory.Motor;
import com.mycompany.myproject.patterns.creational.factory.Suv;
import com.mycompany.myproject.patterns.creational.factory.Wagon;

public interface AbstractCarFactory {

    Suv getSuv();

    Wagon getWagon();

    Motor  getMotor();
}
