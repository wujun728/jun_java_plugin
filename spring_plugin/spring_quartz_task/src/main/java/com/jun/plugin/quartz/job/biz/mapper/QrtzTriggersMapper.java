package com.jun.plugin.quartz.job.biz.mapper;

import java.util.List;

import com.jun.plugin.quartz.job.biz.model.QrtzTriggers;

public interface QrtzTriggersMapper extends BaseMapper<String, QrtzTriggers> {

    List<QrtzTriggers> findAll(QrtzTriggers qrtzTriggers);

}
