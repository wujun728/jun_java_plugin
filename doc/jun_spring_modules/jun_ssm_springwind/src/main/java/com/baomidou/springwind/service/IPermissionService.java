package com.baomidou.springwind.service;

import java.util.List;

import com.baomidou.kisso.Token;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.springwind.entity.Permission;
import com.baomidou.springwind.entity.vo.MenuVO;

/**
 *
 * Permission 表数据服务层接口
 *
 */
public interface IPermissionService extends IService<Permission> {

	List<MenuVO> selectMenuVOByUserId( Long userId );


	List<Permission> selectAllByUserId( Long userId );


	/**
	 * <p>
	 * 是否为可操作的权限
	 * </p>
	 * @param token
	 * 				SSO 票据顶级父类 
	 * @param permission
	 * 				操作权限编码
	 * @return
	 */
	boolean isActionable( Token token, String permission );
}