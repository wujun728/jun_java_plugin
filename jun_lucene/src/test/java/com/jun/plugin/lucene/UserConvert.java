package com.jun.plugin.lucene;

import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.test.lucence.LucenceConvert;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;


/**
 * user 和 docment 互转
 * @author DF
 *
 */
public class UserConvert implements LucenceConvert<User> {

	@Override
	public  User docToObject(Document doc) {
		User u = new User();
		u.setId(doc.get("id"));
		u.setName(doc.getField("name").stringValue());
		u.setSex(doc.get("sex"));
		u.setAge(doc.getField("age").numericValue().intValue());
		u.setIntroduce(doc.get("introduce"));
		u.setMoney(doc.getField("money").numericValue().doubleValue());
		u.setAddress(doc.getField("address").stringValue());
		u.setBirth(new Date(doc.getField("birth").numericValue().longValue()));
		return u;
	}

	/**
	 * 对象转成lucence的document 字典
	 */
	@Override
	public Document objectToDoc(User t) {
		Document doc = new Document();
		doc.add(new StringField("id", t.getId(),Store.YES));//新版版  不再设置是否分词属性  默认 只有textfield会分词
		doc.add(new TextField("name",t.getName(),Store.YES)); //有的需要存储 有的不需要存储，看需求 这里演示都存储
		doc.add(new StringField("sex",t.getSex(),Store.YES));
		doc.add(new IntField("age", t.getAge(), Store.YES));
		doc.add(new TextField("introduce",t.getIntroduce(),Store.YES));
		doc.add(new DoubleField("money", t.getMoney(), Store.YES));
		doc.add(new TextField("address",t.getAddress(),Store.YES));
		doc.add(new LongField("birth", t.getBirth().getTime(), Store.YES));//lucene没有时间索引 可以转成string 或者long  long 更好
		return doc;
	}


}
