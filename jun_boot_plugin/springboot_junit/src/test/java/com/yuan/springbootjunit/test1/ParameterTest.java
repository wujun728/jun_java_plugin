/**
 * @Auther: yuanyuan
 * @Date: 2018/9/5 10:12
 * @Description:
 */
package com.yuan.springbootjunit.test1;


import com.yuan.springbootjunit.SpringbootJunitApplication;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(Parameterized.class)
//1.更改默认的测试运行器为RunWith(Parameterized.class)
@SpringBootTest(classes = SpringbootJunitApplication.class)
@WebAppConfiguration
public class ParameterTest {

    //2.声明变量存放预期值和测试数据
    private int id ;
    private int exceptId;
    private String result;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }




    //3.声明一个返回值 为Collection的公共静态方法，并使用@Parameters进行修饰
    @Parameters
    public static Collection<Object> data() {
        return Arrays.asList(new Object[][]{
                {1, 1,"查询成功"},
                {2, 1,"查询"},
                {2, 2,"查询成功"},
                {3, 3,"查询失败"}
        });
    }

    //4.为测试类声明一个带有参数的公共构造函数，并在其中为之声明变量赋值
    public ParameterTest(int id,int exceptId,String result) {
        this.id = id;
        this.exceptId = exceptId;
        this.result = result;
    }


    //5.运行测试方法，即可完成对多组数据的测试
    @Test
    public void getPersonTest() throws Exception {
        mockMvc.perform(get("/person/getPerson/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(exceptId))
                .andExpect(jsonPath("message").value(result));
    }





}