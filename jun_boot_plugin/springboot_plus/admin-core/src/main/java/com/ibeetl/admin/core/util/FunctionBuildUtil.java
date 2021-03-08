package com.ibeetl.admin.core.util;

import java.util.ArrayList;
import java.util.List;

import com.ibeetl.admin.core.entity.CoreFunction;
import com.ibeetl.admin.core.rbac.tree.FunctionItem;

/**
 * 创建一个功能树，用于前端选择
 * @author Wujun
 *
 */
public class FunctionBuildUtil {
	
	private  FunctionBuildUtil(){
		
	}
	public static  FunctionItem buildOrgTree(List<CoreFunction> list){
		CoreFunction root = new CoreFunction();
		root.setId(0L);
		FunctionItem rootOrg = new  FunctionItem(root);
		buildTreeNode(rootOrg,list);
		return rootOrg;
	}
	
	private static void buildTreeNode(FunctionItem parent,List<CoreFunction> list){
		
		long id = parent.getId();
		List<CoreFunction> dels = new ArrayList<>();
		for(CoreFunction SysFunction:list){
			if(SysFunction.getParentId()!=null && SysFunction.getParentId()==id){
				FunctionItem item = new FunctionItem(SysFunction);
				item.setParent(parent);
				dels.add(SysFunction);
			}
		}
		list.removeAll(dels);
		
		if(list.isEmpty()){
			return ;
		}
		for(FunctionItem child:parent.getChildren()){
			buildTreeNode(child,list);
		}
		
	}
}
