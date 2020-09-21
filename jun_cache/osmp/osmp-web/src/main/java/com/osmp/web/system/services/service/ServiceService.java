package com.osmp.web.system.services.service;

import java.util.List;

import com.osmp.web.system.services.entity.Service;
import com.osmp.web.utils.Pagination;

public interface ServiceService {

	public List<Service> getList(Pagination<Service> p, String where);
}
