package po;

public class Doc {
	String file;
	long size;
	String content;
	
	public Doc(String file, long size, String content) {
		this.file = file;
		this.size = size;
		this.content = content;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
