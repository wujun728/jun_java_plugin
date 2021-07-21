package ReadWriteLock.A4.readwritelock;

public interface ReadWriteStrategy {
    public abstract Object doRead() throws InterruptedException;
    public abstract void doWrite(Object arg) throws InterruptedException;
}
