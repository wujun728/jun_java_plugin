import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Program  : Test.java
 * Author   : liuyh
 * Create   : 2010-12-13 上午11:36:01
 *
 * Copyright 2010 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with Embedded Internet Solutions Inc.
 *
 */

/**
 * 
 * @author liuyh
 * @version 1.0.0
 * @2010-12-13 上午11:36:01
 */
public class Test {
	public static void main(String[] args) {
		   Configuration cfg = new Configuration().configure(); 
		   SchemaExport export = new SchemaExport(cfg); 
		   export.create(true, true); 
		   System.out.println("已完成");
	}

}

