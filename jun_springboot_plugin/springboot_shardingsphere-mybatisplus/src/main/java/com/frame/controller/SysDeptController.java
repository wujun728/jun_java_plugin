package com.frame.controller;


import com.frame.entity.SysDept;
import com.frame.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 系统部门 前端控制器
 * </p>
 *
 * @author 19047921
 * @since 2021-02-09
 */
@RestController
public class SysDeptController {
    @Autowired
    private ISysDeptService iSysDeptService;

    /**
     * @Description: 批量保存用户
     */
    @PostMapping("save-dept")
    public Object saveUser() {
        return iSysDeptService.save(new SysDept().setName("test部门"));
    }
}
