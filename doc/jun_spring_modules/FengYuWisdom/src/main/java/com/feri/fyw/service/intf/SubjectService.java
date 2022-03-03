package com.feri.fyw.service.intf;

import com.feri.fyw.entity.Student;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
public interface SubjectService {
    R save(Subject subject);
    PageBean queryPage(int page,int limit);
    R change(Subject subject);
}
