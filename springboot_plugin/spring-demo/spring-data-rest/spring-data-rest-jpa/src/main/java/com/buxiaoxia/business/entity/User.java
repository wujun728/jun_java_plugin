package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xw on 2017/6/4.
 * 2017-06-04 15:13
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    private Date createAt;

    @OneToOne(mappedBy = "user")
    private Card card;

    @OneToMany(mappedBy = "user")
    private List<Car> cars = new ArrayList<>();

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Language> languages = new ArrayList<>();

    public void setCards(List<Car> cars){
        if(cars != null && cars.size() > 0){
            cars.forEach(car -> car.setUser(this));
            this.cars = cars;
        }else this.cars = null;

    }
}
