package com.zhangzhihao.FileUpload.Java.Model;



import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class File {
	@Id
	private String fileName;
	private String MD5;
	private Date uploadDate;

	@Override
	public String toString() {
		return "File{" +
				"fileName='" + fileName + '\'' +
				", MD5='" + MD5 + '\'' +
				", uploadDate=" + uploadDate +
				'}';
	}

	public File(String fileName, String MD5, Date uploadDate) {
		this.fileName = fileName;
		this.MD5 = MD5;
		this.uploadDate = uploadDate;
	}

	public File() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMD5() {
		return MD5;
	}

	public void setMD5(String MD5) {
		this.MD5 = MD5;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}
