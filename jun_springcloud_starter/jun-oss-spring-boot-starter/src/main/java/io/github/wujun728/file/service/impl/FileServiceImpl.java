package io.github.wujun728.file.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import io.github.wujun728.file.config.FileProperties;
import io.github.wujun728.file.entity.SysFilesEntity;
import io.github.wujun728.file.mapper.SysFilesMapper;
import io.github.wujun728.file.service.FileService;
import io.github.wujun728.file.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson2.JSONObject;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class FileServiceImpl implements FileService {

//    @Value("${filepath}")
    @Value("${file.path}")
    private String file_root;

    @Resource
    private FileProperties fileUploadProperties;

    @Resource
    SysFilesMapper sysFilesMapper;


    @Override
    public String uploadFile(HttpServletRequest request) {
        String messagee = "";
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file = multipartRequest.getFile("file");
        String filename = multipartRequest.getFile("file").getOriginalFilename();
        String username = multipartRequest.getParameter("name");
        if(!username.equals("")){
            //完整的文件路径
            File file1 = new File(file_root + username);
            //文件路径不存在则刷新页面创建路径
            if(!file1.exists()){
                if(!file1.exists()){
                	file1.mkdirs();
                    log.info("用户:{}创建了文件夹{}",username,file);
                }
                // messagee="请刷新再上传";
                
            }
            //文件是否存在
            File file2 = new File(file_root + username+File.separator, filename);
            if(file2.exists()){
            	messagee = "文件已存在，无需上传";
            }else {
            	try{
            		file.transferTo(file2);
            		log.info("用户:{}上传文件{}成功",username,filename);
            		messagee = "上传成功";
            	} catch (IOException e) {
            		messagee = "上传失败";
            		log.info("用户:{}上传文件{}失败",username,filename);
            		e.printStackTrace();
            	}
            }
        }else {
            Cookie[] cookies = request.getCookies();
            String username1 = "";
            if(cookies != null){
                //找到cookie，作为文件上传路径
                for (int i = 0; i <= cookies.length-1; i++) {
                    if(cookies[i].getName().equals("Junior_file")){
                        username1 = cookies[i].getValue();
                        break;
                    }else {
                        continue;
                    }
                }
                File file1 = new File(file_root + username1);
                if(!file1.exists()){
                    messagee = "请刷新再上传";
                }else {
                    File file2 = new File(file_root + username1+File.separator, filename);
                    if(file2.exists()){
                        messagee = "文件已存在，无需上传";
                    }else {
                        try {
                            file.transferTo(file2);

                            saveSysFile(file2, filename,  file2.getAbsolutePath(),username,"user",username);
                            messagee = "上传成功";
                            log.info("临时用户上传文件{}成功",filename);
                        } catch (IOException e) {
                            messagee = "上传失败";
                            log.info("临时用户上传文件{}失败",filename);
                            e.printStackTrace();
                        }
                    }
                }
            }else {
                messagee = "请刷新再上传";
            }
        }
        return messagee;
    }

    private void saveSysFile(File file,String originalFilename,String newFilePathName, String username,String biztype,String bizid) throws IOException {
        String createTime = DateUtil.now();
        String newPath = fileUploadProperties.getPath() + createTime + File.separator;
        File uploadDirectory = new File(newPath);
        if (uploadDirectory.exists()) {
            if (!uploadDirectory.isDirectory()) {
                uploadDirectory.delete();
            }
        } else {
            uploadDirectory.mkdir();
        }
        String fileName = originalFilename;
        String fileNameNew = QiniuUtils.getFileNameByDate(username,fileName);
        // QiniuUtils.uploadFile(file, fileNameNew);
        String downUrl = QiniuUtils.uploadFileV2(file, fileNameNew);
        String URL = QiniuUtils.domain + "/" + fileName;
        String url = fileUploadProperties.getUrl() + "/" + createTime + "/" + fileNameNew;
        String filesize = QiniuUtils.FormetFileSize(file.length());
        this.saveFilesEntity(originalFilename, file.getAbsolutePath(),url,filesize,biztype, bizid);
    }

    private void saveFilesEntity(String fileName, String newFilePathName, String url,String filesize, String biztype, String bizid) {
        SysFilesEntity entity = new SysFilesEntity();
        entity.setFileName(fileName);
        entity.setFilePath(newFilePathName);
        entity.setUrl(url);
        entity.setFileSize(filesize);
        entity.setDictBiztype(biztype);
        entity.setRefBizid(bizid);
        sysFilesMapper.insert(entity);
    }


    //文件下载
    @Override
    public void fileDown(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        String msg = "";
        //获得文件名
        String filename = request.getParameter("filename");
//        获得文件路径
        String filepath = request.getParameter("name");
        if(!filepath.equals("")){
            File file = new File(file_root + filepath + File.separator+filename);
            msg = fileDown(file, response, filename,filepath);
        }else {
            String filepath1 = getMyCookie(request);
            File file = new File(file_root + filepath1 + File.separator + filename);
            msg = fileDown(file, response, filename,filepath1);
        }

        System.out.println(msg);
    }

    //文件删除
    @Override
    public String fileDel(HttpServletRequest request,HttpServletResponse response) {
        String msg  = "";
        String filename = request.getParameter("filename");
        String path = request.getParameter("name");
        if(!path.equals("")){
            File file = new File(file_root + path + File.separator + filename);
            msg = fileDelOpt(file);
            log.info("用户{}删除文件{}成功",path,filename);
        }else {
            String path1 = getMyCookie(request);
            File file = new File(file_root + path1 + File.separator + filename);
            msg = fileDelOpt(file);
            log.info("临时用户删除文件{}成功",path,filename);
        }
        return msg;
    }

    @Override
    public String fileList(HttpServletRequest request) {
        String message = "";
        ArrayList<Object> list = new ArrayList<>();
        String username = request.getParameter("name");
        if(!username.equals("")){
            //完整的文件路径
            File file1 = new File(file_root + username);
            //文件路径不存在则刷新页面创建路径
            if(!file1.exists()){
                message="文件错误";
            }else {
                File[] files = file1.listFiles();
                for (int i = 0; i <=files.length-1; i++) {
                   HashMap<String, Object> map = new HashMap<>();
                   map.put("filename",files[i].getName());
                   map.put("filetime",getModifiedTime(files[i]));
                   map.put("filesize",String.valueOf((((float)Math.round((getFileSize(files[i])/1048576)*100))/100))+" MB");
                   list.add(map);
                }
                message = "文件存在";
            }
        }else {
            Cookie[] cookies = request.getCookies();
            String username1 = "";
            if(cookies != null){
                //找到cookie，作为文件上传路径
                for (int i = 0; i <= cookies.length-1; i++) {
                    if(cookies[i].getName().equals("Junior_file")){
                        username1 = cookies[i].getValue();
                        break;
                    }else {
                        continue;
                    }
                }
                File file = new File(file_root + username1);
                if(!file.exists()){
                    message="文件错误";
                }else {
                    File[] files = file.listFiles();
                    for (int i = 0; i <=files.length-1; i++) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("filename",files[i].getName());
                        map.put("filetime",getModifiedTime(files[i]));
                        map.put("filesize",String.valueOf((((float)Math.round((getFileSize(files[i])/1048576)*100))/100))+" MB");
                        list.add(map);
                    }
                    message = "文件存在";
                }
            }else {
                message="文件错误";
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message",message);
        if(message == "文件存在"){
            jsonObject.put("code",0);
        }else {
            jsonObject.put("code",404);
        }
        jsonObject.put("data",list);
        String res = jsonObject.toJSONString();
        return res;
    }

    //获取文件大小
    private static float getFileSize(File f) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(f);
            return fis.available();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    //获取文件创建时间
    public static String getModifiedTime(File f){
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        //输出：修改时间[2]    2009-08-17 10:32:38
        return formatter.format(cal.getTime());
    }

    //获取cookie
    public static String getMyCookie(HttpServletRequest request) {
        String cookie = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            //找到cookie，作为文件上传路径
            for (int i = 0; i <= cookies.length - 1; i++) {
                if (cookies[i].getName().equals("Junior_file")) {
                    cookie = cookies[i].getValue();
                    break;
                } else {
                    continue;
                }
            }
        }
        return cookie;
    }

    //文件下载操作
    public static String fileDown(File file,HttpServletResponse response,String filename,String filepath) throws UnsupportedEncodingException {
        String msg = "";
        if(file.exists()){
            //设置响应体，响应头
            response.setContentType("application/force-download;charset=UTF-8");
            //文件中含有逗号等，需要将文件名用引号包起来
            response.addHeader("Content-Disposition", "attachment;fileName=" + "\""+ URLEncoder.encode(filename,"utf-8") +"\"");

//            通过文件流下载
            byte[] buffer = new byte[1024];
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                log.info("用户:{}下载文件{}成功",filepath,filename);
                msg = "下载成功";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            log.info("临时用户下载文件{}失败",filename);
            msg = "下载失败";
        }
        return msg;
    }

    //文件删除操作
    public static String fileDelOpt(File file){
        String msg = "";
        if(file.exists()){
            try {
                file.delete();
                msg = "文件删除成功";
            }catch (Exception e){
                e.printStackTrace();
                msg = "文件删除错误";
            }
        }else {
            msg = "文件删除错误";
        }
        return msg;
    }
}
