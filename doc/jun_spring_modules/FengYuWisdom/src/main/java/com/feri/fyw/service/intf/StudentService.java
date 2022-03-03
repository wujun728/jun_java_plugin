package com.feri.fyw.service.intf;

import com.feri.fyw.entity.Student;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
public interface StudentService {
    R save(Student student);
    PageBean queryPage();
    R saveBatch(MultipartFile file);
    R saveBatchV2(MultipartFile file);

    R queryTjSex();
    R queryTjPersons();
}
