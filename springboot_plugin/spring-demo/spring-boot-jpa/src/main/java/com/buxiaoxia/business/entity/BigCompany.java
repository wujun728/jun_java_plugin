package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:34
 */
@Data
@Entity
@Table(name = "big_company")
public class BigCompany {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	// 维护多对多一方
	@ManyToMany
	@JoinTable(name = "rel_country_company",
			joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false))
	private List<Country> countries = new ArrayList<>();

	public BigCompany() {
	}

	public BigCompany(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BigCompany that = (BigCompany) o;

		if (id != that.id) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

}
