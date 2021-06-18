package ReadWriteLock.A4.readwritelock;

public interface GuardStrategy {
    public abstract void beforeReadWait();
    public abstract boolean readGuard();
    public abstract void afterReadWait();
    public abstract void beforeDoRead();
    public abstract void afterDoRead();

    public abstract void beforeWriteWait();
    public abstract boolean writeGuard();
    public abstract void afterWriteWait();
    public abstract void beforeDoWrite();
    public abstract void afterDoWrite();
}
