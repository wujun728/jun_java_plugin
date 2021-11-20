package po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;



@Entity
@Table(name="country")
public class Country {
	@Id @Column(name="country_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private short id;
	private String country;
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	//双向1-N映射，1端放弃关系控制
	@OneToMany(targetEntity=City.class,mappedBy="country",fetch=FetchType.EAGER,cascade=CascadeType.ALL,orphanRemoval=true)
	private Set<City> citys=new HashSet<City>();
	
	public Set<City> getCitys() {
		return citys;
	}
	public void setCitys(Set<City> citys) {
		this.citys = citys;
	}
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	@Override
	public String toString() {
		return "Country [id=" + id + ", country=" + country + ", last_update="
				+ last_update + ", citys=" + citys + "]";
	}
	
}
