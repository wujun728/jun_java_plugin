package com.ic911.htools.web.hadoop;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ic911.core.commons.NetUtils;
import com.ic911.core.hadoop.HadoopHandler;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.TempNode;
import com.ic911.core.hadoop.hdfs.HDFSFile;
import com.ic911.core.hadoop.hdfs.HdfsFileManager;
import com.ic911.core.ssh2.SSH2Runner;
import com.ic911.core.ssh2.SSH2ShortConnectRunner;
import com.ic911.htools.commons.Constants;
import com.ic911.htools.entity.hadoop.AlarmMail;
import com.ic911.htools.service.hadoop.AlarmMailService;
import com.ic911.htools.service.hadoop.MasterConfigService;
import com.ic911.htools.service.security.AccountService;
/**
 * 配置向导
 * @author caoyang
 */
@Controller
@RequestMapping("/hadoop/guide")
public class GuideController {
	private static final Logger log = LoggerFactory.getLogger(GuideController.class);
	private static MasterConfig masterConfig = new MasterConfig();
	private static final String useerror = "警告：系统发现其他用户正在使用，或在使用配置向导时异常退出，系统将会重新为您初始化配置。<a href='/hadoop/guide/index?type=leave' role='button' class='btn dropdown-toggle' data-placement='bottom'>结束配置</a>&nbsp;&nbsp;<a href='/hadoop/guide/index?type=notuse' role='button' class='btn btn-danger dropdown-toggle' data-placement='bottom'>仍然继续</a>";
	private static final String error = "警告：由于配置原因、网络原因或其他用户同时访问，导致配置出现问题，请重新配置！";
	private static boolean use = false;//是否在使用，如果没有使用，恶意跳转，需要踢回首页
	@Autowired
	private MasterConfigService masterConfigService;
	@Autowired
	private AlarmMailService alarmMailService;
	@Autowired
	private AccountService accountService;
	
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
		String type = request.getParameter("type");
		if(type!=null){
			if(type.equals("leave")){
				use = false;
				return "redirect:/index";
			}else if(type.equals("notuse")){
				use = false;
			}
		}
		if(use){
			redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, useerror);
		}
		use = true;
		return "redirect:/hadoop/guide/step1";
	}
	
	/**
	 * 判断是否有效
	 * @return
	 */
	private boolean isEnabled(){
		return !use || masterConfig.getHostname()==null;
	}
	
	private String errorTo(HttpServletRequest request, RedirectAttributes redirectAttributes){
		use = false;
		redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, error);
		return index(request,redirectAttributes);
	}
	
	/**
	 * 步骤1
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step1",method=RequestMethod.GET)
	public String step1(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(!use){//只能单独判断
			return errorTo(request,redirectAttributes);
		}
		request.setAttribute("master", masterConfig);
		return "/hadoop/guide/step1";
	}
	
	/**
	 * 步骤2：通过上一步到此
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step2",method=RequestMethod.GET)
	public String step2(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		request.setAttribute("master", masterConfig);
		return "/hadoop/guide/step2";
	}
	
	/**
	 * 步骤2：通过下一步到此
	 * @param request
	 * @param master
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step2",method=RequestMethod.POST)
	public String step2(HttpServletRequest request,MasterConfig master,RedirectAttributes redirectAttributes) {
		if(!use||master==null){//只能单独判断
			return errorTo(request,redirectAttributes);
		}
		if(master!=null&&master.getIp()!=null&&master.getHostname()!=null){
			boolean exist = masterConfigService.isExist(master.getHostname(), master.getIp());
			if(exist){
				request.setAttribute("master", master);
				redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE,"警告：您配置的Hadoop集群信息已经存在，请重新填写该信息!");
				return "redirect:/hadoop/guide/step1";
			}
			String r = (String)test(request);
			if(r!=null&&r.equals("1")){
				masterConfig = master;
				try{
					masterConfig.setJvmSetupPath(HadoopHandler.getJvmSetupPath(masterConfig));
					masterConfig.setHadoopTmpPath(HadoopHandler.getHadoopTmpPath(masterConfig));
					masterConfig.setHadoopNamePath(HadoopHandler.getHadoopNamePath(masterConfig));
					masterConfig.setHadoopNamesecondaryPath(HadoopHandler.getHadoopNamesecondaryPath(masterConfig));
				}catch(Exception e){
					log.error("出现未知错误!", e);
					return errorTo(request,redirectAttributes);
				}
				request.setAttribute("master", masterConfig);
				return "/hadoop/guide/step2";
			}
		}
		request.setAttribute("master", master);
		redirectAttributes.addFlashAttribute(Constants.ERROR_MESSAGE, Constants.ERROR_INFO+"您的填写的Hadoop连接信息有错误!");
		return "redirect:/hadoop/guide/step1";
	}
	
	/**
	 * 步骤3：通过上一步到此
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step3",method=RequestMethod.GET)
	public String step3(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		try{
			Collection<String> snns = HadoopHandler.getMasters(masterConfig);
			String jt = HadoopHandler.getJobTracker(masterConfig);
			Collection<String> dns = HadoopHandler.getSlaves(masterConfig);
			request.setAttribute("snns", snns);
			request.setAttribute("jt", jt);
			request.setAttribute("dns", dns);
			request.setAttribute("master", masterConfig);
		}catch(Exception e){
			log.error("出现未知错误!", e);
			return errorTo(request,redirectAttributes);
		}
		return "/hadoop/guide/step3";
	}
	
	/**
	 * 步骤3：通过下一步到此
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step3",method=RequestMethod.POST)
	public String step3(HttpServletRequest request,MasterConfig master, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		masterConfig.setHadoopTmpPath(master.getHadoopTmpPath());
		masterConfig.setHadoopNamesecondaryPath(master.getHadoopNamesecondaryPath());
		masterConfig.setJvmSetupPath(master.getJvmSetupPath());
		masterConfig.setHdfsPort(master.getHdfsPort());
		masterConfig.setHdfsBasePath(master.getHdfsBasePath());
		try{
			Collection<String> snns = HadoopHandler.getMasters(masterConfig);
			String jt = HadoopHandler.getJobTracker(masterConfig);
			Collection<String> dns = HadoopHandler.getSlaves(masterConfig);
			request.setAttribute("master", masterConfig);
			request.setAttribute("snns", snns);
			request.setAttribute("jt", jt);
			request.setAttribute("dns", dns);
		}catch(Exception e){
			log.error("出现未知错误!", e);
			return errorTo(request,redirectAttributes);
		}
		return "/hadoop/guide/step3";
	}
	
	/**
	 * 步骤4：通过上一步到此
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step4",method=RequestMethod.GET)
	public String step4(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		try{
			HdfsFileManager fileManager = new HdfsFileManager(masterConfig);
			Collection<HDFSFile> files = fileManager.getFiles();
			fileManager.close();
			request.setAttribute("files", files);
			request.setAttribute("master", masterConfig);
		}catch(Exception e){
			log.error("出现未知错误!", e);
			return errorTo(request,redirectAttributes);
		}
		return "/hadoop/guide/step4";
	}
	
	/**
	 * 步骤4：通过下一步到此
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step4",method=RequestMethod.POST)
	public String step4(HttpServletRequest request,String temp, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		try{
			HdfsFileManager fileManager = new HdfsFileManager(masterConfig);
			Collection<HDFSFile> files = fileManager.getFiles();
			fileManager.close();
			request.setAttribute("files", files);
			request.setAttribute("master", masterConfig);
		}catch(Exception e){
			log.error("出现未知错误!", e);
			return errorTo(request,redirectAttributes);
		}
		return "/hadoop/guide/step4";
	}
	
	/**
	 * 步骤5：通过下一步到此
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/step5",method=RequestMethod.POST)
	public String step5(HttpServletRequest request,MasterConfig master, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		try{
			Collection<String> userMails = accountService.findMails();
			JSONArray mails = new JSONArray(userMails);
			request.setAttribute("master", masterConfig);
			request.setAttribute("mails", mails.toString());
		}catch(Exception e){
			log.error("出现未知错误!", e);
			return errorTo(request,redirectAttributes);
		}
		return "/hadoop/guide/step5";
	}
	
	/**
	 * 配置完成，最后的保存
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/finish",method=RequestMethod.POST)
	public String finish(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(isEnabled()){
			return errorTo(request,redirectAttributes);
		}
		String mails = request.getParameter("mails");
		try{
			if(masterConfig!=null){
				masterConfig = masterConfigService.saveOrUpdate(masterConfig);
			}
			ObjectMapper mapper = new ObjectMapper();
			try {
				Set<String> set = Sets.newHashSet();
				Collection<String> mailList = mapper.readValue(mails, Collection.class);
				set.addAll(mailList);
				if(set!=null && !set.isEmpty()){
					for(String mailStr : set){
						AlarmMail mail = new AlarmMail();
						mail.setMasterConfig(masterConfig);
						mail.setMail(mailStr);
						alarmMailService.saveOrUpdate(mail);
					}
				}
			} catch (JsonParseException e) {
				log.error("JSON解析错误!", e);
			} catch (JsonMappingException e) {
				log.error("JSON映射错误!", e);
			} catch (IOException e) {
				log.error("IO操作出现错误!", e);
			} catch (RuntimeException e) {
				log.error("运行时出现错误!", e);
			}
		}catch(Exception e){
			log.error("出现未知错误!", e);
			return errorTo(request,redirectAttributes);
		}
		use = false;
		masterConfig = new MasterConfig();
		return "/hadoop/guide/finish";
	}
	
	/**
	 * ajax测试连接可用
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/test",method=RequestMethod.POST)
	@ResponseBody
	public Object test(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		String ip = request.getParameter("ip");
		String sshUsername = request.getParameter("sshUsername");
		String sshPassword = request.getParameter("sshPassword");
		String sshPort = request.getParameter("sshPort");
		String encode = request.getParameter("encode");
		String hadoopSetupPath = request.getParameter("hadoopSetupPath");
		Collection<String> infos = Lists.newArrayList();
		infos.add(hostname);
		infos.add(ip);
		infos.add(sshUsername);
		infos.add(sshPassword);
		infos.add(sshPort);
		infos.add(encode);
		infos.add(hadoopSetupPath);
		for(String info : infos){
			if(info==null||info.trim().isEmpty()){
				return "9";//填写信息不全
			}
		}
		try{
			boolean exist = masterConfigService.isExist(hostname, ip);
			if(exist){
				return "11";//hadoop集群配置已经存在
			}
			SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(hostname,ip, Integer.parseInt(sshPort), sshUsername, sshPassword, encode);
			byte[] values = runner.read(hadoopSetupPath.concat("/bin/start-all.sh"));//找一个启动文件
			if(values!=null&&new String(values).indexOf("http://www.apache.org/licenses/LICENSE-2.0")>-1){//查看是否包含特定内容
				String jps = runner.runResultSingleLine("source /etc/profile;jps");
				if(jps.indexOf("NameNode")<0){
					return "13";//没有启动Hadoop服务
				}
				String oshn = runner.runResultSingleLine("hostname");//对比主机名
				if(oshn.trim().equals(hostname.trim())){
					return "1";//正常
				}else{
					return "3";//主机名不正确
				}
			}else{
				return "5";//Hadoop安装目录不正确
			}
		}catch(Exception e){
		}
		return "7";//未知错误导致无法配置
	}
	
	/**
	 * ajax启动hadoop
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/startup",method=RequestMethod.POST)
	@ResponseBody
	public Object startup(HttpServletRequest request) {
		String hostname = request.getParameter("hostname");
		String ip = request.getParameter("ip");
		String sshUsername = request.getParameter("sshUsername");
		String sshPassword = request.getParameter("sshPassword");
		String sshPort = request.getParameter("sshPort");
		String encode = request.getParameter("encode");
		String hadoopSetupPath = request.getParameter("hadoopSetupPath");
		try{
			SSH2ShortConnectRunner runner = new SSH2ShortConnectRunner(hostname,ip, Integer.parseInt(sshPort), sshUsername, sshPassword, encode);
			boolean r = runner.run(hadoopSetupPath.concat("/bin/start-all.sh"));
			return r?'1':'0';
		}catch(Exception e){
		}
		return "0";//未知错误导致无法配置
	}
	
	/**
	 * ajax获取配置文件信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/config",method=RequestMethod.POST)
	@ResponseBody
	public Object config(HttpServletRequest request) {
		String config = request.getParameter("config");
		if(config!=null){
			SSH2Runner runner = new SSH2ShortConnectRunner(new TempNode(masterConfig));
			byte[] value = runner.read(masterConfig.getHadoopSetupPath().concat("/conf/").concat(config));
			return new String(value);
		}
		return "Not Find The File...";
	}
	
	/**
	 * ajax通过IP获取主机名
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/synchn",method=RequestMethod.POST)
	@ResponseBody
	public Object synchn(HttpServletRequest request) {
		String ip = request.getParameter("ip");
		String hostname = NetUtils.getHostnameByIp(ip);
		return hostname;
	}
	
	/**
	 * ajax通过IP同步hosts文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/synchosts",method=RequestMethod.POST)
	@ResponseBody
	public Object syncHosts(HttpServletRequest request) {
		String ip = request.getParameter("ip");
		String rootpsw = request.getParameter("rootpsw");
		int port = 22;
		String sshPort = request.getParameter("sshPort");
		try{
			port = Integer.parseInt(sshPort);
		}catch(Exception e){
		}
		boolean s = HadoopHandler.syncHosts(ip, rootpsw, port);
		return s?'1':'0';
	}
	
}
