package com.ic911.htools.web.hadoop.node;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.TempNode;
import com.ic911.core.hadoop.domain.DataNode;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.core.hadoop.domain.NodeStatus;
import com.ic911.core.ssh2.SSH2ShortConnectRunner;
/**
 * 获得硬件信息
 * @author murenchao
 */
@Controller
@RequestMapping("/hadoop/node/hardware")
public class HardwareController {
	
	@RequestMapping(value="/show",method=RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request,RedirectAttributes redirectAttributes){
		Map<String,Object> hard = new HashMap<String,Object>();
		
		String ip = request.getParameter("ip");
		String namenode = request.getParameter("namenode");
		String hostname = request.getParameter("hostname");		
		TempNode tempNode = new TempNode();
		tempNode.setConfig(HadoopClusterServer.getMasterConfig(namenode));
		SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(hostname,ip, 22, tempNode.getConfig().getSshUsername(), tempNode.getConfig().getSshPassword(), tempNode.getConfig().getEncode());
		
		hard.put("cpu", getLinuxOSCPUInfo(runner));
		hard.put("memory", getLinuxOSMemoryInfo(runner));
		hard.put("disks", getLinuxOSDiskInfo2(runner));
		
		
		return hard;
	}
	/**
	 * 获得硬件设备挂载信息
	 * @param runner
	 * @return
	 */
	private Map<String,Object> getLinuxOSDiskInfo2(SSH2ShortConnectRunner runner){
		Map<String,Object> info = new LinkedHashMap<String,Object>();
		try{
			String[] disk = runner.runResultMultiLine("df -lh");
			List<String> diskInfo = new ArrayList<String>();
			for(int i=1;i<disk.length;i++){
				String d = disk[i];
				String[] dd = d.split(" ");
				if(dd.length==1){
				     diskInfo.add(d);
				     continue; 
				}
				for(int j=0;j<dd.length;j++){
					if(!dd[j].trim().equals("")){
						diskInfo.add(dd[j]);
					}
				}
				info.put(diskInfo.get(0), diskInfo);
				diskInfo = new ArrayList<String>();
			}
		}catch(Exception e){
			
		}
		return info;
		
	}
	
	/**
	 * 获得CPU信息
	 * @param runner
	 * @return
	 */
	private Map<String,Object> getLinuxOSCPUInfo(SSH2ShortConnectRunner runner){
		Map<String,Object> info = new HashMap<String, Object>();
		int load = 0;
		String name = "未知CPU(或虚拟机CPU)";
		try{
			String cpu = null;//CommandUtils.getRunResult("top -bn1");
			cpu = runner.runResultSingleLine("top -bn 1");
			cpu = cpu.substring(cpu.indexOf("Cpu(s):"),cpu.indexOf("Swap:"));
			int s1 = cpu.indexOf("Cpu(s):");
			int s2 = cpu.indexOf("%us,") == -1 ?cpu.indexOf(" us,"):cpu.indexOf("%us,");
			int s3 = cpu.indexOf("%sy,") == -1? cpu.indexOf(" sy,"):cpu.indexOf("%sy,");
			String us = cpu.substring(s1+7,s2).trim();
			String sy = cpu.substring(s2+4,s3).trim();
			if(us!=null&&sy!=null){
				load = new BigDecimal(Float.parseFloat(us) + Float.parseFloat(sy)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
			}
			cpu = null;//CommandUtils.getRunResult("cat /proc/cpuinfo");
			cpu = runner.runResultSingleLine("cat /proc/cpuinfo");
			s1 = cpu.indexOf("model name");
			s2 = cpu.indexOf("stepping");
			name = cpu.substring(s1+10,s2).replace(':',' ').trim();
		}catch (Exception e) {
		}
		info.put("name", name);
		info.put("load", load);
		return info;
	}
	/**
	 * 获得内存信息
	 * @param runner
	 * @return
	 */
	private Map<String,String> getLinuxOSMemoryInfo(SSH2ShortConnectRunner runner){
		//free -m
		Map<String,String> info = new HashMap<String, String>();
		try{
			String mem = null;//CommandUtils.getRunResult("top -bn1");
			mem = runner.runResultSingleLine("free -m");
			mem = mem.substring(mem.indexOf("Mem:")+4,mem.indexOf("-/+"));
			String[] mems = mem.split(" ");
			List<String> mlist = new ArrayList<String>();
			for(String s:mems){
				if(s!=null && !s.trim().equals("")){
					mlist.add(s);
				}
			}
			info.put("total",mlist.get(0));
			info.put("use",mlist.get(1));
			info.put("free",mlist.get(2));
		}catch (Exception e) {
		}
		return info;
	}
	
	/**
	 * 检查部署节点是否合法
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public int check(HttpServletRequest request) {
		String ip = request.getParameter("ip");
		String hostname = request.getParameter("hostname");
		NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
		Collection<DataNode> dateNodes = nameNode.getDataNodes();
		int flag = 0;
		if(nameNode.getIp().equals(ip)){
			flag = 1;
		}else{
			for(DataNode datenode : dateNodes){
				String nodeIp = datenode.getIp();
				NodeStatus nodeStatus = datenode.getStatus();
				if(nodeIp.equals(ip) && nodeStatus.name().equals("LIVE")){
					flag = 2;
					break;
				}
			}
		}
		return flag;
	}

}
