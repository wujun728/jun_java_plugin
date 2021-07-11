package com.buxiaoxia.controller;

import com.buxiaoxia.entity.Department;
import com.buxiaoxia.entity.Staff;
import com.buxiaoxia.repositrory.DepartmentRepository;
import com.buxiaoxia.repositrory.StaffRepository;
import com.buxiaoxia.utils.exception.InvalidRequestException;
import com.buxiaoxia.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 15:32
 */
@RestController
@RequestMapping("/api/v1/deps")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public ResponseEntity addDep(@RequestBody Department department) {
        validData(department);
        departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(department);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneDep(@PathVariable long id) {
        Department department = depNotFoundValid(id);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delDep(@PathVariable long id) {
        Department department = depNotFoundValid(id);
        departmentRepository.delete(id);
        return ResponseEntity.ok(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateDep(@PathVariable long id, @RequestBody Department department) {
        depNotFoundValid(id);
        validData(department);
        department.setId(id);
        departmentRepository.save(department);
        return ResponseEntity.ok(department);
    }


    @RestController
    @RequestMapping("/api/v1/deps/{depId}/staffs")
    public class DepStaffController {

        @Autowired
        private StaffRepository staffRepository;

        @PostMapping("/{staffId}")
        public ResponseEntity addOneStaff2Dep(@PathVariable long depId, @PathVariable long staffId) {
            Department department = depNotFoundValid(depId);
            Staff staff = staffNotFoundValid(staffId);
            staff.setDepartment(department);
            staffRepository.save(staff);
            return ResponseEntity.status(HttpStatus.CREATED).body(staff);
        }

        @DeleteMapping("/{staffId}")
        public ResponseEntity removeOneStaffFromDep(@PathVariable long depId, @PathVariable long staffId) {
            Staff staff = staffNotFoundValid(staffId);
            depNotFoundValid(depId);
            if (staff.getDepartment() == null || staff.getDepartment().getId() != depId) {
                throw new ResourceNotFoundException("部门不存在该员工");
            }
            staff.setDepartment(null);
            staffRepository.save(staff);
            return ResponseEntity.ok(staff);
        }


        @GetMapping()
        public ResponseEntity getStaffs(@PathVariable long depId) {
            Department department = depNotFoundValid(depId);
            return ResponseEntity.ok(new HashMap<String, Set>() {{
                put("staffs", staffRepository.findByDepartment(department));
            }});
        }


        private Staff staffNotFoundValid(long id) {
            Staff staff = staffRepository.findOne(id);
            if (staff == null) {
                throw new ResourceNotFoundException("员工资源不存在");
            }
            return staff;
        }

    }


    private void validData(Department department) {
        if (StringUtils.isEmpty(department.getName()) || StringUtils.isEmpty(department.getLogo())) {
            throw new InvalidRequestException("必填参数不能为空");
        }
    }


    private Department depNotFoundValid(long id) {
        Department department = departmentRepository.findOne(id);
        if (department == null) {
            throw new ResourceNotFoundException("部门资源不存在");
        }
        return department;
    }


}
