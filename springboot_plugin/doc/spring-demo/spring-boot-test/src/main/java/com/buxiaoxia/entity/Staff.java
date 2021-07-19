package com.buxiaoxia.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by xw on 2017/9/13.
 * 2017-09-13 16:12
 */
@Data
@Entity(name = "STAFF")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private Integer age;

    private String headUrl;

    private String des;

    @ManyToOne
    @JoinColumn(name = "dep_id", referencedColumnName = "id")
    private Department department;

    @Transient
    private Job job;

    @Data
    public  static class Job {

        private String capacity;

        private int level;

        private Double salary;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Staff staff = (Staff) o;

        return id.equals(staff.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
