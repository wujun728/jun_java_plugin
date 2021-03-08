package com.springboot.springbootjpaquerydsl.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/25
 * @Time: 23:02
 * @email: inwsy@hotmail.com
 * Description:
 */
@Entity
@Data
@Table(name = "user")
public class UserModel {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    private String id;

    private String nickName;

    private int age;

    private Date createDate;

}
