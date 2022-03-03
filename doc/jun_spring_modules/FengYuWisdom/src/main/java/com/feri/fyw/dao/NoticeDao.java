package com.feri.fyw.dao;

import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Notice;

import java.util.List;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-17 10:53
 */
public interface NoticeDao {
    int insert(Notice notice);
    List<Notice> selectAll();
}
