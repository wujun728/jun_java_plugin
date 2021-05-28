package org.typroject.tyboot.core.auth.face.service;

import org.springframework.stereotype.Service;
import org.typroject.tyboot.core.auth.face.model.ResourceOperationModel;
import org.typroject.tyboot.core.auth.face.orm.dao.ResourceOperationMapper;
import org.typroject.tyboot.core.auth.face.orm.entity.ResourceOperation;
import org.typroject.tyboot.core.rdbms.service.BaseService;

/**
 * <p>
 * 资源操作表 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-17
 */
@Service
public class ResourceOperationService extends BaseService<ResourceOperationModel,ResourceOperation,ResourceOperationMapper>  {
	
}
