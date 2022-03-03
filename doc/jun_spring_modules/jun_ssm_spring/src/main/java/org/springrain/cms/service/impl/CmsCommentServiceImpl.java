package org.springrain.cms.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.cms.entity.CmsComment;
import org.springrain.cms.service.ICmsCommentService;
import org.springrain.frame.entity.IBaseEntity;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.frame.util.SecUtils;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.weixin.sdk.common.api.IWxMpConfig;
import org.springrain.weixin.sdk.common.api.IWxMpConfigService;
import org.springrain.weixin.sdk.common.exception.WxErrorException;
import org.springrain.weixin.sdk.mp.api.IWxMpMaterialService;
import org.springrain.weixin.sdk.mp.api.IWxMpService;


/**
 * 
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:18
 * @see org.springrain.cms.entity.demo.service.impl.CmsComment
 */
@Service("cmsCommentService")
public class CmsCommentServiceImpl extends BaseSpringrainServiceImpl implements ICmsCommentService {
	
	@Resource
	private IWxMpConfigService wxMpConfigService;
	@Resource
	private IWxMpMaterialService wxMpMaterialService;
	@Resource
	private IWxMpService wxMpService;
	
	private void clearListCache(String siteId,String businessId,Page page)throws Exception{
		 String cacheKey="cmsCommentService_findCommentListByBusinessId_"+siteId+"_"+businessId;
		 super.evictByKey(siteId, cacheKey, page);
	}
	
	
	
    @Override
	public String  save(Object entity ) throws Exception{
    	 CmsComment cmsComment=(CmsComment) entity;
    	 String _id = super.save(cmsComment).toString();
    	 
    	 clearListCache(cmsComment.getSiteId(), cmsComment.getBusinessId(), null);
    	 
    	 
    	 
	     return _id;
	}

    @Override
	public String  saveorupdate(Object entity ) throws Exception{
	      CmsComment cmsComment=(CmsComment) entity;
	      
	      String _id = super.saveorupdate(cmsComment).toString();
	      clearListCache(cmsComment.getSiteId(), cmsComment.getBusinessId(), null);
	      
		 return _id;
	}
	
	@Override
    public Integer update(IBaseEntity entity ) throws Exception{
	 CmsComment cmsComment=(CmsComment) entity;
      Integer update = super.update(cmsComment);
	  clearListCache(cmsComment.getSiteId(), cmsComment.getBusinessId(), null);
    	return update;
    }
    @Override
	public CmsComment findCmsCommentById(String id) throws Exception{
	 return super.findById(id,CmsComment.class);
	}
	
/**
 * 列表查询,每个service都会重载,要把sql语句封装到service中,Finder只是最后的方案
 * @param finder
 * @param page
 * @param clazz
 * @param o
 * @return
 * @throws Exception
 */
        @Override
    public <T> List<T> findListDataByFinder(Finder finder, Page page, Class<T> clazz,
			Object o) throws Exception{
			 return super.findListDataByFinder(finder,page,clazz,o);
			}
	/**
	 * 根据查询列表的宏,导出Excel
	 * @param finder 为空则只查询 clazz表
	 * @param ftlurl 类表的模版宏
	 * @param page 分页对象
	 * @param clazz 要查询的对象
	 * @param o  querybean
	 * @return
	 * @throws Exception
	 */
		@Override
	public <T> File findDataExportExcel(Finder finder,String ftlurl, Page page,
			Class<T> clazz, Object o)
			throws Exception {
			 return super.findDataExportExcel(finder,ftlurl,page,clazz,o);
		}

	@Override
	public Integer findCommentsNumByBusinessId(String siteId,String businessId) throws Exception {
		
		
		String cacheKey="cmsCommentService_findCommentsNumByBusinessId_"+siteId+"_"+businessId;
		
		Integer num=super.getByCache(siteId, cacheKey, Integer.class);
		if(num!=null){
			return num;
		}
		
		
		
		Finder finder = new Finder("SELECT COUNT(*) FROM ")
					.append(Finder.getTableName(CmsComment.class))
					.append(" WHERE businessId=:businessId");
		finder.setParam("businessId", businessId);
		 num = super.queryForObject(finder,Integer.class);
		if(num==null){
			num=0;
		}
		
		super.putByCache(siteId, cacheKey, num);
		
	     return num;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CmsComment> findCommentListByBusinessId(String siteId,String businessId,Page page) throws Exception {
		String cacheKey="cmsCommentService_findCommentListByBusinessId_"+siteId+"_"+businessId;
		List<CmsComment> commentList=super.getByCache(siteId, cacheKey, List.class,page);
		if(commentList!=null){
			return commentList;
		}
		Finder finder = Finder.getSelectFinder(CmsComment.class).append(" WHERE businessId=:businessId");
		finder.setParam("businessId", businessId);
		commentList = super.queryForList(finder, CmsComment.class,page);
		if (commentList == null){
			commentList = new ArrayList<>();
		}
		
		super.putByCache(siteId, cacheKey, commentList,page);
		
		return commentList;
	}

	@Override
	public String saveWechatFile(String serverIds,String siteType,String siteId,String businessId) throws WxErrorException, IOException {
		IWxMpConfig wxMpConfig = wxMpConfigService.findWxMpConfigById(siteId);
		wxMpConfig.setTmpDirFile(GlobalStatic.tempRootpath);
		//IWxMpService wxMpService = new WxMpServiceImpl(wxMpConfigService);
		//IWxMpMaterialService wxMpMaterialService = new WxMpMaterialServiceImpl(wxMpService);
		StringBuilder filePath = new StringBuilder();
		String[] serverIdArr = StringUtils.split(serverIds, ",");
		
		File tmpDir = new File(GlobalStatic.tempRootpath);
		if(!tmpDir.exists()) {
            tmpDir.mkdirs();
        }
		for (String serverId : serverIdArr) {
			File downLoadFile = wxMpMaterialService.mediaDownload(wxMpConfig, serverId);
			String formalPath = GlobalStatic.rootDir+"/upload/"+siteType+"/"+siteId+"/"+businessId+"/"+downLoadFile.getName();
			File formalFile = new File(formalPath);
			FileUtils.copyFile(downLoadFile, formalFile);
			filePath.append("/upload/").append(siteType).append("/").append(siteId).append("/").append(businessId).append("/").append(SecUtils.getUUID()).append(downLoadFile.getName()).append(",");
		}
		String path = filePath.toString();
		if(StringUtils.isBlank(path)){
			return null;
		}
			return path;
		}



	@Override
	public void deleteCommentById(String id,String siteId,String businessId) throws Exception {
		super.deleteById(id, CmsComment.class);
		clearListCache(siteId, businessId, null);
	}

	@Override
	public void deleteByCommentIds(List<String> ids,String siteId,String businessId) throws Exception {
		super.deleteByIds(ids, CmsComment.class);
		clearListCache(siteId, businessId, null);
	}

	@Override
	public List<CmsComment> findListByOpenId(String openId, String siteId,
			String businessId, Integer type) throws Exception{
		if(StringUtils.isEmpty(openId) || StringUtils.isEmpty(siteId) || StringUtils.isEmpty(businessId)){
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsComment.class);
		finder.append(" where siteId=:siteId and businessId=:businessId and userId=:openId ")
			.setParam("siteId", siteId).setParam("businessId", businessId)
			.setParam("openId", openId);
		if(type != null){
			finder.append(" and type = :type ").setParam("type", type);
		}
		finder.append(" order by createDate desc ");
		return super.queryForList(finder,CmsComment.class);
	}
	
}
