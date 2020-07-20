package com.ic911.htools.persistence.hadoop;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ic911.htools.entity.hadoop.MonitorStatus;

/**
 * 监控状态信息
 * @author murenchao
 *
 */
public interface MonitorStatusDao extends JpaRepository<MonitorStatus,Long>{
	
}

