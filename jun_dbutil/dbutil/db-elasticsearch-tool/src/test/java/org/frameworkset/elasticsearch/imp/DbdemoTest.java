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

/**
 * <p>Description: 测试代码</p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2018/9/27 20:38
 * @author biaoping.yin
 * @version 1.0
 */
public class DbdemoTest {
	public static void main(String args[]){

		long t = System.currentTimeMillis();
		Dbdemo dbdemo = new Dbdemo();
		boolean dropIndice = true;//CommonLauncher.getBooleanAttribute("dropIndice",false);//同时指定了默认值
//		dbdemo.scheduleImportData(  dropIndice);//定时增量导入
//		dbdemo.externalScheduleImportData(dropIndice);//外部定时器定时增量导入
//		dbdemo.externalFullScheduleImportData(dropIndice);//外部定时器定时增量导入
//		dbdemo.scheduleFullImportData(dropIndice);//定时全量导入
		dbdemo.fullImportData(dropIndice);//一次性全量导入

//		dbdemo.scheduleRefactorImportData(dropIndice);//定时全量导入，在context中排除remark1字段
//		dbdemo.fullAutoUUIDImportData(dropIndice);
//		dbdemo.scheduleFullAutoUUIDImportData(dropIndice);//定时全量导入，自动生成UUID
//		dbdemo.scheduleDatePatternImportData(dropIndice);//定时增量导入，按日期分表yyyy.MM.dd



	}


}
