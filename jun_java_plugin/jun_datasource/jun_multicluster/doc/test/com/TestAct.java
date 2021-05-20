package com;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.caland.common.junit.AbstractSpringJunitTest;
import com.caland.common.page.Pagination;
import com.caland.core.bean.User;
import com.caland.core.query.UserQuery;
import com.caland.core.service.UserService;


/**
 * junit
 * @author lixu
 * @Date [2014-3-18 下午01:14:42]
 */
public class TestAct extends AbstractSpringJunitTest{

	@Test
	@Ignore
	public void testPage1() {
		//1
//		Integer deleteByKey = ds.deleteByKey("qwert0");
//		System.out.println(deleteByKey);
		//2
//		List<String> idList = new ArrayList<String>();
//		idList.add("qwert1");
//		idList.add("qwert2");
//		idList.add("qwert3");
//		idList.add("qwert4");
//
//		ds.deleteByKeys(idList);
		//3
//		Document d = new Document();
//		d.setId("qwert1");
//		d.setCreateDate(new Date());
//		Integer u = ds.updateDocumentByKey(d);
//		System.out.println(u);
		//4
//		String id = "qwert1";
//		Document userByKey = ds.getDocumentByKey(id);
//		System.out.println(userByKey);
		//5
//		List<String> idList = new ArrayList<String>();
//		idList.add("qwert1");
//		idList.add("qwert2");
//		idList.add("qwert3");
//		idList.add("qwert4");
//		List<Document> userByKeys = ds.getDocumentByKeys(idList);
//		for(Object user : userByKeys){
//			System.out.println(user);
//		}
		//6
//		DocumentQuery dq = new DocumentQuery();
//		dq.setId("qwert");
//		dq.setIdLike(true);
//		dq.orderbyId(false);
//		dq.setFields("createDate");
//		List<Document> documentList = ds.getDocumentList(dq);
//		for(Object d : documentList){
//			System.out.println(d);
//		}
		//7
//		dq.setUserId(265);
//		dq.setPage(2);
//		dq.setPageSize(3);
//		dq.orderbyId(false);
//		Pagination userListWithPage = ds.getDocumentListWithPage(dq);
//		for(Object d : userListWithPage.getList()){
//			System.out.println(d);
//		}
	}

	@Autowired
	private UserService us;
	@Test
	@Ignore
	public void testSave() throws Exception{
		User u = new User();
		u.setUsername("zhangsan");
		us.addUser(u);
	}
	@Test
	@Ignore
	public void testUpdate() {
		User u = new User();
		u.setId(11);
		u.setUsername("lisi");
		u.setAge(111);
		us.updateUserByKey(u);
	}
	@Test
	@Ignore
	public void testSearch(){
		UserQuery uq = new UserQuery();
		uq.setUsername("lisi");
		List<User> userList = us.getUserList(uq);
		for(User u : userList){
			System.out.println(u.toString());
		}
	}
	@Test
//	@Ignore
	public void testPage(){
		UserQuery uq = new UserQuery();
		uq.setUsername("lisi");
//		uq.orderbyId(false);
//		uq.orderbyAge(true);
		Pagination page = us.getUserListWithPage(uq);
		for(Object d : page.getList()){
			System.out.println(d);
		}
	}



	
}
