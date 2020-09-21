package com.osmp.web.system.log.errorlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.log.errorlog.dao.ErrorLogMapper;
import com.osmp.web.system.log.errorlog.entity.ErrorLog;
import com.osmp.web.system.log.errorlog.service.ErrorLogService;
import com.osmp.web.utils.Pagination;
import com.osmp.web.utils.Utils;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 上午11:29:07
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

	@Autowired
	private ErrorLogMapper mapper;

	@Override
	public List<ErrorLog> getList(Pagination<ErrorLog> p, ErrorLog errorLog, String startTime, String endTime) {
		String sql= "";
		if (!Utils.isEmpty(startTime) && !Utils.isEmpty(endTime)) {
			sql+="insertTime between '"+startTime+"' and '"+endTime+"' ";
		}
		String service = errorLog.getService();
		String loadIp = errorLog.getLoadIp();
		String bundle = errorLog.getBundle();
		if (!Utils.isEmpty(service)) {
			if(Utils.isEmpty(sql)){
				sql+=" service like '%"+service+"%' ";
			}else{
				sql+=" and service like '%"+service+"%' ";
			}
		}
		if (!Utils.isEmpty(loadIp)) {
			if(Utils.isEmpty(sql)){
				sql+=" loadIp like '%"+loadIp+"%' ";
			}else{
				sql+=" and loadIp like '%"+loadIp+"%' ";
			}
		}
		if (!Utils.isEmpty(bundle)) {
			if(Utils.isEmpty(sql)){
				sql+=" bundle like '%"+bundle+"%' ";
			}else{
				sql+=" and bundle like '%"+bundle+"%' ";
			}
		}
		sql += " order by insertTime desc";
		return mapper.selectByPage(p, ErrorLog.class, sql);
	}

	@Override
	public void insert(ErrorLog p) {
		mapper.insert(p);
	}

	@Override
	public void update(ErrorLog p) {
		mapper.update(p);
	}

	@Override
	public void delete(ErrorLog p) {
		mapper.delete(p);
	}

	@Override
	public ErrorLog get(ErrorLog p) {
		List<ErrorLog> logs = mapper.getObject(p);
		if (null != logs && logs.size() == 1) {
			return logs.get(0);
		} else {
			return new ErrorLog();
		}
	}
}
