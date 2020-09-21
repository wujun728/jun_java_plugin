package org.coody.framework.jdbc.aspect;

import java.sql.Connection;
import java.util.List;

import org.coody.framework.core.annotation.Around;
import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.core.point.AspectPoint;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.jdbc.annotation.Transacted;
import org.coody.framework.jdbc.container.TransactedThreadContainer;

@AutoBuild
public class TransactedAspect {

	/**
	 * 事物控制
	 * @param wrapper
	 * @return
	 * @throws Throwable
	 */
	@Around(annotationClass=Transacted.class)
	public Object transacted(AspectPoint wrapper) throws Throwable{
		if(TransactedThreadContainer.hasTransacted()){
			return wrapper.invoke();
		}
		try{
			TransactedThreadContainer.writeHasTransacted();
			Object result= wrapper.invoke();
			//提交事物
			List<Connection> connections=TransactedThreadContainer.getConnections();
			if(!StringUtil.isNullOrEmpty(connections)){
				for(Connection conn:connections){
					try{
						conn.commit();
					}catch (Exception e) {
					}
				}
			}
			return result;
		}finally {
			//关闭连接
			List<Connection> connections=TransactedThreadContainer.getConnections();
			TransactedThreadContainer.clear();
			if(!StringUtil.isNullOrEmpty(connections)){
				for(Connection conn:connections){
					try{
						conn.close();
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
	}
}
