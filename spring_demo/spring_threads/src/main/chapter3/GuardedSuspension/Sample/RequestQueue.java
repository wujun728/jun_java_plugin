package GuardedSuspension.Sample;

import java.util.LinkedList;

public class RequestQueue {
    private final LinkedList queue = new LinkedList();
    public synchronized Request getRequest() {
        while (queue.size() <= 0) {
            try {                                   
                wait();
            } catch (InterruptedException e) {      
            }                                       
        }                                           
        return (Request)queue.removeFirst();
    }
    public synchronized void putRequest(Request request) {
        queue.addLast(request);
        notifyAll();
    }
}
