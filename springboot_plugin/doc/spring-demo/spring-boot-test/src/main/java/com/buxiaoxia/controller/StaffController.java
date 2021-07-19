package com.buxiaoxia.controller;

import com.buxiaoxia.entity.Department;
import com.buxiaoxia.entity.Staff;
import com.buxiaoxia.repositrory.DepartmentRepository;
import com.buxiaoxia.repositrory.StaffRepository;
import com.buxiaoxia.service.StaffService;
import com.buxiaoxia.utils.exception.InvalidRequestException;
import com.buxiaoxia.utils.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * Created by xw on 2017/9/13.
 * 2017-09-13 16:06
 */
@RestController
@RequestMapping("/api/v1/staffs")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping
    public ResponseEntity addStaff(@RequestBody Staff staff) {
        validData(staff);
        if (staff.getDepartment() != null && staff.getDepartment().getId() != null) {
            Department department = departmentRepository.findOne(staff.getDepartment().getId());
            if (department == null) {
                throw new ResourceNotFoundException("部门不存在");
            }
            staff.setDepartment(department);
        }
        staffRepository.save(staff);
        return ResponseEntity.status(HttpStatus.CREATED).body(staff);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity delStaff(@PathVariable Long id) {
        Staff staff = staffNotFoundValid(id);
        if (staff.getDepartment() != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new HashMap<String, String>() {{
                put("message", "无法删除，先把员工从部门移除");
            }});
        }
        staffRepository.delete(id);
        return ResponseEntity.ok(staff);
    }


    @GetMapping("/{id}")
    public ResponseEntity getStaff(
            @PathVariable Long id,
            @RequestParam(value = "embedJob", required = false, defaultValue = "false") boolean embedJob) {
        Staff staff = staffNotFoundValid(id);
        staffService.getOneStaff(id, embedJob);
        return ResponseEntity.ok(staff);
    }


    @GetMapping("/{staffId}/dep")
    public ResponseEntity getStaffDep(@PathVariable Long staffId) {
        Staff staff = staffNotFoundValid(staffId);
        if (staff.getDepartment() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("message", "部门资源不存在");
            }});
        }
        return ResponseEntity.ok(staff.getDepartment());
    }


    private void validData(Staff staff) {
        if (StringUtils.isEmpty(staff.getName())) {
            throw new InvalidRequestException("必填参数不能为空");
        }
    }


    private Staff staffNotFoundValid(long id) {
        Staff staff = staffRepository.findOne(id);
        if (staff == null) {
            throw new ResourceNotFoundException("员工资源不存在");
        }
        return staff;
    }

}


