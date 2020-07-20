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
import com.ic911.htools.entity.hadoop.AlarmMail;
import com.ic911.htools.persistence.hadoop.AlarmMailDao;
/**
 * 告警邮箱配置
 * @author caoyang
 */
@Service
@Transactional
public class AlarmMailService {
	@Resource
	private AlarmMailDao alarmMailDao;
	@Autowired
	private Validator validator;
	
	public void saveOrUpdate(AlarmMail mail){
		BeanValidators.getValidationExcaption(validator,mail);
		alarmMailDao.save(mail);
	}
	
	public void delete(Long id){
		alarmMailDao.delete(id);
	}
	
	public AlarmMail findOne(Long id){
		return alarmMailDao.findOne(id);
	}
	
	public Page<AlarmMail> findAll(Pageable pageable){
		return alarmMailDao.findAll(pageable);
	}
	
	public Collection<AlarmMail> findAll(){
		return alarmMailDao.findAll();
	}
	
	public Collection<AlarmMail> findAlarmMails(Long masterId){
		return alarmMailDao.findAlarmMails(masterId);
	}
	
}
