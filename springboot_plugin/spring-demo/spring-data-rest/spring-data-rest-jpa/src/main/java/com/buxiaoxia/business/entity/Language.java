package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xw on 2017/6/5.
 * 2017-06-05 12:41
 */
@Data
@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "rel_user_language",uniqueConstraints = {@UniqueConstraint(columnNames = {"language_id","user_id"})},
            joinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    private List<User> users = new ArrayList<>();

}
