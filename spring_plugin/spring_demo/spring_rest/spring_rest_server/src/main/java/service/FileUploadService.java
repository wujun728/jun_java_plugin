package service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	void getUploadFile(MultipartFile uploadfile,String targetDir,String filename);
}
