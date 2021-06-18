package spring_mvc.model;

public class File {
	private String filename;
	private String img;
	private String upload;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUpload() {
		return upload;
	}
	public void setUpload(String upload) {
		this.upload = upload;
	}
	public File(String filename, String img, String upload) {
		this.filename = filename;
		this.img = img;
		this.upload = upload;
	}
	public File() {
	}
}
