package com.xiaoji.ssh.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xiaoji.ssh.entity.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml" })  
public class BookDaoTest {
	@Autowired
	private BookDao bookDao;
	
	@Test
	public void testGetBookList(){
		System.out.println("xiaoji");
	}
	
	@Test
	public void testSaveBookList(){
		System.out.println(bookDao.getBookList());
	}
	@Test
	public void testUpdateBookList(){
		Book book = new Book();
		book.setName("ssh3");
		book.setDescription("ssh3");
		bookDao.saveBook(book);
		System.out.println("ok");
	}
}
