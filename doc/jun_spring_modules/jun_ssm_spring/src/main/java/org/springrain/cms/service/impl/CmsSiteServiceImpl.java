package org.springrain.cms.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springrain.cms.entity.CmsLink;
import org.springrain.cms.entity.CmsSite;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.Enumerations.OrgType;
import org.springrain.frame.util.Enumerations.UserOrgType;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.SecUtils;
import org.springrain.system.entity.Org;
import org.springrain.system.entity.UserOrg;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.IOrgService;
import org.springrain.system.service.ITableindexService;
import org.springrain.system.service.IUserOrgService;
import org.springrain.weixin.entity.WxCpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.service.IWxMpServletService;

/**
 * 在此加入类描述
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version 2016-11-10 11:55:21
 * @see org.springrain.cms.entity.demo.service.impl.CmsSite
 */
@Service("cmsSiteService")
public class CmsSiteServiceImpl extends BaseSpringrainServiceImpl implements
		ICmsSiteService {

	@Resource
	private ITableindexService tableindexService;

	@Resource
	private ICmsLinkService cmsLinkService;

	@Resource
	private IOrgService orgService;
	@Resource
	private IUserOrgService userOrgService;

	@Resource
	private IWxMpConfigService wxMpConfigService;

	@Resource
	private IWxMpServletService wxMpServletService;

	@Override
	public Object saveorupdate(Object entity) throws Exception {
		CmsSite cmsSite = (CmsSite) entity;
		String siteId;
		if (StringUtils.isBlank(cmsSite.getId())) {
			cmsSite.setUserId(SessionUser.getUserId());
			siteId = this.saveCmsSite(cmsSite);
			// 清除站点下的站点列表缓存
		} else {
			siteId = this.updateCmsSite(cmsSite);
		}
		evictByKey(GlobalStatic.cacheKey, "cmsSiteService_findListDataByFinder");
		return siteId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object queryBean) throws Exception {
		List<String> orgIds = userOrgService
				.findOrgIdsByManagerUserId(SessionUser.getUserId());
		finder = Finder.getSelectFinder(CmsSite.class).append(
				" where orgId in (:orgIds)");
		finder.setParam("orgIds", orgIds);
		List<CmsSite> siteList;
		if (page.getPageIndex() == 1) {
			siteList = getByCache(GlobalStatic.cacheKey,
					"cmsSiteService_findListDataByFinder", ArrayList.class);
			if (CollectionUtils.isEmpty(siteList)) {// 缓存中没有
				siteList = super.findListDataByFinder(finder, page,
						CmsSite.class, queryBean);
				putByCache(GlobalStatic.cacheKey,
						"cmsSiteService_findListDataByFinder", siteList);
			}
		} else {
			siteList = super.findListDataByFinder(finder, page, CmsSite.class,
					queryBean);
		}
		return (List<T>) siteList;
	}

	@Override
	public String saveCmsSite(CmsSite cmsSite) throws Exception {
		if (cmsSite == null) {
			return null;
		}
		if ("-1".equals(cmsSite.getThemeId())) {
			cmsSite.setThemeId("");
		}
		String id = tableindexService.updateNewId(CmsSite.class);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		cmsSite.setId(id);
		// cmsSite.setOrgId(orgId);
		super.save(cmsSite);

		// 如果是企业号或服务号，新建配置信息
		Integer siteType = cmsSite.getSiteType();
		if (OrgType.cp.getType()-siteType==0) {
			/*
			 * CmsSiteWxconfig config = new CmsSiteWxconfig(SecUtils.getUUID());
			 * config.setActive(1); config.setSiteId(id);
			 * cmsSiteWxconfigService.save(config);
			 */
			WxCpConfig config = new WxCpConfig();
			config.setId(id);
			config.setActive(1);
			config.setSiteId(id);
			wxMpServletService.save(config);
		}
		if (OrgType.mp.getType() -siteType == 0) {
			WxCpConfig config = new WxCpConfig();
			config.setId(id);
			config.setActive(1);
			config.setSiteId(id);
			wxMpServletService.save(config);
		}

		int statichtml = 0; // 默认不静态化
		// 保存 相应的前台 link 链接
		CmsLink cmsLink = new CmsLink();
		cmsLink.setBusinessId(id);
		cmsLink.setSiteId(id);
		cmsLink.setModelType(Enumerations.CmsLinkModeType.站点.getType());
		cmsLink.setName(cmsSite.getName());
		cmsLink.setJumpType(0);
		cmsLink.setLookcount(1);
		cmsLink.setStatichtml(statichtml);// 默认不静态化
		cmsLink.setActive(1);// 默认可以使用 ,可以用为1啊大哥
		cmsLink.setSortno(1);
		// 首页默认
		String _index = "/f/"
				+ OrgType.getOrgType(cmsSite.getSiteType()).name() + "/" + id
				+ "/index";
		cmsLink.setDefaultLink(_index);
		cmsLink.setLink(_index);
		// 设置模板路径
		cmsLink.setFtlfile("/u/" + id + "/index");
		cmsLink.setNodeftlfile("/u/" + id + "/channel");
		cmsLinkService.save(cmsLink);

		// 保存 相应的后台台 link 链接 ===预留====
		cmsLink = new CmsLink();
		cmsLink.setId(null);
		cmsLink.setModelType(Enumerations.CmsLinkModeType.站长后台.getType());
		_index = "/s/" + id + "/index";
		cmsLink.setStatichtml(statichtml);
		cmsLink.setDefaultLink(_index);
		cmsLink.setLink(_index);
		cmsLink.setFtlfile("/u/" + id + "/s/index");
		cmsLink.setActive(0);// 默认不可以用
		cmsLink.setSortno(1);
		cmsLink.setSiteId(id);
		cmsLink.setBusinessId(id);
		cmsLinkService.save(cmsLink);

		File t_file = new File(GlobalStatic.webInfoDir + "/freemarker/u/" + id + "/");
		File f_file = new File(GlobalStatic.webInfoDir + "/freemarker/u/" + id + "/f/");
		File s_file = new File(GlobalStatic.webInfoDir + "/freemarker/u/" + id + "/s/");
		if (!t_file.exists()) {
			t_file.mkdirs();
		}
		if (!s_file.exists()) {
			s_file.mkdirs();
		}
		if (!f_file.exists()) {
			f_file.mkdirs();
		}

		String str_f_jsdir = GlobalStatic.rootDir + "/u/" + id + "/f/js/";
		String str_f_cssdir = GlobalStatic.rootDir + "/u/" + id + "/f/css/";
		String str_f_imgdir = GlobalStatic.rootDir + "/u/" + id + "/f/img/";
		
		String str_s_jsdir = GlobalStatic.rootDir + "/u/" + id + "/s/js/";
		String str_s_cssdir = GlobalStatic.rootDir + "/u/" + id + "/s/css/";
		String str_s_imgdir = GlobalStatic.rootDir + "/u/" + id + "/s/img/";

		String str_upload = GlobalStatic.rootDir + "/upload/u/" + id + "/";

		File f_jsdir = new File(str_f_jsdir);
		if (!f_jsdir.exists()) {
			f_jsdir.mkdirs();
		}
		File f_cssdir = new File(str_f_cssdir);
		if (!f_cssdir.exists()) {
			f_cssdir.mkdirs();
		}
		File f_imgdir = new File(str_f_imgdir);
		if (!f_imgdir.exists()) {
			f_imgdir.mkdirs();
		}
		
		File s_jsdir = new File(str_s_jsdir);
		if (!s_jsdir.exists()) {
			s_jsdir.mkdirs();
		}
		File s_cssdir = new File(str_s_cssdir);
		if (!s_cssdir.exists()) {
			s_cssdir.mkdirs();
		}
		File s_imgdir = new File(str_s_imgdir);
		if (!s_imgdir.exists()) {
			s_imgdir.mkdirs();
		}

		File uploaddir = new File(str_upload);
		if (!uploaddir.exists()) {
			uploaddir.mkdirs();
		}
		if (StringUtils.isNotBlank(cmsSite.getLogo())) {// 如果有logo，将logo从临时文件夹移动到正式文件夹
			File tmpLogo = new File(GlobalStatic.rootDir + cmsSite.getLogo());
			File formalLogo = new File(str_upload + tmpLogo.getName());
			if (!formalLogo.exists()) {
				boolean createNewFile = formalLogo.createNewFile();
				if (!createNewFile) {
					return null;
				}
			}

			FileUtils.copyFile(tmpLogo, formalLogo);
			tmpLogo.deleteOnExit();
		}

		putByCache(id, "cmsSiteService_findCmsSiteById_" + id, cmsSite);

		// 创建站点部门
		Org org = new Org(id);
		org.setName(cmsSite.getName());
		org.setDescription(cmsSite.getDescription());
		org.setOrgType(cmsSite.getSiteType());
		org.setPid(cmsSite.getOrgId());
		org.setActive(1);
		String orgId = orgService.saveOrg(org);
		// 创建站点用户关联新
		UserOrg userOrg = new UserOrg(SecUtils.getUUID());
		userOrg.setUserId(SessionUser.getUserId());
		userOrg.setOrgId(orgId);
		userOrg.setHasleaf(0);
		userOrg.setManagerType(UserOrgType.getUserOrgTypeByName(
				UserOrgType.主管.name()).getType());
		userOrgService.save(userOrg);
		// 如果选择默认模板，把默认模板下的文件拷贝一份到站点文件夹下面
		if (StringUtils.isNotBlank(cmsSite.getThemeId())
				&& !"-1".equals(cmsSite.getThemeId())) {
			// 拷贝页面
			File html_oldPath = new File(GlobalStatic.webInfoDir
					+ "/freemarker/themes/" + cmsSite.getThemeName() + "/");
			FileUtils.copyDirectoryToDirectory(html_oldPath, t_file);
			// 拷贝js、css、img
			File js_file = new File(GlobalStatic.rootDir + "/u/" + id);
			File js_oldPath = new File(GlobalStatic.rootDir + "/themes/"
					+ cmsSite.getThemeName() + "/");
			FileUtils.copyDirectoryToDirectory(js_oldPath, js_file);
		}
		return id;
	}

	@Override
	public CmsSite findCmsSiteById(String id) throws Exception {
		CmsSite site = getByCache(id, "cmsSiteService_findCmsSiteById_" + id,
				CmsSite.class);
		if (site == null) {
			site = findById(id, CmsSite.class);
			putByCache(id, "cmsSiteService_findCmsSiteById_" + id, site);
		}
		return site;
	}

	@Override
	public String updateCmsSite(CmsSite cmsSite) throws Exception {
		if ("-1".equals(cmsSite.getThemeId())) {
			cmsSite.setThemeId("");
		}
		// 保存图片附件
		super.update(cmsSite, true);
		String siteId = cmsSite.getId();
		putByCache(siteId, "cmsSiteService_findCmsSiteById_" + siteId, cmsSite);
		// 如果选择默认模板，把默认模板下的文件拷贝一份到站点文件夹下面
		if (StringUtils.isNotBlank(cmsSite.getThemeId())) {
			// 拷贝页面
			File t_file = new File(GlobalStatic.webInfoDir + "/freemarker/u/"
					+ siteId + "/");
			File html_oldPath = new File(GlobalStatic.webInfoDir
					+ "/freemarker/themes/" + cmsSite.getThemeName() + "/");
			FileUtils.copyDirectoryToDirectory(html_oldPath, t_file);
			// 拷贝js、css、img
			File js_file = new File(GlobalStatic.rootDir + "/u/" + siteId);
			File js_oldPath = new File(GlobalStatic.rootDir + "/themes/"
					+ cmsSite.getThemeName() + "/");
			FileUtils.copyDirectoryToDirectory(js_oldPath, js_file);
		}
		return null;
	}

	@Override
	public Integer findSiteTypeById(String siteId) throws Exception {
		if (StringUtils.isBlank(siteId)) {
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsSite.class, "siteType")
				.append(" WHERE id=:siteId ");
		finder.setParam("siteId", siteId);

		return super.queryForObject(finder, Integer.class);
	}

	@Override
	public List<CmsSite> findSiteByUserId(String userId) throws Exception {
		Finder finder = Finder.getSelectFinder(CmsSite.class).append(
				" WHERE userId=:userId");
		finder.setParam("userId", userId);
		return super.queryForList(finder, CmsSite.class);
	}

	@Override
	public <T> T findById(Object id, Class<T> clazz) throws Exception {
		return super.findById(id, clazz);
	}

	@Override
	public String saveTmpLogo(MultipartFile tempFile, String siteId)
			throws IOException {
		String filePath = "/upload/" + siteId + "/logo/" + SecUtils.getUUID()
				+ tempFile.getOriginalFilename();
		File file = new File(GlobalStatic.rootDir + filePath);
		File fileParentDir = file.getParentFile();
		if (!fileParentDir.exists()) {
            fileParentDir.mkdirs();
        }
		if (!file.exists()) {
			boolean createNewFile = file.createNewFile();
			if (!createNewFile) {
				return null;
			}
		}

		tempFile.transferTo(file);
		return File.separator + filePath;
	}

	@Override
	public List<CmsSite> findMpSiteByUserId(String userId) throws Exception {
		Finder finder = Finder.getSelectFinder(CmsSite.class).append(
				" WHERE userId=:userId and siteType=:siteType");
		finder.setParam("userId", userId).setParam("siteType",
				OrgType.mp.getType());
		return super.queryForList(finder, CmsSite.class);
	}

}
