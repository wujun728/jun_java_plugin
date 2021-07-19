package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * country（国家）  1:1 president（总统）
 * country（国家）  1:n province（省份）
 * country（国家）  n:n company（跨国公司）
 * <p>
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:34
 */
@Data
@Entity
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@OneToOne(mappedBy = "country", cascade = CascadeType.ALL)
	private President president;
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Province> provinces = new ArrayList<>();
	@ManyToMany(mappedBy = "countries", cascade = CascadeType.ALL)
	private List<BigCompany> companies = new ArrayList<>();

	public Country() {
	}

	public void setPresident(President president) {
		president.setCountry(this);
		this.president = president;
	}

	public void addProvince(Province province) {
		province.setCountry(this);
		provinces.add(province);
	}

	public void setProvinces(List<Province> provinces){
		if(provinces != null){
			provinces.forEach(province -> province.setCountry(this));
		}
		this.provinces = provinces;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Country country = (Country) o;

		if (id != country.id) return false;
		if (name != null ? !name.equals(country.name) : country.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}


}
