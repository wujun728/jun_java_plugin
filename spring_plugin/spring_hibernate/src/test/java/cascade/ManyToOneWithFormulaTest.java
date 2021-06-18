/*
  * Hibernate, Relational Persistence for Idiomatic Java
  *
  * Copyright (c) 2009, Red Hat, Inc. and/or its affiliates or third-
  * party contributors as indicated by the @author tags or express
  * copyright attribution statements applied by the authors.
  * All third-party contributions are distributed under license by
  * Red Hat, Inc.
  *
  * This copyrighted material is made available to anyone wishing to
  * use, modify, copy, or redistribute it subject to the terms and
  * conditions of the GNU Lesser General Public License, as published
  * by the Free Software Foundation.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this distribution; if not, write to:
  *
  * Free Software Foundation, Inc.
  * 51 Franklin Street, Fifth Floor
  * Boston, MA  02110-1301  USA
  */
package cascade;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Company;
import com.ketayao.learn.hibernate.entity.Language;
import com.ketayao.learn.hibernate.entity.Message;
import com.ketayao.learn.hibernate.entity.Person;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/**
 * @author Sharath Reddy
 */
public class ManyToOneWithFormulaTest {
	@Test
	public void testManyToOneFromPk() throws Exception {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		Company company = new Company();
		company.setQ("A");
		
		List<Company> companies = new ArrayList<Company>();
		companies.add(company);
		
		Person person = new Person();
		person.setName("yao");
		person.setCompanies(companies);
		
		company.setPerson(person);
		
		s1.save(person);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testManyToOneFromPk2() throws Exception {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		s1.enableFilter("getACompany");
		Person person = (Person) s1.get(Person.class, 1);
		
		List<Company> companies = person.getCompanies();
		for (Company company : companies) {
			System.out.println(company.getQ());
		}
		
		//s1.save(person);
		s1.getTransaction().commit();
	}

	@Test
	public void testManyToOneFromPk3() throws Exception {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();

		
		Language language = new Language();
		language.setCode("zh");
		language.setName("中文");
		
		Message message = new Message();
		message.setLanguage(language);
//		message.setLanguageCode("zh");
//		message.setLanguageName("普通话");
		
		Message message2 = new Message();
		message2.setLanguage(language);
//		message2.setLanguageCode("zh");
//		message2.setLanguageName("四川话");
		
		List<Message> messages = new ArrayList<Message>();
		messages.add(message);
		messages.add(message2);
		
		language.setMessages(messages);
		
		s1.save(language);
		s1.getTransaction().commit();
	}

	@Test
	public void testManyToOneFromPk4() throws Exception {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		Language language = (Language)s1.get(Language.class, "zh");
		List<Message> messages = language.getMessages();
		
		for (Message message : messages) {
			System.err.println(message.getLanguage().getName());
		}
		
		//s1.save(language);
		s1.getTransaction().commit();
	}
}
