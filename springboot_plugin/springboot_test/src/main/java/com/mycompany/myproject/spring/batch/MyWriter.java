package com.mycompany.myproject.spring.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
public class MyWriter implements ItemWriter {
    @Override
    public void write(List items) throws Exception {

        System.out.println("MyWriter write");
    }
}
