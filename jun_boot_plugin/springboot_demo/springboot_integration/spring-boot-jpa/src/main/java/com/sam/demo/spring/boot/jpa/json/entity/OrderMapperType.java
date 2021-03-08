package com.sam.demo.spring.boot.jpa.json.entity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OrderMapperType implements UserType {

	@Override
	public int[] sqlTypes() {
		return new int[] { StringType.INSTANCE.sqlType() };
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Class<HashSet> returnedClass() {
		return HashSet.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if (x == null || y == null) {
			return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		String json = rs.getString(names[0]);
		if (json == null || json.trim().length() == 0) {
			return new HashSet<Order>();
		}

		Gson gson = new GsonBuilder().create();
		Type setType = new TypeToken<HashSet<Order>>(){}.getType();
		Set<Order> sets = gson.fromJson(json, setType);
		return sets;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, StringType.INSTANCE.sqlType());
		} else {
			Gson gson = new GsonBuilder().create();
			st.setString(index, gson.toJson(value));
		}

	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null)
			return null;
		HashSet<Order> jsonList = new HashSet<Order>();
		jsonList.addAll((HashSet<Order>)value);
		return jsonList;
	}

	@Override
	public boolean isMutable() {
		return true;  
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return ((Serializable)value); 
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;  
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;  
	}

}