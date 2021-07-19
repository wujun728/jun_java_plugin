package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xw on 2017/6/5.
 * 2017-06-05 11:19
 */
@Data
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String carNum;

    private Date createAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
