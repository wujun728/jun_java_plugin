package com.buxiaoxia.repositrory;

import com.buxiaoxia.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 15:31
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
