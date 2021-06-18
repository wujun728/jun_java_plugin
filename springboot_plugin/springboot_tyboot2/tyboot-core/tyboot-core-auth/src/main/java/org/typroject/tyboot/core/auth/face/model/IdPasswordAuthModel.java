package org.typroject.tyboot.core.auth.face.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: IdPasswordAuthModel.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: IdPasswordAuthModel.java  Tyrest\magintrursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IdPasswordAuthModel extends AuthModel {
	private static final long serialVersionUID = -5347656325432534L;

	private String password;

}

/*
 * $Log: av-env.bat,v $
 */