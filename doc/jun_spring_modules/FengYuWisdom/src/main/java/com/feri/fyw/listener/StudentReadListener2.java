package com.feri.fyw.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.feri.fyw.dao.StudentDao;
import com.feri.fyw.entity.Student;
import org.springframework.scheduling.concurrent.DefaultManagedAwareThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 09:24
 */
public class StudentReadListener2 implements ReadListener<Student> {
    public List<Student> list=new ArrayList<>();
    private StudentDao dao;
    //线程池
    private ThreadPoolExecutor executor;
    public StudentReadListener2(StudentDao dao){
        this.dao=dao;
        //Java官方 可以使用线程池工具类创建线程池，但是阿里开发规范，规定不允许使用工具类
        //Executors.newScheduledThreadPool(10);
        executor=new ThreadPoolExecutor(4,10,5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),new DefaultManagedAwareThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    }
    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {

    }

    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        list.add(student);
        //分片处理
        if(list.size()>=1000) {
            List<Student> l=new ArrayList<>();
            l.addAll(list);
            list.clear();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    dao.insertBatch(l);
                    //记录日志内容
                }
            });
        }
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertBatch(list);
                //记录日志内容
            }
        });
    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return false;
    }
}
