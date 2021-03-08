package spring_mvc.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class FileController {
	@ResponseBody
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String upload(MultipartFile file,HttpServletRequest request){
		System.out.println(file.getContentType());
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		String path=request.getServletContext().getRealPath("/common/img");
		System.out.println(path);
		File d=new File(path);
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(d,file.getOriginalFilename()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "上传成功!";
	}
	@RequestMapping(value="/download",method=RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletRequest request,String filename) throws IOException{
		String path=request.getServletContext().getRealPath("/common/img");

		File file=new File(path,filename);
		HttpHeaders headers=new HttpHeaders();
		String fileName=new String(filename.getBytes("UTF-8"),"iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);
		
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		//headers.setContentLength(contentLength);//设置长度
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
	}
	@ResponseBody
	@RequestMapping(value="/getfiles",method=RequestMethod.GET)
	public List<spring_mvc.model.File> getfiles(HttpServletRequest request){
		List<spring_mvc.model.File> files=new ArrayList<spring_mvc.model.File>();
		String path=request.getServletContext().getRealPath("/common/img");
		File file=new File(path);
		for(File f:file.listFiles()){
			spring_mvc.model.File fi=
					new spring_mvc.model.File(f.getName(),f.toURI().toString(),f.toURI().toString());
			files.add(fi);
		}
		return files;
	}
}
