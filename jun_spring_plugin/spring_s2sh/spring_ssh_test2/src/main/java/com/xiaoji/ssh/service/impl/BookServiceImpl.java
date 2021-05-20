package com.xiaoji.ssh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoji.ssh.dao.BookDao;
import com.xiaoji.ssh.entity.Book;
import com.xiaoji.ssh.service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookDao bookDao;

	@Override
	public List<Book> getBookList() {
		return bookDao.getBookList();
	}

	@Override
	public void saveBook(Book book) {
		bookDao.saveBook(book);		
	}

	@Override
	public void deleteBook(int bookId) {
		bookDao.deleteBook(bookId);
	}

	@Override
	public void updateBook(Book book) {
		bookDao.updateBook(book);		
	}

	@Override
	public Book getBookByBookId(int bookId) {
		return bookDao.getBookByBookId(bookId);
	}
	
	
}
