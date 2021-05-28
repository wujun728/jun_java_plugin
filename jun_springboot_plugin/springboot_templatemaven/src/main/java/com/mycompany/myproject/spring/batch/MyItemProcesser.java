package com.mycompany.myproject.spring.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@StepScope
@Component
public class MyItemProcesser implements ItemProcessor {
    @Override
    public Object process(Object item) throws Exception {
        return null;
    }
}
