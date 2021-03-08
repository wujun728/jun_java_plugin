package com.springcloud.springbootjpadruid.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Date: 2019/9/17 16:49
 * @Version: 1.0
 * @Desc:
 */
@Entity
@Data
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable=false, length=36)
    private String id;
    @Column(name = "nick_name")
    private String nickName;
    @Column(nullable = false)
    private int age;
}
