package com.osmp.web.system.servers.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.base.core.SystemGlobal;
import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.system.servers.dao.ServersMapper;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.entity.VmData;
import com.osmp.web.system.servers.service.ServersService;
import com.osmp.web.utils.Pagination;
import com.osmp.web.utils.Utils;

/**
 * Description:
 *
 * @author: wangkaiping
 * @date: 2014年11月27日 上午9:50:56
 */
@Service
public class ServersServiceImpl implements ServersService {

	private static final int FIRST = 0;//第1个数据
	
	@Autowired
	private ServersMapper serversMapper;

	private final static String START_UP = "./start";
	private final static String SHUT_DOWN = "./stop";

	@Override
	public Map<String, Object> startUp(String serverId) {
		return execSSHCommand(serverId, START_UP);
	}

	@Override
	public Map<String, Object> shutDown(String serverId) {
		return execSSHCommand(serverId, SHUT_DOWN);
	}

	@Override
	public void insert(Servers server) {
		serversMapper.insert(server);
	}

	@Override
	public void update(Servers server) {
		serversMapper.update(server);
	}

	@Override
	public void delete(Servers server) {
		serversMapper.delete(server);
	}

	@Override
	public Servers getServers(Servers server) {
		final List<Servers> list = serversMapper.getObject(server);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return new Servers();

	}

	public List<Servers> getAllServers(String where) {
		return serversMapper.selectAll(Servers.class, where);
	}

	@Override
	public List<Servers> getServersByPage(Pagination<Servers> p) {
		return serversMapper.selectByPage(p, Servers.class, "");
	}

	/**
	 * 执行命令的具体方法
	 *
	 * @param serverId
	 * @param command
	 * @return
	 */
	private Map<String, Object> execSSHCommand(String serverId, String command) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", false);
		result.put("msg", "连接服务器异常");

		Servers server = new Servers();
		server.setId(serverId);

		final List<Servers> list = serversMapper.getObject(server);
		if (null != list) {
			server = list.get(0);
			result = Utils.sshRomoteCommand(server.getServerIp(), server.getSshPort(), server.getUser(),
					server.getPassword(), "source /etc/profile; cd " + server.getCommandPath() + ";" + command);
			if ((boolean) result.get("success")) {
				server.setState(server.getState() == 1 ? 2 : 1);
				update(server);
			}
			return result;
		}

		return result;
	}

	@Override
	public void addVmdataMap(String ip, String memTotal, String memUse,
			String cpuUse, String time) {
		VmData vmdata = new VmData();
		vmdata.setIp(ip);
		vmdata.setMemTotal(Double.parseDouble(memTotal));
		vmdata.setMemUse(Double.parseDouble(memUse));
		vmdata.setCpuUse(Double.parseDouble(cpuUse));
		vmdata.setTime(new Date(Long.parseLong(time)));
		if (SystemGlobal.vmDataMap.containsKey(ip)) {
			LinkedList<VmData> vmdataList = SystemGlobal.vmDataMap.get(ip);
			if (vmdataList.size() == SystemConstant.TIME_COUNT) {
				vmdataList.remove(ServersServiceImpl.FIRST);
				vmdataList.addLast(vmdata);
			}else{
				vmdataList.addLast(vmdata);
			}
			SystemGlobal.vmDataMap.put(ip, vmdataList);
		}else{
			LinkedList<VmData> vmdataList = new LinkedList<VmData>();
			vmdataList.addLast(vmdata);
			SystemGlobal.vmDataMap.put(ip, vmdataList);
		}
	}

	@Override
	public List<Servers> getAllRunServers() {
		List<Servers> list = serversMapper.selectAll(Servers.class, "");
		List<Servers> result = new ArrayList<Servers>();
		for (Servers i : list) {
			if (SystemFrameWork.checkServerState(i.getServerIp())) {
				result.add(i);
			} 
		}
		return result;
	}

}
