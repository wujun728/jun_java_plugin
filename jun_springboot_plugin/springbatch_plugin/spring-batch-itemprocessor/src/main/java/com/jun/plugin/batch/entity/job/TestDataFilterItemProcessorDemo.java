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

/**
 * @author MrBird
 */
@Component
public class TestDataFilterItemProcessorDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ListItemReader<TestData> simpleReader;
    @Autowired
    private TestDataFilterItemProcessor testDataFilterItemProcessor;

    @Bean
    public Job testDataFilterItemProcessorJob() {
        return jobBuilderFactory.get("testDataFilterItemProcessorJob")
                .start(step())
                .build();
    }

    private Step step() {
        return stepBuilderFactory.get("step")
                .<TestData, TestData>chunk(2)
                .reader(simpleReader)
                .processor(testDataFilterItemProcessor)
                .writer(list -> list.forEach(System.out::println))
                .build();
    }
}
