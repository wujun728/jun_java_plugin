package com.lli.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lli.webservice.CommonService;

@Configuration
public class CxfConfig {
    @Autowired
    private Bus bus;

    @Autowired
    CommonService commonService;

    /** JAX-WS **/
    @Bean
    public Endpoint endpoint2() {
        EndpointImpl endpoint = new EndpointImpl(bus, commonService);
        endpoint.publish("/CommonService");
        return endpoint;
    }

    // @Bean // 修改默认的servlet名称为test 默认为services
    // public ServletRegistrationBean dispatcherServlet() {
    // return new ServletRegistrationBean(new CXFServlet(), "/test/*");
    // }
    //
    // @Bean(name = Bus.DEFAULT_BUS_ID)
    // public SpringBus springBus() {
    // return new SpringBus();
    // }
    //
    // @Bean
    // public UserService userService() {
    // return new UserServiceImpl();
    // }
    //
    // @Bean
    // public Endpoint endpoint() {
    // EndpointImpl endpoint = new EndpointImpl(springBus(), userService());
    // endpoint.publish("/user");
    // return endpoint;
    // }
}