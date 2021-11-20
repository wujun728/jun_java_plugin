package book.xml;

/**
 * 描述学生的JavaBean
 */
public class StudentBean {
	// 学生姓名
	private String name;
	// 学生性别 
	private String gender;
	// 学生年龄
	private int age;
	// 学生电话号码
	private String phone;

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("姓名：").append(this.name).append("；  ");
		sb.append("性别：").append(gender).append("；  ");
		sb.append("年龄：").append(age).append("；  ");
		sb.append("电话：").append(phone);
		return sb.toString();
	}
	
	/**
	 * @return 返回 age。
	 */
	public int getAge() {
		return age;
	}
	/**
	 * @param age 要设置的 age。
	 */
	public void setAge(int age) {
		this.age = age;
	}
	/**
	 * @return 返回 gender。
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender 要设置的 gender。
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return 返回 name。
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name 要设置的 name。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return 返回 phone。
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone 要设置的 phone。
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}