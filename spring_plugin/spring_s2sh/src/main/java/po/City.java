package po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="city")
public class City {
	@Id @Column(name="city_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private short id;
	private String city;
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	
	
	
	public City() {
	}
	

	public City(String city, Date last_update, Country country) {
		this.city = city;
		this.last_update = last_update;
		this.country = country;
	}

	//1-N，N端，注解外键列
	@ManyToOne(targetEntity=Country.class)
	@JoinColumn(name="country_id")
	private Country country;
	
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
}
