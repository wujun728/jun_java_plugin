package com.buxiaoxia.controller;

import com.buxiaoxia.Application;
import com.buxiaoxia.client.AvatarClient;
import com.buxiaoxia.client.JobClient;
import com.buxiaoxia.entity.Department;
import com.buxiaoxia.entity.Staff;
import com.buxiaoxia.repositrory.DepartmentRepository;
import com.buxiaoxia.repositrory.StaffRepository;
import com.buxiaoxia.service.StaffService;
import com.buxiaoxia.utils.JacksonUtil;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by xw on 2017/9/14.
 * 2017-09-14 14:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = {Application.class})
public class StaffControllerTest {

    @Mock
    private AvatarClient avatarClient;
    @Mock
    private JobClient jobClient;
    @InjectMocks
    @Autowired
    private StaffService staffService;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;


    private Staff normalStaffWithoutDep, missNameStaffWithoutDep, missDesStaffWithoutDep, normalStaffWithDep,
            missNameStaffWithDep, missDesStaffWithDep, normalStaffWithErrorDep;

    private Department dep1, invalidDep;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

        dep1 = new Department();
        dep1.setDescription("第一部门");
        dep1.setName("第一部门");
        dep1.setLogo("http://123123/1231/1231.png");
        departmentRepository.save(dep1);

        invalidDep = new Department();
        invalidDep.setDescription("不存在的部门");
        invalidDep.setName("不存在的部门");
        invalidDep.setId(9999999999L);

        normalStaffWithoutDep = new Staff();
        normalStaffWithoutDep.setDes("张三是一名员工");
        normalStaffWithoutDep.setName("张三");

        missNameStaffWithoutDep = new Staff();
        missNameStaffWithoutDep.setDes("没有名字的员工");

        missDesStaffWithoutDep = new Staff();
        missDesStaffWithoutDep.setName("李四");


        normalStaffWithDep = new Staff();
        normalStaffWithDep.setDes("张三是一名员工");
        normalStaffWithDep.setName("张三");
        normalStaffWithDep.setDepartment(dep1);


        missNameStaffWithDep = new Staff();
        missNameStaffWithDep.setDes("没有名字的员工");
        missNameStaffWithDep.setDepartment(dep1);


        missDesStaffWithDep = new Staff();
        missDesStaffWithDep.setName("李四");
        missDesStaffWithDep.setDepartment(dep1);


        normalStaffWithErrorDep = new Staff();
        normalStaffWithErrorDep.setDes("张三是一名员工");
        normalStaffWithErrorDep.setName("张三");
        normalStaffWithErrorDep.setDepartment(invalidDep);

    }

    /**
     * 员工添加用例：1-1-正常员工添加（没有部门信息）
     */
    @Test
    public void test_1_1_add_staff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalStaffWithoutDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工添加用例：1-2-员工名字缺失添加（没有部门信息）
     */
    @Test
    public void test_1_2_add_staff_miss_name() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(missNameStaffWithoutDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("必填参数不能为空")));
    }


    /**
     * 员工添加用例：1-3-员工描述缺失添加（没有部门信息）
     */
    @Test
    public void test_1_3_add_staff_miss_des() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(missDesStaffWithoutDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("李四")))
                .andExpect(jsonPath("$.des", nullValue()))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工添加用例：1-4-正常员工添加（携带部门信息）
     */
    @Test
    public void test_1_4_add_staff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalStaffWithDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.department", notNullValue()))
                .andExpect(jsonPath("$.department.name", is("第一部门")))
                .andExpect(jsonPath("$.department.logo", is("http://123123/1231/1231.png")))
                .andExpect(jsonPath("$.department.description", is("第一部门")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工添加用例：1-5-员工名字缺失添加（携带部门信息）
     */
    @Test
    public void test_1_5_add_staff_miss_name() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(missNameStaffWithDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("必填参数不能为空")));
    }


    /**
     * 员工添加用例：1-6-员工描述缺失添加（携带部门信息）
     */
    @Test
    public void test_1_6_add_staff_miss_des() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(missDesStaffWithDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("李四")))
                .andExpect(jsonPath("$.department", notNullValue()))
                .andExpect(jsonPath("$.department.name", is("第一部门")))
                .andExpect(jsonPath("$.department.logo", is("http://123123/1231/1231.png")))
                .andExpect(jsonPath("$.department.description", is("第一部门")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工添加用例：1-7-员工携带不存在的部门信息
     */
    @Test
    public void test_1_7_add_staff_with_error_dep_info() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/staffs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JacksonUtil.stringify(normalStaffWithErrorDep))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门不存在")));
    }


    /**
     * 员工删除用例：2-1-员工没有关联部门，正常删除
     */
    @Test
    public void test_2_1_del_staff() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/staffs/" + normalStaffWithoutDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工删除用例：2-2-员工关联部门，无法删除
     */
    @Test
    public void test_2_2_del_staff_with_dep() throws Exception {
        staffRepository.save(normalStaffWithDep);
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/staffs/" + normalStaffWithDep.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", is("无法删除，先把员工从部门移除")));
    }


    /**
     * 员工删除用例：2-3-员工不存在
     */
    @Test
    public void test_2_3_del_null_staff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/staffs/-1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("员工资源不存在")));
    }


    /**
     * 员工获取用例：3-1-员工获取，不扩展job信息
     */
    @Test
    public void test_3_1_get_one_staff() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        Mockito.when(this.avatarClient.getAvatar(normalStaffWithoutDep.getId())).thenReturn("http://wwww");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithoutDep.getId())
                .param("embedJob", "false")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工获取用例：3-2-员工获取，扩展job信息
     */
    @Test
    public void test_3_2_get_one_staff() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        Staff.Job staffJob = new Staff.Job();
        staffJob.setCapacity("good");
        staffJob.setLevel(1);
        staffJob.setSalary(10000.00d);

        Mockito.when(this.avatarClient.getAvatar(normalStaffWithoutDep.getId())).thenReturn("http://wwww");
        Mockito.when(this.jobClient.getJobInfoByStaffId(normalStaffWithoutDep.getId())).thenReturn(staffJob);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithoutDep.getId())
                .param("embedJob", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.headUrl", is("http://wwww")))
                .andExpect(jsonPath("$.job", notNullValue()))
                .andExpect(jsonPath("$.job.capacity", is("good")))
                .andExpect(jsonPath("$.job.level", is(1)))
                .andExpect(jsonPath("$.job.salary", is(10000.00d)))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工获取用例：3-3-员工不存在获取
     */
    @Test
    public void test_3_3_get_one_null_staff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/-1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("员工资源不存在")));
    }


    /**
     * 员工获取用例：3-4-获取员工头像异常  超时，降级返回
     */
    @Test
    public void test_3_4_get_one_staff_error_header() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithoutDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.headUrl", nullValue()))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    /**
     * 员工获取用例：3-5-获取员工job异常  超时，降级返回
     */
    @Test
    public void test_3_5_get_one_staff_error_job() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        Mockito.when(this.avatarClient.getAvatar(normalStaffWithoutDep.getId())).thenReturn("http://wwww");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithoutDep.getId())
                .param("embedJob", "true")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.headUrl", is("http://wwww")))
                .andExpect(jsonPath("$.job", nullValue()))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工获取用例：3-6-获取员工，员工不存在部门信息
     */
    @Test
    public void test_3_6_get_one_staff_without_dep() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        Mockito.when(this.avatarClient.getAvatar(normalStaffWithoutDep.getId())).thenReturn("http://wwww");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithoutDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.headUrl", is("http://wwww")))
                .andExpect(jsonPath("$.job", nullValue()))
                .andExpect(jsonPath("$.department", nullValue()))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工获取用例：3-7-获取员工，员工存在部门信息
     */
    @Test
    public void test_3_7_get_one_staff_with_dep() throws Exception {
        staffRepository.save(normalStaffWithDep);
        Mockito.when(this.avatarClient.getAvatar(normalStaffWithDep.getId())).thenReturn("http://wwww");
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithDep.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("张三")))
                .andExpect(jsonPath("$.des", is("张三是一名员工")))
                .andExpect(jsonPath("$.headUrl", is("http://wwww")))
                .andExpect(jsonPath("$.department", notNullValue()))
                .andExpect(jsonPath("$.department.id", is(normalStaffWithDep.getDepartment().getId().intValue())))
                .andExpect(jsonPath("$.department.name", is(normalStaffWithDep.getDepartment().getName())))
                .andExpect(jsonPath("$.department.logo", is(normalStaffWithDep.getDepartment().getLogo())))
                .andExpect(jsonPath("$.id", notNullValue()));
    }


    /**
     * 员工获取用例：4-1-获取员工部门，员工存在部门信息
     */
    @Test
    public void test_4_1_get_one_staff_dep() throws Exception {
        staffRepository.save(normalStaffWithDep);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithDep.getId() + "/dep")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(normalStaffWithDep.getDepartment().getId().intValue())))
                .andExpect(jsonPath("$.name", is(normalStaffWithDep.getDepartment().getName())))
                .andExpect(jsonPath("$.logo", is(normalStaffWithDep.getDepartment().getLogo())));
    }


    /**
     * 员工获取用例：4-2-获取员工部门，员工不存在
     */
    @Test
    public void test_4_2_get_one_staff_dep_null_staff() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/-1/dep")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("员工资源不存在")));
    }


    /**
     * 员工获取用例：4-3-获取员工部门，部门不存在
     */
    @Test
    public void test_4_3_get_one_staff_dep_null_dep() throws Exception {
        staffRepository.save(normalStaffWithoutDep);
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/staffs/" + normalStaffWithoutDep.getId() + "/dep")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("部门资源不存在")));
    }


}
