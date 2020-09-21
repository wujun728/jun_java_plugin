/*   
 * Project: OSMP
 * FileName: LogTask.java
 * version: V1.0
 */
package com.osmp.log.task;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;

import com.osmp.intf.define.config.FrameConst;
import com.osmp.jdbc.support.JdbcFinderManager;
import com.osmp.jdbc.support.JdbcTemplate;
import com.osmp.log.impl.ReferenceServiceManager;
import com.osmp.log.util.ThrowExceptionUtil;

public class LogTask implements Runnable {
	private LogEntry paramLogEntry;

	public LogTask(LogEntry paramLogEntry) {
		this.paramLogEntry = paramLogEntry;
	}

	public void run() {
		if (paramLogEntry == null)
			return;
		JdbcFinderManager jdbcManager = ReferenceServiceManager.getJdbcFinederManager();
		if (jdbcManager == null)
			return;
		JdbcTemplate template = jdbcManager.findJdbcTemplate("osmp");
		if (template == null)
			return;
		String loadIp = FrameConst.getLoadIp();
		// 1:error 3:info
		int level = paramLogEntry.getLevel();
		String bundlename = paramLogEntry.getBundle() == null ? "" : paramLogEntry.getBundle().getSymbolicName();
		if (bundlename.startsWith("osmp")) {
			String version = paramLogEntry.getBundle() == null ? "" : paramLogEntry.getBundle().getVersion().toString();
			String servicename = "";
			ServiceReference srf = paramLogEntry.getServiceReference();
			if (srf != null) {
				Bundle bld = paramLogEntry.getBundle();
				if (bld != null) {
					Object srv = bld.getBundleContext().getService(srf);
					servicename = srv == null ? "" : srv.getClass().getName();
				}
			}
			if (level == 1) {
				String sql = jdbcManager.getQueryStringByStatementKey("osmp.log.insertErrorLog");
				if (sql == null)
					return;
				String exception = paramLogEntry.getException() == null ? "" : ThrowExceptionUtil
						.getStackTrace(paramLogEntry.getException());
				template.update(sql, bundlename, version, servicename, paramLogEntry.getMessage(), exception,
						paramLogEntry.getTime(), loadIp);
			} else if (level == 3 || level == 2) {
				String sql = jdbcManager.getQueryStringByStatementKey("osmp.log.insertInfoLog");
				if (sql == null)
					return;
				template.update(sql, bundlename, version, servicename, paramLogEntry.getMessage(),
						paramLogEntry.getTime(), loadIp);
			}
		}

	}
}
