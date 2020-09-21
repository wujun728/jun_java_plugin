package com.osmp.web.system.sermapping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.sermapping.dao.SerMappingMapper;
import com.osmp.web.system.sermapping.entity.SerMapping;
import com.osmp.web.system.sermapping.service.SerMappingService;
import com.osmp.web.utils.Pagination;

@Service
public class SerMappingServiceImpl implements SerMappingService {

	@Autowired
	private SerMappingMapper serMappingMapper;
	
	@Override
	public List<SerMapping> getList(Pagination<SerMapping> p, String where) {
		return serMappingMapper.selectByPage(p,SerMapping.class, where);
	}

	@Override
	public void addSerMapping(SerMapping serMapping) {
		serMappingMapper.insert(serMapping);
	}

	@Override
	public void deleteSerMapping(SerMapping serMapping) {
		serMappingMapper.delete(serMapping);
	}
	
	@Override
	public SerMapping getSerMapping(SerMapping serMapping) {
		List<SerMapping> list = serMappingMapper.getObject(serMapping);
        if (null != list) {
            return list.get(0);
        }
        return new SerMapping();
	}

	
	@Override
	public void updateSerMapping(SerMapping serMapping) {
		serMappingMapper.update(serMapping);		
	}

	@Override
	public List<String> getBundels() {
		return serMappingMapper.getBundles();
	}

	@Override
	public List<String> getVersions() {
		return serMappingMapper.getVersions();
	}

	@Override
	public List<String> getServices() {
		return serMappingMapper.getServices();
	}
	
}
