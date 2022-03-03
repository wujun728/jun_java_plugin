package org.springrain.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springrain.frame.util.Enumerations.UserOrgType;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.Page;
import org.springrain.system.entity.Org;
import org.springrain.system.entity.User;
import org.springrain.system.entity.UserOrg;
import org.springrain.system.entity.UserRole;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IUserOrgService;

/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2013-07-06 15:28:18
 * @see org.springrain.springrain.service.impl.TuserOrg
 */
@Service("userOrgService")
public class UserOrgServiceImpl extends BaseSpringrainServiceImpl implements IUserOrgService {

	@Override
	public List<User> findUserByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append("  u,").append(Finder.getTableName(UserOrg.class)).append(" re where re.userId=u.id and re.orgId=:orgId order by u.id asc ");
		finder.setParam("orgId", orgId);
		return super.queryForList(finder,User.class);
	}
	
	
	@Override
	public List<String> findUserIdsByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder finder=new Finder("SELECT re.userId FROM ").append(Finder.getTableName(UserOrg.class)).append(" re where  re.orgId=:orgId order by re.userId asc ");
		finder.setParam("orgId", orgId);
		return super.queryForList(finder,String.class);
	}
	
	

	@Override
	public List<User> findAllUserByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder f_code=Finder.getSelectFinder(Org.class, "comcode").append(" where id=:orgId ");
		f_code.setParam("orgId", orgId);
		String comcode=super.queryForObject(f_code, String.class);
		
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append("  u,").append(Finder.getTableName(UserOrg.class)).append(" re,").append(Finder.getTableName(Org.class)).append(" org WHERE org.id=re.orgId and u.id=re.userId and org.comcode like :comcode  order by u.id asc ");
		finder.setParam("comcode", comcode+"%");
		return super.queryForList(finder,User.class);
	}
	
	@Override
	public List<String> findAllUserIdsByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder f_code=Finder.getSelectFinder(Org.class, "comcode").append(" where id=:orgId ");
		f_code.setParam("orgId", orgId);
		String comcode=super.queryForObject(f_code, String.class);
		
		Finder finder=new Finder("SELECT re.userId FROM ").append(Finder.getTableName(UserOrg.class)).append(" re,").append(Finder.getTableName(Org.class)).append(" org WHERE org.id=re.orgId and org.comcode like :comcode  order by re.userId asc ");
		finder.setParam("comcode", comcode+"%");
		return super.queryForList(finder,String.class);
	}

	@Override
	public List<Org> findOrgByUserId(String userId) throws Exception {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		Finder finder=new Finder("SELECT org.* FROM  ").append(Finder.getTableName(UserOrg.class)).append(" re ,").append(Finder.getTableName(Org.class)).append(" org  WHERE re.userId=:userId and org.id=re.orgId  order by org.id asc   ");
		finder.setParam("userId", userId);
		return super.queryForList(finder, Org.class);
	}
	@Override
	public List<UserOrg> findManagerOrgByUserId(String userId) throws Exception {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		Finder finder=new Finder("SELECT re.*,org.name  orgName FROM  ").append(Finder.getTableName(UserOrg.class)).append(" re ,").append(Finder.getTableName(Org.class)).append(" org  WHERE re.userId=:userId and org.id=re.orgId  order by org.id asc   ");
		finder.setParam("userId", userId);
		return super.queryForList(finder, UserOrg.class);
	}
	
	@Override
	public List<String> findOrgIdsByUserId(String userId) throws Exception {
		if(StringUtils.isBlank(userId)){
			return null;
		}
		Finder finder=new Finder("SELECT re.orgId FROM  ").append(Finder.getTableName(UserOrg.class)).append(" re ").append("   WHERE re.userId=:userId    order by re.orgId asc   ");
		finder.setParam("userId", userId);
		return super.queryForList(finder, String.class);
	}
	
	
	

	@Override
	public List<User> findManagerUserByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder finder=new Finder("SELECT u.* FROM ").append(Finder.getTableName(User.class)).append(" u,").append(Finder.getTableName(Org.class)).append(" org,").append(Finder.getTableName(UserRole.class)).append(" re  WHERE re.roleId=org.managerRoleId and org.id=:orgId and u.id=re.userId order by re.userId asc   ");
		finder.setParam("orgId", orgId);
		return super.queryForList(finder, User.class);
	}
	
	
	

	@Override
	public List<String> findManagerUserIdsByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		Finder finder=new Finder("SELECT re.userId FROM ").append(Finder.getTableName(Org.class)).append(" org,").append(Finder.getTableName(UserRole.class)).append(" re  WHERE re.roleId=org.managerRoleId and org.id=:orgId order by re.userId asc   ");
		finder.setParam("orgId", orgId);
		return super.queryForList(finder, String.class);
	}
	
	

	@Override
	public Integer findAllUserCountByOrgId(String orgId) throws Exception {
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		//Finder f_code=new Finder("SELECT comcode FROM t_org where id=:orgId ");
		Finder f_code=Finder.getSelectFinder(Org.class, "comcode").append(" where id=:orgId ");
		f_code.setParam("orgId", orgId);
		String comcode=super.queryForObject(f_code, String.class);
		
		Finder finder=new Finder("SELECT count(re.userId) FROM ").append(Finder.getTableName(UserOrg.class)).append(" re,").append(Finder.getTableName(Org.class)).append(" org WHERE org.id=re.orgId and org.comcode like :comcode");
		finder.setParam("comcode", comcode+"%");
		return super.queryForObject(finder,Integer.class);
	}


	@Override
	public List<String> findOrgIdsByManagerUserId(String managerUserId,Page page) throws Exception {
		if(StringUtils.isEmpty(managerUserId)){
			return null;
		}
		
		Finder finder = findOrgIdsSQLByManagerUserId(managerUserId);
		if(finder==null){
			return null;
		}
		if(StringUtils.isEmpty(finder.getSql())){
			return null;
		}
//		Finder finder=new Finder(findOrgIdsSQLByManagerUserId);
//		finder.setEscapeSql(false);
		return super.queryForList(finder, String.class,page);
	}


	@Override
	public List<Org> findOrgByManagerUserId(String managerUserId,Page page) throws Exception {
		if(StringUtils.isEmpty(managerUserId)){
			return null;
		}
		
		
//		Finder f = findOrgIdsSQLByManagerUserId(managerUserId);
//		if(StringUtils.isEmpty(f.getSql())){
//			return null;
//		}
		
		
       Finder  f=wrapWheresSQLByManagerUserId(managerUserId);
		if(f==null){
			return null;
		}
		
		if(StringUtils.isEmpty(f.getSql())){
			return null;
		}
		
		StringBuilder hasLeafBuffer=new StringBuilder();
		Finder finder=new Finder(hasLeafBuffer.toString());
		finder.append(" SELECT frame_system_temp_org.*  FROM ").append(Finder.getTableName(Org.class));
		finder.append(" frame_system_temp_org WHERE 1=1 ");
		finder.appendFinder(f);
		finder.append(" order by frame_system_temp_org.id  asc ");
		
		
		return super.queryForList(finder,Org.class,page);
		
	}


	@Override
	public List<String> findUserIdsByManagerUserId(String managerUserId,Page page) throws Exception {
		
		Finder f = findUserIdsSQLByManagerUserId(managerUserId);
		
		if(f==null){
			return null;
		}
		
		if(StringUtils.isEmpty(f.getSql())){
			return null;
		}
		
//		userIdsSQLByManagerUserId=userIdsSQLByManagerUserId+" order by  frame_system_temp_user_org.userId asc ";
		
		Finder finder=new Finder();
		finder.appendFinder(f);
		finder.append(" order by  frame_system_temp_user_org.userId asc ");
		
		return super.queryForList(finder, String.class,page);
	}


	@Override
	public List<User> findUserByManagerUserId(String managerUserId,Page page) throws Exception {
		Finder f=wrapWheresSQLByManagerUserId(managerUserId);
		if(f==null){
			return null;
		}
		if(StringUtils.isBlank(f.getSql())){
			return null;
		}
		Finder finder=new Finder("SELECT u.* FROM ");
		finder.append(Finder.getTableName(User.class)).append(" u,").append(Finder.getTableName(UserOrg.class)).append(" re,").append(Finder.getTableName(Org.class)).append(" frame_system_temp_org ");
		finder.append(" WHERE u.id=re.userId and re.orgId=frame_system_temp_org.id ");
//		finder.append(wheresql);
		finder.appendFinder(f);
		finder.append(" order by  u.id asc ");
		return super.queryForList(finder, User.class,page);
	}


	@Override
	public List<String> findOrgIdsByManagerUserId(String managerUserId) throws Exception {
		return findOrgIdsByManagerUserId(managerUserId, null);
	}


	@Override
	public List<Org> findOrgByManagerUserId(String managerUserId) throws Exception {
		return findOrgByManagerUserId(managerUserId, null);
	}


	@Override
	public List<String> findUserIdsByManagerUserId(String managerUserId) throws Exception {
		return findUserIdsByManagerUserId(managerUserId, null);
	}


	@Override
	public List<User> findUserByManagerUserId(String managerUserId) throws Exception {
		return findUserByManagerUserId(managerUserId, null);
	}
	
	
	

	@Override
	public Finder findOrgIdsSQLByManagerUserId(String managerUserId) throws Exception {
		Finder f=wrapWheresSQLByManagerUserId(managerUserId);
		
		if(f==null){
			return new Finder();
		}
		
		if(StringUtils.isEmpty(f.getSql())){
			return new Finder();
		}
		Finder hasLeafBuffer=new Finder();
		hasLeafBuffer.append(" SELECT frame_system_temp_org.id  FROM ").append(Finder.getTableName(Org.class));
		hasLeafBuffer.append(" frame_system_temp_org WHERE 1=1 ").appendFinder(f); 
//		hasLeafBuffer.append(" order by frame_system_temp_org.id  asc "); 
		
		 return hasLeafBuffer;
		
		
	}
	
	@Override
	public Finder findUserIdsSQLByManagerUserId(String managerUserId) throws Exception {
		Finder f=wrapWheresSQLByManagerUserId(managerUserId);
		if(f==null){
			return new Finder();
		}
		if(StringUtils.isBlank(f.getSql())){
			return new Finder();
		}
		
		
		Finder sb=new Finder("SELECT  frame_system_temp_user_org.userId FROM ");
		sb.append(Finder.getTableName(UserOrg.class)).append(" frame_system_temp_user_org,").append(Finder.getTableName(Org.class)).append(" frame_system_temp_org ");
		sb.append(" WHERE frame_system_temp_user_org.orgId=frame_system_temp_org.id  ");
		sb.appendFinder(f);
		
		 return sb;
	}
	
	
	
	
	

	

	private Finder wrapWheresSQLByManagerUserId(String managerUserId) throws Exception {
		if(StringUtils.isEmpty(managerUserId)){
			return null;
		}
		//只有主管和代主管有部门管理权限 普通员工没有管理权限
		Finder f1=new Finder("SELECT * FROM  ").append(Finder.getTableName(UserOrg.class)).append(" WHERE  userId=:userId  and managerType in ("+UserOrgType.主管.getType()+","+UserOrgType.虚拟主管.getType()+","+UserOrgType.代理主管.getType()+") ");
		f1.setParam("userId", managerUserId);
		
		List<UserOrg> list = super.queryForList(f1, UserOrg.class); 
		
		if(CollectionUtils.isEmpty(list)){
			return null;
		}
		
		List<String> noLeafList=new ArrayList<>();
		
		Finder hasLeafBuffer=new Finder();
		
		hasLeafBuffer.append("  and ( 1=2 ");
		
	    for(UserOrg re:list){
			String orgId=re.getOrgId();
			Integer hasLeaf = re.getHasleaf();
			if(hasLeaf==0){//不包含子部门
				noLeafList.add(orgId);
			}else if(hasLeaf==1){//包含子部门
				String indexsign="frame_system_temp_comcode_"+String.valueOf(list.indexOf(re));
				hasLeafBuffer.append(" or frame_system_temp_org.comcode like :").append(indexsign).append(" ");
				hasLeafBuffer.setParam(indexsign, "%,"+orgId+",%"); 
			}
		}
		
		if(!CollectionUtils.isEmpty(noLeafList)){
			if(!CollectionUtils.isEmpty(list)){
				//前面有sql加连接符
				hasLeafBuffer.append(" or ");
			}
			
			hasLeafBuffer.append(" frame_system_temp_org.id in (:frame_system_temp_orglist) ");
			hasLeafBuffer.setParam("frame_system_temp_orglist", noLeafList); 
		}
		
		hasLeafBuffer.append(") "); 
		
		return hasLeafBuffer;
	}

		
}
