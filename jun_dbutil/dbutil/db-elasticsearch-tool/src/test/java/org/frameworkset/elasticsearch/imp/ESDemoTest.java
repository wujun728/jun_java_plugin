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
 * <p>Description: 从es中查询数据导入数据库案例</p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/1/11 14:39
 * @author biaoping.yin
 * @version 1.0
 */
public class ESDemoTest {
	public static void main(String[] args){


		ES2DBDemo esDemo = new ES2DBDemo();
		//		esDemo.directExport();
//		esDemo.exportData();
//		esDemo.exportSliceData();
//		esDemo.exportSliceDataWithInnerhit();
		esDemo.exportParallelData();
		//结束所以后台程序，退出，测试时打开
		//BaseApplicationContext.shutdown();
	}

}
