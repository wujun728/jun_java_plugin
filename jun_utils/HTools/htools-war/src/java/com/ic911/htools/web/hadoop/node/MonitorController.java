package com.ic911.htools.web.hadoop.node;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.HadoopHandler;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.NodeManagerProxy;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.TempNode;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.core.ssh2.SSH2ShortConnectRunner;
import com.ic911.htools.service.hadoop.MasterConfigService;
/**
 * 节点监控
 * @author caoyang
 */
@Controller
@RequestMapping("/hadoop/node")
public class MonitorController {
	
	@Autowired
	private MasterConfigService masterConfigService;
	
	/**
	 * 选择页面，如果有多个hadoop集群，就进入list选择;
	 * 如果只有一个hadoop集群，就走默认的;
	 * 如果没有，就要提到首页，提示使用配置向导;
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/monitor",method=RequestMethod.GET)
	public String index(HttpServletRequest request,RedirectAttributes redirectAttributes) {
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}else{
			if(configs.size()>1){
				return "redirect:/hadoop/node/monitor/list";
			}else{
				return "redirect:/hadoop/node/monitor/hostname/"+configs.iterator().next().getHostname();
			}
		}
	}
	
	/**
	 * hadoop集群列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/monitor/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}
		request.setAttribute("configs", configs);
		return "/hadoop/node/monitor/list";
	}
	
	/**
	 * 开始监控
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/monitor/hostname/{hostname}",method=RequestMethod.GET)
	public String monitor(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
			request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			request.setAttribute("master", HadoopClusterServer.getMasterConfig(hostname));
			return "/hadoop/node/monitor/index";
		}
		return "redirect:/index";
	}
	
	/**
	 * 暂停监控
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/monitor/pause/hostname/{hostname}",method=RequestMethod.GET)
	public String pause(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			MasterConfig masterConfig = masterConfigService.findOne(hostname);
			masterConfig.setEnabled(false);
			masterConfigService.saveOrUpdate(masterConfig);
		}
		return "redirect:/hadoop/node/monitor/list";
	}
	
	/**
	 * 继续监控
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/monitor/restore/hostname/{hostname}",method=RequestMethod.GET)
	public String restore(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			MasterConfig masterConfig = masterConfigService.findOne(hostname);
			masterConfig.setEnabled(true);
			masterConfigService.saveOrUpdate(masterConfig);
		}
		return "redirect:/hadoop/node/monitor/list";
	}
	
	

	/**
	 * 进行节点退役
	 * @author murenchao
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/monitor/adddecommis",method=RequestMethod.POST)
	@ResponseBody
	public Object addDecommis(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String hostname = request.getParameter("hostname");
		String ip = request.getParameter("ip");
		String namenode = request.getParameter("namenode");
		System.out.println(hostname);
		TempNode tempNode = new TempNode();
		tempNode.setConfig(HadoopClusterServer.getMasterConfig(namenode));
		tempNode.setHostname(hostname);
		tempNode.setIp(ip);
		Map<String,Object> s = new HashMap<String,Object>();
		s.put("status", false);
		if(NodeManagerProxy.getManager().removeDynamicDataNode(tempNode) && HadoopHandler.refreshDFSNodes(tempNode.getConfig().getHostname())){
			s.put("status", true);
	    }//end if
		return s;
	}

	/**
	 * 显示日志
	 * @author murenchao
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/monitor/showlog",method=RequestMethod.POST)
	@ResponseBody
	public Object showLog(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String type = request.getParameter("type");
		String ip = request.getParameter("ip");
		String namenode = request.getParameter("namenode");
		String hostname = request.getParameter("hname");		
		System.out.println(namenode+" "+hostname+" "+ip+" type"+type);
		TempNode tempNode = new TempNode();
		tempNode.setConfig(HadoopClusterServer.getMasterConfig(namenode));
		Map<String,Object> logs = new HashMap<String,Object>();
		SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(hostname,ip, 22, tempNode.getConfig().getSshUsername(), tempNode.getConfig().getSshPassword(), tempNode.getConfig().getEncode());
		if(type.equals("namenode")){
		  logs.put("logs",runner.runResultMultiLine("source /etc/profile; cat $HADOOP_HOME/logs/*-namenode-"+hostname+".log"));
		}else if(type.equals("jobtracker")){
		  logs.put("logs",runner.runResultMultiLine("source /etc/profile;cat $HADOOP_HOME/logs/*-jobtracker-"+hostname+".log"));
		}else if(type.equals("secondaryname")){
			logs.put("logs",runner.runResultMultiLine("source /etc/profile;cat  $HADOOP_HOME/logs/*-secondarynamenode-"+hostname+".log"));
		}else if(type.equals("datanode")){
			logs.put("logs",runner.runResultMultiLine("source /etc/profile;cat $HADOOP_HOME/logs/*-datanode-"+hostname+".log"));
		}

		return logs;
	}
}
