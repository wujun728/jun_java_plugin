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

import com.frameworkset.util.SimpleStringUtil;
import org.frameworkset.elasticsearch.serial.SerialUtil;
import org.frameworkset.spi.geoip.IpInfo;
import org.frameworkset.tran.DataRefactor;
import org.frameworkset.tran.ExportResultHandler;
import org.frameworkset.tran.context.Context;
import org.frameworkset.tran.es.input.db.ES2DBExportBuilder;
import org.frameworkset.tran.metrics.TaskMetrics;
import org.frameworkset.tran.schedule.ExternalScheduler;
import org.frameworkset.tran.schedule.ImportIncreamentConfig;
import org.frameworkset.tran.schedule.quartz.AbstractDB2ESQuartzJobHandler;
import org.frameworkset.tran.task.TaskCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;

/**
 * <p>Description: 使用quartz等外部环境定时运行导入数据，需要调试测试quatz作业同步功能，按如下配置进行操作：</p>
 * 1.在配置文件中添加quartz作业配置-resources/org/frameworkset/task/quarts-task.xml相关内容
 * <list>
 *
 * 			<property name="quartzES2DBImportTask" jobid="QuartzES2DBImportTask"
 * 							  bean-name="QuartzES2DBImportTask"
 * 							  method="execute"
 * 							  cronb_time="${quartzImportTask.crontime}" used="false"
 * 							  shouldRecover="false"
 * 					/>
 *
 * </list>
 *
 * 	<property name="QuartzES2DBImportTask" class="org.frameworkset.elasticsearch.imp.QuartzES2DBImportTask"
 * 			  destroy-method="destroy"
 * 			  init-method="init"
 * 	/>
 *
 * 2.添加一个带main方法的作业运行
 * public class QuartzTest {
 * 	public static void main(String[] args){
 * 		TaskService.getTaskService().startService();
 *        }
 * }
 * 然后运行main方法即可
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/4/13 13:45
 * @author biaoping.yin
 * @version 1.0
 */
public class QuartzES2DBImportTask extends AbstractDB2ESQuartzJobHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	public void init(){
		externalScheduler = new ExternalScheduler();
		externalScheduler.dataStream((Object params)->{
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



			importBuilder.setExportResultHandler(new ExportResultHandler() {
				@Override
				public void success(TaskCommand taskCommand, Object result) {
					System.out.println("success");
					TaskMetrics taskMetrics = taskCommand.getTaskMetrics();
					logger.info(taskMetrics.toString());
				}

				@Override
				public void error(TaskCommand taskCommand, Object result) {
					System.out.println("error");
					TaskMetrics taskMetrics = taskCommand.getTaskMetrics();
					logger.info(taskMetrics.toString());
				}

				@Override
				public void exception(TaskCommand taskCommand, Exception exception) {
					System.out.println("exception");
					TaskMetrics taskMetrics = taskCommand.getTaskMetrics();
					logger.info(taskMetrics.toString());
				}

				@Override
				public int getMaxRetry() {
					return -1;
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
//		final AtomicInteger s = new AtomicInteger(0);
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

//				//修改字段名称title为新名称newTitle，并且修改字段的值
//				context.newName2ndData("title","newTitle",(String)context.getValue("title")+" append new Value");
					context.addIgnoreFieldMapping("subtitle");
					/**
					 * 获取ip对应的运营商和区域信息
					 */
					IpInfo ipInfo = context.getIpInfoByIp("113.12.192.230");
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
			return importBuilder;
		});

	}


}
