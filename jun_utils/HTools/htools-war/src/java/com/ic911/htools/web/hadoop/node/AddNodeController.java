package com.ic911.htools.web.hadoop.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.core.commons.NetUtils;
import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.HadoopNodeManager;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.NodeManagerProxy;
import com.ic911.core.hadoop.NodeMonitor;
import com.ic911.core.hadoop.OperationNodeConfig;
import com.ic911.core.hadoop.TempNode;
import com.ic911.core.hadoop.domain.NameNode;
import com.ic911.core.ssh2.SSH2ShortConnectRunner;
import com.ic911.htools.commons.Constants;
import com.ic911.htools.service.hadoop.MasterConfigService;
/**
 * 添加节点
 * @author murenchao
 */
@Controller
@RequestMapping("/hadoop/node/add")
public class AddNodeController {
	private static MasterConfig masterConfig = null;
	private static final String useerror = "警告：系统发现其他用户正在使用，或在使用配置向导时异常退出，系统将会重新为您初始化配置。<a href='/hadoop/guide/index?type=leave' role='button' class='btn btn-primary dropdown-toggle' data-placement='bottom'>结束配置</a>&nbsp;&nbsp;<a href='/hadoop/guide/index?type=notuse' role='button' class='btn btn-warning dropdown-toggle' data-placement='bottom'>仍然继续</a>";
	private static boolean use = false;//是否在使用，如果没有使用，恶意跳转，需要踢回首页
	private List<Map<String,Object>> infos = null;
	@Autowired
	private MasterConfigService masterConfigService;
	/**
	 * 添加数据节点step1
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/datanode/hostname/{hostname}",method=RequestMethod.GET)
	public String addNode(@PathVariable String hostname,HttpServletRequest request){
		if(hostname != null){
			masterConfig = HadoopClusterServer.getMasterConfig(hostname);
			NameNode nameNode = NodeMonitor.getSynchronizer().getNodeStatus(hostname);
			//request.setAttribute("hostname", hostname);
			request.setAttribute("nameNode", nameNode);
			request.setAttribute("master", masterConfig);
			return "/hadoop/node/addnode/step1";
		}
	   return "/hadoop/node/monitor/index";	
	}
	/***
	 * 部署一个数据节点和加入一个新节点
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/deployonenode",method=RequestMethod.POST)
	@ResponseBody
	public Object deployOneNodeStep2(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String ip = request.getParameter("ip");
		String nameNode = request.getParameter("namenode");
		String sshRootPassword = request.getParameter("sshRootPassword");
		String sshUsername = request.getParameter("sshUsername");
		String sshPassword = request.getParameter("sshPassword");
		String sshPort = request.getParameter("sshPort");
		String encode = request.getParameter("encode");
		//String hadoopSetupPath = request.getParameter("hadoopSetupPath");
		//String 
		//操作类型
	    OperationNodeConfig onc = new OperationNodeConfig();
	    onc.setAutoStartupDataNode(Boolean.parseBoolean(request.getParameter("autoStartupDataNode")));
	    onc.setSyncAllNodeConfig(Boolean.parseBoolean(request.getParameter("syncAllNodeConfig")));
	    onc.setStartTaskTracker(Boolean.parseBoolean(request.getParameter("taskTracker")));
	    onc.setStopTaskTracker(Boolean.parseBoolean(request.getParameter("taskTracker")));
	    onc.setRemoveLogs(Boolean.parseBoolean(request.getParameter("removeLogs")));
	    onc.setAddHosts(Boolean.parseBoolean(request.getParameter("addHosts")));
		onc.setStartBalancer(Boolean.parseBoolean(request.getParameter("balancer")));
		onc.setAddSlaves(Boolean.parseBoolean(request.getParameter("addSlaves")));
		onc.setChangeProfile(Boolean.parseBoolean(request.getParameter("changeProfile")));
		onc.setCloseFirewall(Boolean.parseBoolean(request.getParameter("firewell")));
		//存在无用参数
		Map<String,Object> map = testInfo(ip, sshRootPassword, sshUsername, sshPassword,sshPort,encode);
		
		TempNode tempNode = new TempNode();
		tempNode.setConfig(HadoopClusterServer.getMasterConfig(nameNode));
		tempNode.setIp(ip);
		tempNode.setSshPassword(sshPassword);
		tempNode.setSshRootPassword(sshRootPassword);
		//检测网络是否畅通
		if(map.containsKey("isExists") && map.containsKey("usernameAndPassword")){
			if(map.containsKey("hostname")) tempNode.setHostname((String)map.get("hostname"));
		    //判断有没有hadoop用户
			if(!map.containsKey("isHadoop")){
				SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(tempNode);
				runner.runResultSingleLine("useradd "+sshUsername+" -m;echo hadoop:"+sshPassword+" > ~/pwd.txt;chpasswd < ~/pwd.txt;rm -f ~/pwd.txt");
			}
			boolean deploy = false;
			//判断是否已经安装过hadoop（没有判断hadoop的版本）
			if(map.containsKey("hadooppath")){
				//添加以存在的节点
				deploy = NodeManagerProxy.getManager().addDynamicDataNode(tempNode, onc);
			}else{
				//部署新节点
				deploy = NodeManagerProxy.getManager().setupHadoop(tempNode,onc);
			}//end if
			if(deploy){
				map.put("status",true);
			}
		}//end if
		return  map;
	}
	/**
	 * 添加数据节点deploy
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/deploy",method=RequestMethod.POST)
	@ResponseBody
	public Object addNodeStep2(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String sshRootPassword = request.getParameter("sshRootPassword");
		String sshUsername = request.getParameter("sshUsername");
		String sshPassword = request.getParameter("sshPassword");
		//String 
		//操作类型
	    OperationNodeConfig onc = new OperationNodeConfig();
	    onc.setAutoStartupDataNode(Boolean.parseBoolean(request.getParameter("autoStartupDataNode")));
	    onc.setSyncAllNodeConfig(Boolean.parseBoolean(request.getParameter("syncAllNodeConfig")));
	    onc.setStartTaskTracker(Boolean.parseBoolean(request.getParameter("taskTracker")));
	    onc.setStopTaskTracker(Boolean.parseBoolean(request.getParameter("taskTracker")));
	    onc.setRemoveLogs(Boolean.parseBoolean(request.getParameter("removeLogs")));
	    onc.setAddHosts(Boolean.parseBoolean(request.getParameter("addHosts")));
		onc.setStartBalancer(Boolean.parseBoolean(request.getParameter("balancer")));
		onc.setAddSlaves(Boolean.parseBoolean(request.getParameter("addSlaves")));
		onc.setChangeProfile(Boolean.parseBoolean(request.getParameter("changeProfile")));
		onc.setCloseFirewall(Boolean.parseBoolean(request.getParameter("firewell")));
	    //选择的要部署的主机集	
			String nodesips = request.getParameter("nodeips");
			String[] ips = nodesips.split("\\|");
			HadoopNodeManager hnm = NodeManagerProxy.getManager();
			infos = new ArrayList<Map<String,Object>>();
			for(int i=0;i<ips.length;i++){
				String[] info = ips[i].split(","); 
				TempNode tempNode = new TempNode();
				tempNode.setConfig(masterConfig);
				tempNode.setHostname(info[1]);
				tempNode.setIp(info[0]);
				tempNode.setSshPassword(sshPassword);
				tempNode.setSshRootPassword(sshRootPassword);
				if(info[2].equals("undefined")){
					System.out.println("create");
					SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(tempNode);
					runner.runResultSingleLine("useradd "+sshUsername+" -m;echo hadoop:"+sshPassword+" > ~/pwd.txt;chpasswd < ~/pwd.txt;rm -f ~/pwd.txt");
				}
				boolean deploy = false; 
				if(!info[3].equals("undefined")){
					//添加以存在的节点
					deploy = hnm.addDynamicDataNode(tempNode, onc);
				}else{
					//部署新节点
					deploy = hnm.setupHadoop(tempNode,onc);
				}//end if
				if(deploy){
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("ip", info[0]);
					infos.add(m);
				}//end if
			}//end for
		return infos;
	}
	
	
	/**
	 * 向导请求入口
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		masterConfig = new MasterConfig();
		if(masterConfig==null){
			masterConfig = new MasterConfig();
		}

		if(use){
			redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, useerror);
		}
		use = true;
		return "redirect:/hadoop/node/addnode/step1";
	}
	
	
	/**
	 * ajax测试连接可用
	 * @param request
	 * @return		
	 */
	@RequestMapping(value="/testroot",method=RequestMethod.POST)
	@ResponseBody
	public Object testRoot(HttpServletRequest request) {
		String ip = request.getParameter("ip");
		String sshRootPassword = request.getParameter("sshRootPassword");
		String sshUsername = request.getParameter("sshUsername");
		String sshPassword = request.getParameter("sshPassword");
		String sshPort = request.getParameter("sshPort");
		String encode = request.getParameter("encode");
		//String hadoopSetupPath = request.getParameter("hadoopSetupPath");
		List<String> ips = null;
		infos = new ArrayList<Map<String,Object>>();
		Map<String,Object> size = new HashMap<String,Object>();
		if(ip.indexOf('/')>-1){
			ips = this.ip4Split(ip);
			if(ips == null) return null;
			for(String _ip : ips){
				infos.add(this.testInfo(_ip , sshRootPassword, sshUsername, sshPassword, sshPort, encode));
			}//end for
			size.put("length", ips.size());
			return size;
		}else{
			infos.add(this.testInfo(ip, sshRootPassword, sshUsername, sshPassword, sshPort, encode));
			size.put("length",infos.size());
			return size;
		}//end if
	}//end testRoot
	
	/**
	 * 获取检测信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getnodeinfo",method=RequestMethod.POST)
	@ResponseBody
	public Object getNodeInfo(HttpServletRequest request) {
			return infos;
	}
	
    /**
     * 检查网络和一些基本配置信息
     * 存在无用参数
     * @param ip
     * @param sshRootPassword
     * @param sshUsername
     * @param sshPassword
     * @param sshPort
     * @param encode
     * @param hadoopSetupPath
     * @return
     */
	private Map<String,Object> testInfo(String ip,String sshRootPassword,String sshUsername,String sshPassword,String sshPort,String encode){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("ip", ip);	
		List<String> PingList = NetUtils.isExistsByPingIp(ip,1);
		StringBuffer pingsb = new StringBuffer();
		for(String p : PingList){
				pingsb.append(p);
				pingsb.append("\n");
		}
		map.put("ping_ip", pingsb.toString());
		if(pingsb.length()>0 && (pingsb.indexOf("Destination Host Unreachable") == -1 && pingsb.indexOf("无法访问目标主机") == -1)){
			map.put("isExists", true);
		
			//判断root密码是否正确
			try{
				SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(ip,ip, Integer.parseInt(sshPort), "root", sshRootPassword, encode);
				map.put("rootPassword", true);
				//判断主机是否存在hadoop用户
				String hostname = runner.runResultSingleLine("hostname");
				map.put("hostname", hostname);
				//存在hadoop用户
				String isHadoop = runner.runResultSingleLine("grep 'hadoop' /etc/passwd | wc -l").trim();
				if(!isHadoop.equals("0")){
					map.put("isHadoop", true);
					//判断填写的是否是hadoop用户
					if(sshUsername.equals("hadoop")){
						map.put("hadoop", true);
					}//end if
				}//end if
			}catch(Exception e){
			}//end try
			//判断普通用户用户名和密码是否正确
			try{
				SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(ip,ip, Integer.parseInt(sshPort), sshUsername, sshPassword, encode);
				if(!map.containsKey("hostname")){
					String hostname = runner.runResultSingleLine("hostname");
					map.put("hostname", hostname);
				}//end if
				if(!map.containsKey("isHadoop")){
					String isHadoop = runner.runResultSingleLine("grep 'hadoop' /etc/passwd | wc -l").trim();
					if(!isHadoop.equals("0")){
						map.put("isHadoop", true);
						//判断填写的是否是hadoop用户
						if(sshUsername.equals("hadoop")){
							map.put("hadoop", true);
						}//end if
					}//end if
				}//end if
				map.put("usernameAndPassword", true);
				//判断hadoop是否已经安装过
				try{
					String isHadoopPath = runner.runResultSingleLine("source /etc/profile;echo $HADOOP_HOME").trim();
					map.put("hadooppath", isHadoopPath);
					String javahome = runner.runResultSingleLine("source /etc/profile;echo $JAVA_HOME").trim();
					map.put("javahome", javahome);
				}catch(Exception ex){
				}//end try
			}catch(Exception e){
			}//end try
			
		}else{
			
		}//end if
		System.out.println(map);
		return map;
	}
	/**
	 * 获得ip范围
	 * @param ip
	 * @return List<String>
	 */
	private List<String> ip4Split(String ip){
		if(ip == null) return null;
		ip = ip.trim();
		System.out.println(ip);
		String[] ipsuffix = ip.split("\\."); 
		System.out.println(ipsuffix.length);
		if(ipsuffix.length > 0){
		    String[] suffix = ipsuffix[ipsuffix.length-1].split("/");
		    int suffix1 = 0;
		    int suffix2 = -1;
		    if(suffix.length>1){
			    suffix1 = Integer.parseInt(suffix[0]);
			    suffix2 = Integer.parseInt(suffix[1]);
			    List<String> ipsList = new ArrayList<String>();
			    for(int i=suffix1;i<=suffix2;i++){
			    	ipsList.add(ipsuffix[0]+"."+ipsuffix[1]+"."+ipsuffix[2]+"."+i);
			    }//end for
			    System.out.println(ipsList.toString());
			    return ipsList;
		    }//end if
		}//endif
		return null;
	}
}
