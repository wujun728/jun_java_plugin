/**
 * Copyright (c) 2011-2015, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wujun728.db.record;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
//import com.jfinal.log.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SqlReporter.
 */
public class SqlReporter implements InvocationHandler {
	
	private Connection conn;
	private static boolean loggerOn = false;
	private static final Logger log = LoggerFactory.getLogger(SqlReporter.class);
	
	SqlReporter(Connection conn) {
		this.conn = conn;
	}
	
	public static void setLogger(boolean on) {
		SqlReporter.loggerOn = on;
	}
	
	@SuppressWarnings("rawtypes")
	Connection getConnection() {
		Class clazz = conn.getClass();
		return (Connection)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{Connection.class}, this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			if (method.getName().equals("prepareStatement")) {
				String info = "Sql: " + args[0];
				if (loggerOn)
					log.info(info);
				else
					System.out.println(info);
			}
			return method.invoke(conn, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}




