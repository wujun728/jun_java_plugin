package com.feri.fyw.service.intf;

import com.feri.fyw.entity.StudentJob;
import com.feri.fyw.entity.WeekExam;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-18 10:04
 */
public interface StudentJobService {
    //新增
    R save(StudentJob job);
    //新增批量
    R saveBatch(MultipartFile file);
    //查询
    PageBean queryAll(int page,int limit);
    //生成榜单
    void createTop(HttpServletRequest request,OutputStream os);

}
