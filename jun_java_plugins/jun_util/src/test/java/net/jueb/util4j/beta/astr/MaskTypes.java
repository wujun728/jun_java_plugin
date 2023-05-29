package net.jueb.util4j.beta.astr;
/**
 * 地图掩码意义
 * @author liuyuhua
 *
 */
public class MaskTypes {
	
	/**路径中 0 为可以通过*/
	public final static byte PATH_PASS            = 0;
	
	/**路径中 1 为障碍*/
	public final static byte  PATH_BARRIER        = 1;
	
	/**路径中 2 为半透明*/
	public  final static byte PATH_TRANSLUCENCE   = 2;	
	
	/** 路径中 3 为摆摊位*/
	public final static byte  PATH_BOOTH	      = 3;

}