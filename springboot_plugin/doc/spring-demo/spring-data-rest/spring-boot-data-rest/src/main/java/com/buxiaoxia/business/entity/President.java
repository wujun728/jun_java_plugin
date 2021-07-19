package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:34
 */
@Data
@Entity
public class President {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Integer age;

	@OneToOne
	@JoinColumn(name = "country_id", referencedColumnName = "id")
	private Country country;

	public President() {
	}

	// 需要设置country的president为当前country
	public void setCountry(Country country) {
		country.setPresident(this);
		this.country = country;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		President president = (President) o;

		if (id != president.id) return false;
		if (name != null ? !name.equals(president.name) : president.name != null) return false;
		if (age != null ? !age.equals(president.age) : president.age != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		return result;
	}

}
