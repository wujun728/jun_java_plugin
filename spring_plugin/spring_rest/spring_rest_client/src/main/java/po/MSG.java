package po;

import java.io.Serializable;

public class MSG implements Serializable{
	String status;

	Object data;

	public MSG() {
	}

	public MSG(String status) {
		this.status = status;
	}
	
	public MSG(String status, Object data) {
		this.status = status;
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
