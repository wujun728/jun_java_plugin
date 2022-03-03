package org.springrain.system.service;

import org.springrain.frame.entity.AuditLog;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-04-02 10:17:31
 * @see org.springrain.frame.entity.springrain.service.AuditLog
 */
public interface IAuditlogService extends IBaseSpringrainService {
	String saveAuditlog(AuditLog entity) throws Exception;
    String saveorupdateAuditlog(AuditLog entity) throws Exception;
	Integer updateAuditlog(AuditLog entity) throws Exception;
	AuditLog findAuditlogById(Object id) throws Exception;
	
}
