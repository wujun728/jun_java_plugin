package cn.jiangzeyin.database.base;


import cn.jiangzeyin.system.SystemDbLog;

/**
 * 写入数据
 *
 * @author jiangzeyin
 */
public abstract class WriteBase<T> extends Base<T> {

    private T data;
    private Throwable throwable;
    private boolean isAsync;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public void setAsync(boolean isAsyn) {
        this.isAsync = isAsyn;
    }

    /**
     * 异步执行
     *
     * @author jiangzeyin
     */
    public abstract void run();

    /**
     * 同步执行
     *
     * @return 结果id
     * @author jiangzeyin
     */
    public abstract long syncRun();

    /**
     * @param data 对应实体
     */
    public WriteBase(T data) {
        // TODO Auto-generated constructor stub
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public WriteBase<T> getWriteBase() {
        return this;
    }

    /**
     * @param t 异常
     * @author jiangzeyin
     */
    @Override
    public void isThrows(Throwable t) {
        // TODO Auto-generated method stub
        if (isAsync()) {
            t.addSuppressed(getThrowable());
            if (isThrows()) {
                throw new RuntimeException(t);
            } else {
                SystemDbLog.getInstance().error("执行数据库操作", t);
            }
        } else {
            super.isThrows(t);
        }
    }

    /**
     * @author jiangzeyin
     */
    @Override
    protected void recycling() {
        // TODO Auto-generated method stub
        super.recycling();
        data = null;
    }
}
