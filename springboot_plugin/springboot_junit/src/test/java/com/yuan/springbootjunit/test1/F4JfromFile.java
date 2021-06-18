/**
 * @Auther: yuanyuan
 * @Date: 2018/9/5 14:43
 * @Description:
 */
package com.yuan.springbootjunit.test1;

import com.yuan.springbootjunit.SpringbootJunitApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = SpringbootJunitApplication.class)
@WebAppConfiguration
public class F4JfromFile  {


    private MockMvc mockMvc;


    @Autowired
    private WebApplicationContext wac;

    private TestContextManager testContextManager;

    @BeforeEach
    public void setUp() throws Exception {
        this.testContextManager = new TestContextManager(getClass());
        this.testContextManager.prepareTestInstance(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }



    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv",numLinesToSkip = 1)
    public void getPersonTest(int id, int exceptId,String result) throws Exception {
        mockMvc.perform(get("/person/getPerson/"+id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("data.id").value(exceptId))
                .andExpect(jsonPath("message").value(result));
    }


}
