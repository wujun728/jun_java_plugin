package org.smartboot.service.util;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractService {
	@Autowired
	protected OperationTransactionTemplate operateTemplate;
}
