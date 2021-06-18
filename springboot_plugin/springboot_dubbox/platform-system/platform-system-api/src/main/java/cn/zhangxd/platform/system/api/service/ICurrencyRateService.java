package cn.zhangxd.platform.system.api.service;


import java.util.Map;

/**
 * 汇率接口
 *
 * @author Wujun
 */
@FunctionalInterface
public interface ICurrencyRateService {

    /**
     * 获得当前USD->币种汇率
     *
     * @return Map rate
     */
    Map<String, String> getRate();

}
