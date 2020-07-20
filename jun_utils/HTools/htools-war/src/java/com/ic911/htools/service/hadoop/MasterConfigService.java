package com.ic911.htools.service.hadoop;

import java.util.Collection;

import javax.annotation.Resource;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ic911.core.commons.BeanValidators;
import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.htools.persistence.hadoop.MasterConfigDao;
/**
 * Hadoop集群主节点配置信息
 * @author caoyang
 */
@Service
@Transactional
public class MasterConfigService {
	@Resource
	private MasterConfigDao masterConfigDao;
	@Autowired
	private Validator validator;
	
	public MasterConfig saveOrUpdate(final MasterConfig config){
		BeanValidators.getValidationExcaption(validator,config);
		MasterConfig masterConfig = masterConfigDao.save(config);
		HadoopClusterServer.putMasterConfig(masterConfig);
		return masterConfig;
	}
	
	public void delete(Long id){
		MasterConfig config = findOne(id);
		masterConfigDao.delete(id);
		HadoopClusterServer.removeMasterConfig(config);
	}
	
	public MasterConfig findOne(Long id){
		return masterConfigDao.findOne(id);
	}
	
	public MasterConfig findOne(String hostname){
		return masterConfigDao.findMasterConfigByHostname(hostname);
	}
	
	public Page<MasterConfig> findAll(Pageable pageable){
		return masterConfigDao.findAll(pageable);
	}
	
	public Collection<MasterConfig> findAll(){
		return masterConfigDao.findAll();
	}
	
	public boolean isExist(String hostname,String ip){
		long count = masterConfigDao.findMasterConfigs(hostname, ip);
		return count >0;
	}
	
}
