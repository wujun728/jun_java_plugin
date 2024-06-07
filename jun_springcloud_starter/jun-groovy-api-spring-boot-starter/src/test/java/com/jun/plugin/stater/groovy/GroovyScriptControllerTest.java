//package com.jun.plugin.stater.groovy;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
///**
// *  groovy 脚本引擎测试
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = ApiGroovyScriptConfiguration.class)
//public class GroovyScriptControllerTest {
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext context;
//    @Autowired
//
//    @Before
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//    @Test
//    public void test() throws Exception {
//        String groovyScript = "spring.getBean(\"scriptEngineManager\").toString()";
//        MvcResult mvcResult = mockMvc.perform(post("/groovy").param("script", groovyScript))
//                .andExpect(status().isOk())
//                .andReturn();
//        System.out.println(mvcResult.getResponse().getContentAsString());
//    }
//}
