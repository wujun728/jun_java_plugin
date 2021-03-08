package com.wangxin.common.constants;

import java.util.HashMap;
import java.util.Map;

/** 
 * @Description 常量类
 * @author Wujun
 * @date Apr 24, 2017 12:53:11 PM  
 */
public class Constants {

    /* 分页操作时，每页只显示10条 */
    public static final Integer PAGE_SIZE = 10;
    
    /* session & session key */
    public static final String PERMISSION_SESSION = "permission_session";
    public static final String SESSION_KEY = "session_key";

    /** 删除标识 */
    public final static int IS_DEL_D = 1;/** 已删除 1 */
    public final static int IS_DEL_U = 0; /** 未删除 0 */
    public static final Map<Integer, String> IS_DEL_MAP = new HashMap<Integer, String>();
    public static final Map<Integer, String> DEL_MAP = new HashMap<Integer, String>();
    
    static {
        IS_DEL_MAP.put(IS_DEL_D, "已删除");
        IS_DEL_MAP.put(IS_DEL_U, "未删除");

        DEL_MAP.put(IS_DEL_D, "无效");
        DEL_MAP.put(IS_DEL_U, "有效");
    }
    
    public final static String SALES_ORDER_TYPE_PHY = "01"; //实物订单
    public final static String SALES_ORDER_TYPE_VTU = "02"; //虚拟订单
    public final static String SALES_ORDER_TYPE_EASY= "05"; //轻松购
    public final static String SALES_ORDER_TYPE_CHARGE = "07"; //生活缴费
    public final static String SALES_ORDER_TYPE_RMA = "10"; //退款

}
