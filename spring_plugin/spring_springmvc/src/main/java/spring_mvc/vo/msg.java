package spring_mvc.vo;

public class msg {
	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public msg(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public msg() {
	}
	
}
