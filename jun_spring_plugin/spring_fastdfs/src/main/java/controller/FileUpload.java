package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import po.Myfile;
import fastdfs.FastDFSClient;


@Controller
public class FileUpload {
	@RequestMapping("/fileupload")
	public String upload(){
		return "fileupload";
	}
	
	@RequestMapping(value="/uploadfile",method=RequestMethod.POST)
	@ResponseBody
	public Myfile fileupload(@RequestParam MultipartFile uploadfile,HttpServletRequest request) throws Exception{
		String fileId = FastDFSClient.uploadFile(uploadfile.getInputStream(), uploadfile.getOriginalFilename());
		System.out.println("Upload local file " + uploadfile.getOriginalFilename() + " ok, fileid=" + fileId);
		Myfile file=new Myfile();
		file.setFileid(fileId);
		file.setFilename(uploadfile.getOriginalFilename());
		return file;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	@ResponseBody
	public void delete(@RequestParam String fileid) throws Exception{
		FastDFSClient.deleteFile(fileid);
	}	

}
