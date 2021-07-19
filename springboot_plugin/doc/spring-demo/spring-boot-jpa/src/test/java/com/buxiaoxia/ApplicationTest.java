package com.buxiaoxia;

import com.buxiaoxia.business.entity.BigCompany;
import com.buxiaoxia.business.entity.Country;
import com.buxiaoxia.business.entity.President;
import com.buxiaoxia.business.entity.Province;
import com.buxiaoxia.business.repository.CompanyRepository;
import com.buxiaoxia.business.repository.CountryRepository;
import com.buxiaoxia.business.repository.PresidentRepository;
import com.buxiaoxia.business.repository.ProvinceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by xw on 2017/2/22.
 * 2017-02-22 18:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
public class ApplicationTest {

	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private PresidentRepository presidentRepository;
	@Autowired
	private ProvinceRepository provinceRepository;

	@Before
	public void before() {
		Country country = new Country("中国");
		Country country2 = new Country("美国");
		country.addProvince(new Province("江苏"));
		country.addProvince(new Province("上海"));
		President president = new President("不小下");
		country.setPresident(president);
		BigCompany company1 = new BigCompany("公司1");
		BigCompany company2 = new BigCompany("公司2");
//		country.setCompanies(Arrays.asList(company1, company2));
		countryRepository.save(country);
		countryRepository.save(country2);
		//公司1对应1个国家
		company1.setCountries(new ArrayList() {{
			add(country);
		}});
		companyRepository.save(company1);
		//公司2对应2个国家
		company2.setCountries(new ArrayList() {{
			add(country);
		}});
		companyRepository.save(company2);
		System.out.println("===================================");
	}

	@Test
	public void testSave() {
		assertEquals(2, countryRepository.findAll().size());
		assertEquals(2, companyRepository.findAll().size());
		assertEquals(1, presidentRepository.findAll().size());
		assertEquals(2, provinceRepository.findAll().size());
	}

	@Test
	public void testOneToOneFind() {
		Country country = countryRepository.findOneByName("中国");
		assertEquals("不小下", country.getPresident().getName());
	}

	@Test
	public void testOneToManyFind() {
		Country country = countryRepository.findOneByName("中国");
		assertEquals(2, country.getProvinces().size());
	}

	@Test
	@Transactional
	public void testManyToManyFind() {
		BigCompany company1 = companyRepository.findOneByName("公司1");
		assertEquals(1, company1.getCountries().size());
	}

	@Test
	@Transactional
	public void testMainDelete() {
		// 多对多维护方先删除关系 company 是维护方
//		companyRepository.deleteAll();
		companyRepository.findAll().forEach(bigCompany -> {
			bigCompany.setCountries(null);
			companyRepository.save(bigCompany);
		});
		companyRepository.findAll().forEach(bigCompany -> {
			assertNull(bigCompany.getCountries());
		});
		Country country = countryRepository.findOneByName("中国");
		countryRepository.delete(country.getId());
		country = countryRepository.findOneByName("中国");
		assertNull(country);
	}

	@After
	public void after() {
		System.out.println("===================================");
		countryRepository.deleteAll();
		companyRepository.deleteAll();
	}
}
