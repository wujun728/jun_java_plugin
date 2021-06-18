package com.ibeetl.admin.core.util.beetl;

import java.util.List;

import org.beetl.core.Context;
import org.beetl.core.Function;

import com.ibeetl.admin.core.gen.model.Verify;

/**
 * 格式化校验集合
 */
public class VerifyForamtFunction implements Function {

    @Override
    public String call(Object[] arg0, Context arg1) {
    	
    	StringBuilder sb = new StringBuilder("");
    	
    	if(arg0[0] instanceof List){
    		List<Verify> list = (List)arg0[0];
    		for (int i = 0; i < list.size(); i++) {
    			Verify verify = list.get(i);
    			if(i < list.size() - 1){
    				sb.append(verify.getName()+"|");
    			}else{
    				sb.append(verify.getName());
    			}
			}
    	}
    	
        return sb.toString();
    }

}
