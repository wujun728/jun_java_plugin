package com.feri.fyw.service.intf;

import com.feri.fyw.entity.DayExam;
import com.feri.fyw.entity.WeekExam;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:04
 */
public interface WeekExamService {
    //新增
    R save(WeekExam exam);
    //新增批量
    R saveBatch(MultipartFile file);
    //查询
    PageBean queryAll(int page,int limit);
}
