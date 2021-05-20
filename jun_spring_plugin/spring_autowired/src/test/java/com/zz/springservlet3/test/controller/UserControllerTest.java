package com.zz.springservlet3.test.controller;


import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest extends BaseControllerTest {

    @Test
    public void get_all() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].username").value("root")).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void get_one() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.username").value("root")).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}
