package org.frameworkset.elasticsearch.imp;
/**
 * Copyright 2008 biaoping.yin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.frameworkset.common.poolman.BatchHandler;
import com.frameworkset.common.poolman.ConfigSQLExecutor;
import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.poolman.util.SQLUtil;
import com.frameworkset.util.SimpleStringUtil;
import org.frameworkset.elasticsearch.ElasticSearchHelper;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.elasticsearch.entity.ESDatas;
import org.frameworkset.elasticsearch.scroll.HandlerInfo;
import org.frameworkset.elasticsearch.scroll.ScrollHandler;
import org.frameworkset.elasticsearch.serial.ESInnerHitSerialThreadLocal;
import org.frameworkset.elasticsearch.serial.SerialUtil;
import org.frameworkset.spi.assemble.PropertiesContainer;
import org.frameworkset.tran.DataRefactor;
import org.frameworkset.tran.DataStream;
import org.frameworkset.tran.context.Context;
import org.frameworkset.tran.es.input.db.ES2DBExportBuilder;
import org.frameworkset.tran.schedule.CallInterceptor;
import org.frameworkset.tran.schedule.ImportIncreamentConfig;
import org.frameworkset.tran.schedule.TaskContext;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 从es中查询数据导入数据库案例，同步处理程序，如需调试同步功能，直接运行main方法即可</p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/1/11 14:39
 * @author biaoping.yin
 * @version 1.0
 */
public class ES2DBDemo {
	public static void main(String[] args){
		ES2DBDemo esDemo = new ES2DBDemo();
//		esDemo.scheduleScrollRefactorImportData();
		esDemo.exportSliceDataWithInnerhit();

		System.out.println("complete.");
	}
	protected void buildDBConfigAndStartDatasource(){

		PropertiesContainer propertiesContainer = new PropertiesContainer();
		propertiesContainer.addConfigPropertiesFile("application.properties");
		String dbName  = propertiesContainer.getProperty("db.name");
		String dbUser  = propertiesContainer.getProperty("db.user");
		String dbPassword  = propertiesContainer.getProperty("db.password");
		String dbDriver  = propertiesContainer.getProperty("db.driver");
		String dbUrl  = propertiesContainer.getProperty("db.url");


		String validateSQL  = propertiesContainer.getProperty("db.validateSQL");


		String _jdbcFetchSize = propertiesContainer.getProperty("db.jdbcFetchSize");
		Integer jdbcFetchSize = null;
		if(_jdbcFetchSize != null && !_jdbcFetchSize.equals(""))
			jdbcFetchSize  = Integer.parseInt(_jdbcFetchSize);

		//启动数据源
		SQLUtil.startPool(dbName,//数据源名称
				dbDriver,//mysql驱动
				dbUrl,//mysql链接串
				dbUser,dbPassword,//数据库账号和口令
				validateSQL, //数据库连接校验sql
				jdbcFetchSize // jdbcFetchSize
		);


	}
	/**
	 * 采用原始方法导入数据
	 */
	public void directExport()  {

		//启动数据源
		/**
		SQLUtil.startPool("test",//数据源名称
				"com.mysql.jdbc.Driver",//mysql驱动
				"jdbc:mysql://localhost:3306/bboss?useCursorFetch=true",//mysql链接串
				"root","123456",//数据库账号和口令
				"select 1 ", //数据库连接校验sql
				10000 // jdbcFetchSize
		);*/
		buildDBConfigAndStartDatasource();
		//scroll分页检索
		final int batchSize = 5000;

		final String sql = "insert into batchtest (name) values(?)";
		String url = "dbdemo/_search";
		String dslName = "scrollQuery";
		final String sqlName = "insertSQL";
		String dsl2ndSqlFile = "dsl2ndSqlFile.xml";
		String scrollLiveTime = "100m";
		Map params = new HashMap();
		params.put("size", batchSize);//每页5000条记录
		final BatchHandler<Map> batchHandler = new BatchHandler<Map>() {
			@Override
			public void handler(PreparedStatement stmt, Map esrecord, int i) throws SQLException {
				stmt.setString(1, (String) esrecord.get("logContent"));
			}
		};

		//清空数据
		try {
			SQLExecutor.delete("delete from batchtest");
		}
		catch (Exception e){

		}
		//采用自定义handler函数处理每个scroll的结果集后，response中只会包含总记录数，不会包含记录集合
		//scroll上下文有效期1分钟；大数据量时可以采用handler函数来处理每次scroll检索的结果，规避数据量大时存在的oom内存溢出风险
		final ConfigSQLExecutor configSQLExecutor = new ConfigSQLExecutor(dsl2ndSqlFile);
		ClientInterface clientUtil = ElasticSearchHelper.getConfigRestClientUtil(dsl2ndSqlFile);
		ESDatas<Map> response = clientUtil.scroll(url, dslName, scrollLiveTime, params, Map.class, new ScrollHandler<Map>() {
			public void handle(ESDatas<Map> response, HandlerInfo handlerInfo) throws Exception {//自己处理每次scroll的结果
				List<Map> datas = response.getDatas();
				long totalSize = response.getTotalSize();
				System.out.println("totalSize:"+totalSize+",datas.size:"+datas.size());
				if(sql == null) {
					configSQLExecutor.executeBatch(sqlName, datas, batchSize, batchHandler);
				}
				else{
					SQLExecutor.executeBatch(sql, datas, batchSize, batchHandler);
				}
			}
		});

	}

	/**
	 * 采用工具串行导入
	 */
	public void exportData(){

		ES2DBExportBuilder exportBuilder = new ES2DBExportBuilder();
		exportBuilder.setSqlName("insertSQL"); //指定将es文档数据同步到数据库的sql语句名称，配置在dsl2ndSqlFile.xml中
		exportBuilder
				.setDsl2ndSqlFile("dsl2ndSqlFile.xml")//配置dsl和sql语句的配置文件
				.setDslName("scrollQuery") //指定从es查询索引文档数据的dsl语句名称，配置在dsl2ndSqlFile.xml中
				.setScrollLiveTime("10m") //scroll查询的scrollid有效期
//					 .setSliceQuery(true)
//				     .setSliceSize(5)
				.setQueryUrl("dbdemo/_search") //查询索引表demo中的文档数据
				.setBatchHandler(new BatchHandler<Map>() { //重要每条文档记录交个这个回调函数处理
					@Override
					public void handler(PreparedStatement stmt, Map esrecord, int i) throws SQLException {
						stmt.setString(1, (String) esrecord.get("logContent"));//将当期文档的字段设置到db批处理语句中
					}
				})
		//				//添加dsl中需要用到的参数及参数值
//				.addParam("var1","v1")
//				.addParam("var2","v2")
//				.addParam("var3","v3")
		;
		exportBuilder.setBatchSize(5000).setFetchSize(5000);		  //指定批量获取es数据size
		DataStream dataStream = exportBuilder.builder();
		dataStream.execute();
		dataStream.destroy();
	}

	/**
	 * 采用工具并行导入
	 */
	public void exportParallelData(){

		ES2DBExportBuilder exportBuilder = new ES2DBExportBuilder();
		exportBuilder.setBatchSize(5000).setFetchSize(5000).setParallel(true); //指定批量获取es数据size
		exportBuilder.setSqlName("insertSQL"); //指定将es文档数据同步到数据库的sql语句名称，配置在dsl2ndSqlFile.xml中
		exportBuilder.setDsl2ndSqlFile("dsl2ndSqlFile.xml")//配置dsl和sql语句的配置文件
				.setDslName("scrollQuery") //指定从es查询索引文档数据的dsl语句名称，配置在dsl2ndSqlFile.xml中
				.setScrollLiveTime("10m") //scroll查询的scrollid有效期

//					 .setSliceQuery(true)
//				     .setSliceSize(5)
				.setQueryUrl("dbdemo/_search") //查询索引表demo中的文档数据
				.setBatchHandler(new BatchHandler<Map>() { //重要每条文档记录交个这个回调函数处理
					@Override
					public void handler(PreparedStatement stmt, Map esrecord, int i) throws SQLException {
						stmt.setString(1, (String) esrecord.get("logContent"));//将当期文档的字段设置到db批处理语句中
					}
				})
//				//添加dsl中需要用到的参数及参数值
//				.addParam("var1","v1")
//				.addParam("var2","v2")
//				.addParam("var3","v3")
		;

		DataStream dataStream = exportBuilder.builder();
		dataStream.execute();
		dataStream.destroy();
	}

	/**
	 * 采用工具slice并行导入
	 */

	public void exportSliceData(){

		ES2DBExportBuilder exportBuilder = new ES2DBExportBuilder();
		exportBuilder.setBatchSize(5000).setFetchSize(5000);	 //指定批量获取es数据size
		exportBuilder.setSqlName("insertSQL"); //指定将es文档数据同步到数据库的sql语句名称，配置在dsl2ndSqlFile.xml中
		exportBuilder.setDsl2ndSqlFile("dsl2ndSqlFile.xml")
				.setDslName("scrollSliceQuery")
				.setScrollLiveTime("10m")
					 .setSliceQuery(true) //设置并行从es获取数据标识
				     .setSliceSize(5) //设置并行slice个数
				.setQueryUrl("dbdemo/_search")
//				     .setPrintTaskLog(true)
				.setBatchHandler(new BatchHandler<Map>() {
					@Override
					public void handler(PreparedStatement stmt, Map esrecord, int i) throws SQLException {
						stmt.setString(1, (String) esrecord.get("logContent"));
					}
				})
//				//添加dsl中需要用到的参数及参数值
//				.addParam("var1","v1")
//				.addParam("var2","v2")
//				.addParam("var3","v3")
				;

		DataStream dataStream = exportBuilder.builder();
		dataStream.execute();
		dataStream.destroy();
	}

	/**
	 * 采用工具slice并行导入
	 */

	public void exportSliceDataWithInnerhit(){

		ES2DBExportBuilder exportBuilder = new ES2DBExportBuilder();
		exportBuilder.setBatchSize(100).setFetchSize(20);
		exportBuilder.setSqlName("insertSQL"); //指定将es文档数据同步到数据库的sql语句名称，配置在dsl2ndSqlFile.xml中
		exportBuilder.setDsl2ndSqlFile("dsl2ndSqlFile.xml")
				.setDslName("scrollSliceQuery")
				.setScrollLiveTime("10m")
				.setSliceQuery(true) //设置并行从es获取数据标识
				.setSliceSize(5) //设置并行slice个数
				.setQueryUrl("dbdemo/_search")
//				     .setPrintTaskLog(true)
				.setBatchHandler(new BatchHandler<Map>() {
					@Override
					public void handler(PreparedStatement stmt, Map esrecord, int i) throws SQLException {
						stmt.setString(1, (String) esrecord.get("logContent"));
					}
				})
//				//添加dsl中需要用到的参数及参数值
//				.addParam("var1","v1")
//				.addParam("var2","v2")
//				.addParam("var3","v3")
		;

		DataStream dataStream = exportBuilder.builder();
		try {
			ESInnerHitSerialThreadLocal.setESInnerTypeReferences(Object.class);
			dataStream.execute();
			dataStream.destroy();
		}
		finally {
			ESInnerHitSerialThreadLocal.clean();
		}
	}

	/**
	 * 在代码中写sql导入
	 */
	public void exportDataUseSQL(){
		ES2DBExportBuilder exportBuilder = new ES2DBExportBuilder();
		exportBuilder.setBatchSize(5000).setFetchSize(5000);
		exportBuilder.setSql("insert into batchtest (name) values(?)"); //直接指定sql语句
		exportBuilder.setDsl2ndSqlFile("dsl2ndSqlFile.xml")

				.setDslName("scrollSliceQuery")
				.setScrollLiveTime("10m")
				.setSliceQuery(true)
				.setSliceSize(5)
				.setQueryUrl("dbdemo/_search")
				.setBatchHandler(new BatchHandler<Map>() {
					@Override
					public void handler(PreparedStatement stmt, Map esrecord, int i) throws SQLException {
						stmt.setString(1, (String) esrecord.get("logContent"));
					}
				})
//				//添加dsl中需要用到的参数及参数值
//				.addParam("var1","v1")
//				.addParam("var2","v2")
//				.addParam("var3","v3")
		;

		DataStream dataStream = exportBuilder.builder();
		dataStream.execute();
		dataStream.destroy();
	}

	public void scheduleSlieRefactorImportData(){
		ES2DBExportBuilder importBuilder = new ES2DBExportBuilder();
		importBuilder.setBatchSize(2).setFetchSize(10);


		//指定导入数据的sql语句，必填项，可以设置自己的提取逻辑，
		// 设置增量变量log_id，增量变量名称#[log_id]可以多次出现在sql语句的不同位置中，例如：
		// select * from td_sm_log where log_id > #[log_id] and parent_id = #[log_id]
		// log_id和数据库对应的字段一致,就不需要设置setLastValueColumn信息，
		// 但是需要设置setLastValueType告诉工具增量字段的类型
		importBuilder.setSqlName("insertSQLnew"); //指定将es文档数据同步到数据库的sql语句名称，配置在dsl2ndSqlFile.xml中
		/**
		 * es相关配置
		 */
		importBuilder
				.setDsl2ndSqlFile("dsl2ndSqlFile.xml")
				.setDslName("scrollSliceQuery")
				.setScrollLiveTime("10m")
				.setSliceQuery(true)
				.setSliceSize(5)
				.setQueryUrl("dbdemo/_search")

//				//添加dsl中需要用到的参数及参数值
				.addParam("var1","v1")
				.addParam("var2","v2")
				.addParam("var3","v3");

		//定时任务配置，
		importBuilder.setFixedRate(false)//参考jdk timer task文档对fixedRate的说明
//					 .setScheduleDate(date) //指定任务开始执行时间：日期
				.setDeyLay(1000L) // 任务延迟执行deylay毫秒后执行
				.setPeriod(10000L); //每隔period毫秒执行，如果不设置，只执行一次
		//定时任务配置结束

		//设置任务执行拦截器，可以添加多个
		importBuilder.addCallInterceptor(new CallInterceptor() {
			@Override
			public void preCall(TaskContext taskContext) {
				System.out.println("preCall");
			}

			@Override
			public void afterCall(TaskContext taskContext) {
				System.out.println("afterCall");
			}

			@Override
			public void throwException(TaskContext taskContext, Exception e) {
				System.out.println("throwException");
			}
		}).addCallInterceptor(new CallInterceptor() {
			@Override
			public void preCall(TaskContext taskContext) {
				System.out.println("preCall 1");
			}

			@Override
			public void afterCall(TaskContext taskContext) {
				System.out.println("afterCall 1");
			}

			@Override
			public void throwException(TaskContext taskContext, Exception e) {
				System.out.println("throwException 1");
			}
		});
//		//设置任务执行拦截器结束，可以添加多个
		//增量配置开始
		importBuilder.setLastValueColumn("logId");//手动指定数字增量查询字段，默认采用上面设置的sql语句中的增量变量名称作为增量查询字段的名称，指定以后就用指定的字段
		importBuilder.setFromFirst(true);//setFromfirst(false)，如果作业停了，作业重启后从上次截止位置开始采集数据，
		//setFromfirst(true) 如果作业停了，作业重启后，重新开始采集数据
		importBuilder.setLastValueStorePath("es2dbdemo_import");//记录上次采集的增量字段值的文件路径，作为下次增量（或者重启后）采集数据的起点，不同的任务这个路径要不一样
//		importBuilder.setLastValueStoreTableName("logs");//记录上次采集的增量字段值的表，可以不指定，采用默认表名increament_tab
		importBuilder.setLastValueType(ImportIncreamentConfig.NUMBER_TYPE);//如果没有指定增量查询字段名称，则需要指定字段类型：ImportIncreamentConfig.NUMBER_TYPE 数字类型
		// 或者ImportIncreamentConfig.TIMESTAMP_TYPE 日期类型
		//增量配置结束

		//映射和转换配置开始
//		/**
//		 * db-es mapping 表字段名称到es 文档字段的映射：比如document_id -> docId
//		 * 可以配置mapping，也可以不配置，默认基于java 驼峰规则进行db field-es field的映射和转换
//		 */
//		importBuilder.addFieldMapping("document_id","docId")
//				.addFieldMapping("docwtime","docwTime")
//				.addIgnoreFieldMapping("channel_id");//添加忽略字段
//
//
//		/**
//		 * 为每条记录添加额外的字段和值
//		 * 可以为基本数据类型，也可以是复杂的对象
//		 */
//		importBuilder.addFieldValue("testF1","f1value");
//		importBuilder.addFieldValue("testInt",0);
//		importBuilder.addFieldValue("testDate",new Date());
//		importBuilder.addFieldValue("testFormateDate","yyyy-MM-dd HH",new Date());
//		TestObject testObject = new TestObject();
//		testObject.setId("testid");
//		testObject.setName("jackson");
//		importBuilder.addFieldValue("testObject",testObject);
		importBuilder.addFieldValue("author","作者");

		/**
		 * 重新设置es数据结构
		 */
		importBuilder.setDataRefactor(new DataRefactor() {
			public void refactor(Context context) throws Exception  {
				//可以根据条件定义是否丢弃当前记录
				//context.setDrop(true);return;
//				if(s.incrementAndGet() % 2 == 0) {
//					context.setDrop(true);
//					return;
//				}


				context.addFieldValue("author","duoduo");
				context.addFieldValue("title","解放");
				context.addFieldValue("subtitle","小康");
				context.addFieldValue("collecttime",new Date());//

//				context.addIgnoreFieldMapping("title");
				//上述三个属性已经放置到docInfo中，如果无需再放置到索引文档中，可以忽略掉这些属性
//				context.addIgnoreFieldMapping("author");

				//修改字段名称title为新名称newTitle，并且修改字段的值
				context.newName2ndData("title","newTitle",(String)context.getValue("title")+" append new Value");
				context.addIgnoreFieldMapping("subtitle");
				/**
				 * 获取ip对应的运营商和区域信息
				 */
				Map ipInfo = (Map)context.getValue("ipInfo");
				if(ipInfo != null)
					context.addFieldValue("ipinfo", SimpleStringUtil.object2json(ipInfo));
				else{
					context.addFieldValue("ipinfo", "");
				}
				context.addFieldValue("collecttime",new Date());
				/**
				 //关联查询数据,单值查询
				 Map headdata = SQLExecutor.queryObjectWithDBName(Map.class,context.getEsjdbc().getDbConfig().getDbName(),
				 "select * from head where billid = ? and othercondition= ?",
				 context.getIntegerValue("billid"),"otherconditionvalue");//多个条件用逗号分隔追加
				 //将headdata中的数据,调用addFieldValue方法将数据加入当前es文档，具体如何构建文档数据结构根据需求定
				 context.addFieldValue("headdata",headdata);
				 //关联查询数据,多值查询
				 List<Map> facedatas = SQLExecutor.queryListWithDBName(Map.class,context.getEsjdbc().getDbConfig().getDbName(),
				 "select * from facedata where billid = ?",
				 context.getIntegerValue("billid"));
				 //将facedatas中的数据,调用addFieldValue方法将数据加入当前es文档，具体如何构建文档数据结构根据需求定
				 context.addFieldValue("facedatas",facedatas);
				 */
			}
		});
		//映射和转换配置结束

		/**
		 * 一次、作业创建一个内置的线程池，实现多线程并行数据导入elasticsearch功能，作业完毕后关闭线程池
		 */
		importBuilder.setParallel(true);//设置为多线程并行批量导入,false串行
		importBuilder.setQueue(10);//设置批量导入线程池等待队列长度
		importBuilder.setThreadCount(50);//设置批量导入线程池工作线程数量
		importBuilder.setContinueOnError(true);//任务出现异常，是否继续执行作业：true（默认值）继续执行 false 中断作业执行
		importBuilder.setAsyn(false);//true 异步方式执行，不等待所有导入作业任务结束，方法快速返回；false（默认值） 同步方式执行，等待所有导入作业任务结束，所有作业结束后方法才返回
//		importBuilder.setDebugResponse(false);//设置是否将每次处理的reponse打印到日志文件中，默认false，不打印响应报文将大大提升性能，只有在调试需要的时候才打开，log日志级别同时要设置为INFO
//		importBuilder.setDiscardBulkResponse(true);//设置是否需要批量处理的响应报文，不需要设置为false，true为需要，默认true，如果不需要响应报文将大大提升处理速度

		importBuilder.setDebugResponse(false);//设置是否将每次处理的reponse打印到日志文件中，默认false
		importBuilder.setDiscardBulkResponse(true);//设置是否需要批量处理的响应报文，不需要设置为false，true为需要，默认false
		/**
		 importBuilder.setEsIdGenerator(new EsIdGenerator() {
		 //如果指定EsIdGenerator，则根据下面的方法生成文档id，
		 // 否则根据setEsIdField方法设置的字段值作为文档id，
		 // 如果默认没有配置EsIdField和如果指定EsIdGenerator，则由es自动生成文档id

		 @Override
		 public Object genId(Context context) throws Exception {
		 return SimpleStringUtil.getUUID();//返回null，则由es自动生成文档id
		 }
		 });
		 */
		/**
		 * 执行数据库表数据导入es操作
		 */
		DataStream dataStream = importBuilder.builder();
		dataStream.execute();//执行导入操作

		System.out.println();


	}

	public void scheduleScrollRefactorImportData(){
		ES2DBExportBuilder importBuilder = new ES2DBExportBuilder();
		importBuilder.setBatchSize(2).setFetchSize(10);


		//指定导入数据的sql语句，必填项，可以设置自己的提取逻辑，
		// 设置增量变量log_id，增量变量名称#[log_id]可以多次出现在sql语句的不同位置中，例如：
		// select * from td_sm_log where log_id > #[log_id] and parent_id = #[log_id]
		// log_id和数据库对应的字段一致,就不需要设置setLastValueColumn信息，
		// 但是需要设置setLastValueType告诉工具增量字段的类型
		importBuilder.setSqlName("insertSQLnew"); //指定将es文档数据同步到数据库的sql语句名称，配置在dsl2ndSqlFile.xml中
		/**
		 * es相关配置
		 */
		importBuilder
				.setDsl2ndSqlFile("dsl2ndSqlFile.xml")
				.setDslName("scrollQuery")
				.setScrollLiveTime("10m")
//				.setSliceQuery(true)
//				.setSliceSize(5)
				.setQueryUrl("dbdemo/_search")

//				//添加dsl中需要用到的参数及参数值
				.addParam("var1","v1")
				.addParam("var2","v2")
				.addParam("var3","v3");

		//定时任务配置，
		importBuilder.setFixedRate(false)//参考jdk timer task文档对fixedRate的说明
//					 .setScheduleDate(date) //指定任务开始执行时间：日期
				.setDeyLay(1000L) // 任务延迟执行deylay毫秒后执行
				.setPeriod(10000L); //每隔period毫秒执行，如果不设置，只执行一次
		//定时任务配置结束

		//设置任务执行拦截器，可以添加多个
		importBuilder.addCallInterceptor(new CallInterceptor() {
			@Override
			public void preCall(TaskContext taskContext) {
				System.out.println("preCall");
			}

			@Override
			public void afterCall(TaskContext taskContext) {
				System.out.println("afterCall");
			}

			@Override
			public void throwException(TaskContext taskContext, Exception e) {
				System.out.println("throwException");
			}
		}).addCallInterceptor(new CallInterceptor() {
			@Override
			public void preCall(TaskContext taskContext) {
				System.out.println("preCall 1");
			}

			@Override
			public void afterCall(TaskContext taskContext) {
				System.out.println("afterCall 1");
			}

			@Override
			public void throwException(TaskContext taskContext, Exception e) {
				System.out.println("throwException 1");
			}
		});
//		//设置任务执行拦截器结束，可以添加多个
		//增量配置开始
		importBuilder.setLastValueColumn("logId");//指定数字增量查询字段变量名称
		importBuilder.setFromFirst(true);//setFromfirst(false)，如果作业停了，作业重启后从上次截止位置开始采集数据，
		//setFromfirst(true) 如果作业停了，作业重启后，重新开始采集数据
		importBuilder.setLastValueStorePath("es2dbdemo_import");//记录上次采集的增量字段值的文件路径，作为下次增量（或者重启后）采集数据的起点，不同的任务这个路径要不一样
//		importBuilder.setLastValueStoreTableName("logs");//记录上次采集的增量字段值的表，可以不指定，采用默认表名increament_tab
		importBuilder.setLastValueType(ImportIncreamentConfig.NUMBER_TYPE);//如果没有指定增量查询字段名称，则需要指定字段类型：ImportIncreamentConfig.NUMBER_TYPE 数字类型
		// 或者ImportIncreamentConfig.TIMESTAMP_TYPE 日期类型
		//增量配置结束

		//映射和转换配置开始
//		/**
//		 * db-es mapping 表字段名称到es 文档字段的映射：比如document_id -> docId
//		 * 可以配置mapping，也可以不配置，默认基于java 驼峰规则进行db field-es field的映射和转换
//		 */
//		importBuilder.addFieldMapping("document_id","docId")
//				.addFieldMapping("docwtime","docwTime")
//				.addIgnoreFieldMapping("channel_id");//添加忽略字段
//
//
//		/**
//		 * 为每条记录添加额外的字段和值
//		 * 可以为基本数据类型，也可以是复杂的对象
//		 */
//		importBuilder.addFieldValue("testF1","f1value");
//		importBuilder.addFieldValue("testInt",0);
//		importBuilder.addFieldValue("testDate",new Date());
//		importBuilder.addFieldValue("testFormateDate","yyyy-MM-dd HH",new Date());
//		TestObject testObject = new TestObject();
//		testObject.setId("testid");
//		testObject.setName("jackson");
//		importBuilder.addFieldValue("testObject",testObject);
		importBuilder.addFieldValue("author","作者");

		/**
		 * 重新设置es数据结构
		 */
		importBuilder.setDataRefactor(new DataRefactor() {
			public void refactor(Context context) throws Exception  {
				//可以根据条件定义是否丢弃当前记录
				//context.setDrop(true);return;
//				if(s.incrementAndGet() % 2 == 0) {
//					context.setDrop(true);
//					return;
//				}


				context.addFieldValue("author","duoduo");
				context.addFieldValue("title","解放");
				context.addFieldValue("subtitle","小康");
				context.addFieldValue("collecttime",new Date());//

//				context.addIgnoreFieldMapping("title");
				//上述三个属性已经放置到docInfo中，如果无需再放置到索引文档中，可以忽略掉这些属性
//				context.addIgnoreFieldMapping("author");

				//修改字段名称title为新名称newTitle，并且修改字段的值
				context.newName2ndData("title","newTitle",(String)context.getValue("title")+" append new Value");
				context.addIgnoreFieldMapping("subtitle");
				/**
				 * 获取ip对应的运营商和区域信息
				 */
				Map ipInfo = (Map)context.getValue("ipInfo");
				if(ipInfo != null)
					context.addFieldValue("ipinfo", SimpleStringUtil.object2json(ipInfo));
				else{
					context.addFieldValue("ipinfo", "");
				}
				DateFormat dateFormat = SerialUtil.getDateFormateMeta().toDateFormat();
				Date optime = context.getDateValue("logOpertime",dateFormat);
				context.addFieldValue("logOpertime",optime);
				context.addFieldValue("collecttime",new Date());

				/**
				 //关联查询数据,单值查询
				 Map headdata = SQLExecutor.queryObjectWithDBName(Map.class,context.getEsjdbc().getDbConfig().getDbName(),
				 "select * from head where billid = ? and othercondition= ?",
				 context.getIntegerValue("billid"),"otherconditionvalue");//多个条件用逗号分隔追加
				 //将headdata中的数据,调用addFieldValue方法将数据加入当前es文档，具体如何构建文档数据结构根据需求定
				 context.addFieldValue("headdata",headdata);
				 //关联查询数据,多值查询
				 List<Map> facedatas = SQLExecutor.queryListWithDBName(Map.class,context.getEsjdbc().getDbConfig().getDbName(),
				 "select * from facedata where billid = ?",
				 context.getIntegerValue("billid"));
				 //将facedatas中的数据,调用addFieldValue方法将数据加入当前es文档，具体如何构建文档数据结构根据需求定
				 context.addFieldValue("facedatas",facedatas);
				 */
			}
		});
		//映射和转换配置结束

		/**
		 * 一次、作业创建一个内置的线程池，实现多线程并行数据导入elasticsearch功能，作业完毕后关闭线程池
		 */
		importBuilder.setParallel(true);//设置为多线程并行批量导入,false串行
		importBuilder.setQueue(10);//设置批量导入线程池等待队列长度
		importBuilder.setThreadCount(50);//设置批量导入线程池工作线程数量
		importBuilder.setContinueOnError(true);//任务出现异常，是否继续执行作业：true（默认值）继续执行 false 中断作业执行
		importBuilder.setAsyn(false);//true 异步方式执行，不等待所有导入作业任务结束，方法快速返回；false（默认值） 同步方式执行，等待所有导入作业任务结束，所有作业结束后方法才返回
//		importBuilder.setDebugResponse(false);//设置是否将每次处理的reponse打印到日志文件中，默认false，不打印响应报文将大大提升性能，只有在调试需要的时候才打开，log日志级别同时要设置为INFO
//		importBuilder.setDiscardBulkResponse(true);//设置是否需要批量处理的响应报文，不需要设置为false，true为需要，默认true，如果不需要响应报文将大大提升处理速度
		importBuilder.setPrintTaskLog(true);
		importBuilder.setDebugResponse(false);//设置是否将每次处理的reponse打印到日志文件中，默认false
		importBuilder.setDiscardBulkResponse(true);//设置是否需要批量处理的响应报文，不需要设置为false，true为需要，默认false
		/**
		 importBuilder.setEsIdGenerator(new EsIdGenerator() {
		 //如果指定EsIdGenerator，则根据下面的方法生成文档id，
		 // 否则根据setEsIdField方法设置的字段值作为文档id，
		 // 如果默认没有配置EsIdField和如果指定EsIdGenerator，则由es自动生成文档id

		 @Override
		 public Object genId(Context context) throws Exception {
		 return SimpleStringUtil.getUUID();//返回null，则由es自动生成文档id
		 }
		 });
		 */
		/**
		 * 执行数据库表数据导入es操作
		 */
		DataStream dataStream = importBuilder.builder();
		dataStream.execute();//执行导入操作

		System.out.println();


	}
}
