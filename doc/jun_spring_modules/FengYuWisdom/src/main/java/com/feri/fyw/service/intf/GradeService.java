package com.feri.fyw.service.intf;

import com.feri.fyw.entity.Grade;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
public interface GradeService {
    R save(Grade grade);
    PageBean queryPage(int page,int limit);
}
