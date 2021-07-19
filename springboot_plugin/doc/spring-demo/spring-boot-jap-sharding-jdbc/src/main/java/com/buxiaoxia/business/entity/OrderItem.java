package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author xiaw
 * @date 2018/4/19 11:17
 * Description:
 */
@Data
@Entity
@Table(name = "t_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 261534701950670670L;

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "user_id")
    private int userId;

}
