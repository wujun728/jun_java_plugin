package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.BigCompany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:43
 */
public interface CompanyRepository extends JpaRepository<BigCompany,Integer> {

	BigCompany findOneByName(String name);
}
