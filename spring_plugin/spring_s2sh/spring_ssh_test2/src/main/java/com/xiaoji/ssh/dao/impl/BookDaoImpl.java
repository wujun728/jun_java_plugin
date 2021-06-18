package com.xiaoji.ssh.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.xiaoji.ssh.dao.BookDao;
import com.xiaoji.ssh.entity.Book;


@Repository
public class BookDaoImpl  implements BookDao {
	
	private HibernateTemplate hibernateTemplate;
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	@Autowired
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public List<Book> getBookList() {
		List<Book> list = (List<Book>)getHibernateTemplate().find("From Book");
		return list;
	}

	public void saveBook(Book book) {
		getHibernateTemplate().save(book);
		
	}

	public void deleteBook(int bookId) {
		getHibernateTemplate().delete(getBookByBookId(bookId));
		
	}

	public void updateBook(Book book) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(book);
	}

	public Book getBookByBookId(int bookId) {
		Book book = (Book)getHibernateTemplate().get(Book.class, bookId);
		return book;
	}
	

}
