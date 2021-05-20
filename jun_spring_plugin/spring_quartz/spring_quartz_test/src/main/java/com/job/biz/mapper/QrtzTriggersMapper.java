package com.job.biz.mapper;

import java.util.List;

import com.job.biz.model.QrtzTriggers;

public interface QrtzTriggersMapper extends BaseMapper<String, QrtzTriggers> {

    List<QrtzTriggers> findAll(QrtzTriggers qrtzTriggers);

}
