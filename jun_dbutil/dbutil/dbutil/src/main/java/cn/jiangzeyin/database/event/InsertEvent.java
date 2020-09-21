package cn.jiangzeyin.database.event;


/**
 * 添加数据事件
 *
 * @author jiangzeyin
 */
public interface InsertEvent {
    /**
     *
     */
    enum BeforeCode {
        CONTINUE(0, "继续", 0), END(1, "结束", -100);

        BeforeCode(int code, String desc, int resultCode) {
            this.code = code;
            this.desc = desc;
            this.resultCode = resultCode;
        }

        private int code;
        private String desc;
        private int resultCode;

        public int getResultCode() {
            return resultCode;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    /**
     * 开始事件之前
     *
     * @return 验证结果
     * @author jiangzeyin
     */
    int beforeI(Object object);

    /**
     * 操作成功
     *
     * @param dataId 结果id
     * @author jiangzeyin
     */
    void completeI(long dataId);

    /**
     * 出现异常
     *
     * @param throwable 异常
     * @author jiangzeyin
     */
    void errorI(Throwable throwable);
}
