package com.feri.fyw.service.intf;

import com.feri.fyw.entity.Project;
import com.feri.fyw.entity.StudentProject;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
public interface StudentProjectService {
    R save(StudentProject project);
    PageBean queryPage(int page,int limit);

}