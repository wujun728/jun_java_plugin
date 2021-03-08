package com.springboot.springbootjpaquerydsl.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/25
 * @Time: 23:03
 * @email: inwsy@hotmail.com
 * Description:
 */
@Entity
@Data
@Table(name = "lesson")
public class LessonModel {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    private String id;

    private String name;

    private Date startDate;

    private String address;

    private String userId;
}
