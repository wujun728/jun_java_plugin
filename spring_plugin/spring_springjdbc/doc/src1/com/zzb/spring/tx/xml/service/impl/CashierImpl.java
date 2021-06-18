package com.zzb.spring.tx.xml.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.zzb.spring.tx.xml.service.BookShopService;
import com.zzb.spring.tx.xml.service.Cashier;

//这个类表示 买多本书
public class CashierImpl implements Cashier {

	private BookShopService bookShopService;
	
	public void setBookShopService(BookShopService bookShopService) {
		this.bookShopService = bookShopService;
	}
	
	//这已经是个事务
	@Transactional
	@Override
	public void checkout(String username, List<String> isbns) {
		for(String isbn: isbns){
			bookShopService.purchase(username, isbn);//这里调用另一个事务方法，涉及到事务传播
		}
	}

}
