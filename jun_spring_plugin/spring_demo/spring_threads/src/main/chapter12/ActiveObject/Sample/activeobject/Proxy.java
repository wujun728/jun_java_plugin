package ActiveObject.Sample.activeobject;

class Proxy implements ActiveObject {
    private final SchedulerThread scheduler;
    private final Servant servant;
    public Proxy(SchedulerThread scheduler, Servant servant) {
        this.scheduler = scheduler;
        this.servant = servant;
    }
    public Result makeString(int count, char fillchar) {
        FutureResult future = new FutureResult();
        scheduler.invoke(new MakeStringRequest(servant, future, count, fillchar));
        return future;
    }
    public void displayString(String string) {
        scheduler.invoke(new DisplayStringRequest(servant, string));
    }
}
