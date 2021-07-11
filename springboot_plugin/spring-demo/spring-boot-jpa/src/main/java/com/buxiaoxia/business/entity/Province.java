package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:34
 */
@Data
@Entity
public class Province {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "country_id", referencedColumnName = "id")
	private Country country;

	public Province() {
	}

	public Province(String name) {
		this.name = name;
	}

	public Province(String name, Country country) {
		this.name = name;
		this.country = country;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Province province = (Province) o;

		if (id != province.id) return false;
		if (name != null ? !name.equals(province.name) : province.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

}
