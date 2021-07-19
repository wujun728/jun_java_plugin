package com.buxiaoxia.repositrory;

import com.buxiaoxia.entity.Department;
import com.buxiaoxia.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

/**
 * Created by xw on 2017/9/13.
 * 2017-09-13 19:00
 */
public interface StaffRepository extends JpaRepository<Staff, Long> {

    Set<Staff> findByDepartment(Department department);
}
