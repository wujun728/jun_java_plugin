package com.designpatterns.threefactory;

import com.designpatterns.threefactory.abstractfactory.AbstractFactory;
import com.designpatterns.threefactory.abstractfactory.BlueCircleFactory;
import com.designpatterns.threefactory.abstractfactory.RedRectangleFactory;
import com.designpatterns.threefactory.factory.*;
import com.designpatterns.threefactory.simplefactory.FoodFactory;
import org.junit.jupiter.api.Test;

/**
 * @author: BaoZhou
 * @date : 2018/12/27 11:40
 */
public class FactoryPatternTest {

    /**
     * 简单工厂模式,mybatis里配置类使用
     */
    @Test
    public void simpleFactory() throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        Food food = FoodFactory.FoodFactory(AppleFood.class);
        Food food2 = FoodFactory.FoodFactory(BeefFood.class);
        Food food3 = FoodFactory.FoodFactory(VegetableFood.class);
        System.out.println(food.type());
        System.out.println(food2.type());
        System.out.println(food3.type());

    }

    /**
     * 工厂模式,mybatis里配置类使用
     */
    @Test
    public void Factory() {
        AppleFactory factory = new AppleFactory();
        Food food = factory.createFood();
        BeefFactory factory2 = new BeefFactory();
        Food food2 = factory2.createFood();
        VegetableFactory factory3 = new VegetableFactory();
        Food food3 = factory3.createFood();
        System.out.println(food.type());
        System.out.println(food2.type());
        System.out.println(food3.type());
    }

    /**
     * 抽象工厂模式
     *
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    @Test
    public void AbstractFactory() {
        AbstractFactory redRectangleFactory = new RedRectangleFactory();
        AbstractFactory blueCircleFactory = new BlueCircleFactory();
        redRectangleFactory.getColor().fill();
        redRectangleFactory.getShape().draw();
        blueCircleFactory.getColor().fill();
        blueCircleFactory.getShape().draw();
    }


}
