package com.ibeetl.admin.core.util.beetl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.sql.core.engine.SQLParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.entity.CoreRoleFunction;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.rbac.DataAccess;
import com.ibeetl.admin.core.rbac.DataAccessFactory;
import com.ibeetl.admin.core.rbac.DataAccessResullt;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.util.FunctionLocal;

/**
 * 数据权限拼sql，配合DataAccessFactory
 * @author Wujun
 *
 */
@Component
public class DataAccessFunction implements Function {
	
	Log log = LogFactory.getLog(DataAccessFunction.class);
	
	@Autowired
	CorePlatformService platFormService;
	@Autowired
	DataAccessFactory dataAccessFactory;
	
	private static Map defaultTargets = new HashMap();
	static{
		//数据库默认的跟组织和用户相关字段
		defaultTargets.put("org", "org_id");
		defaultTargets.put("user", "user_id");
	}
	private static final String 	SQL_MY_DATA = "user_id=? ";
	private static final String 	SQL_MY_ORG_DATA = "org_id=? ";
	

	
	public Object call(Object[] paras, Context ctx){
		//项目初期，总是返回1==1，避免数据权限带来的麻烦 
		CoreUser user = platFormService.getCurrentUser(); 
		//{"org":"org_id","user","user_id"}
		Map targets  = this.defaultTargets;
		//用户调用conroller 结果"user.view"
		String functionCode  = FunctionLocal.get();	
		
		if(paras.length==1){
			Object o = paras[0];
			if(o instanceof String){
				functionCode = (String)o;
			}else if(o instanceof Map){
				targets  = (Map)paras[1];
			}
		}else if(paras.length==2){
			functionCode = (String)paras[0];
			targets  = (Map)paras[1];
		}
		
		
		
		if(platFormService.isSupperAdmin(user)){
			return " 1=1 /* admin */ ";
		}
		Long currentOrgId = platFormService.getCurrentOrgId();
		
		List<CoreRoleFunction> roleFuns = platFormService.getRoleFunction(user.getId(),currentOrgId,functionCode);
		if(roleFuns.isEmpty()){
			//如果没有配置数据权限，是1=1,因此为角色指定功能的时候，需要设定数据权限，否则查询到所有数据
			return "1=1 /* empty data access */ ";
		}
		
		
		
		List<Object> list = (List<Object>)ctx.getGlobal("_paras");
		StringBuilder sb = new StringBuilder("( ");
		//数据权限范围划定
		boolean hasAppend = false;
		for(int i=0;i<roleFuns.size();i++){
			CoreRoleFunction fun = roleFuns.get(i);
			Integer accessType = fun.getDataAccessType();
			if(accessType==null){
				continue;
			}
			if(hasAppend){
				sb.append(" or ");
			}
			hasAppend = true;
			DataAccess data = dataAccessFactory.getDataAccess(accessType);
			DataAccessResullt ret = data.getOrg(user.getId(), currentOrgId);
			
			switch(ret.getStatus()){
			case NoneOrg:{
				sb.append(targets.get("org")+" in (-1) ");
				break;
			}
			case AllOrg:{
				//sql 不包含组织机构过滤信息
				sb.append(" 1=1 /* AllOrg */ ");
				break;
			}
			case OnlyUser:{
				List<Long> ids = ret.getUserIds();
				sb.append(targets.get("user"));
				if(ids.size()==0){
					sb.append("=-1/*指定用户，但没有候选用户*/");
					continue;
				}
					
				if(ids.size()==1){
					sb.append(" =? ");
					list.add(new SQLParameter(ids.get(0)));
					continue;
				}
				sb.append(" in (");
				for(int z=0;z<ids.size();z++){
					sb.append(" ? ");
					list.add(new SQLParameter(ids.get(z)));
					if(z!=ids.size()-1){
						sb.append(",");
					}
				}
				sb.append(") ");
				break;
			
			}
			case OnlyOrg:{
				List<Long> ids = ret.getOrgIds();
				sb.append(targets.get("org"));
				if(ids.size()==0){
					sb.append("=-1/*指定机构，但没有候选机构*/");
					continue;
				}
					
				if(ids.size()==1){
					sb.append(" =? ");
					list.add(new SQLParameter(ids.get(0)));
					continue;
				}
				sb.append(" in (");
				for(int z=0;z<ids.size();z++){
					sb.append("?");
					list.add(new SQLParameter(ids.get(z)));
					if(z!=ids.size()-1){
						sb.append(",");
					}
				}
				sb.append(") ");
				break;
			}
			default:{
				log.warn("错误的"+ret.getStatus().toString());
				throw new UnsupportedOperationException(ret.getStatus().toString());
			}
			}

			
		}
		sb.append(" ) ");
		
		return sb.toString();
	}
	

	
	
}
