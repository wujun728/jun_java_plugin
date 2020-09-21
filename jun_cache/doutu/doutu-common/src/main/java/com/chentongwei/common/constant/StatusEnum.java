package com.chentongwei.common.constant;

/**
 * 状态常量
 * 
 * @author TongWei.Chen 2017年5月30日00:27:47
 */
public enum StatusEnum implements IBaseEnum {

    /*图片来源*/
    PICTURE_ORIGIN_USER(2, "用户"),

    /*图片作废*/
    PICTURE_OBSOLETE(0, "已作废"),
    PICTURE_NORMAL(1, "正常"),

    /*图片举报*/
	REPORT_WAIT_CHECK_STATUS(1, "待审核"),
	REPORT_PASS_CHECK_STATUS(2, "审核通过"),
	REPORT_REFUSE_CHECK_STATUS(3, "审核拒绝"),

    /*用户*/
    USER_ENABLED(1, "正常"),
    IS_ADMIN(1, "管理员")
	;
	
	//状态码
    private int code;
    //消息
    private String msg;
	
	private StatusEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
	
	
}
