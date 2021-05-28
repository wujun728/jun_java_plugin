package boot.spring.po;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//@Document(collection = "picture")
public class Picture implements Serializable{
	//pid供mongodb内部使用
	@Id
	private String id;
	private String filename;
	private String path;
	private Long size;
	
	public Picture(){}
	
	

	public Picture( String filename, String path, Long size) {
		this.filename = filename;
		this.path = path;
		this.size = size;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}



	@Override
	public String toString() {
		return "Picture [id=" + id + ", filename=" + filename + ", path=" + path + ", size=" + size + "]";
	}
	
}
