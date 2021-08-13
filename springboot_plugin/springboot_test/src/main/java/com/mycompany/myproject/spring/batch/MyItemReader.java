package com.mycompany.myproject.spring.batch;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.batch.api.chunk.AbstractItemReader;


@StepScope
@Component
public class MyItemReader extends AbstractItemCountingItemStreamItemReader
        implements ResourceAwareItemReaderItemStream, InitializingBean
{


    public Object readItem() throws Exception {

        System.out.println("MyItemReader readItem");

        return null;
    }

    @Override
    public void setResource(Resource resource) {

    }

    @Override
    protected Object doRead() throws Exception {
        return null;
    }

    @Override
    protected void doOpen() throws Exception {

    }

    @Override
    protected void doClose() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
