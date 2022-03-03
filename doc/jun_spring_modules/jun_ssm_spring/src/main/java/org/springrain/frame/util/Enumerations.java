package org.springrain.frame.util;


/**
 * 平台枚举类型
 * @author caomei
 *
 */
public class Enumerations {
    
    
    /**
     * 用户类型枚举
     * @author caomei
     *
     */
	public enum UserType{
		系统后台管理员(0),平台管理员(1),分店管理员(2),第三方店铺管理员(3),前端普通用户(4);
		int type;
		private UserType(Integer type){
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		/**
		 * 获取用户类型
		 * @param type
		 * @return
		 */
		public static UserType getUserType(Integer type){
			switch (type) {
				case 0:
					return 系统后台管理员;
				case 1:
					return 平台管理员;
				case 2:
					return 分店管理员;
				case 3:
					return 第三方店铺管理员;
				case 4:
					return 前端普通用户;
				default:
					return null;
			}
		}
	}
	
	
	/**
	 * 用户接入设备类型
	 * @author caomei
	 *
	 */
	public enum UserPlateformDeviceType{
		公众号(1),小程序(2),企业号(3),APP(4),UDESK(5),QQ(6),微博(7);
		int type;
		private UserPlateformDeviceType(Integer type){
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		public static UserPlateformDeviceType getUserPlateformDeviceType(Integer type){
			switch (type) {
				case 1:
					return 公众号;
				case 2:
					return 小程序;
				case 3:
					return 企业号;
				case 4:
					return APP;
				case 5:
					return UDESK;
				case 6:
					return QQ;
				case 7:
					return 微博;
				default:
					return null;
			}
		}
	}
	
	
	
	/**
	 * 部门类型
	 * @author caomei
	 *
	 */
	public enum OrgType{
		部门(1),虚拟权限组(2),站长部门(10),mp(11),cp(12),pc(13),wap(14),xcx(15),app(16);
		int type;
		private OrgType(Integer type){
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		public static OrgType getOrgType(Integer type){
			switch (type) {
				case 1:
					return 部门;
				case 2:
					return 虚拟权限组;
				case 10:
					return 站长部门;
				case 11:
					return mp;
				case 12:
					return cp;
				case 13:
					return pc;
				case 14:
					return wap;
				case 15:
					return xcx;
				case 16: 
					return app;
				default:
					return null;
			}
		}
		
		public static OrgType getOrgTypeByName(String name){
			if(mp.name().equals(name))
				return mp;
			else if(wap.name().equals(name))
				return wap;
			else if(pc.name().equals(name))
				return pc;
			else if(cp.name().equals(name))
				return cp;
			else if(xcx.name().equals(name))
				return xcx;
			else 
				return null;
		}
	}
	
	
	
	
	/**
	 * 用户部门角色的枚举
	 * @author caomei
	 *
	 */
	public enum UserOrgType{
		会员(0),员工(10),主管(11),代理主管(12),虚拟主管(13);
		int type;
		private UserOrgType(Integer type){
			this.type = type;
		}
		
		public int getType() {
			return type;
		}
		
		public static UserOrgType getUserOrgType(Integer type){
			switch (type) {
				case 0:
					return 会员;
				case 10:
					return 员工;
				case 11:
					return 主管;
				case 12:
					return 代理主管;
				case 13:
					return 虚拟主管;
				default:
					return null;
			}
		}
		
		public static UserOrgType getUserOrgTypeByName(String name){
			if("会员".equals(name))
				return 会员;
			else if("员工".equals(name))
				return 员工;
			else if("主管".equals(name))
				return 主管;
			else if("代理主管".equals(name))
				return 代理主管;
			else if("虚拟主管".equals(name))
				return 虚拟主管;
			else 
				return null;
		}
	}
	
	
	/**
	 * @author Administrator
	 * 链接类型
	 */
	public enum CmsLinkModeType{
		/*
		 * 0站点（先不用）
		 * 1开头前台相关（包括微信和PC和其它）
		 * 3投票相关
		 * 4开头站工后相关
		 * 
		 * */
		站点(0),前台(1),前台栏目(10),前台栏目DOM(11),前台内容(12),前台栏目继承页(13),投票(30),站长后台(4),站长后台列表(40),站长后台更新(41),站长后台查看(42);
		int type;
		
		private CmsLinkModeType(Integer type) {
			this.type = type;
		}
		
		public static CmsLinkModeType getCmsLinkModeType(int type){
			switch (type) {
				case 0:
					return 站点;
				case 1:
					return 前台;
				case 10:
					return 前台栏目;
				case 11:
					return 前台栏目DOM;
				case 12:
					return 前台内容;
				case 13:
					return 前台栏目继承页;
				case 30:
					return 投票;
				case 4:
					return 站长后台;
				case 40:
					return 站长后台列表;
				case 41:
					return 站长后台更新;
				case 42:
					return 站长后台查看;
				default:
					return null;
			}
		}

		public int getType() {
			return type;
		}
	}
	/**
	 * @author Administrator
	 *  主题类型
	 *  此为主题类型，和站点类型要区分开
	 *  目前允许PC站点选手机主题，这个不限止死，
	 *  如果要完全匹配，后台限止就OK了
	 */
	public enum ThemeSiteType{
		/*
		 *  
		 * 
		 * */
		pc(1),mp(2),pc_mp(3);
		int type;
		
		private ThemeSiteType(Integer type) {
			this.type = type;
		}
		
		public static ThemeSiteType getThemeSiteType(int type){
			switch (type) {
				case 1:
					return pc;
				case 2:
					return mp;
				case 3:
					return pc_mp;
				default:
					return null;
			}
		}

		public int getType() {
			return type;
		}
	}
	
	/**
	 * 自定义参数枚举 cms_property 表 inputType 的枚举
	 * @author dmin93
	 * @date 2017年9月13日
	 */
	public enum CMSPropertyInputType{
		TEXT(0),DATE(1),DATETIME(2),INT(3),FLOAT(4),SELECT(5),FILE(6),IMG(7),IMGS(8);
		Integer type;
		
		private CMSPropertyInputType(Integer type) {
			this.type = type;
		}
		
		public static CMSPropertyInputType getCMSPropertyInputType(Integer type) {
			switch(type) {
				case 0:
					return TEXT;
				case 1:
					return DATE;
				case 2:
					return DATETIME;
				case 3:
					return INT;
				case 4:
					return FLOAT;
				case 5:
					return SELECT;
				case 6:
					return FILE;
				case 7:
					return IMG;
				case 8:
					return IMGS;
				default: 
					return null;
			}
		}

		public Integer getType() {
			return type;
		}
		
	}
	
	/**
	 * 自定义参数枚举 cms_property 表 modelType 的枚举
	 * @author dmin93
	 * @date 2017年9月13日
	 */
	public enum CMSPropertyModelType{
		SITE(0),CHANNEL(1),CONTENT(2),投票(3),收款设置(4);
		Integer type;
		
		private CMSPropertyModelType(Integer type) {
			this.type = type;
		}
		
		public static CMSPropertyModelType getCMSPropertyInputType(Integer type) {
			switch(type) {
				case 0:
					return SITE;
				case 1:
					return CHANNEL;
				case 2:
					return CONTENT;
				case 3:
					return 投票;
				case 4:
					return 收款设置;
				default: 
					return null;
			}
		}

		public Integer getType() {
			return type;
		}
		
	}
	

	
}
