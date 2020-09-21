package cn.jiangzeyin.database.event;


/**
 * 更新数据事件
 *
 * @author jiangzeyin
 */
public interface UpdateEvent {

    /**
     * 操作成功
     *
     * @param dataId id
     * @author jiangzeyin
     */
    void completeU(Object dataId);

    /**
     * 出现异常
     *
     * @param throwable 异常
     * @author jiangzeyin
     */
    void errorU(Throwable throwable);
}
