package com.wangxin.common.constants;

import java.util.HashMap;
import java.util.Map;

import com.wangxin.common.properties.ConstantProperties;

/**
 * @Description 收件配置，RPT_MAIL_ADDRESS_T.MAIL_TYPE对应的值
 * @author Wujun
 * @date Apr 25, 2017 9:39:46 AM
 */
public enum MailAddressEnum {

     sales_order_day_report("template/sales_order_day_report", "运营销售订单统计日报表", "01"), 
     sales_order_month_report("template/sales_order_month_report", "运营销售订单统计月报表", "02"), 
     customer_day_report("template/customer_day_report", "运营用户统计日报表", "03"), 
     customer_month_report("template/customer_month_report", "运营用户统计月报表", "04"), 
     item_report("template/item_report", "运营商品统计报表", "05");

    MailAddressEnum(String url, String name, String value) {
        this.url = url;
        this.name = name;
        this.value = value;
    }

    public static Map<String, String> getMailAddressType() {
        Map<String, String> result = new HashMap<String, String>();
        MailAddressEnum[] array = MailAddressEnum.values();
        if (array != null && array.length > 0) {
            for (MailAddressEnum ma : array) {
                result.put(ma.getValue(), ma.getName());
            }
        }
        return result;
    }

    private String url;
    private String name;
    private String value;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return ConstantProperties.getValue(ConstantProperties.xjhrptdomain) + "/" + this.url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
