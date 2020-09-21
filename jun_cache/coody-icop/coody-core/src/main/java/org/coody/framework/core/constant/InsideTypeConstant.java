package org.coody.framework.core.constant;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsideTypeConstant {

	public static final List<Class<?>> INSIDE_TYPES=new ArrayList<Class<?>>(Arrays.asList(
			new Class<?>[]{
				String.class,Integer.class,Double.class,Float.class,Boolean.class,Date.class
				}));
}
