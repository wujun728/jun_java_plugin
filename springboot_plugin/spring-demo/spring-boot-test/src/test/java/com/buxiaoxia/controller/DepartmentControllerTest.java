package com.buxiaoxia.controller;

import com.buxiaoxia.Application;
import com.buxiaoxia.entity.Department;
import com.buxiaoxia.entity.Staff;
import com.buxiaoxia.repositrory.DepartmentRepository;
import com.buxiaoxia.repositrory.StaffRepository;
import com.buxiaoxia.utils.JacksonUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 18:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = {Application.class})
public class DepartmentControllerTest {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;

    private Department normalDep, missNameDep, missLogoDep, normalModDep, normalModDepWithId, modMissNameDep, modMissLogoDep;

    private Staff normalStaffWithoutDep, normalStaffWithoutDep2;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

        normalDep = new Department();
        normalDep.setName("第一部门");
        normalDep.setDescription("当前是第一部门");
        normalDep.setLogo("http://jyx365.cn/pic/log1.png");

        missNameDep = new Department();
        missNameDep.setDescription("缺失名字的部门");
        missNameDep.setLogo("http://jyx365.cn/pic/missname.png");

        missLogoDep = new Department();
        missLogoDep.setDescription("缺失logo的部门");
        missLogoDep.setName("缺失logo的部门");


        normalModDep = new Department();
        normalModDep.setName("第一部门-修改版");
        normalModDep.setDescription("当前是第一部门-修改版");
        normalModDep.setLogo("http://jyx365.cn/pic/log123.png");

        normalModDepWithId = new Department();
        normalModDepWithId.setId(9999999999L);
        normalModDepWithId.setName("第一部门-修改版");
        normalModDepWithId.setDescription("当前是第一部门-修改版");
        normalModDepWithId.setLogo("http://jyx365.cn/pic/log123.png");


        modMissNameDep = new Department();
        modMissNameDep.setDescription("缺失名字的部门");
        modMissNameDep.setLogo("http://jyx365.cn/pic/missname.png");


        modMissLogoDep = new Department();
        modMissLogoDep.setDescription("缺失logo的部门");
        modMissLogoDep.setName("缺失logo的部门");


        normalStaffWithoutDep = new Staff();
        normalStaffWithoutDep.setDes("张三是一名员工");
        normalStaffWithoutDep.setName("张三");

        normalStaffWithoutDep2 = new Staff();
        normalStaffWithoutDep2.setDes("李四是一名员工");
        normalStaffWithoutDep2.setName("李四");

    }


    /**
     * 部门添加用例：1-1-正常部门添加
     */
    @Test
    public void test_1_1_add_dep() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/deps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("第一部门")))
                .andExpect(jsonPath("$.description", is("当前是第一部门")))
                .andExpect(jsonPath("$.logo", is("http://jyx365.cn/pic/log1.png")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    /**
     * 部门添加用例：1-2-部门名字缺失
     */
    @Test
    public void test_1_2_add_dep_miss_name() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/deps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(missNameDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("必填参数不能为空")));
    }

    /**
     * 部门添加用例：1-3-部门logo缺失
     */
    @Test
    public void test_1_3_add_dep_miss_logo() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/deps")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(missLogoDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("必填参数不能为空")));
    }

    /**
     * 单个部门获取用例：2-1-部门正常获取
     */
    @Test
    public void test_2_1_get_one_dep() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/" + normalDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("第一部门")))
                .andExpect(jsonPath("$.description", is("当前是第一部门")))
                .andExpect(jsonPath("$.logo", is("http://jyx365.cn/pic/log1.png")))
                .andExpect(jsonPath("$.id", is(normalDep.getId().intValue())));
    }


    /**
     * 单个部门获取用例：2-2-获取不存在部门
     */
    @Test
    public void test_2_2_get_null_dep() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }


    /**
     * 单个部门获取用例：2-3-获取异常id的部门
     */
    @Test
    public void test_2_3_get_error_id_dep() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/adfsdfsasdf")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }


    /**
     * 单个部门删除例：3-1-正常删除
     */
    @Test
    public void test_3_1_del_dep() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/deps/" + normalDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("第一部门")))
                .andExpect(jsonPath("$.description", is("当前是第一部门")))
                .andExpect(jsonPath("$.logo", is("http://jyx365.cn/pic/log1.png")))
                .andExpect(jsonPath("$.id", is(normalDep.getId().intValue())));
    }


    /**
     * 单个部门删除例：3-2-部门不存在部门
     */
    @Test
    public void test_3_2_del_null_dep() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }


    /**
     * 部门修改用例：4-1-正常部门修改
     */
    @Test
    public void test_4_1_mod_dep() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/deps/" + normalDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalModDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("第一部门-修改版")))
                .andExpect(jsonPath("$.description", is("当前是第一部门-修改版")))
                .andExpect(jsonPath("$.logo", is("http://jyx365.cn/pic/log123.png")))
                .andExpect(jsonPath("$.id", is(normalDep.getId().intValue())));
    }


    /**
     * 部门修改用例：4-2-正常部门修改-携带其他id
     */
    @Test
    public void test_4_2_mod_with_id_dep() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/deps/" + normalDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalModDepWithId))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("第一部门-修改版")))
                .andExpect(jsonPath("$.description", is("当前是第一部门-修改版")))
                .andExpect(jsonPath("$.logo", is("http://jyx365.cn/pic/log123.png")))
                .andExpect(jsonPath("$.id", is(normalDep.getId().intValue())));
    }


    /**
     * 部门修改用例：4-3-部门不存在部门
     */
    @Test
    public void test_4_3_mod_null_dep() throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/deps/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalModDepWithId))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }


    /**
     * 部门修改用例：4-4-部门名称不存在
     */
    @Test
    public void test_4_4_mod_dep_miss_name() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/deps/" + normalDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(modMissNameDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("必填参数不能为空")));
    }

    /**
     * 部门修改用例：4-4-部门名称不存在
     */
    @Test
    public void test_4_5_mod_dep_miss_logo() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.put("/api/v1/deps/" + normalDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(modMissLogoDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("必填参数不能为空")));
    }


    /**
     * 部门添加员工用例：5-1-部门添加员工
     */
    @Test
    public void test_5_1_dep_add_staff() throws Exception {
        departmentRepository.save(normalDep);
        staffRepository.save(normalStaffWithoutDep);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/deps/" + normalDep.getId() + "/staffs/" + normalStaffWithoutDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(normalStaffWithoutDep.getId().intValue())))
                .andExpect(jsonPath("$.des", is(normalStaffWithoutDep.getDes())))
                .andExpect(jsonPath("$.name", is(normalStaffWithoutDep.getName())))
                .andExpect(jsonPath("$.department.id", is(normalDep.getId().intValue())));

    }

    /**
     * 部门添加员工用例：5-2-部门添加员工-员工不存在
     */
    @Test
    public void test_5_2_dep_add_staff_null_staff() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/deps/" + normalDep.getId() + "/staffs/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("员工资源不存在")));
    }

    /**
     * 部门添加员工用例：5-3-部门添加员工-部门不存在
     */
    @Test
    public void test_5_3_dep_add_staff_null_dep() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/deps/-1/staffs/" + normalStaffWithoutDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }


    /**
     * 部门移除员工用例：6-1-部门移除员工
     */
    @Test
    public void test_6_1_dep_remove_staff() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        departmentRepository.save(normalDep);
        normalDep.setStaffs(new HashSet<Staff>() {{
            add(normalStaffWithoutDep);
        }});
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/deps/" + normalDep.getId() + "/staffs/" + normalStaffWithoutDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(normalStaffWithoutDep.getId().intValue())))
                .andExpect(jsonPath("$.des", is(normalStaffWithoutDep.getDes())))
                .andExpect(jsonPath("$.name", is(normalStaffWithoutDep.getName())))
                .andExpect(jsonPath("$.department", nullValue()));
    }


    /**
     * 部门移除员工用例：6-2-部门移除员工 部门不不存在
     */
    @Test
    public void test_6_2_dep_remove_staff_null_dep() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/deps/-1/staffs/" + normalStaffWithoutDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }

    /**
     * 部门移除员工用例：6-3-部门移除员工 员工不不存在
     */
    @Test
    public void test_6_3_dep_remove_staff_null_staff() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/deps/" + normalDep.getId() + "/staffs/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("员工资源不存在")));
    }


    /**
     * 部门获取员工用例：7-1-获取部门员工
     */
    @Test
    public void test_7_1_dep_get_staff() throws Exception {
        departmentRepository.save(normalDep);
        normalStaffWithoutDep.setDepartment(normalDep);
        staffRepository.save(normalStaffWithoutDep);
        normalStaffWithoutDep2.setDepartment(normalDep);
        staffRepository.save(normalStaffWithoutDep2);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/" + normalDep.getId() + "/staffs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.staffs", notNullValue()))
                .andExpect(jsonPath("$.staffs.length()", is(2)))
                .andExpect(jsonPath("$.staffs[0].id", is(normalStaffWithoutDep.getId().intValue())))
                .andExpect(jsonPath("$.staffs[0].name", is(normalStaffWithoutDep.getName())))
                .andExpect(jsonPath("$.staffs[0].des", is(normalStaffWithoutDep.getDes())))
                .andExpect(jsonPath("$.staffs[1].id", is(normalStaffWithoutDep2.getId().intValue())))
                .andExpect(jsonPath("$.staffs[1].name", is(normalStaffWithoutDep2.getName())))
                .andExpect(jsonPath("$.staffs[1].des", is(normalStaffWithoutDep2.getDes())));
    }

    /**
     * 部门获取员工用例：7-2-获取部门员工，员工数为空
     */
    @Test
    public void test_7_2_dep_get_staff() throws Exception {
        departmentRepository.save(normalDep);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/" + normalDep.getId() + "/staffs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.staffs", notNullValue()))
                .andExpect(jsonPath("$.staffs.length()", is(0)));
    }

    /**
     * 部门获取员工用例：7-3-获取部门员工，部门不存在
     */
    @Test
    public void test_7_3_dep_get_staff_null_dep() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/deps/-1/staffs")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }
}
