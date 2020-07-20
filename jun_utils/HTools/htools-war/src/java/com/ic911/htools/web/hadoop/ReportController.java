package com.ic911.htools.web.hadoop;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.HadoopNodeManager;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.NodeInfo;
import com.ic911.core.hadoop.NodeManagerProxy;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.htools.commons.FileFormatUtils;
import com.ic911.htools.entity.hadoop.NodeStatusReport;
import com.ic911.htools.entity.hadoop.Report;
import com.ic911.htools.service.hadoop.ReportService;
/**
 * 报表业务类
 * @author changcheng
 */
@Controller
@RequestMapping("/hadoop/report")
public class ReportController {
	
	private static Report report = new Report();
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}else{
			if(configs.size()>1){
				return "redirect:/hadoop/report/list";
			}else{
				return "redirect:/hadoop/report/choose/"+configs.iterator().next().getHostname();
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
		return "/hadoop/report/list";
	}
	
	/**
	 * 选择报表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/choose/{hostname}",method=RequestMethod.GET)
	public String choose(@PathVariable String hostname,HttpServletRequest request) {
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}
		request.setAttribute("hostname", hostname);
		return "/hadoop/report/index";
	}
	
	/**
	 * HDFS使用情况报表
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/hdfs_report/{hostname}",method=RequestMethod.GET)
	public String hdfsreport(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
				
			long capacitySum = 0; //总容量
			long dfsUsed = 0; //总DFS使用量
			long dfsUnUsed = 0; //总的非DFS使用量
			long remainingSum = 0; //DFS剩余容量
			
			HadoopNodeManager manage = NodeManagerProxy.getManager(hostname);
			Collection<NodeInfo> nodeinfos = manage.getDataNodes();
			for(NodeInfo info : nodeinfos){
				capacitySum += info.getCapacity();
				dfsUsed += info.getDfsUsed();
				remainingSum += info.getRemaining();
			}
			dfsUnUsed += capacitySum - dfsUsed - remainingSum;
			
			report.setCapacitySumByte(capacitySum);
			report.setDfsUsedByte(dfsUsed);
			report.setDfsUsed(FileFormatUtils.FormetFileSize(dfsUsed));
			report.setDfsUnUsedByte(dfsUnUsed);
			report.setDfsUnUsed(FileFormatUtils.FormetFileSize(dfsUnUsed));
			report.setRemainingSumByte(remainingSum);
			report.setRemainingSum(FileFormatUtils.FormetFileSize(remainingSum));
			request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			request.setAttribute("report", report);
			
			return "/hadoop/report/hdfsreport";
		}
		return "redirect:/index";
	}
	
	/** 节点健康情况报表
	 * @param hostname
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/node_report/{hostname}",method=RequestMethod.GET)
	public String nodereport(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname!=null&&HadoopClusterServer.getMasterConfig(hostname)!=null){
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
				
			SimpleDateFormat dateFmt=new SimpleDateFormat("yyyy-MM-dd"); 
			Date now = new Date();
			
			String today = dateFmt.format(now);
			String end = today + " 23:59:59";
			
			List<NodeStatusReport> nodeStatusReport = reportService.findReport(today,end);
			
			request.setAttribute("rep", nodeStatusReport);
			
			request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			
			return "/hadoop/report/nodereport";
		}
		return "redirect:/index";
	}
	
}
