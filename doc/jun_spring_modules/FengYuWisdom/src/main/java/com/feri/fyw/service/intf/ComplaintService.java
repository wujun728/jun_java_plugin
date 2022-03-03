package com.feri.fyw.service.intf;

import com.feri.fyw.entity.Complaint;
import com.feri.fyw.entity.Subject;
import com.feri.fyw.vo.PageBean;
import com.feri.fyw.vo.R;

import javax.servlet.http.HttpSession;

/**
 * @program: FengYuWisdom
 * @description:
 * @author: Feri(邢朋辉)
 * @create: 2021-06-16 09:52
 */
public interface ComplaintService {
    R save(Complaint complaint, HttpSession session);
    PageBean queryPage(String msg,int page,int limit);
    R change(Complaint complaint, HttpSession session);
}