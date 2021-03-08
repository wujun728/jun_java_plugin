package com.ibeetl.admin.core.util.beetl;

import java.util.List;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ibeetl.admin.core.rbac.tree.FunctionItem;
import com.ibeetl.admin.core.rbac.tree.OrgItem;
import com.ibeetl.admin.core.service.CorePlatformService;

/**
 * 手工构造一个功能树，书上包含模块，功能，按钮
 * @author Wujun
 *
 */
@Component
public class SysFunctionTreeFunction implements Function {

	@Autowired
	CorePlatformService platFormService;
	
	
	public Object call(Object[] paras, Context ctx) {
		FunctionItem tree = platFormService.buildFunction();
		StringBuilder sb = new StringBuilder(256).append("[");
		FunctionItem  root = tree.getChildren().get(0);
		build(sb,root);
		sb.append("]");
		return sb.toString();
		
	}
	
	private void build(StringBuilder sb,FunctionItem item){

		sb.append("{name:'").append(item.getData().getName()).append("',code:'").append(item.getData().getCode());
		sb.append("',id:").append(item.getData().getId());
		if (item.getData().getParentId() == 0){
			sb.append(",open:true");
		}
		List<FunctionItem> list = item.getChildren();
		int size = list.size();
		if(size==0){
			sb.append("}").append("\n");
			return ;
		}
		sb.append(",\n children:[");
		
		for(int i=0;i<size;i++){
			FunctionItem child = list.get(i);
			build(sb,child);
			if(!isLast(i,size)){
				sb.append(",\n");
			}
		}
		
		
		sb.append("]}").append("\n");
	}
	
	private boolean isLast(int index,int size){
		return index==(size-1);
	}

}
