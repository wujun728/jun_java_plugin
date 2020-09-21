package com.osmp.web.system.servers.service;

import java.util.List;
import java.util.Map;

import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 *
 * @author: wangkaiping
 * @date: 2014年11月27日 上午9:48:16
 */
public interface ServersService {

	public List<Servers> getAllServers(String where);

	public List<Servers> getServersByPage(Pagination<Servers> p);

	public Servers getServers(Servers server);

	public void insert(Servers server);

	public void update(Servers server);

	public void delete(Servers server);

	/**
	 * 启动服务器
	 *
	 * @param command
	 * @return
	 */
	public Map<String, Object> startUp(String serverId);

	/**
	 * 停止服务器
	 *
	 * @param command
	 * @return
	 */
	public Map<String, Object> shutDown(String serverId);
	
	/**
	 * 性能监控，显示数据
	 * @param ip
	 * @param memTotal
	 * @param memUse
	 * @param cpuUse
	 * @param time
	 */
	public void addVmdataMap(String ip, String memTotal, String memUse, String cpuUse, String time);
	
	/**
	 * 获得所有运行状态的服务器
	 * @return
	 */
	public List<Servers> getAllRunServers();

}
