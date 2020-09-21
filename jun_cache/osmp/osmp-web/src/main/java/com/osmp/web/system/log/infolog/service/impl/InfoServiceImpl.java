package com.osmp.web.system.log.infolog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.log.infolog.dao.InfoMapper;
import com.osmp.web.system.log.infolog.entity.InfoLog;
import com.osmp.web.system.log.infolog.service.InfoService;
import com.osmp.web.utils.Pagination;
import com.osmp.web.utils.Utils;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 上午10:13:25
 */
@Service
public class InfoServiceImpl implements InfoService {

	@Autowired
	private InfoMapper infoMapper;

	@Override
	public List<InfoLog> getList(Pagination<InfoLog> p, InfoLog infoLog, String startTime, String endTime) {
		String sql = "";
		if (!Utils.isEmpty(startTime) && !Utils.isEmpty(endTime)) {
			sql += "insertTime between '" + startTime + "' and '" + endTime + "' ";
		}
		String service = infoLog.getService();
		String loadIp = infoLog.getLoadIp();
		String bundle = infoLog.getBundle();
		if (!Utils.isEmpty(service)) {
			if (Utils.isEmpty(sql)) {
				sql += " service = '" + service + "' ";
			} else {
				sql += " and service = '" + service + "' ";
			}
		}
		if (!Utils.isEmpty(loadIp)) {
			if (Utils.isEmpty(sql)) {
				sql += " loadIp = '" + loadIp + "' ";
			} else {
				sql += " and loadIp = '" + loadIp + "' ";
			}
		}
		if (!Utils.isEmpty(bundle)) {
			if (Utils.isEmpty(sql)) {
				sql += " bundle = '" + bundle + "' ";
			} else {
				sql += " and bundle = '" + bundle + "' ";
			}
		}
		sql += "order by occurTime desc";

		return infoMapper.selectByPage(p, InfoLog.class, sql);
	}

	@Override
	public InfoLog get(InfoLog p) {
		List<InfoLog> list = infoMapper.getObject(p);
		if (null != list && list.size() == 1) {
			return list.get(0);
		}
		return new InfoLog();
	}

	@Override
	public void delete(InfoLog p) {
		infoMapper.delete(p);
	}

}
