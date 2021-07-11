package com.buxiaoxia.business.entity;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by xw on 2017/6/4.
 * 2017-06-04 15:14
 */
@Data
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNum;

    private Date createAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    @RestResource(path = "user", rel = "user")
    private User user;

}
