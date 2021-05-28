package org.typroject.tyboot.api.face.systemctl.enumeration;

public enum OperateType {


    /**
     * 点赞
     */
    LIKE("点赞"),

    /**
     * 查看
     */
    VIEW("查看"),


    /**
     * 评论
     */
    COMMENT("评论"),

    /**
     * 收藏
     */
    COLLECT("收藏"),

    /**
     * 查看人数
     */
    USER_VIEWED("查看人数");


    private String operateName;

    OperateType(String operateName)
    {
        this.operateName = operateName;
    }


    public String getOperateName() {
        return operateName;
    }
}
