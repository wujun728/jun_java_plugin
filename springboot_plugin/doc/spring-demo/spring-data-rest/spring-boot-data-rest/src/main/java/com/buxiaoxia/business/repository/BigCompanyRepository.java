package com.buxiaoxia.business.repository;

import com.buxiaoxia.business.entity.BigCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by xw on 2017/5/10.
 * 2017-05-10 13:56
 */
// 通过该注解，可以修改 URI 的Path
@RepositoryRestResource(collectionResourceRel = "companies",path = "companies")
public interface BigCompanyRepository extends JpaRepository<BigCompany, Integer> {
}
