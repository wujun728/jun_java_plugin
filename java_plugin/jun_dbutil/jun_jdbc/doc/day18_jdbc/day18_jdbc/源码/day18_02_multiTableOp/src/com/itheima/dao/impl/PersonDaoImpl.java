package com.itheima.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.domain.IdCard;
import com.itheima.domain.Person;
import com.itheima.util.DBCPUtil;

public class PersonDaoImpl {
	private QueryRunner qr = new QueryRunner(DBCPUtil.getDataSource());
	
	public void savePerson(Person p){
		try{
			qr.update("insert into persons values(?,?)", p.getId(),p.getName());
			IdCard idcard = p.getIdcard();
			if(idcard!=null){
				qr.update("insert into id_card (id,num) values (?,?)", p.getId(),idcard.getNum());
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	//查询人信息是，要不要查对应的idcard呢？ 建议查出来。
	public Person findPersonById(int personId){
		try{
			Person p = qr.query("select * from persons where id=?", new BeanHandler<Person>(Person.class),personId);
			if(p!=null){
				IdCard idcard = qr.query("select * from id_card where id=?",  new BeanHandler<IdCard>(IdCard.class),personId);
				p.setIdcard(idcard);
			}
			return p;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
