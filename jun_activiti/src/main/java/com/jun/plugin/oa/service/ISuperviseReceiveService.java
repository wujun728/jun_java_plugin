package com.jun.plugin.oa.service;

import java.util.List;

import com.jun.plugin.oa.entity.SuperviseReceive;
import com.jun.plugin.oa.pagination.Page;

public interface ISuperviseReceiveService {

	public List<SuperviseReceive> getListPage(Page<SuperviseReceive> page) throws Exception;
}
