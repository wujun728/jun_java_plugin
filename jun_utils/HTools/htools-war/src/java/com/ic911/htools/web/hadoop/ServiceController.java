package com.ic911.htools.web.hadoop;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.HadoopHandler;
import com.ic911.core.hadoop.HadoopNodeManager;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.NodeManagerProxy;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.NodeStatusSynchronizer;
import com.ic911.core.hadoop.TempNode;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.core.hadoop.domain.SecondaryNameNode;
/**
 * Hadoop管理
 * @author changcheng
 */
@Controller
@RequestMapping("/hadoop/service")
public class ServiceController {
	
	Collection<MasterConfig> masters;
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}else{
			if(configs.size()>1){
				return "redirect:/hadoop/service/list";
			}else{
				return "redirect:/hadoop/service/hostname/"+configs.iterator().next().getHostname();
			}
		}
		
	}
	
	/**
	 * hadoop集群列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}
		request.setAttribute("configs", configs);
		return "/hadoop/service/list";
	}
	
	/**
	 * 集群服务管理
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/hostname/{hostname}",method=RequestMethod.GET)
	public String monitor(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
			
			Boolean safeFlag = HadoopHandler.isSafemode(hostname);
			request.setAttribute("isSafe", safeFlag);
			
			request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			return "/hadoop/service/index";
		}
		return "redirect:/index";
	}
	
	
	/**
	 * 启动Hadoop全部服务
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/start_all",method=RequestMethod.POST)
	@ResponseBody
	public Boolean startAll(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopClusterServer.startAll(hostname);
		return b;
	}
	
	/**
	 * 启动只包含DFS的Hadoop服务
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/start_dfs",method=RequestMethod.POST)
	@ResponseBody
	public Boolean startDFS(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopClusterServer.startDFS(hostname);
		return b;
	}
	
	/**
	 * 停止Hadoop服务
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/stop_all",method=RequestMethod.POST)
	@ResponseBody
	public Boolean stopAll(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopClusterServer.stopAll(hostname);
		return b;
	}
	
	/**
	 * 重启Hadoop服务
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/restart_all",method=RequestMethod.POST)
	@ResponseBody
	public Boolean restartAll(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopClusterServer.restartAll(hostname);
		return b;
	}
	
	/**
	 * 查看是否开启安全模式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/is_safemode",method=RequestMethod.POST)
	@ResponseBody
	public Boolean isSafemode(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopHandler.isSafemode(hostname);
		return b;
	}
	
	/**
	 * 强制进入安全模式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/enter_safemode",method=RequestMethod.POST)
	@ResponseBody
	public Boolean enterSafemode(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopHandler.enterSafemode(hostname);
		return b;
	}
	
	/**
	 * 强制退出安全模式
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/leave_safemode",method=RequestMethod.POST)
	@ResponseBody
	public Boolean leaveSafemode(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopHandler.leaveSafemode(hostname);
		return b;
	}
	
	/**
	 * 开启负载均衡
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/start_name_node_balancer",method=RequestMethod.POST)
	@ResponseBody
	public Boolean startNameNodeBalancer(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		Boolean b = HadoopHandler.startNameNodeBalancer(hostname);
		return b;
	}
	
	/**
	 * 通知所有节点重启
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/reboot_all_nodes",method=RequestMethod.POST)
	@ResponseBody
	public Boolean rebootAllNodes(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		HadoopNodeManager manager = NodeManagerProxy.getManager(hostname);
		Boolean b = manager.rebootAllNodes();
		return b;
	}
	
	/**
	 * 通知所有节点关机
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/shutdown_all_nodes",method=RequestMethod.POST)
	@ResponseBody
	public Boolean shutdownAllNodes(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		HadoopNodeManager manager = NodeManagerProxy.getManager(hostname);
		Boolean b = manager.shutdownAllNodes();
		return b;
	}
	
	/**
	 * 通知所有节点清空Hadoop日志信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/clean_all_logs",method=RequestMethod.POST)
	@ResponseBody
	public Boolean cleanAllLogs(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		HadoopNodeManager manager = NodeManagerProxy.getManager(hostname);
		Boolean b = manager.cleanAllLogs();
		return b;
	}
	
	/**
	 * 备份恢复NameNode主节点信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/backup_namenode",method=RequestMethod.POST)
	@ResponseBody
	public Boolean backupNameNode(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		MasterConfig master = HadoopClusterServer.getMasterConfig(hostname);
		NodeStatusSynchronizer synchronizer = NodeMonitor.getSynchronizer();
		NameNode nn = synchronizer.getNodeStatus(hostname);
		SecondaryNameNode snn = nn.getSecondaryNameNode();
		
		TempNode node = new TempNode();
		node.setConfig(master);
		node.setHostname(snn.getHostname());
		node.setIp(snn.getIp());
		
		HadoopNodeManager manager = NodeManagerProxy.getManager(hostname);
		Boolean b = manager.recoverNameNode(node);
		return b;
	}
	
	/**
	 * 格式化NameNode主节点
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/format_name_node",method=RequestMethod.POST)
	@ResponseBody
	public Boolean formatNameNode(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		HadoopNodeManager manager = NodeManagerProxy.getManager(hostname);
		Boolean b = manager.formatNameNodeDaemon();
		return b;
	}
	
}
