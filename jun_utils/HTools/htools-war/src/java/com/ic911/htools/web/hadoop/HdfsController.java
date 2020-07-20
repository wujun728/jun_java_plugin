package com.ic911.htools.web.hadoop;


import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ic911.htools.commons.ModelToJson;
import com.ic911.core.hadoop.HadoopClusterServer;
import com.ic911.core.hadoop.HadoopHandler;
import com.ic911.core.hadoop.MasterConfig;
import com.ic911.core.hadoop.hdfs.HDFSFile;
import com.ic911.core.hadoop.hdfs.HdfsFileManager;

@Controller
@RequestMapping("/hadoop/hdfs")
public class HdfsController {

	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index() {
		
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}else{
			if(configs.size()>1){
				return "redirect:/hadoop/hdfs/list";
			}else{
				return "redirect:/hadoop/hdfs/host/"+configs.iterator().next().getHostname();
			}
		}
		
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Collection<MasterConfig> configs = HadoopClusterServer.getMasterConfigs();
		if(configs==null||configs.isEmpty()){
			return "redirect:/index";
		}
		request.setAttribute("configs", configs);
		return "/hadoop/hdfs/list";
	}
	
	@RequestMapping(value="/host/{hostname}",method=RequestMethod.GET)
	public String initHdfs(@PathVariable String hostname,HttpServletRequest request) {
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			if(HadoopHandler.isSafemode(hostname)){
				HadoopHandler.leaveSafemode(hostname);
			}
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config);
			Collection<HDFSFile> hdfsFiles = manager.getFiles();
			List<String[]> strArrList = pathStrToList(config.getHdfsBasePath(),config.getHdfsBasePath());
			request.setAttribute("strArrList", strArrList);
			request.setAttribute("currName", strArrList.get(strArrList.size() - 1)[1]);
			request.setAttribute("hdfsFiles", hdfsFiles);
			request.setAttribute("currPath", config.getHdfsBasePath());
			request.setAttribute("hostname", hostname);
			manager.close();
		}
		return "/hadoop/hdfs/hdfsReadFold";
	}
	

	@RequestMapping(value="/fold",method=RequestMethod.POST)
	public String hdfsReadFold(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String fullpath = request.getParameter("currPath");
		String hostname = request.getParameter("hostname");
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null && fullpath != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),fullpath);
			Collection<HDFSFile> hdfsFiles = manager.getFiles();
			List<String[]> strArrList = pathStrToList(fullpath,config.getHdfsBasePath());
			request.setAttribute("strArrList", strArrList);
			request.setAttribute("currName", strArrList.get(strArrList.size() - 1)[1]);
			request.setAttribute("hdfsFiles", hdfsFiles);
			request.setAttribute("currPath", fullpath);
			request.setAttribute("hostname", hostname);
			manager.close();
		}else if(hostname == null){
			return "redirect:/hadoop/hdfs/list";
		}else{
			return "redirect:/hadoop/hdfs/host/" + hostname;
		}
		return "/hadoop/hdfs/hdfsReadFold";
	}

	@RequestMapping(value="/flushfold",method=RequestMethod.GET)
	public String hdfsReadFoldFlush(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String fullpath = request.getParameter("currPath");
		String hostname = request.getParameter("hostname");
		Cookie[] cookies = request.getCookies();
		if(fullpath == null && hostname == null && cookies != null && cookies.length > 0){
			for(Cookie co : cookies){
				if("currPath".equals(co.getName())){
					fullpath = co.getValue();
				}
				if("hostname".equals(co.getName())){
					hostname = co.getValue();
				}
				co.setMaxAge(0);
			}
		}
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null && fullpath != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),fullpath);
			Collection<HDFSFile> hdfsFiles = manager.getFiles();
			List<String[]> strArrList = pathStrToList(fullpath,config.getHdfsBasePath());
			request.setAttribute("strArrList", strArrList);
			request.setAttribute("currName", strArrList.get(strArrList.size() - 1)[1]);
			request.setAttribute("hdfsFiles", hdfsFiles);
			request.setAttribute("currPath", fullpath);
			request.setAttribute("hostname", hostname);
			manager.close();
		}else if(hostname == null){
			return "redirect:/hadoop/hdfs/list";
		}else{
			return "redirect:/hadoop/hdfs/host/" + hostname;
		}
		return "/hadoop/hdfs/hdfsReadFold";
	}
	
	@RequestMapping(value="/file",method=RequestMethod.POST)
	public String hdfsReadFile(HttpServletRequest request,RedirectAttributes redirectAttributes)throws UnsupportedEncodingException{
		String fullpath = request.getParameter("currPath");
		String hostname = request.getParameter("hostname");
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null && fullpath != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),fullpath);
			HDFSFile hdfsFile = manager.getFile();
			if(hdfsFile != null){
				String fileName = hdfsFile.getFileName();
				int pageSize = 4096;
				int pageCount = (int)Math.ceil(hdfsFile.getLen() / pageSize) == 0 ? 1 : (int)Math.ceil(hdfsFile.getLen() / pageSize);
			
				if(hdfsFile.getLen() < pageSize){
					pageSize = (int)hdfsFile.getLen();
				}else{
					if(hdfsFile.getLen() - pageNum * pageSize < pageSize){
						pageSize = (int)hdfsFile.getLen() - (pageNum - 1) * pageSize;
					}
				}
				byte[] result = manager.getFileData(fileName, (pageNum - 1) * pageSize , pageSize);
			
				String content = new String(result);
				List<String[]> strArrList = pathStrToList(fullpath,config.getHdfsBasePath());
				request.setAttribute("strArrList", strArrList);
				request.setAttribute("currName", strArrList.get(strArrList.size() - 1)[1]);
				request.setAttribute("hdfsFile", hdfsFile);
				request.setAttribute("content", content);
				request.setAttribute("currPath", fullpath);
				request.setAttribute("hostname", hostname);
				request.setAttribute("pageNum", pageNum);
				request.setAttribute("pageCount", pageCount);
				request.setAttribute("parentPath", fullpath.substring(0,fullpath.lastIndexOf('/')));
				manager.close();
			}
		}else if(hostname == null){
			return "redirect:/hadoop/hdfs/list";
		}else{
			return "redirect:/hadoop/hdfs/host/" + hostname;
		}
		return "/hadoop/hdfs/hdfsReadFile";
	}
	
	/**
	 * 重命名,oldname为旧的文件名，newname为新的文件名
	 * @param request
	 * @return 
	 */
	@RequestMapping(value="/rename",method=RequestMethod.POST)
	//@ResponseBody
	public String hdfsRename(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		String oldname = request.getParameter("oldname");
		String newname = request.getParameter("newname");
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),parentpath);
			manager.renameDirOrFile(oldname, newname);
			
			manager.close();
			flushPage(parentpath,hostname,response);
		}
		//return "success";
		return "redirect:/hadoop/hdfs/flushfold";
	}
	
	/**
	 * 删除文件或文件夹,filename为文件名或文件夹名
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	//@ResponseBody
	public String hdfsDelete(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		String delname = request.getParameter("delname");
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),parentpath);
			manager.deleteDirOrFile(delname);
			
			manager.close();
			flushPage(parentpath,hostname,response);
		}
		//return "success";
		return "redirect:/hadoop/hdfs/flushfold";
	}
	
	/**
	 * 创建文件，name为文件名
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/mkfile",method=RequestMethod.POST)
	//@ResponseBody
	public String hdfsMkFile(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes)throws UnsupportedEncodingException{
		String name = request.getParameter("name");
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");
		//String str = "";
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),parentpath);
			if(!manager.existsDirOrFile(name)){
				manager.createFile(name, new String("").getBytes("UTF-8"));
			}else{
				manager.close();
				//return "existed";  //当文件已存在则在前台不现实内容
			}
			manager.getFile(name);
			//str = ModelToJson.getBeanToJson(hdfsFile);
			manager.close();
			flushPage(parentpath,hostname,response);
		}
		return "redirect:/hadoop/hdfs/flushfold";
	}
	
	/**
	 * 创建文件夹，name为文件夹名
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value="/mkfold",method=RequestMethod.POST)
	//@ResponseBody
	public String hdfsMkFold(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes)throws UnsupportedEncodingException{
		String name = request.getParameter("name");
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");
		//String str = "";
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),parentpath);
			if(!manager.existsDirOrFile(name)){
				manager.makeDir(name);
				//str = ModelToJson.getBeanToJson(hdfsFile);
			}else{
				manager.close();
				//return "existed";
			}
			
			manager.close();
			flushPage(parentpath,hostname,response);
		}
		return "redirect:/hadoop/hdfs/flushfold";
	}
	
	/**
	 * 追加内容
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/appenddata",method=RequestMethod.POST)
	@ResponseBody
	public String hdfsAppendData(HttpServletRequest request)throws Exception{
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");
		String data = request.getParameter("data");
		String filename = request.getParameter("filename");
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),parentpath);
			if(manager.appendDataToFile(filename , data)){
				byte[] value = manager.getFileData(filename);
				String content = "";
				if(value != null){
					content = new String(value);
				}
				request.setAttribute("content", content);
				manager.close();
				return "success";
			}else{
				byte[] value = manager.getFileData(filename);
				String dataVal = "";
				if(value != null){
					dataVal = new String(value);
				}
				if(data != null && data.length() > 0){
					dataVal += "\n" + data; 
				}
				manager.deleteDirOrFile(filename);
				manager.createFile(filename, dataVal.getBytes());
				request.setAttribute("content", dataVal);
				manager.close();
				return "success";
			}	
		}
		return "fail";
	}
	
	/**
	 * 上传文件
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="uploadfile",method=RequestMethod.POST)
	@ResponseBody
	public String hdfsUploadFile(HttpServletRequest request,
			HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException {
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");

		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		commonsMultipartResolver.setDefaultEncoding("utf-8");

		String str = "";

		if (hostname != null
				&& HadoopClusterServer.getMasterConfig(hostname) != null) {
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),
					config.getHdfsPort(), parentpath == null ? "" : parentpath);
			if (commonsMultipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iter = multipartRequest.getFileNames();
				while (iter.hasNext()) {
					MultipartFile file = multipartRequest.getFile((String) iter.next());

					if (file != null) {
						//对文件名进行特殊字符过滤
						String fullName = file.getOriginalFilename();
						String reg = "[^a-zA-Z\\.0-9\u4E00-\uFA29\uE7C7-\uE7F3]";
						fullName = fullName.replaceAll(reg, "");
						if (!manager.existsDirOrFile(fullName)) {
							String fileName = fullName.substring(0,fullName.indexOf("."));
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
							// 上传的文件名都是乱码，则将当前时间设置为文件名
							String targetFileName = "".equals(fileName) ? formatter.format(new Date()) + fullName.substring(0) : fullName;
							manager.createFile(targetFileName , new String(file.getBytes(), "GBK").getBytes("UTF-8"));

							manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(), (parentpath == null ? "" : parentpath) + "/" + targetFileName);
							HDFSFile hdfsFile = manager.getFile();
							str = ModelToJson.getBeanToJson(hdfsFile);
						}
					}
				}
			}
			manager.close();
		}
		return str;
	}
	
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/downfile",method=RequestMethod.POST)
	//@ResponseBody
	public void hdfsDownFile(HttpServletRequest request,HttpServletResponse response)throws IOException{
		String hostname = request.getParameter("hostname");
		String parentpath = request.getParameter("parentpath");
		String filename = request.getParameter("filename");

		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),parentpath);
			try{
				if(manager != null){
					response.reset();
				
					response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename,"utf-8"));
					response.setContentType("application/octet-stream");
					response.setCharacterEncoding("UTF-8");
				
					OutputStream os = new BufferedOutputStream(response.getOutputStream());
					os.write(manager.getFileData(filename));
					os.flush();
					os.close();
					manager.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 在拖拽文件的时候判断文件名是否在目标文件夹中存在，hostname记录主机名，filename记录拖拽的文件名，targetpath记录目标文件夹路径
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="dragisexist",method=RequestMethod.POST)
	@ResponseBody
	public String dragIsExist(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String hostname = request.getParameter("hostname");
		String filename = request.getParameter("filename");
		String targetpath = request.getParameter("targetpath");
		String isExist = "";
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),targetpath);
			if(manager.existsDirOrFile(filename)){
				isExist = "true";
			}else{
				isExist = "false";
			}
		}
		return isExist;
	}
	
	
	/**
	 * 拖拽文件到文件夹中，hostname记录主机名，currpath记录当前路径，filename记录拖拽的文件名，targetpath记录目标文件夹路径,isExist是记录文件名在文件夹中是否存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="dragfile",method=RequestMethod.POST)
	@ResponseBody
	public String dragFile(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String hostname = request.getParameter("hostname");
		String currpath = request.getParameter("currpath");
		String filename = request.getParameter("filename");
		String targetpath = request.getParameter("targetpath");
		String isExist = request.getParameter("isexist");
		if(hostname != null && HadoopClusterServer.getMasterConfig(hostname) != null){
			MasterConfig config = HadoopClusterServer.getMasterConfig(hostname);
			HdfsFileManager currManager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),currpath);
			HdfsFileManager manager = new HdfsFileManager(config.getIp(),config.getHdfsPort(),targetpath);
			//处理存在相同文件名的文件，删除原来的文件
			if(isExist.equals("true")){
				manager.deleteDirOrFile(filename);
			}
			//在文件夹下面创建文件
			manager.createFile(filename, currManager.getFileData(filename));
			//删除原来文件目录下面的文件
			currManager.deleteDirOrFile(filename);
			
			manager.close();
			currManager.close();
		}
		return "success";
	}
	
	/**
	 * 将路径转换成字符串数组，这样有利于点击单个目录文件
	 * @param fullPath
	 * @param basePath
	 * @return
	 */
	public List<String[]> pathStrToList(String fullPath,String basePath){
		String tmpPath;
		String[] strArr = {};
		if(!fullPath.equals(basePath)){
			tmpPath = fullPath.substring(basePath.length() + 1);
			strArr = tmpPath.split("/");
		}
		List<String[]> strList = new ArrayList<String[]>();
		StringBuffer hrefPath = new StringBuffer(basePath);
		String[] tmpStrArr = {hrefPath.toString(),basePath};
		strList.add(tmpStrArr);
		for(int idx = 0 ,count = strArr.length; idx < count ; idx ++){
			String[] tmpArr = {hrefPath.append("/").append(strArr[idx]).toString(),strArr[idx]};
			strList.add(tmpArr);
		}
		return strList;
	}
	
	public void flushPage(String parentpath,String hostname,HttpServletResponse response){
		Cookie cookieCurrPath = new Cookie("currPath",parentpath);
		Cookie cookieHostName = new Cookie("hostname",hostname);
		cookieCurrPath.setPath("/");
		cookieHostName.setPath("/");
		response.addCookie(cookieCurrPath);
		response.addCookie(cookieHostName);
	}
}
