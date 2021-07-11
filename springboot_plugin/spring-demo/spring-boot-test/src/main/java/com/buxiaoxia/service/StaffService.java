package com.buxiaoxia.service;

import com.buxiaoxia.client.AvatarClient;
import com.buxiaoxia.client.JobClient;
import com.buxiaoxia.entity.Staff;
import com.buxiaoxia.repositrory.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xw on 2017/9/13.
 * 2017-09-13 19:03
 */
@Service
public class StaffService {

    @Autowired
    private AvatarClient avatarClient;
    @Autowired
    private JobClient jobClient;

    @Autowired
    private StaffRepository staffRepository;

    public Staff getOneStaff(Long id, boolean embedJob) {
        Staff staff = staffRepository.findOne(id);
        if (staff == null) {
            return null;
        }
        staff.setHeadUrl(avatarClient.getAvatar(staff.getId()));
        if (embedJob) {
            staff.setJob(jobClient.getJobInfoByStaffId(staff.getId()));
        }
        return staff;
    }

}
