package cn.itcast.mis.req.form;

public class UserForm {

	private Integer userId;
	private String userName;
	private String userEmail;
	private String userPassowrd;
	
	
	public UserForm(Integer userId, String userName, String userEmail,
			String userPassowrd) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPassowrd = userPassowrd;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPassowrd() {
		return userPassowrd;
	}
	public void setUserPassowrd(String userPassowrd) {
		this.userPassowrd = userPassowrd;
	}
	
	
}
