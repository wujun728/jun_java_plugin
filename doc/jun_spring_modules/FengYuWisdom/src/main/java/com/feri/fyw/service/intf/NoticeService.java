package com.feri.fyw.service.intf;

import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Notice;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;

import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
public interface NoticeService {
    R save(Notice notice, HttpSession session);
    PageBean queryPage(int page,int limit);
    R saveRead(int nid, HttpSession session);

}