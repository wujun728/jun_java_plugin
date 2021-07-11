package modisefileupload.java.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;
/**
 * In this class we will turn all the Class attributes into database table components
 * adding data validations
 * @author mrmodise
 *
 */
@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="userId",unique=true, nullable=false)
	private Long userId;
	
	@Column(name="firstName", nullable=false)
	@NotEmpty(message="The first name must be captured")
	private String firstName;
	
	@Column(name="lastName", nullable=false)
	@NotEmpty(message="The last name must be captured")
	private String lastName;
	
	@Column(name="address", nullable=false)
	@NotEmpty(message="The address details must be captured")
	private String address;
	
	@Column(name="age", nullable=false)
	@Min(value=0, message="Age cannot be less than 1")
	@NotNull(message="The age must be captured")
	private int age;
	
	@Transient
	private MultipartFile userImage;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public MultipartFile getUserImage() {
		return userImage;
	}
	public void setUserImage(MultipartFile userImage) {
		this.userImage = userImage;
	}
	
	

}
