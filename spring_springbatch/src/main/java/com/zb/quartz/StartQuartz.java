package com.zb.quartz;

import java.io.FileNotFoundException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * 启动定时调度
 * 
 * 作者: zhoubang 日期：2015年7月27日 上午9:18:05
 */
public class StartQuartz {

    public static void main(String[] args) throws FileNotFoundException {

        /*
         * new ClassPathXmlApplicationContext(
         * "/net/etongbao/vasp/ac/resources/quartz-context.xml");
         */

        // 测试从csv中读取数据
        /**
         * 该csv事例有问题，目前找不出原因所在.所以就先不要研究csv文件的读写了.请测试研究其他2种吧.
         * 如果哪位朋友找出原因所在，请发我邮箱zhoubang85@163.com，注明主题和内容。thanks
         */
        /*
         * ApplicationContext context = new
         * ClassPathXmlApplicationContext("/spring/applicationContext.xml");
         * JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
         * Job job = (Job) context.getBean("ledgerJob"); try { launcher.run(job,
         * new JobParameters()); } catch (Exception e) { e.printStackTrace(); }
         */

        // 测试读取数据库的ledgers表数据，并输出到控制台
        /**
         * (要想测试写入数据到数据库表，请先清空【ledgers】表的数据. 接下来请修改对应的spring-txt-batch.xml文件配置.
         * 将60行注释的代码段去掉
         * .同时注释该代码段上面的代码.然后再将77-80行的注释去掉即可.最后再执行此main方法测试.看看是否写入了数据库表)
         */
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository((JobRepository) c.getBean("jobRepository"));
        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        try {
            launcher.run((Job) c.getBean("ledgerJob"), new JobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 处理xml文件写入到另一个xml.
        /**
         * 测试之前，请先将/resources/datas目录下的input.xml与output.xml复制到f盘目录下.否则无法测试运行.
         * 目录可以自己指定.
         */
        /*
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository((JobRepository) c.getBean("jobRepository"));
        launcher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        try { // JOB实行 
            JobExecution result = launcher.run((Job) c.getBean("ledgerJob"),
                    new JobParametersBuilder().addString("inputFilePath", "f:\\input.xml").addString("outputFilePath", "f:\\output.xml").toJobParameters());
            // 运行结果输出
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        
        
    }
}
