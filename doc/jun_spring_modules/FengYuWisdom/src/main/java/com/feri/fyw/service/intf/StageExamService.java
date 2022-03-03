package com.feri.fyw.service.intf;

import com.feri.fyw.entity.DayExam;
import com.feri.fyw.entity.StageExam;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:04
 */
public interface StageExamService {
    //新增
    R save(StageExam exam);
    //新增批量
    R saveBatch(MultipartFile file);
    //查询
    PageBean queryAll(int page,int limit);
}
