package org.typroject.tyboot.api.face.privilage.service;

import org.springframework.stereotype.Service;
import org.typroject.tyboot.api.face.privilage.model.AgencyMenuModel;
import org.typroject.tyboot.api.face.privilage.orm.dao.AgencyMenuMapper;
import org.typroject.tyboot.api.face.privilage.orm.entity.AgencyMenu;
import org.typroject.tyboot.core.rdbms.service.BaseService;


/**
 * <p>
 * 角色与菜单关系表 服务实现类
 * </p>
 *
 * @author Wujun
 * @since 2017-08-18
 */
@Service
public class AgencyMenuService extends BaseService<AgencyMenuModel,AgencyMenu,AgencyMenuMapper> {
	
}
