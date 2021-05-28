package org.typroject.tyboot.core.restful.limit;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;


/**
 * 请求频次定义
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Frequency {

    /**
     * 周期单位
     */
    private TimeUnit timeUnit;



    /**
     * 时间周期长度
     */
    private Long  period;


    /**
     *限制请求数量
     */
    private Long quantity;
}
