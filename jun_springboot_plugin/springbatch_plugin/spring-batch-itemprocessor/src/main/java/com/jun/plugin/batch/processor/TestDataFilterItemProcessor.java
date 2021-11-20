package com.jun.plugin.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.jun.plugin.batch.entity.TestData;

/**
 * @author MrBird
 */
@Component
public class TestDataFilterItemProcessor implements ItemProcessor<TestData, TestData> {
    @Override
    public TestData process(TestData item) {
        // 返回null，会过滤掉这条数据
        return "".equals(item.getField3()) ? null : item;
    }
}
