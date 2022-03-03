package com.jun.plugin.utils.biz;

import java.util.List;

import com.jun.mis.entity.Resource;

/**
 * jquery-Treeview树形菜单json数据生成工具类
 * @描述：
 *       由于jquery_treeview插件需要的json数据属性格式是固定的，无法用json-lib自动生成，除非实体类属性与之对应
 *       采用ajax异步加载子节点方式，初始阶段只加载顶级节点
 *       生成的json格式如下：
 *                       {
 *                       "id":"1",           //id
 *                       "text":"aaa",       //显示值
 *                       "value":"1",        //提交值
 *                       "expanded":true,     //是否展开
 *                       "hasChildren":true,   //是否有子节点
 *                       "ChildNodes":[{},{}], //子节点集合
 *                       "showcheck":true,  //是否显示checkbox
 *                       "complete":false   //是否已加载子节点
 *                       } 
 * @author Wujun
 * @createTime   2011-9-1 下午08:50:14
 */
public class TreeViewUtils {
	/**
	 * 为菜单名称添加超链接
	 * @描述：便于为菜单添加执行动作，
	 *       如点击后执行某个action并在target指定目标内显示执行返回的结果
	 * @param name 菜单名称
	 * @param url 链接地址
	 * @param target 链接目标
	 */
	public static String getLinkName(String name,String url,String target){
		StringBuffer buffer = new StringBuffer();
		if(null == url || url.trim().equals("")){
			return name;
		}
		buffer.append("<a href='").append(url).append("'");
		if(null != target && !target.trim().equals("")){
			buffer.append(" target='").append(target).append("'");
		}
		buffer.append(">").append(name).append("</a>");
		return buffer.toString();
	}
	
	/**
	 * 将权限对象转换成Json字符串
	 * @描述：转换后格式如{"id":"u_1","text":"aaa","hasChildren":true}
	 */
	public static String objectToJsonString(Resource resource){
		return objectToJsonString(resource,false);
	}
	/**
	 * 将权限对象转换成Json字符串(重载)
	 * @描述：转换后格式如{"id":"u_1","text":"aaa","hasChildren":true}
	 */
	public static String objectToJsonString(Resource resource,boolean showButton){
		StringBuffer buffer = new StringBuffer();
		String linkName = "";
		if(resource.getUrl().equals("")){
			linkName = resource.getName();
		} else {
			linkName = getLinkName(resource.getName(),resource.getUrl(),"rightFrame");
		}
		buffer.append("{");
		buffer.append("\"id\":\"").append(resource.getId()).append("\",");
		buffer.append("\"text\":\"").append(linkName).append("\"");
		if(showButton){
			if(!resource.isLeaf()){
				buffer.append(",\"hasChildren\":true");
				buffer.append(",\"classes\":\"folder\"");
			} else{
				buffer.append(",\"classes\":\"file\"");
			}
		} else{
			if(!resource.isMenuLeaf()){
				buffer.append(",\"hasChildren\":true");
				buffer.append(",\"classes\":\"folder\"");
			} else{
				buffer.append(",\"classes\":\"file\"");
			}
		}
		/*if(resource.getParent() != null 
		   && resource.getParent().getId() != null 
		   && resource.getParent().getId().longValue() == 1){
			buffer.append(",\"expanded\":\"true\"");
		}*/
		buffer.append("}");
		return buffer.toString();
	}
	/**
	 * 生成子节点的json字符串
	 * @param children 子节点集合
	 */
	public static String gernerateTreeChildJsonString(List<Resource> children){
		if(null == children || children.size() == 0){
			return "";
		}
		StringBuilder buffer = new StringBuilder();
		buffer.append("[");
		int i = 0;
		for(Resource resource : children){
			if(resource.getType() == 0){
				continue;
			}
			if(i > 0){
				buffer.append(",");
			}
			buffer.append(objectToJsonString(resource));
			i++;
		}
		buffer.append("]");
		return buffer.toString();
	}
	
	
	
	public static void main(String[] args) {
		//System.out.println(getLinkName("权限管理","resourceAction!findResources.do","leftFrame"));
		
		/*Resource resource = new Resource();
		resource.setId(1l);
		resource.setName("用户管理");
		resource.setLeaf(false);
		System.out.println(objectToJsonString(resource));*/
		
		/*Resource resource1 = new Resource();
		resource1.setId(1l);
		resource1.setName("用户管理");
		resource1.setLeaf(false);
		Resource resource2 = new Resource();
		resource2.setId(2l);
		resource2.setName("角色管理");
		resource2.setLeaf(false);
		Resource resource3 = new Resource();
		resource3.setId(3l);
		resource3.setName("权限管理");
		resource3.setLeaf(false);
		List<Resource> children = new ArrayList<Resource>();
		children.add(resource1);
		children.add(resource2);
		children.add(resource3);
		System.out.println(gernerateTreeChildJsonString(children));*/
	}
}
