package com.erp.listener;

import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;

public class PostUpdate implements PostUpdateEventListener
{
	private static final long serialVersionUID = 4682862230739354180L;
	public void onPostUpdate(PostUpdateEvent event )
	{
//		String fileRealDir = ServletActionContext.getRequest().getSession().getServletContext().getRealPath(File.separator);
//		System.out.println(fileRealDir);
	}
}
