package com.jun.plugin.batch.entity.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.jun.plugin.batch.entity.TestData;
import com.jun.plugin.batch.processor.TestDataFilterItemProcessor;
import com.jun.plugin.batch.processor.TestDataTransformItemPorcessor;

/**
 * @author MrBird
 */
@Component
public class TestDataTransformItemPorcessorDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<TestData> simpleReader;
    @Autowired
    private TestDataTransformItemPorcessor testDataTransformItemPorcessor;

    @Bean
    public Job testDataTransformItemPorcessorJob() {
        return jobBuilderFactory.get("testDataTransformItemPorcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<TestData, TestData>chunk(2)
                .reader(simpleReader)
                .processor(testDataTransformItemPorcessor)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
