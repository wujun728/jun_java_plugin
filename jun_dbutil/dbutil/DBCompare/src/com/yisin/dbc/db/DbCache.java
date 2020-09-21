package com.yisin.dbc.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yisin.dbc.entity.DbTable;
import com.yisin.dbc.entity.DbTableColumn;

public class DbCache {

    public static List<DbTable> mysqlDbTableList1 = new ArrayList<DbTable>();
	public static List<DbTable> mysqlDbTableList2 = new ArrayList<DbTable>();

	public static Map<String, List<DbTableColumn>> mysqlDbColumnMap1 = new HashMap<String, List<DbTableColumn>>();
	public static Map<String, List<DbTableColumn>> mysqlDbColumnMap2 = new HashMap<String, List<DbTableColumn>>();

}
