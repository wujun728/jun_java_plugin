package com.ic911.htools.persistence.hadoop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ic911.core.hadoop.MasterConfig;
/**
 * 主节点配置信息
 * @author caoyang
 */
public interface MasterConfigDao extends JpaRepository<MasterConfig,Long>{
	
	@Query("SELECT COUNT(master.id) FROM MasterConfig master WHERE master.hostname=?1 OR master.ip=?2")
	public long findMasterConfigs(String hostname,String ip);
	
	@Query("SELECT master FROM MasterConfig master WHERE master.hostname=?1")
	public MasterConfig findMasterConfigByHostname(String hostname);
	
}
