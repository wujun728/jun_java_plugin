package com.jun.plugin.compile.util;

/**
 * 描述: json格式数据返回码
 *<ul>
 *      <li>100 : 用户未登录 </li>
 *      <li>200 : 成功 </li>
 *      <li>300 : 失败 </li>
 * </ul>
 */
public enum JsonReturnCode {

    NOT_LOGIN("401","未登录"),
    SUCCESS ("200","成功"),
    FAIL ("500","内部失败"),
	ACCESS_ERROR ("403","禁止访问"),
	NOT_FOUND ("404","页面未发现");
    private String code;
    private String desc;

    JsonReturnCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
