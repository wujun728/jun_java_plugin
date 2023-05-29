package com.jun.plugin.guice.order;


public interface OrderService {

    void add(Order order);

    void remove(Order order);

    Order get(int id);
}
