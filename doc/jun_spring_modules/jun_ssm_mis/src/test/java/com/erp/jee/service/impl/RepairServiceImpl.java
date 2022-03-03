package com.erp.jee.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.IBaseDao;
import com.erp.jee.entity.DictParamEntity;
import com.erp.jee.model.Tauth;
import com.erp.jee.model.Tbug;
import com.erp.jee.model.Tmenu;
import com.erp.jee.model.Tonline;
import com.erp.jee.model.Trole;
import com.erp.jee.model.Troletauth;
import com.erp.jee.model.Tuser;
import com.erp.jee.model.Tusertrole;
import com.erp.jee.service.RepairServiceI;
import com.erp.service.impl.BaseServiceImpl;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.Encrypt2;

/**
 * 修复数据库Service
 * 
 * @author Wujun
 * 
 */
@Service("repairService")
public class RepairServiceImpl extends BaseServiceImpl implements RepairServiceI {

	private IBaseDao<Tbug> bugDao;
	private IBaseDao<Tuser> userDao;
	private IBaseDao<Tmenu> menuDao;
	private IBaseDao<Tonline> onlineDao;
	private IBaseDao<Tauth> authDao;
	private IBaseDao<Trole> roleDao;
	private IBaseDao<Tusertrole> userroleDao;
	private IBaseDao<Troletauth> roleauthDao;
	@Autowired
	private IBaseDao<DictParamEntity> dictParamEntityDao;

	public IBaseDao<Tbug> getBugDao() {
		return bugDao;
	}

	@Autowired
	public void setBugDao(IBaseDao<Tbug> bugDao) {
		this.bugDao = bugDao;
	}

	public IBaseDao<Troletauth> getRoleauthDao() {
		return roleauthDao;
	}

	@Autowired
	public void setRoleauthDao(IBaseDao<Troletauth> roleauthDao) {
		this.roleauthDao = roleauthDao;
	}

	public IBaseDao<Tusertrole> getUserroleDao() {
		return userroleDao;
	}

	@Autowired
	public void setUserroleDao(IBaseDao<Tusertrole> userroleDao) {
		this.userroleDao = userroleDao;
	}

	public IBaseDao<Trole> getRoleDao() {
		return roleDao;
	}

	@Autowired
	public void setRoleDao(IBaseDao<Trole> roleDao) {
		this.roleDao = roleDao;
	}

	public IBaseDao<Tauth> getAuthDao() {
		return authDao;
	}

	@Autowired
	public void setAuthDao(IBaseDao<Tauth> authDao) {
		this.authDao = authDao;
	}

	public IBaseDao<Tonline> getOnlineDao() {
		return onlineDao;
	}

	@Autowired
	public void setOnlineDao(IBaseDao<Tonline> onlineDao) {
		this.onlineDao = onlineDao;
	}

	public IBaseDao<Tmenu> getMenuDao() {
		return menuDao;
	}

	@Autowired
	public void setMenuDao(IBaseDao<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}

/*	public IBaseDao<Tuser> getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(IBaseDao<Tuser> userDao) {
		this.userDao = userDao;
	}*/

	synchronized public void deleteAndRepair() {
		bugDao.executeHql("delete Tbug");
		onlineDao.executeHql("delete Tonline");
		menuDao.executeHql("update Tmenu t set t.tmenu = null");
		menuDao.executeHql("delete Tmenu");
		roleauthDao.executeHql("delete Troletauth");
		userroleDao.executeHql("delete Tusertrole");
		authDao.executeHql("update Tauth t set t.tauth = null");
		authDao.executeHql("delete Tauth");
		roleDao.executeHql("delete Trole");
		userDao.executeHql("delete Tuser");
		repair();
	}

	synchronized public void repair() {
		repairMenu();// 修复菜单权限【权限控制到菜单级别】
		//repairAuth();// 修复权限
		repairRole();// 修复角色
		repairUser();// 修复用户
		repairRoleAuth();// 修复角色和权限的关系
		repairUserRole();// 修复用户和角色的关系
		repairDict();// 修复字典
	}

	private void repairUserRole() {
		userroleDao.executeHql("delete Tusertrole t where t.tuser.cid in ( '0' )");

		Tusertrole userrole = new Tusertrole();
		userrole.setCid(UUID.randomUUID().toString());
		userrole.setTrole(roleDao.get(Trole.class, "0"));
		userrole.setTuser(userDao.get(Tuser.class, "0"));
		userroleDao.save(userrole);
	}

	private void repairRoleAuth() {
		roleauthDao.executeHql("delete Troletauth t where t.trole.cid = '0'");

		Trole role = roleDao.get(Trole.class, "0");

		List<Tauth> auths = authDao.find("from Tauth");
		if (auths != null && auths.size() > 0) {
			for (Tauth auth : auths) {
				Troletauth roleauth = new Troletauth();
				roleauth.setCid(UUID.randomUUID().toString());
				roleauth.setTrole(role);
				roleauth.setTauth(auth);
				roleauthDao.save(roleauth);
			}
		}
	}

	private void repairRole() {
		Trole admin = new Trole();
		admin.setCid("0");
		admin.setCname("超级管理员");
		admin.setCdesc("拥有系统所有权限");
		roleDao.saveOrUpdate(admin);

		Trole guest = new Trole();
		guest.setCid("1");
		guest.setCname("Guest");
		guest.setCdesc("");
		roleDao.saveOrUpdate(guest);
	}

	private void repairAuth() {
		authDao.executeHql("update Tauth a set a.tauth = null");

		Tauth easyssh = new Tauth();
		easyssh.setCid("0");
		easyssh.setTauth(null);
		easyssh.setCname("首页");
		easyssh.setCurl("");
		easyssh.setCseq(BigDecimal.valueOf(1));
		easyssh.setCdesc("EasySSH示例项目");
		authDao.saveOrUpdate(easyssh);

		Tauth sjkgl = new Tauth();
		sjkgl.setCid("sjkgl");
		sjkgl.setTauth(easyssh);
		sjkgl.setCname("数据库管理");
		sjkgl.setCurl("");
		sjkgl.setCseq(BigDecimal.valueOf(1));
		sjkgl.setCdesc("可查看数据库链接信息，SQL语句执行情况");
		authDao.saveOrUpdate(sjkgl);

		Tauth ljcjk = new Tauth();
		ljcjk.setCid("ljcjk");
		ljcjk.setTauth(sjkgl);
		ljcjk.setCname("连接池监控");
		ljcjk.setCurl("/datasourceAction!druid.action");
		ljcjk.setCseq(BigDecimal.valueOf(1));
		ljcjk.setCdesc("可查看数据库链接信息");
		authDao.saveOrUpdate(ljcjk);

		Tauth xtgl = new Tauth();
		xtgl.setCid("xtgl");
		xtgl.setTauth(easyssh);
		xtgl.setCname("系统管理");
		xtgl.setCurl("");
		xtgl.setCseq(BigDecimal.valueOf(2));
		xtgl.setCdesc("");
		authDao.saveOrUpdate(xtgl);

		Tauth yhgl = new Tauth();
		yhgl.setCid("yhgl");
		yhgl.setTauth(xtgl);
		yhgl.setCname("用户管理");
		yhgl.setCurl("");
		yhgl.setCseq(BigDecimal.valueOf(1));
		yhgl.setCdesc("");
		authDao.saveOrUpdate(yhgl);

		Tauth yhglym = new Tauth();
		yhglym.setCid("yhglym");
		yhglym.setTauth(yhgl);
		yhglym.setCname("用户管理页面");
		yhglym.setCurl("/userAction!user.action");
		yhglym.setCseq(BigDecimal.valueOf(1));
		yhglym.setCdesc("");
		authDao.saveOrUpdate(yhglym);

		Tauth yhcx = new Tauth();
		yhcx.setCid("yhcx");
		yhcx.setTauth(yhgl);
		yhcx.setCname("用户查询");
		yhcx.setCurl("/userAction!datagrid.action");
		yhcx.setCseq(BigDecimal.valueOf(2));
		yhcx.setCdesc("");
		authDao.saveOrUpdate(yhcx);

		Tauth yhadd = new Tauth();
		yhadd.setCid("yhadd");
		yhadd.setTauth(yhgl);
		yhadd.setCname("用户添加");
		yhadd.setCurl("/userAction!add.action");
		yhadd.setCseq(BigDecimal.valueOf(3));
		yhadd.setCdesc("");
		authDao.saveOrUpdate(yhadd);

		Tauth yhedit = new Tauth();
		yhedit.setCid("yhedit");
		yhedit.setTauth(yhgl);
		yhedit.setCname("用户修改");
		yhedit.setCurl("/userAction!edit.action");
		yhedit.setCseq(BigDecimal.valueOf(4));
		yhedit.setCdesc("");
		authDao.saveOrUpdate(yhedit);

		Tauth yhsc = new Tauth();
		yhsc.setCid("yhsc");
		yhsc.setTauth(yhgl);
		yhsc.setCname("用户删除");
		yhsc.setCurl("/userAction!delete.action");
		yhsc.setCseq(BigDecimal.valueOf(5));
		yhsc.setCdesc("");
		authDao.saveOrUpdate(yhsc);

		Tauth yhxgmm = new Tauth();
		yhxgmm.setCid("yhxgmm");
		yhxgmm.setTauth(yhgl);
		yhxgmm.setCname("修改密码");
		yhxgmm.setCurl("/userAction!modifyPwd.action");
		yhxgmm.setCseq(BigDecimal.valueOf(6));
		yhxgmm.setCdesc("批量修改用户密码");
		authDao.saveOrUpdate(yhxgmm);

		Tauth yhxgjs = new Tauth();
		yhxgjs.setCid("yhxgjs");
		yhxgjs.setTauth(yhgl);
		yhxgjs.setCname("修改角色");
		yhxgjs.setCurl("/userAction!modifyUserRole.action");
		yhxgjs.setCseq(BigDecimal.valueOf(7));
		yhxgjs.setCdesc("批量修改用户角色");
		authDao.saveOrUpdate(yhxgjs);

		Tauth jsgl = new Tauth();
		jsgl.setCid("jsgl");
		jsgl.setTauth(xtgl);
		jsgl.setCname("角色管理");
		jsgl.setCurl("");
		jsgl.setCseq(BigDecimal.valueOf(2));
		jsgl.setCdesc("");
		authDao.saveOrUpdate(jsgl);

		Tauth jsglym = new Tauth();
		jsglym.setCid("jsglym");
		jsglym.setTauth(jsgl);
		jsglym.setCname("角色管理页面");
		jsglym.setCurl("/roleAction!role.action");
		jsglym.setCseq(BigDecimal.valueOf(1));
		jsglym.setCdesc("");
		authDao.saveOrUpdate(jsglym);

		Tauth jscx = new Tauth();
		jscx.setCid("jscx");
		jscx.setTauth(jsgl);
		jscx.setCname("角色查询");
		jscx.setCurl("/roleAction!datagrid.action");
		jscx.setCseq(BigDecimal.valueOf(2));
		jscx.setCdesc("");
		authDao.saveOrUpdate(jscx);

		Tauth jsadd = new Tauth();
		jsadd.setCid("jsadd");
		jsadd.setTauth(jsgl);
		jsadd.setCname("角色添加");
		jsadd.setCurl("/roleAction!add.action");
		jsadd.setCseq(BigDecimal.valueOf(3));
		jsadd.setCdesc("");
		authDao.saveOrUpdate(jsadd);

		Tauth jsedit = new Tauth();
		jsedit.setCid("jsedit");
		jsedit.setTauth(jsgl);
		jsedit.setCname("角色编辑");
		jsedit.setCurl("/roleAction!edit.action");
		jsedit.setCseq(BigDecimal.valueOf(4));
		jsedit.setCdesc("");
		authDao.saveOrUpdate(jsedit);

		Tauth jsdelete = new Tauth();
		jsdelete.setCid("jsdelete");
		jsdelete.setTauth(jsgl);
		jsdelete.setCname("角色删除");
		jsdelete.setCurl("/roleAction!delete.action");
		jsdelete.setCseq(BigDecimal.valueOf(5));
		jsdelete.setCdesc("");
		authDao.saveOrUpdate(jsdelete);

		Tauth qxgl = new Tauth();
		qxgl.setCid("qxgl");
		qxgl.setTauth(xtgl);
		qxgl.setCname("权限管理");
		qxgl.setCurl("");
		qxgl.setCseq(BigDecimal.valueOf(3));
		qxgl.setCdesc("");
		authDao.saveOrUpdate(qxgl);

		Tauth qxglym = new Tauth();
		qxglym.setCid("qxglym");
		qxglym.setTauth(qxgl);
		qxglym.setCname("权限管理页面");
		qxglym.setCurl("/authAction!auth.action");
		qxglym.setCseq(BigDecimal.valueOf(1));
		qxglym.setCdesc("");
		authDao.saveOrUpdate(qxglym);

		Tauth qxcx = new Tauth();
		qxcx.setCid("qxcx");
		qxcx.setTauth(qxgl);
		qxcx.setCname("权限查询");
		qxcx.setCurl("/authAction!treegrid.action");
		qxcx.setCseq(BigDecimal.valueOf(2));
		qxcx.setCdesc("");
		authDao.saveOrUpdate(qxcx);

		Tauth qxadd = new Tauth();
		qxadd.setCid("qxadd");
		qxadd.setTauth(qxgl);
		qxadd.setCname("权限添加");
		qxadd.setCurl("/authAction!add.action");
		qxadd.setCseq(BigDecimal.valueOf(3));
		qxadd.setCdesc("");
		authDao.saveOrUpdate(qxadd);

		Tauth qxedit = new Tauth();
		qxedit.setCid("qxedit");
		qxedit.setTauth(qxgl);
		qxedit.setCname("权限编辑");
		qxedit.setCurl("/authAction!edit.action");
		qxedit.setCseq(BigDecimal.valueOf(4));
		qxedit.setCdesc("");
		authDao.saveOrUpdate(qxedit);

		Tauth qxdelete = new Tauth();
		qxdelete.setCid("qxdelete");
		qxdelete.setTauth(qxgl);
		qxdelete.setCname("权限删除");
		qxdelete.setCurl("/authAction!delete.action");
		qxdelete.setCseq(BigDecimal.valueOf(5));
		qxdelete.setCdesc("");
		authDao.saveOrUpdate(qxdelete);

		Tauth cdgl = new Tauth();
		cdgl.setCid("cdgl");
		cdgl.setTauth(xtgl);
		cdgl.setCname("菜单管理");
		cdgl.setCurl("");
		cdgl.setCseq(BigDecimal.valueOf(4));
		cdgl.setCdesc("");
		authDao.saveOrUpdate(cdgl);

		Tauth cdglym = new Tauth();
		cdglym.setCid("cdglym");
		cdglym.setTauth(cdgl);
		cdglym.setCname("菜单管理页面");
		cdglym.setCurl("/menuAction!menu.action");
		cdglym.setCseq(BigDecimal.valueOf(1));
		cdglym.setCdesc("");
		authDao.saveOrUpdate(cdglym);

		Tauth cdcx = new Tauth();
		cdcx.setCid("cdcx");
		cdcx.setTauth(cdgl);
		cdcx.setCname("菜单查询");
		cdcx.setCurl("/menuAction!treegrid.action");
		cdcx.setCseq(BigDecimal.valueOf(2));
		cdcx.setCdesc("");
		authDao.saveOrUpdate(cdcx);

		Tauth cdadd = new Tauth();
		cdadd.setCid("cdadd");
		cdadd.setTauth(cdgl);
		cdadd.setCname("菜单添加");
		cdadd.setCurl("/menuAction!add.action");
		cdadd.setCseq(BigDecimal.valueOf(3));
		cdadd.setCdesc("");
		authDao.saveOrUpdate(cdadd);

		Tauth cdedit = new Tauth();
		cdedit.setCid("cdedit");
		cdedit.setTauth(cdgl);
		cdedit.setCname("菜单编辑");
		cdedit.setCurl("/menuAction!edit.action");
		cdedit.setCseq(BigDecimal.valueOf(4));
		cdedit.setCdesc("");
		authDao.saveOrUpdate(cdedit);

		Tauth cddelete = new Tauth();
		cddelete.setCid("cddelete");
		cddelete.setTauth(cdgl);
		cddelete.setCname("菜单删除");
		cddelete.setCurl("/menuAction!delete.action");
		cddelete.setCseq(BigDecimal.valueOf(5));
		cddelete.setCdesc("");
		authDao.saveOrUpdate(cddelete);

		Tauth buggl = new Tauth();
		buggl.setCid("buggl");
		buggl.setTauth(xtgl);
		buggl.setCname("BUG管理");
		buggl.setCurl("");
		buggl.setCseq(BigDecimal.valueOf(5));
		buggl.setCdesc("");
		authDao.saveOrUpdate(buggl);

		Tauth bugglym = new Tauth();
		bugglym.setCid("bugglym");
		bugglym.setTauth(buggl);
		bugglym.setCname("BUG管理页面");
		bugglym.setCurl("/bugAction!bug.action");
		bugglym.setCseq(BigDecimal.valueOf(1));
		bugglym.setCdesc("");
		authDao.saveOrUpdate(bugglym);

		Tauth bugcx = new Tauth();
		bugcx.setCid("bugcx");
		bugcx.setTauth(buggl);
		bugcx.setCname("BUG查询");
		bugcx.setCurl("/bugAction!datagrid.action");
		bugcx.setCseq(BigDecimal.valueOf(2));
		bugcx.setCdesc("");
		authDao.saveOrUpdate(bugcx);

		Tauth bugadd = new Tauth();
		bugadd.setCid("bugadd");
		bugadd.setTauth(buggl);
		bugadd.setCname("BUG描述添加");
		bugadd.setCurl("/bugAction!add.action");
		bugadd.setCseq(BigDecimal.valueOf(3));
		bugadd.setCdesc("");
		authDao.saveOrUpdate(bugadd);

		Tauth bugedit = new Tauth();
		bugedit.setCid("bugedit");
		bugedit.setTauth(buggl);
		bugedit.setCname("BUG编辑");
		bugedit.setCurl("/bugAction!edit.action");
		bugedit.setCseq(BigDecimal.valueOf(4));
		bugedit.setCdesc("");
		authDao.saveOrUpdate(bugedit);

		Tauth bugdelete = new Tauth();
		bugdelete.setCid("bugdelete");
		bugdelete.setTauth(buggl);
		bugdelete.setCname("BUG删除");
		bugdelete.setCurl("/bugAction!delete.action");
		bugdelete.setCseq(BigDecimal.valueOf(5));
		bugdelete.setCdesc("");
		authDao.saveOrUpdate(bugdelete);

		Tauth bugupload = new Tauth();
		bugupload.setCid("bugupload");
		bugupload.setTauth(buggl);
		bugupload.setCname("BUG上传");
		bugupload.setCurl("/bugAction!upload.action");
		bugupload.setCseq(BigDecimal.valueOf(6));
		bugupload.setCdesc("");
		authDao.saveOrUpdate(bugupload);

	}

	private void repairMenu() {
		menuDao.executeHql("update Tmenu m set m.tmenu = null");

		Tmenu root = new Tmenu();
		root.setCid("0");
		root.setCname("首页");
		root.setCseq(BigDecimal.valueOf(1));
		root.setCurl("");
		root.setCtype(Constants.TAUTH_CTYPE_01);
		root.setTmenu(null);
		menuDao.saveOrUpdate(root);

		Tmenu jeecgDemo = new Tmenu();
		jeecgDemo.setCid("JeecgDemo");
		jeecgDemo.setCname("Jeecg 演示");
		jeecgDemo.setCseq(BigDecimal.valueOf(2));
		jeecgDemo.setCurl("");
		jeecgDemo.setCtype(Constants.TAUTH_CTYPE_01);
		jeecgDemo.setTmenu(root);
		menuDao.saveOrUpdate(jeecgDemo);
		
		Tmenu oneJeecgIndex = new Tmenu();
		oneJeecgIndex.setCid("jeecgOrderMain");
		oneJeecgIndex.setCname("一对多数据模型(明细Tab选项卡)");
		oneJeecgIndex.setCtype(Constants.TAUTH_CTYPE_01);
		oneJeecgIndex.setCseq(BigDecimal.valueOf(3));
		oneJeecgIndex.setCurl("jeecgOrderMainAction!goJeecgOrderMain.action");
		oneJeecgIndex.setTmenu(jeecgDemo);
		menuDao.saveOrUpdate(oneJeecgIndex);
		
		Tmenu jeecgOrderMainSingle = new Tmenu();
		jeecgOrderMainSingle.setCid("jeecgOrderMainSingle");
		jeecgOrderMainSingle.setCname("一对多数据模型(默认)");
		jeecgOrderMainSingle.setCtype(Constants.TAUTH_CTYPE_01);
		jeecgOrderMainSingle.setCseq(BigDecimal.valueOf(3));
		jeecgOrderMainSingle.setCurl("jeecgOrderMainSingleAction!goJeecgOrderMainSingle.action");
		jeecgOrderMainSingle.setTmenu(jeecgDemo);
		menuDao.saveOrUpdate(jeecgOrderMainSingle);
		
		Tmenu jeecgOneTest = new Tmenu();
		jeecgOneTest.setCid("jeecgOneTest");
		jeecgOneTest.setCname("单表模型(默认风格二)");
		jeecgOneTest.setCtype(Constants.TAUTH_CTYPE_01);
		jeecgOneTest.setCseq(BigDecimal.valueOf(3));
		jeecgOneTest.setCurl("jeecgOneTestAction!goJeecgOneTest.action");
		jeecgOneTest.setTmenu(jeecgDemo);
		menuDao.saveOrUpdate(jeecgOneTest);
		
		Tmenu jeecgOneDemo = new Tmenu();
		jeecgOneDemo.setCid("jeecgOneDemo");
		jeecgOneDemo.setCname("单表模型(默认)");
		jeecgOneDemo.setCtype(Constants.TAUTH_CTYPE_01);
		jeecgOneDemo.setCseq(BigDecimal.valueOf(3));
		jeecgOneDemo.setCurl("jeecgOneDemoAction!goJeecgOneDemo.action");
		jeecgOneDemo.setTmenu(jeecgDemo);
		menuDao.saveOrUpdate(jeecgOneDemo);
		
		Tmenu jeecgOneRow = new Tmenu();
		jeecgOneRow.setCid("jeecgOneRow");
		jeecgOneRow.setCname("单表模型(行编辑)");
		jeecgOneRow.setCtype(Constants.TAUTH_CTYPE_01);
		jeecgOneRow.setCseq(BigDecimal.valueOf(3));
		jeecgOneRow.setCurl("jeecgOneRowAction!goJeecgOneRow.action");
		jeecgOneRow.setTmenu(jeecgDemo);
		menuDao.saveOrUpdate(jeecgOneRow);
		
		
		//-----------
		
		Tmenu buss = new Tmenu();
		buss.setCid("ywgl");
		buss.setCname("生成器例子");
		buss.setCseq(BigDecimal.valueOf(2));
		buss.setCurl("");
		buss.setCtype(Constants.TAUTH_CTYPE_01);
		buss.setTmenu(root);
		menuDao.saveOrUpdate(buss);
		
		Tmenu oneIndex = new Tmenu();
		oneIndex.setCid("one");
		oneIndex.setCname("单表模型");
		oneIndex.setCtype(Constants.TAUTH_CTYPE_01);
		oneIndex.setCseq(BigDecimal.valueOf(3));
		oneIndex.setCurl("personAction!goPerson.action");
		oneIndex.setTmenu(buss);
		menuDao.saveOrUpdate(oneIndex);
		
		Tmenu onetomainIndex = new Tmenu();
		onetomainIndex.setCid("onetomain");
		onetomainIndex.setCname("一对多模型");
		onetomainIndex.setCtype(Constants.TAUTH_CTYPE_01);
		onetomainIndex.setCseq(BigDecimal.valueOf(3));
		onetomainIndex.setCurl("gbuyOrderAction!goGbuyOrder.action");
		onetomainIndex.setTmenu(buss);
		menuDao.saveOrUpdate(onetomainIndex);
		
		Tmenu buggl = new Tmenu();
		buggl.setCid("buggl");
		buggl.setCname("文本编辑器 Demo");
		buggl.setCtype(Constants.TAUTH_CTYPE_01);
		buggl.setCseq(BigDecimal.valueOf(5));
		buggl.setCurl("bugAction!goBug.action");
		buggl.setTmenu(buss);
		menuDao.saveOrUpdate(buggl);
		
		Tmenu buggl2 = new Tmenu();
		buggl2.setCid("buggl2");
		buggl2.setCname("Spring Jdbc分页");
		buggl2.setCtype(Constants.TAUTH_CTYPE_01);
		buggl2.setCseq(BigDecimal.valueOf(6));
		buggl2.setCurl("demoAction!goDemoJdbc.action");
		buggl2.setTmenu(buss);
		menuDao.saveOrUpdate(buggl2);
		
		
		Tmenu highcharts = new Tmenu();
		highcharts.setCid("Highcharts");
		highcharts.setCname("Highcharts报表");
		highcharts.setCtype(Constants.TAUTH_CTYPE_01);
		highcharts.setCseq(BigDecimal.valueOf(2));
		highcharts.setCurl("");
		highcharts.setTmenu(root);
		menuDao.saveOrUpdate(highcharts);

		Tmenu highcharts_01 = new Tmenu();
		highcharts_01.setCid("highcharts_01");
		highcharts_01.setCname("Line 例子");
		highcharts_01.setCtype(Constants.TAUTH_CTYPE_01);
		highcharts_01.setCseq(BigDecimal.valueOf(3));
		highcharts_01.setCurl("chartsAction!goLine.action");
		highcharts_01.setTmenu(highcharts);
		menuDao.saveOrUpdate(highcharts_01);

		Tmenu highcharts_02 = new Tmenu();
		highcharts_02.setCid("highcharts_02");
		highcharts_02.setCname("Pie 例子");
		highcharts_02.setCtype(Constants.TAUTH_CTYPE_01);
		highcharts_02.setCseq(BigDecimal.valueOf(3));
		highcharts_02.setCurl("chartsAction!goPie.action");
		highcharts_02.setTmenu(highcharts);
		menuDao.saveOrUpdate(highcharts_02);
		
		
		
		Tmenu sjkgl = new Tmenu();
		sjkgl.setCid("sjkgl");
		sjkgl.setCname("数据库管理");
		sjkgl.setCtype(Constants.TAUTH_CTYPE_01);
		sjkgl.setCseq(BigDecimal.valueOf(2));
		sjkgl.setCurl("");
		sjkgl.setTmenu(root);
		menuDao.saveOrUpdate(sjkgl);

		Tmenu druidIndex = new Tmenu();
		druidIndex.setCid("druidIndex");
		druidIndex.setCname("druid监控");
		druidIndex.setCtype(Constants.TAUTH_CTYPE_01);
		druidIndex.setCseq(BigDecimal.valueOf(3));
		druidIndex.setCurl("datasourceAction!goDruid.action");
		druidIndex.setTmenu(sjkgl);
		menuDao.saveOrUpdate(druidIndex);

		Tmenu xtgl = new Tmenu();
		xtgl.setCid("xtgl");
		xtgl.setCname("系统管理");
		xtgl.setCtype(Constants.TAUTH_CTYPE_01);
		xtgl.setCseq(BigDecimal.valueOf(3));
		xtgl.setCurl("");
		xtgl.setTmenu(root);
		menuDao.saveOrUpdate(xtgl);

		Tmenu yhgl = new Tmenu();
		yhgl.setCid("yhgl");
		yhgl.setCname("用户管理");
		yhgl.setCtype(Constants.TAUTH_CTYPE_01);
		yhgl.setCseq(BigDecimal.valueOf(1));
		yhgl.setCurl("userAction!goUser.action");
		yhgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(yhgl);

		Tmenu jsgl = new Tmenu();
		jsgl.setCid("jsgl");
		jsgl.setCname("角色管理");
		jsgl.setCtype(Constants.TAUTH_CTYPE_01);
		jsgl.setCseq(BigDecimal.valueOf(2));
		jsgl.setCurl("roleAction!goRole.action");
		jsgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(jsgl);

		Tmenu qxgl = new Tmenu();
		qxgl.setCid("qxgl");
		qxgl.setCname("按鈕权限");
		qxgl.setCtype(Constants.TAUTH_CTYPE_01);
		qxgl.setCseq(BigDecimal.valueOf(4));
		qxgl.setCurl("authAction!goAuth.action");
		qxgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(qxgl);
		
		Tmenu qxgl_sub = new Tmenu();
		qxgl_sub.setCid("qxgl_sub");
		qxgl_sub.setCname("按鈕权限");
		qxgl_sub.setCtype(Constants.TAUTH_CTYPE_02);
		qxgl_sub.setCseq(BigDecimal.valueOf(4));
		qxgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(qxgl_sub);

		Tmenu cdgl = new Tmenu();
		cdgl.setCid("cdgl");
		cdgl.setCname("菜单管理");
		cdgl.setCtype(Constants.TAUTH_CTYPE_01);
		cdgl.setCseq(BigDecimal.valueOf(3));
		cdgl.setCurl("menuAction!goMenu.action");
		cdgl.setTmenu(xtgl);
		menuDao.saveOrUpdate(cdgl);
		
		Tmenu dict = new Tmenu();
		dict.setCid("dict");
		dict.setCname("字典管理");
		dict.setCtype(Constants.TAUTH_CTYPE_01);
		dict.setCseq(BigDecimal.valueOf(5));
		dict.setCurl("dictParamAction!goDictParam.action");
		dict.setTmenu(xtgl);
		menuDao.saveOrUpdate(dict);
		

		Tmenu note = new Tmenu();
		note.setCid("note");
		note.setCname("公告管理");
		note.setCtype(Constants.TAUTH_CTYPE_01);
		note.setCseq(BigDecimal.valueOf(6));
		note.setCurl("noteAction!goNote.action");
		note.setTmenu(xtgl);
		menuDao.saveOrUpdate(note);
		
		
		Tmenu jbpm = new Tmenu();
		jbpm.setCid("jbpm");
		jbpm.setCname("工作流管理");
		jbpm.setCtype(Constants.TAUTH_CTYPE_01);
		jbpm.setCseq(BigDecimal.valueOf(4));
		jbpm.setCurl("");
		jbpm.setTmenu(root);
		menuDao.saveOrUpdate(jbpm);

		Tmenu deploy = new Tmenu();
		deploy.setCid("deploy");
		deploy.setCname("流程定义列表");
		deploy.setCtype(Constants.TAUTH_CTYPE_01);
		deploy.setCseq(BigDecimal.valueOf(1));
		deploy.setCurl("jbpm4JeecgAction!goDeployList.action");
		deploy.setTmenu(jbpm);
		menuDao.saveOrUpdate(deploy);
		
		
//		Tmenu buggl = new Tmenu();
//		buggl.setCid("buggl");
//		buggl.setCname("BUG管理");
//		buggl.setCseq(BigDecimal.valueOf(5));
//		buggl.setCurl("bugAction!goBug.action");
//		buggl.setTmenu(xtgl);
//		menuDao.saveOrUpdate(buggl);
//
//		Tmenu rzgl = new Tmenu();
//		rzgl.setCid("rzgl");
//		rzgl.setCname("日志管理");
//		rzgl.setCseq(BigDecimal.valueOf(4));
//		rzgl.setCurl("");
//		rzgl.setTmenu(root);
//		menuDao.saveOrUpdate(rzgl);
//
//		Tmenu yhdlrz = new Tmenu();
//		yhdlrz.setCid("yhdlrz");
//		yhdlrz.setCname("用户登录日志");
//		yhdlrz.setCseq(BigDecimal.valueOf(1));
//		yhdlrz.setCurl("");
//		yhdlrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(yhdlrz);
//
//		Tmenu yhzxrz = new Tmenu();
//		yhzxrz.setCid("yhzxrz");
//		yhzxrz.setCname("用户注销日志");
//		yhzxrz.setCseq(BigDecimal.valueOf(2));
//		yhzxrz.setCurl("");
//		yhzxrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(yhzxrz);
//
//		Tmenu yhglrz = new Tmenu();
//		yhglrz.setCid("yhglrz");
//		yhglrz.setCname("用户管理日志");
//		yhglrz.setCseq(BigDecimal.valueOf(3));
//		yhglrz.setCurl("");
//		yhglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(yhglrz);
//
//		Tmenu jsglrz = new Tmenu();
//		jsglrz.setCid("jsglrz");
//		jsglrz.setCname("角色管理日志");
//		jsglrz.setCseq(BigDecimal.valueOf(4));
//		jsglrz.setCurl("");
//		jsglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(jsglrz);
//
//		Tmenu qxglrz = new Tmenu();
//		qxglrz.setCid("qxglrz");
//		qxglrz.setCname("权限管理日志");
//		qxglrz.setCseq(BigDecimal.valueOf(5));
//		qxglrz.setCurl("");
//		qxglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(qxglrz);
//
//		Tmenu cdglrz = new Tmenu();
//		cdglrz.setCid("cdglrz");
//		cdglrz.setCname("菜单管理日志");
//		cdglrz.setCseq(BigDecimal.valueOf(6));
//		cdglrz.setCurl("");
//		cdglrz.setTmenu(rzgl);
//		menuDao.saveOrUpdate(cdglrz);

	}

	private void repairUser() {
		List<Tuser> t = userDao.find("from Tuser t where t.cname = ? and t.cid != ?", "admin", "0");// cid!='0'并且cname='admin'这是错误的数据，需要修复
		if (t != null && t.size() > 0) {
			for (Tuser u : t) {
				u.setCname(u.getCname() + UUID.randomUUID().toString());
			}
		}

		Tuser admin = new Tuser();
		admin.setCid("0");
		admin.setRealname("超级用户");
		admin.setCname("admin");
		admin.setStatus("1");
		admin.setCpwd(Encrypt2.e("admin"));
		userDao.saveOrUpdate(admin);
	}
	
	private void repairDict() {
		DictParamEntity dictParamEntity = new DictParamEntity();
		dictParamEntity.setDelflag(0);
		dictParamEntity.setObid("001");
		dictParamEntity.setParamValue("01");
		dictParamEntity.setParamName("男");
		dictParamEntity.setParamLevel("001");
		dictParamEntity.setParamLevelDec("性别");
		dictParamEntityDao.saveOrUpdate(dictParamEntity);
		
		DictParamEntity dictParamEntity2 = new DictParamEntity();
		dictParamEntity2.setDelflag(0);
		dictParamEntity2.setObid("002");
		dictParamEntity2.setParamValue("02");
		dictParamEntity2.setParamName("女");
		dictParamEntity2.setParamLevel("001");
		dictParamEntity2.setParamLevelDec("性别");
		dictParamEntityDao.saveOrUpdate(dictParamEntity2);
	}

}
