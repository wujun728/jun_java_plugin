package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xiaw
 * @date 2018/4/19 10:48
 * Description:
 */
@Data
@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "status")
    private String status;

}