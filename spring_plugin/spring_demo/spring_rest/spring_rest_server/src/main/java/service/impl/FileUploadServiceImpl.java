package service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import service.FileUploadService;

@Service
public class FileUploadServiceImpl implements FileUploadService{

	public void getUploadFile(MultipartFile uploadfile,String targetDir,String filename) {
		try {
			File targetfile=new File(targetDir,filename);
			uploadfile.transferTo(targetfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
