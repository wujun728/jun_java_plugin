package org.lxh.tagdemo ;
import javax.servlet.jsp.tagext.* ;
public class BodyIterateTagExtraInfo extends TagExtraInfo {
	public VariableInfo[] getVariableInfo(TagData data){
		return new VariableInfo[] {new VariableInfo(data.getId(),"java.lang.String",true,VariableInfo.NESTED)} ;
	}
}