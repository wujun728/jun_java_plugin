package com.jun.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jun.entity.Resource;

/**
 * 系统权限工具类
 * @author Wujun
 * @createTime   2011-8-22 下午10:31:59
 */
public class ResourceUtils {
	/**
	 * 生成Tree结构的HTML代码块
	 * @param topResourceList 顶级权限的一级子权限集合
	 * @param rootName 页面上根节点UL的id名称
	 * @param isRoot 是否为根节点
	 * @param addHref  是否为节点添加超链接
	 * @param targetName 链接到的frame的name名称
	 */
	public static String getTreeHTML(Collection<Resource> topResourceList,String rootName,boolean isRoot,boolean addHref,String targetName){
		if(topResourceList.size() == 0){
			return "";
		}
		StringBuffer treeHTML = new StringBuffer();
		treeHTML.append(isRoot?"<ul id=\"" + rootName + "\" class=\"filetree\">":"<ul>");
		for(Resource resource:topResourceList){
			treeHTML.append("<li class=\"closed\">");
			treeHTML.append("    <input type=\"checkbox\" id=\"ch_" + resource.getId() + "\" name=\"roleVO.resourceIds\" value=\"" + resource.getId() + "\"/>");
			//treeHTML.append("<label for=\"ch_" + resource.getId()+ "\"><span class=\"folder\">" + resource.getName()+"</span></label>");
			//treeHTML.append("<span class=\"folder\">" + resource.getName()+"</span>");
			treeHTML.append("<label for=\"ch_" + resource.getId()+ "\">");
			if(resource.isLeaf()){
				treeHTML.append("<span class=\"file\">");
			} else{
				treeHTML.append("<span class=\"folder\">");
			}
			/*if(addHref && !GerneralUtil.isNULLOrPropertyEmpty(resource.getUrl())){
				treeHTML.append("<a href=\"").append(resource.getUrl()).append("\"");
				treeHTML.append(" target=\"").append(targetName).append("\">");
			}*/
			treeHTML.append(resource.getName());
			/*if(addHref && !GerneralUtil.isNULLOrPropertyEmpty(resource.getUrl())){
				treeHTML.append("</a>");
			}*/
			treeHTML.append("</span></label>");
			treeHTML.append(getTreeHTML(resource.getChildren(),rootName,false,addHref,targetName));
			treeHTML.append("</li>");
		}
		treeHTML.append("</ul>");
		return treeHTML.toString();
	}
	
	/**
	 * 返回权限树形字符串
	 * @param topresourceCollection  顶级权限集合
	 * @param prefix                   输出权限名称的前缀
	 * @parem list                     权限输出集合
	 * @return
	 */
	private static void getResourceTreesString(Collection<Resource> topResourceCollection,String prefix,List<Resource> list){
		for (Resource resource : topResourceCollection) {
			Resource copy = new Resource();
			copy.setId(resource.getId());
			copy.setName(prefix + "┝" + resource.getName());
			list.add(copy);
			getResourceTreesString(resource.getChildren(),prefix + "　",list);
		}
	}
	
	/**
	 * 根据顶级权限获取所有权限(权限名称经过修改)
	 */
	public static List<Resource> getAllResourceList(List<Resource> topResourceList){
		List<Resource> list = new ArrayList<Resource>();
		getResourceTreesString(topResourceList,"",list);
		return list;
	}
	
	/**
	 * 给定集合中移除指定权限及其子权限
	 */
	public static void removeSomeResourcesAndChildren(List<Resource> resourceList,Resource resource){
		resourceList.remove(resource);
		for (Resource rs : resource.getChildren()) {
			removeSomeResourcesAndChildren(resourceList,rs);
		}
	}
	
	/**
	 * 给定集合中移除指定权限的子权限
	 * @param isfirst true表示不删除当前权限 false表示删除当前权限及其子权限
	 */
	public static void removeSomeResourcesAndChildren(List<Resource> resourceList,Resource resource,boolean isfirst){
		if(!isfirst){
			resourceList.remove(resource);
		}
		for (Resource rs : resource.getChildren()) {
			isfirst = false;
			removeSomeResourcesAndChildren(resourceList,rs);
		}
	}
}
