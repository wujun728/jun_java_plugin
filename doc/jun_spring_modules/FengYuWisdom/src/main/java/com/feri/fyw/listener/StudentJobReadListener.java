package com.feri.fyw.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.feri.fyw.entity.StageExam;
import com.feri.fyw.entity.StudentJob;

import java.util.Map;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-22 09:30
 */
public class StudentJobReadListener implements ReadListener<StudentJob> {
    @Override
    public void onException(Exception e, AnalysisContext analysisContext) throws Exception {

    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {

    }

    @Override
    public void invoke(StudentJob job, AnalysisContext analysisContext) {

    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return true;
    }
}
