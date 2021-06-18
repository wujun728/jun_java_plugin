package spring_mvc.model;

public class address {
	private String tel;
	private String street;
	private String No;
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNo() {
		return No;
	}
	public void setNo(String no) {
		No = no;
	}
	public address(String tel, String street) {
	
		this.tel = tel;
		this.street = street;
	}
	@Override
	public String toString() {
		return "address [tel=" + tel + ", street=" + street + ", No=" + No + "]";
	}
	
}
