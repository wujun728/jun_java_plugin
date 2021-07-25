package com.job.biz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.biz.mapper.CustomerMapper;
import com.job.biz.model.Customer;
import com.job.common.datasource.DataSource;
import com.job.common.datasource.DataSourceEnum;

@Service("customerService")
@DataSource(DataSourceEnum.QUARTZ)
public class CustomerService extends DefaultService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public String getClassName() {
        return "customerService";
    }

    @Override
    @PostConstruct
    public void addService() {
        log.info("#CustomerService.class add to ServerBuilderContext.servers");
        ServerBuilderContext.addServer(getClassName(), CustomerService.class);
    }

    @Override
    public void execute() {
        log.info("#begin {}.execute()", getClassName());
        // 这里执行定时调度业务
        Map<String, Object> map = new HashMap<String, Object>();
        List<Customer> list = customerMapper.findAllByFilter(map);
        for (Customer customer : list) {
            log.info("#customer id={} , name={} , email={} , age={}", customer.getId(), customer.getName(), customer.getEmail(), customer.getAge());
        }
        log.info("#end {}.execute()\r\t", getClassName());
    }

}
