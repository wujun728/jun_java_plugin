package com.zhangzhihao.FileUpload.Java.Controller;

import com.zhangzhihao.FileUpload.Java.Model.File;
import com.zhangzhihao.FileUpload.Java.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

import static com.zhangzhihao.FileUpload.Java.Utils.CreateMd5.createMd5;
import static com.zhangzhihao.FileUpload.Java.Utils.SaveFile.getRealPath;
import static com.zhangzhihao.FileUpload.Java.Utils.SaveFile.saveFile;


@Controller
@RequestMapping("/FileUpload")
public class FileUploadController {
	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/Index", method = RequestMethod.GET)
	public String Index() {
		return "FileUpload/Index";
	}

	@ResponseBody
	@RequestMapping(value = "/FileUp", method = RequestMethod.POST)
	public String fileUpload(@RequestParam("id") String id,
	                         @RequestParam("name") String name,
	                         @RequestParam("type") String type,
	                         @RequestParam("lastModifiedDate") String lastModifiedDate,
	                         @RequestParam("size") int size,
	                         @RequestParam("file") MultipartFile file) {
		String fileName;

		try {
			String ext = name.substring(name.lastIndexOf("."));
			fileName = UUID.randomUUID().toString() + ext;
			saveFile(getRealPath(), fileName, file);
		} catch (Exception ex) {
			return "{\"error\":true}";
		}
		try {
			fileService.save(new File(fileName, createMd5(file).toString(), new Date()));
		} catch (Exception e) {
			return "{\"error\":true}";
		}

		return "{jsonrpc = \"2.0\",id = id,filePath = \"/Upload/\" + fileFullName}";
	}
}
