### db.record
通过 record(map)的方式操作表数据,似orm非orm,半对像操作
本类库基于jfinal V1.9 改造

### maven 使用
```
<dependency>
	<groupId>com.jun.plugin</groupId>
	<artifactId>jun-commmon</artifactId>
	<version>1.0.9</version>
</dependency>
```

### 示例

##### 1.neizhi
```
{
    "columns": {
        "column": [
            "appId",
            "name as XINGMING",
            "case when password is null then 'null' else '******' end as passwd"
        ]
    },
    "wheres": {
        "name": "测试0913-111122222222444",
        "password@>=": "123456",
        "phone@like": "13111111111",
        "profile@like": "%null",
        "roleId@in": [
            1,
            2,
            3
        ],
        "b": "普通用户666",
        "appId": 100001
    },
    "orders": [
        "name@desc",
        "password@sac"
    ]
}
```

##### 1.初始化Db
```
//初始化数据连接
Db.init("jdbc:mysql://host:port/test?characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&serverTimezone=GMT%2B8","root", "xxx");
//打印sql日志
Db.use().setShowSql(true);
```

##### 2.查询数据
```
//简单查询
List<Record> baskets = Db.find("select * from biz_test");
for (Record record : baskets) {
	System.out.println(record.getStr("id"));
	System.out.println(record.toJson());
}

//根据id查询
Record record = Db.findById("biz_test", "001")

//查询首条数据
Db.findFirst("select * from biz_test where id = ?", "001")

//分页查询 count参数决定是否统计总行数
Page<Record> p = Db.paginate(1, 2, "select * from biz_test where id>?", false, "1000");
p.getList();
p.getPageNumber();
p.getPageSize();
p.getTotalPage();
p.getTotalRow();
```

##### 3.新增
```
Record r = new Record();
r.set("id", "ddddd");
Db.save("biz_test", r);
```

##### 4.更新
```
Record r = new Record();
r.set("id", "ddddd");
Db.update("biz_test", r);
//主键名称非id
//Db.update("biz_test", "id", r);
```

##### 4.删除
```
Db.deleteById("biz_test", "001");
//Db.update("delete from biz_test");
```

##### 5.事务
```
Db.tx(new TransactionWrap() {
		@Override
		public boolean run() throws SQLException {
			try {
				Record r = new Record();
				r.set("id", "ddddd");
				Db.save("biz_test", r);
				
				r.set("id", "ddddd");
				r.set("remarks", "remarks");
				Db.update("biz_test", "id", r);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
	});
```

##### 6.多数据源
```
Db.init("jdbc:mysql://host:port/test?characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&serverTimezone=GMT%2B8","root", "xxx");
Db.initAlias("toDb","jdbc:mysql://xxx:3306/platform_dress_base?characterEncoding=utf-8&autoReconnect=true&autoReconnectForPools=true&serverTimezone=GMT%2B8","user","password")

//使用 Db.use 切换数据源
Db.use().find();//默认数据源
Db.use("toDb").find();//指定数据源
```
