package org.springrain.cms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springrain.cms.directive.CmsChannelListDirective;
import org.springrain.cms.entity.CmsChannel;
import org.springrain.cms.entity.CmsLink;
import org.springrain.cms.service.ICmsChannelService;
import org.springrain.cms.service.ICmsLinkService;
import org.springrain.cms.service.ICmsSiteService;
import org.springrain.cms.util.DirectiveUtils;
import org.springrain.frame.util.Enumerations;
import org.springrain.frame.util.Enumerations.OrgType;
import org.springrain.frame.util.Finder;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.Page;
import org.springrain.system.service.BaseSpringrainServiceImpl;
import org.springrain.system.service.ITableindexService;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;



/**
 * TODO 在此加入类描述
 * @copyright {@link weicms.net}
 * @author springrain<Auto generate>
 * @version  2016-11-10 11:55:17
 * @see org.springrain.cms.entity.demo.service.impl.CmsChannel
 */
@Service("cmsChannelService")
public class CmsChannelServiceImpl extends BaseSpringrainServiceImpl implements ICmsChannelService {

	@Resource
	private ITableindexService tableindexService;
	
	@Resource
	private ICmsLinkService cmsLinkService;
	
	
	@Resource
	private ICmsSiteService cmsSiteService;

	@Override
	public Object saveorupdate(Object entity) throws Exception {
		CmsChannel channel = (CmsChannel) entity;
		evictByKey(GlobalStatic.cacheKey, "cmsChannelService_findListDataByFinder");
		
		
		evictByKey(channel.getSiteId(), "cmsChannelService_findTreeByPid_null_"+channel.getSiteId());
		if(StringUtils.isBlank(channel.getId())){
			return this.saveChannel(channel);
		}else{
			return this.updateChannel(channel);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findListDataByFinder(Finder finder, Page page,
			Class<T> clazz, Object queryBean) throws Exception {
		List<CmsChannel> channelList;
		if(page.getPageIndex()==1){
			channelList = getByCache(GlobalStatic.cacheKey, "cmsChannelService_findListDataByFinder", List.class,page);
			if(CollectionUtils.isEmpty(channelList)){
				channelList = super.findListDataByFinder(finder, page, CmsChannel.class, queryBean);
				putByCache(GlobalStatic.cacheKey, "cmsChannelService_findListDataByFinder", channelList,page);
			}
		}else{
			channelList =  super.findListDataByFinder(finder, page, CmsChannel.class, queryBean);
		}
		return (List<T>) channelList;
	}
	
    @Override
	public String  saveChannel(CmsChannel cmsChannel) throws Exception{
    	if(cmsChannel==null){
    		return null;
    	}
        String siteId=cmsChannel.getSiteId();
        
        if(StringUtils.isBlank(siteId)){
        	return null;
        }
        
        Integer siteType=cmsSiteService.findSiteTypeById(siteId);
        if(siteType==null){
        	return null;
        }
        
    	
	    String id= tableindexService.updateNewId(CmsChannel.class);
	    if(StringUtils.isEmpty(id)){
	    	return null;
	    }
	    cmsChannel.setId(id);
	    //pid和siteId一致设空
	    if(siteId.equals(cmsChannel.getPid())){
	    	cmsChannel.setPid(null);
	    }
	    String _c=	findChannelNewComcode(id, cmsChannel.getPid(),siteId);
	    cmsChannel.setComcode(_c);
	    super.save(cmsChannel);
	    
	    
       //保存 相应的 link 链接   微信前端，以后会扩展PC等
	    CmsLink cmsLink=new CmsLink();
	    cmsLink.setModelType(Enumerations.CmsLinkModeType.前台栏目.getType());
	    cmsLink.setBusinessId(id);
	    cmsLink.setSiteId(siteId);
	    cmsLink.setName(cmsChannel.getName());
	    cmsLink.setJumpType(cmsChannel.getJumpType());
	    cmsLink.setLookcount(1);
	    cmsLink.setStatichtml(0);//默认不静态化
	    cmsLink.setActive(1);//默认可以使用，可以用为1啊大哥，怎么是0呢之前
	    cmsLink.setSortno(1);
	    cmsLink.setLoginuser(cmsChannel.getLoginuser());
	    //首页默认  			如果前台不传，就取默认
	    String _index="/f/"+OrgType.getOrgType(siteType).name()+"/"+siteId+"/"+id;
	    cmsLink.setDefaultLink(_index);
	    if(StringUtils.isEmpty(cmsChannel.getLink())){
	    	cmsLink.setLink(_index);
	    }else{
	    	cmsLink.setLink(cmsChannel.getLink());
	    }
	    //设置模板路径  本栏目的查看取父类的，而这次传的  为下面的做准备
	    String  ftlFile=cmsChannel.getFtlListPath();
	    if(StringUtils.isEmpty(ftlFile)){
	    	 String pid = cmsChannel.getPid();
	 	    if(StringUtils.isBlank(pid)){
	 	    	pid = siteId;
	 	    	//如果父类为空，到站点的
	 	    	ftlFile=cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, pid, Enumerations.CmsLinkModeType.站点.getType()).getNodeftlfile();
	 	    }else{
	 	    	//如果不为空取父类的，默认只给"站长后台列表"的  nodeftlfile，当然其它的保存的时候设置为空了
	 	    	ftlFile=cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, pid, Enumerations.CmsLinkModeType.站长后台列表.getType()).getNodeftlfile();
	 	    }
	    }
	    cmsLink.setFtlfile(ftlFile);//此处应该不完全从上级继承？？？不好使啊
	    cmsLink.setNodeftlfile(null);
	    cmsLinkService.save(cmsLink);
	    //首页AJAX  DOM
	    cmsLink.setId(null);
	    cmsLink.setModelType(Enumerations.CmsLinkModeType.前台栏目DOM.getType());
	    _index="/f/"+OrgType.getOrgType(siteType).name()+"/"+siteId+"/"+id+"/dom";
	    cmsLink.setDefaultLink(_index);
	    cmsLink.setFtlfile(ftlFile+"Dom");
	    cmsLink.setLink(_index);
	    cmsLinkService.save(cmsLink);
	    //保存 相应的站长管理后台list列表 link 链接
	    cmsLink.setId(null);
	    cmsLink.setModelType(Enumerations.CmsLinkModeType.站长后台列表.getType());
	    _index="/s/"+siteId+"/"+id+"/content/list";
	    cmsLink.setDefaultLink(_index);
	    cmsLink.setLink(_index);
	    cmsLink.setFtlfile(cmsChannel.getFtlListPath());
	    cmsLink.setNodeftlfile(cmsChannel.getNodeftlPath());//只存List
	    cmsLinkService.save(cmsLink);
	    cmsLink.setNodeftlfile(null);
	    //保存 相应的站长管理后台update link 链接
	    cmsLink.setId(null);
	    cmsLink.setModelType(Enumerations.CmsLinkModeType.站长后台更新.getType());
	    _index = "/s/"+siteId+"/"+id+"/content/update/pre";
	    cmsLink.setDefaultLink(_index);
	    cmsLink.setLink(_index);
	    cmsLink.setFtlfile(cmsChannel.getFtlUpdatePath());
	    cmsLinkService.save(cmsLink);
	    //保存 相应的站长管理后台look link 链接
	    cmsLink.setId(null);
	    cmsLink.setModelType(Enumerations.CmsLinkModeType.站长后台查看.getType());
	    _index = "/s/"+siteId+"/"+id+"/content/look";
	    cmsLink.setDefaultLink(_index);
	    cmsLink.setLink(_index);
	    cmsLink.setFtlfile(cmsChannel.getFtlLookPath());
	    cmsLinkService.save(cmsLink);
	    
	    evictByKey(siteId, "cmsChannelService_findTreeByPid_"+cmsChannel.getPid()+"_"+siteId);
	    putByCache(siteId, "cmsChannelService_findTreeChannel_"+siteId, cmsChannel);
	    return id;
	}

  
	@Override
    public Integer updateChannel(CmsChannel cmsChannel) throws Exception{
		if(cmsChannel==null){
    		return null;
    	}
		String id=cmsChannel.getId();
		String pid=cmsChannel.getPid();
		String siteId=cmsChannel.getSiteId();
		
		if(StringUtils.isBlank(siteId)||StringUtils.isBlank(id)){
			return null;
		}
		  //pid和siteId一致设空
	    if(siteId.equals(cmsChannel.getPid())){
	    	cmsChannel.setPid("");
	    }
		//修改cmsLink
		CmsLink link = cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, id,Enumerations.CmsLinkModeType.站长后台列表.getType());//只查列表即可
		//看前台的是否有改变    此除特殊，前台的ftl取的是后台列表的node可能会有PC以后
		if(link!=null&&!link.getNodeftlfile().equals(cmsChannel.getNodeftlPath())){
			//修改之前的文章   相当于对 之前的文章 换前台模板
			Finder finder=Finder.getUpdateFinder(CmsLink.class," ftlfile=:ftlfile where siteId=:siteId and ftlfile=:oldftlfile and businessId like :bid");
			finder.setParam("ftlfile", cmsChannel.getNodeftlPath()).setParam("siteId", siteId).setParam("oldftlfile", link.getNodeftlfile()).setParam("bid", "c_%");
			super.update(finder);
			//临时开关处理  开始     只保留 40的nodefltlfile  此处方法为处理老数据，部署完后可注释掉
			finder=Finder.getUpdateFinder(CmsLink.class," nodeftlfile=null where siteId=:siteId and businessId=:businessId and modelType <> 40 ");
			finder.setParam("siteId", siteId).setParam("businessId", id);
			super.update(finder);
			//临时开关处理  结束
			//修改现有
			link.setNodeftlfile(cmsChannel.getNodeftlPath());
			cmsLinkService.saveorupdate(link);
		}
		if(link!=null&&!link.getFtlfile().equals(cmsChannel.getFtlListPath())){
			link.setFtlfile(cmsChannel.getFtlListPath());
			cmsLinkService.saveorupdate(link);
		}
		
		link = cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, id,Enumerations.CmsLinkModeType.站长后台更新.getType());
		if(link!=null&&!link.getFtlfile().equals(cmsChannel.getFtlUpdatePath())){
			link.setFtlfile(cmsChannel.getFtlUpdatePath());
			cmsLinkService.saveorupdate(link);
		}
		
		link = cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, id,Enumerations.CmsLinkModeType.站长后台查看.getType());
		if(link!=null&&!link.getFtlfile().equals(cmsChannel.getFtlLookPath())){
			link.setFtlfile(cmsChannel.getFtlLookPath());
			cmsLinkService.saveorupdate(link);
		}
		//看前台的croller
		link = cmsLinkService.findLinkBySiteBusinessIdModelType(siteId, id,Enumerations.CmsLinkModeType.前台栏目.getType());
		if(link!=null&&!link.getLink().equals(cmsChannel.getLink())){
			link.setLink(cmsChannel.getLink());
			cmsLinkService.saveorupdate(link);
		}
		if(link!=null&&!link.getFtlfile().equals(cmsChannel.getFrontFtl())){
			link.setFtlfile(cmsChannel.getFrontFtl());
			cmsLinkService.saveorupdate(link);
		}
		if(link!=null&&!link.getJumpType().equals(cmsChannel.getJumpType())){
			link.setJumpType(Integer.valueOf(cmsChannel.getJumpType()));
			cmsLinkService.saveorupdate(link);
		}
		if(link!=null&&!link.getLoginuser().equals(cmsChannel.getLoginuser())){
			link.setLoginuser(cmsChannel.getLoginuser());
			cmsLinkService.saveorupdate(link);
		}
		
		
		
		Finder f_old_c=Finder.getSelectFinder(CmsChannel.class, "comcode").append(" WHERE id=:id ").setParam("id", id);
		
		String old_c=super.queryForObject(f_old_c, String.class);
		
		String new_c=findChannelNewComcode(id, pid,siteId);
		
		if(new_c.equalsIgnoreCase(old_c)){//编码没有改变
			evictByKey(siteId, "cmsChannelService_findTreeByPid_"+pid+"_"+siteId);
			return super.update(cmsChannel,true);
		}
		cmsChannel.setComcode(new_c);
		Integer update = super.update(cmsChannel,true);
		//级联更新
		Finder f_s_list=Finder.getSelectFinder(CmsChannel.class, "id,comcode").append(" WHERE comcode like :comcode and id<>:id ").setParam("comcode", old_c+"%").setParam("id", id);
	    List<CmsChannel> list = super.queryForList(f_s_list, CmsChannel.class);
	    if(CollectionUtils.isEmpty(list)){
	    	 return update;
	    }
		
	    for(CmsChannel ch:list){
	    	String _id=ch.getId();
		    String _c=	findChannelNewComcode(_id, id,siteId);
		    ch.setComcode(_c);
		    ch.setPid(id);
	    }
		super.update(list,true);
		super.cleanCache(siteId);
	    return update;

    }
    @Override
	public CmsChannel findCmsChannelById(String id) throws Exception{
    	return findById(id,CmsChannel.class);
	}
    
    
    
    @SuppressWarnings("unchecked")
	@Override
	public List<CmsChannel> findTreeByPid(String pid,String siteId) throws Exception {
    	if(StringUtils.isBlank(siteId)){
    		return null;
    	}
    	
    	List<CmsChannel> channelList = null;//getByCache(siteId, "cmsChannelService_findTreeByPid_"+pid+"_"+siteId, List.class);
    	if(CollectionUtils.isNotEmpty(channelList)){
    		return channelList;
    	}
    	
    	
    	
    	 
    		Finder finder=Finder.getSelectFinder(CmsChannel.class).append("  WHERE active=:active and siteId=:siteId ").setParam("siteId", siteId).setParam("active", 1);
            
            if(StringUtils.isNotBlank(pid)){
            	finder.append(" and comcode like :comcode and id<>:pid ").setParam("comcode", "%,"+pid+",%").setParam("pid", pid);
            }
            finder.append(" order by sortno asc ");
            
            channelList=super.queryForList(finder, CmsChannel.class);
    		if(CollectionUtils.isEmpty(channelList)){
    			return null;
    		}
    		//查所有的links
    		for(CmsChannel e:channelList){
    			this.addLinks(e);
    		}
    		//
    		
    		List<CmsChannel> wrapList=new ArrayList<>();
    		diguiwrapList(channelList, wrapList, null);
    		putByCache(siteId, "cmsChannelService_findTreeByPid_"+pid+"_"+siteId,wrapList);
    		return wrapList;
    	
	}
    private void addLinks(CmsChannel e)throws Exception{
    	Finder finder=Finder.getSelectFinder(CmsLink.class);
    	finder.append(" where siteId=:siteId and businessId=:businessId and active=1 ");
    	finder.setParam("siteId", e.getSiteId()).setParam("businessId", e.getId());
    	List<CmsLink> links=queryForList(finder, CmsLink.class);
    	if(CollectionUtils.isNotEmpty(links)){
    		for(CmsLink cmsLink:links){
    			if(Enumerations.CmsLinkModeType.站长后台列表.getType()==cmsLink.getModelType()){
    				e.setFtlListPath(cmsLink.getFtlfile());
    				e.setNodeftlPath(cmsLink.getNodeftlfile());
    			}
    			if(Enumerations.CmsLinkModeType.站长后台更新.getType()==cmsLink.getModelType()){
    				e.setFtlUpdatePath(cmsLink.getFtlfile());
    			}
    			if(Enumerations.CmsLinkModeType.站长后台查看.getType()==cmsLink.getModelType()){
    				e.setFtlLookPath(cmsLink.getFtlfile());
    			}
    			if(Enumerations.CmsLinkModeType.前台栏目.getType()==cmsLink.getModelType()){
    				e.setLink(cmsLink.getLink());
    				e.setJumpType(cmsLink.getJumpType());
    				e.setFrontFtl(cmsLink.getFtlfile());
    				e.setLoginuser(cmsLink.getLoginuser());
    			}
    		}
    	}
    }
	@Override
	public List<CmsChannel> findTreeChannel(String siteId) throws Exception {
		return findTreeByPid(null, siteId);
	}

	@Override
	public String findChannelNewComcode(String channelId,String pid,String siteId) throws Exception {
		
		if(StringUtils.isBlank(channelId)||StringUtils.isBlank(siteId)){
			return null;
		}
		
		String comcode=null;
		Finder f_p_c=Finder.getSelectFinder(CmsChannel.class, "comcode").append(" WHERE id=:id and siteId=:siteId ").setParam("siteId", siteId).setParam("id", pid);
		String p_c=super.queryForObject(f_p_c, String.class);
		//如果没有上级部门
		if(StringUtils.isEmpty(p_c)){
			p_c=",";
		}
		
		comcode=p_c+channelId+",";
		
		return comcode;
	}
	
	
	private List<CmsChannel> diguiwrapList(List<CmsChannel> from,List<CmsChannel> tolist,String parentId){
		if(CollectionUtils.isEmpty(from)){
			return null;
		}
		
		for(int i=0;i<from.size();i++){
			CmsChannel m=from.get(i);
			if(m==null){
				continue;
			}
			String pid=m.getPid();
			if((pid==null)&&(parentId!=null)){
				continue;
			}
			if((parentId==m.getPid())||(m.getPid().equals(parentId))){
				List<CmsChannel> leaf=new ArrayList<>();
				m.setLeaf(leaf);
				tolist.add(m);
			    diguiwrapList(from, leaf, m.getId());
			}
		}
		
		return tolist;
	}
	
	@Override
	public <T> T findById(Object id, Class<T> clazz) throws Exception {
		// TODO Auto-generated method stub
		return super.findById(id, clazz);
	}
	
	@Override
	public List<CmsChannel> findListByIdsForTag(List<String> asList,
			String siteId, Map params) throws Exception{
		if(StringUtils.isEmpty(siteId) || asList == null || asList.size() <= 0){
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsChannel.class);
		finder.append(" where id in (:ids) and siteId = :siteId ")
			.setParam("ids", asList).setParam("siteId", siteId);
		
		// 常用参数（表与实体类直接对应的字段）
		/*CmsChannel queryBean = new CmsChannel();
		if(params!=null){
			BeanUtils.populate(queryBean, params);
			super.getFinderWhereByQueryBean(finder, queryBean);
		}*/
		
		List<CmsChannel> channelList = super.queryForList(finder, CmsChannel.class);
		return channelList;
	}

	@Override
	public List<CmsChannel> findListForTag(Map params, Environment env,
			String siteId) throws Exception {
		if(StringUtils.isEmpty(siteId)){
			return null;
		}
		Finder finder = Finder.getSelectFinder(CmsChannel.class);
		finder.append(" where siteId = :siteId ").setParam("siteId", siteId);
		
		
		if(params==null){
			finder.append(" and pid is null ");
			finder.append(getOrderSql(0));
			return super.queryForList(finder, CmsChannel.class, null);
		}
		
		
		// 常用参数（表与实体类直接对应的字段）
		CmsChannel queryBean = new CmsChannel();
		BeanUtils.populate(queryBean, params);
		super.getFinderWhereByQueryBean(finder, queryBean);
		
		// 父类ID集合,不存在时只获取顶级栏目
		String pids = DirectiveUtils.getString("pids", params);
		if(StringUtils.isNotEmpty(pids)){
			List<String> pidList = Arrays.asList(pids.split(CmsChannelListDirective.PARAM_SPLIT));
			finder.append(" and pid in (:pids) ").setParam("pids", pidList);
		}else{
			finder.append(" and pid is null ");
		}
		
		
		
		
		

		// 分页
		Page page = null;
		if(params.get("page") != null){
			StringModel stringModel = (StringModel) params.get("page");
			page = (Page) stringModel.getAdaptedObject(Page.class);
			// 修改分页大小
			Integer pageSize = DirectiveUtils.getInt("pageSize", params);
			if(pageSize!=null){
				page.setPageSize(pageSize);
			}
		}
		
		// 排序
		Integer orderBy = DirectiveUtils.getInt(CmsChannelListDirective.PARAM_ORDER_BY, params);
		if (orderBy == null) {
			orderBy = 0;
		}
		finder.append(getOrderSql(orderBy));
		
		return super.queryForList(finder, CmsChannel.class, page);
	}
	
	/**
	 * 获取排序sql
	 * @param orderBy
	 * @return
	 */
	private String getOrderSql(int orderBy){
		String orderSql = "";
		switch (orderBy) {
		case 0: // 默认排序
			orderSql = " order by sortno ";
			break;
		case 1: // name 名称 升序
			orderSql = " order by name asc ";
			break;
		case 2: // name 名称 降序
			orderSql = " order by name desc ";
			break;
		case 3: // lookcount 打开次数 升序
			orderSql = " order by lookcount asc ";
			break;
		case 4: // lookcount 打开次数 降序
			orderSql = " order by lookcount desc ";
			break;
		case 5: // active 状态 升序
			orderSql = " order by active asc ";
			break;
		case 6: // active 状态 降序
			orderSql = " order by active desc ";
			break;
		case 7: // hasContent 是否有内容 升序
			orderSql = " order by hasContent asc ";
			break;
		case 8: // hasContent 是否有内容 降序
			orderSql = " order by hasContent desc ";
			break;
		default:
			break;
		}
		return orderSql;
	}

	@Override
	public CmsChannel findCmsChannelById(String id, String siteId) throws Exception{
		Finder finder = Finder.getSelectFinder(CmsChannel.class);
		finder.append(" where id=:id and siteId=:siteId ")
			.setParam("id", id).setParam("siteId", siteId);
		return super.queryForObject(finder, CmsChannel.class);
	}
	
}
