package com.flying.cattle.wf.service.impl;

import org.springframework.stereotype.Service;
import com.flying.cattle.wf.aid.ServiceImpl;
import com.flying.cattle.wf.dao.CommodityRepository;
import com.flying.cattle.wf.entity.Commodity;
import com.flying.cattle.wf.service.CommodityService;

@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityRepository, Commodity> implements CommodityService {

}
