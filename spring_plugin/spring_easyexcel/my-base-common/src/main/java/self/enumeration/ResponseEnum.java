package self.enumeration;

/**
 * 返回逻辑
 */
public enum ResponseEnum {
    /** 预计内的成功 */
    SUCCESS("200",true),
    /** 意料内的失败 */
    FAIL("-200",false);

    private String code;
    private Boolean isSuccess;

    ResponseEnum(String code, Boolean isSuccess) {
        this.code = code;
        this.isSuccess = isSuccess;
    }

    public String getCode() {
        return code;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }
}
