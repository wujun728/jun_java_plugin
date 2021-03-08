package com.lmy.demo.batch;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lmy.demo.listener.JobListener;
import com.lmy.demo.pojo.PostPojo;
import com.lmy.demo.pojo.PostPojo;
import com.lmy.demo.rest.RestService;
import com.lmy.demo.utils.JSONUtils;

/**
 * Created by EalenXie on 2018/9/10 14:50.
 * :@EnableBatchProcessing提供用于构建批处理作业的基本配置
 */
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class DataBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataBatchConfiguration.class);
    
    @Resource
    private JobBuilderFactory jobBuilderFactory;    //用于构建JOB

    @Resource
    private StepBuilderFactory stepBuilderFactory;  //用于构建Step

    @Resource
    private EntityManagerFactory emf;           //注入实例化Factory 访问数据

    @Resource
    private JobListener jobListener;            //简单的JOB listener
    
    @Autowired
    private RestService restService;

    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    @Bean
    public Job dataHandleJob() {
        return jobBuilderFactory.get("dataHandleJob").
                incrementer(new RunIdIncrementer()).
                start(handleDataStep()).    //start是JOB执行的第一个step
//                next(xxxStep()).
//                next(xxxStep()).
//                ...
        listener(jobListener).      //设置了一个简单JobListener
                build();
    }

    /**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     */
    @Bean
    public Step handleDataStep() {
        return stepBuilderFactory.get("getData").
                <PostPojo, PostPojo>chunk(5).        // <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
                faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(100).skip(Exception.class). //捕捉到异常就重试,重试100次还是异常,JOB就停止并标志失败
                reader(getDataReader()).         //指定ItemReader
                processor(getDataProcessor()).   //指定ItemProcessor
                writer(getDataWriter()).         //指定ItemWriter
                build();
    }

    @Bean
    public ItemReader<PostPojo> getDataReader() {
    	String result=restService.getPosts();
    	ListItemReader<PostPojo> listItem=new ListItemReader<PostPojo>(
    			JSONUtils.stringToList(result, new TypeReference<List<PostPojo>>() {}));
//        //读取数据,这里可以用JPA,JDBC,JMS 等方式 读入数据
//        JpaPagingItemReader<PostPojo> reader = new JpaPagingItemReader<>();
//        //这里选择JPA方式读数据 一个简单的 native SQL
//        String sqlQuery = "SELECT * FROM PostPojo";
//        try {
//            JpaNativeQueryProvider<PostPojo> queryProvider = new JpaNativeQueryProvider<>();
//            queryProvider.setSqlQuery(sqlQuery);
//            queryProvider.setEntityClass(PostPojo.class);
//            queryProvider.afterPropertiesSet();
//            reader.setEntityManagerFactory(emf);
//            reader.setPageSize(3);
//            reader.setQueryProvider(queryProvider);
//            reader.afterPropertiesSet();
//            //所有ItemReader和ItemWriter实现都会在ExecutionContext提交之前将其当前状态存储在其中,如果不希望这样做,可以设置setSaveState(false)
//            reader.setSaveState(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return listItem;
    }

    @Bean
    public ItemProcessor<PostPojo, PostPojo> getDataProcessor() {
        return new ItemProcessor<PostPojo, PostPojo>() {
            @Override
            public PostPojo process(PostPojo PostPojo) throws Exception {
                log.info("processor data : " + PostPojo.toString());  //模拟  假装处理数据,这里处理就是打印一下
                return PostPojo;
            }
        };
//        lambda也可以写为:
//        return PostPojo -> {
//            log.info("processor data : " + PostPojo.toString());
//            return PostPojo;
//        };
    }

    @Bean
    public ItemWriter<PostPojo> getDataWriter() {
        return list -> {
            for (PostPojo PostPojo : list) {
                log.info("write data : " + PostPojo); //模拟 假装写数据 ,这里写真正写入数据的逻辑
            }
        };
    }
}