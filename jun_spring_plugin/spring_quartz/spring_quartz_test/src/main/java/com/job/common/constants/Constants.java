package com.job.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wujun
 *
 */
public class Constants {

    /* 分页操作时，每页只显示10条 */
    public static final Integer PAGE_SIZE = 10;

    public static final String TRIGGERNAME = "triggerName";
    public static final String TRIGGERGROUP = "triggerGroup";
    public static final String STARTTIME = "startTime";
    public static final String ENDTIME = "endTime";
    public static final String REPEATCOUNT = "repeatCount";
    public static final String REPEATINTERVEL = "repeatInterval";

    public static final Map<String, String> status = new HashMap<String, String>();
    static {
        status.put("ACQUIRED", "运行中");
        status.put("PAUSED", "暂停中");
        status.put("WAITING", "等待中");
    }

}
