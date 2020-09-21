package com.holder.test;

import java.util.List;

import com.holder.dbhelper.InstanceSqlHelper;
import com.holder.dbhelper.MySelect;

public class TestMain {
	public static <T> List<T> get() {
		return null;
	}

	public static void main(String[] args) {
		System.out.println(InstanceSqlHelper.getInsertSql(MySelect.class));
		System.out.println(InstanceSqlHelper.getUpdateSql(MySelect.class));
		System.out.println(InstanceSqlHelper.getSelectSql(MySelect.class));
	}
}
