package com.ic911.htools.persistence.hadoop;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ic911.htools.entity.hadoop.AlarmMail;

public interface AlarmMailDao extends JpaRepository<AlarmMail,Long>{
	
	@Query("SELECT mail FROM AlarmMail mail WHERE mail.masterConfig.id=?1")
	public Collection<AlarmMail> findAlarmMails(Long masterId);
	
}
