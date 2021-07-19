package com.buxiaoxia.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 15:27
 */
@Data
@Entity(name = "DEPARTMENT")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String logo;

    private String description;

    @JsonIgnore
    @OrderBy("id")
    @OneToMany(mappedBy = "department", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private Set<Staff> staffs;

    public void setStaffs(Set<Staff> staffs) {
        if (this.staffs == null) {
            this.staffs = new HashSet<>();
        }
        if (staffs != null && staffs.size() > 0) {
            staffs.forEach(staff -> staff.setDepartment(this));
            this.staffs.addAll(staffs);
        } else this.staffs.clear();
    }

}
