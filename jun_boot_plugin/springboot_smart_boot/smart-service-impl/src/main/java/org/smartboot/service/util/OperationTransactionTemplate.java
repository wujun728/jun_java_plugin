package org.smartboot.service.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.service.facade.enumeration.ResultCodeEnum;
import org.smartboot.service.facade.result.ServiceResult;
import org.smartboot.shared.SmartException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 服务层事务模板
 *
 * @author Wujun
 * @version OperationTransactionTemplate.java, v 0.1 2015年11月5日 上午11:21:11 Seer
 *          Exp.
 */
public class OperationTransactionTemplate {
	private TransactionTemplate transactionTemplate;

	private Logger logger = LogManager.getLogger(OperationTransactionTemplate.class);

	public void operateWithTransaction(ServiceResult<?> result, ServiceCallback callback) {
		operate(result, callback, true);
	}

	public void operateWithoutTransaction(ServiceResult<?> result, ServiceCallback callback) {
		operate(result, callback, false);
	}

	private void operate(ServiceResult<?> result, final ServiceCallback callback, boolean withTransaction) {
		try {
			callback.doCheck();

			if (withTransaction && transactionTemplate != null) {
				transactionTemplate.execute(new TransactionCallback<Object>() {
					public Object doInTransaction(TransactionStatus status) {
						callback.doOperate();
						return null;
					}

				});
			} else {
				callback.doOperate();
			}
			callback.doAfterOperate();
			result.setSuccess(true);
		} catch (SmartException e) {
			if (e.getLevel() == null) {
				logger.error("", e);
			} else if (logger.isEnabled(e.getLevel())) {
				logger.log(e.getLevel(), "", e);
			}
			ResultCodeEnum code = ResultCodeEnum.getEnumByCode(e.getCode());
			if (code != null) {
				result.setResultCode(code);
			}
			result.setMessage(e.getToast());
		} catch (NullPointerException e) {
			logger.error("", e);
			result.setMessage("空指针异常");
		} catch (Exception e) {
			logger.error("", e);
			result.setMessage("数据异常");
		}
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
}
