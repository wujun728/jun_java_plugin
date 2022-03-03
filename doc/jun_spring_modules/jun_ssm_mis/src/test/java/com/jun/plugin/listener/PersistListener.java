package com.jun.plugin.listener;

import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.event.spi.PersistEvent;
import org.hibernate.event.spi.PersistEventListener;

public class PersistListener implements PersistEventListener {
	private static final long serialVersionUID = 3887442936066323114L;

	public void onPersist(PersistEvent event) throws HibernateException {
		System.out.println(event.getEntityName() + "----->>");
	}

	@SuppressWarnings("rawtypes")
	public void onPersist(PersistEvent event, Map map) throws HibernateException {
		System.out.println(event.getEntityName() + "----->>");
	}

}
