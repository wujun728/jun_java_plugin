package cn.jiangzeyin.database.event;


/**
 * 删除数据事件
 *
 * @author jiangzeyin
 */
public interface RemoveEvent {
    /**
     * 操作成功
     *
     * @param dataId 结果id
     * @author jiangzeyin
     */
    void completeR(long dataId);

    /**
     * 出现异常
     *
     * @param throwable 异常
     * @author jiangzeyin
     */
    void errorR(Throwable throwable);
}
