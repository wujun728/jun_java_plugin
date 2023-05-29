package mybatis.mate.dict.entity;

public enum StatusEnum {
    online(0, "在线"),
    offline(1, "离线"),
    unknown(2, "未知");

    private int status;
    private String desc;

    StatusEnum(final int status, final String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static StatusEnum get(int status) {
        StatusEnum[] statusEnums = StatusEnum.values();
        for (StatusEnum se : statusEnums) {
            if (se.status == status) {
                return se;
            }
        }
        return StatusEnum.unknown;
    }

    public String getDesc() {
        return desc;
    }
}
