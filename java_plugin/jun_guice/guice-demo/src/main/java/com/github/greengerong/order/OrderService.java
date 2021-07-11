package com.github.greengerong.order;


public interface OrderService {

    void add(Order order);

    void remove(Order order);

    Order get(int id);
}
