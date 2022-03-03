package com.jun.admin.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jun.admin.service.ICommonService;
import com.jun.admin.util.DateUtil;
import com.jun.admin.util.LogFactory;
import com.jun.admin.util.RequestUtil;
import com.jun.admin.util.StringUtil;

@Controller
@RequestMapping(value = "/upload.do")
public class UploadAction {

	@Autowired(required = false)
	@Qualifier("jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;

	
	@Autowired(required = false)
	@Qualifier("commonServiceImpl")
	protected ICommonService commonServiceImpl;
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@RequestMapping(value = "status=upload")
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request,HttpServletResponse response, ModelMap model) {
		System.out.println("开始");
		Map map = RequestUtil.getAllParameters(request);
		String method = request.getParameter("method");

		String path = "";
		String fileName="";
		if(System.getProperty("os.name").toLowerCase().indexOf("windows")>=0 ){
			path = request.getSession().getServletContext().getRealPath("upload")+System.getProperty("file.separator");
		}else if(System.getProperty("os.name").toLowerCase().indexOf("linux")>=0){
			path = System.getProperty("file.separator")+"opt"+System.getProperty("file.separator")+"apps"+System.getProperty("file.separator")+"pic"+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")+"";
		}else{
			path = request.getSession().getServletContext().getRealPath("upload")+System.getProperty("file.separator");
		}
	    
		String temp = request.getSession().getServletContext().getRealPath("/") + "upload"+System.getProperty("file.separator")+"temp"; // 临时目录
		if(!file.isEmpty()){
			fileName = file.getOriginalFilename();
//			fileName = UUID.randomUUID().toString();
			String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//			fileName = new Date().getTime() + ".jpg";
			Random r = new Random();
			int rannum = (int) (r.nextDouble() * (9999 - 1000 + 1)) + 1000;
			fileName = DateUtil.getNowStrDate() +"-"+ rannum;
			fileName += extName;
			if ("AdvertSave".equalsIgnoreCase(method)) {
				path+="advert"+System.getProperty("file.separator");
			}
			if ("FirmSave".equalsIgnoreCase(method)) {
				path+="firm"+System.getProperty("file.separator");
			}
			if ("EquipSave".equalsIgnoreCase(method)) {
				path+="equip"+System.getProperty("file.separator");
			}
			System.out.println(path);
			System.out.println("path="+path+fileName);
			
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存
			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		if ("AdvertSave".equalsIgnoreCase(method)) {
			String ID = StringUtil.decodeToUtf(map.get("ID"));
			System.out.println("ID2=" + map.get("ID"));
			LogFactory.getInstance().getLogger().info("info");
			int flag = 0;
			if ("".equals(ID.trim())) {
				map.put("FILE_NAME", fileName);
				LogFactory.getInstance().getLogger().info("info");
//				flag = this.firmServiceImpl.saveFirmADDInfo(map);
				LogFactory.getInstance().getLogger().info(flag + "");
			}
			if (!"".equals(ID.trim())) {
				if(!file.isEmpty()){
					map.put("FILE_NAME", fileName);
				}
//				flag = this.firmServiceImpl.updateFirmADDInfo(map);
			}
			System.out.println("flag=" + flag);
		}
		if ("FirmSave".equalsIgnoreCase(method)) {
			String ID = StringUtil.decodeToUtf(map.get("ID"));
			System.out.println("ID2=" + map.get("ID"));
			LogFactory.getInstance().getLogger().info("info");
			int flag = 0;
			if ("".equals(ID.trim())) {
				map.put("FIRM_PIC", fileName);
				LogFactory.getInstance().getLogger().info("info");
//				flag = this.firmServiceImpl.saveFirmInfo(map);
				LogFactory.getInstance().getLogger().info(flag + "");
			}
			if (!"".equals(ID.trim())) {
				if(!file.isEmpty()){
					map.put("FIRM_PIC", fileName);
				}
//				flag = this.firmServiceImpl.updateFirmInfo(map);
				flag++;
			}
			System.out.println("flag=" + flag);
		}
		if ("EquipSave".equalsIgnoreCase(method)) {
			String ID = StringUtil.decodeToUtf(map.get("ID"));
			System.out.println("ID2=" + map.get("ID"));
			LogFactory.getInstance().getLogger().info("info");
			int flag = 0;
			if ("".equals(ID.trim())) {
				map.put("G_EQUIP_PIC", fileName);
				LogFactory.getInstance().getLogger().info("info");
//				flag=this.commonServiceImpl.saveEquip(map);
				LogFactory.getInstance().getLogger().info(flag + "");
			}
			if (!"".equals(ID.trim())) {
				if(!file.isEmpty()){
					map.put("G_EQUIP_PIC", fileName);
				}
//				flag=this.commonServiceImpl.updateEquip(map);
				flag++;
			}
			System.out.println("flag=" + flag);
		}
		
		
		model.addAttribute("fileUrl", request.getContextPath() + "/upload/" + fileName);
		PrintWriter out = null;
		try {
			out = encodehead(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 这个地方不能少，否则前台得不到上传的结果
		out.write("1");
		out.close();
		
		return null;
	}
	
	private PrintWriter encodehead(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		return response.getWriter();
	}
	
	
    /** 
     *  根据路径删除指定的目录或文件，无论存在与否 
     *@param sPath  要删除的目录或文件 
     *@return 删除成功返回 true，否则返回 false。 
     */  
    public boolean DeleteFolder(String sPath) {  
        boolean flag = false;  
        File file = new File(sPath);  
        // 判断目录或文件是否存在  
        if (!file.exists()) {  // 不存在返回 false  
            return flag;  
        } else {  
            // 判断是否为文件  
            if (file.isFile()) {  // 为文件时调用删除文件方法  
                return deleteFile(sPath);  
            } else {  // 为目录时调用删除目录方法  
                return deleteDirectory(sPath);  
            }  
        }  
    }  
    
    
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	} 
	
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   sPath 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     */  
    public boolean deleteDirectory(String sPath) {  
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
        if (!sPath.endsWith(File.separator)) {  
            sPath = sPath + File.separator;  
        }  
        File dirFile = new File(sPath);  
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (!dirFile.exists() || !dirFile.isDirectory()) {  
            return false;  
        }  
        boolean flag = false;  
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();  
        for (int i = 0; i < files.length; i++) {  
            //删除子文件  
            if (files[i].isFile()) {  
                flag = deleteFile(files[i].getAbsolutePath());  
                if (!flag) break;  
            } //删除子目录  
            else {  
                flag = deleteDirectory(files[i].getAbsolutePath());  
                if (!flag) break;  
            }  
        }  
        if (!flag) return false;  
        //删除当前目录  
        if (dirFile.delete()) {  
            return true;  
        } else {  
            return false;  
        }  
    }  

}