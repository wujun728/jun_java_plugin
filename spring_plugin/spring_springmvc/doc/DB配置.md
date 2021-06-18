1、官方下载地址及使用文件地址
	下载地址：https://github.com/mybatis/mybatis-3
	使用文档地址:http://www.mybatis.org/mybatis-3/zh/index.html
2、导入相关jar包
	mybatisjar包:mybatis-3.4.x.jar
	jdbc驱动:mysql-connector-java-5.1.4.x.jar
	junit测试包 4.x
	
3、在resources目录下创建mybatis-config.xml
	然后对其进行初始配置:详见mybatis-config.xml文件
	
4、创建以下配置文件及相关类、接口
	4.1 PO持久对象: 
		4.1.1 Account 类名与表名相同.
		4.1.2  其属性与表字段一一对应
		4.1.3  表中多个单词组成的字段以“_”下划线分隔,对应PO对象里属性为驼峰命名.
				如表字段为"nick_name" ，PO对应属性为 nickName;
		4.1.4  类型：表中为Number类型的对应PO对象中的Number类型及其包装类
				  表中vachar、char、text等对应PO对象中的String类形
	4.2 AccountMapper 接口 等同于 DAO接口(可以不需要)
	4.3 AccountMapper.xml 映射配置文件 一般与AccountMapper接口一一对应
		(一个AccountMapper接口对应一个AccountMapper.xml映射文件)
		4.3.1 在该配置文件中增加一个select标签.
			 其属性returnType值为PO持久对象全路径(包+类)
			 id属性符合java标识符命名规则即可
			 在标签块中加入查询sql语句
			 
5、创建Test类
	5.1 创建并初始化SqlSessionFactory对象:
		//Resources读取配置文件路径从classes根路径开始
		String resource = "mybatis-config.xml";
		InputStream inputStream = 						Resources.getResourceAsStream(resource);
		SqlSessionFactory sqlSessionFactory = new 						SqlSessionFactoryBuilder().build(inputStream);
	5.2 通过SqlSessionFactory获取SqlSession对象
		SqlSession session = sqlSessionFactory.openSession();
		//第一个参数 :
		//	为 Mapper.xml映射文件中根节点mapper的namespace属性值.select标签的id属性值
		List<Account> list = session.selectList("第一个参数");	
		System.out.println( list );
		
	
		
	
	