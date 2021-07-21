package Balking.A4;

import java.util.LinkedList;

public class RequestQueue {
    private static final long TIMEOUT = 30000;
    private final LinkedList queue = new LinkedList();
    public synchronized Request getRequest() {      
        long start = System.currentTimeMillis(); // 开始时间
        while (queue.size() <= 0) {                 
            long now = System.currentTimeMillis(); //现在时间
            long rest = TIMEOUT - (now - start); //剩下时间
            if (rest <= 0) {
                throw new LivenessException("thrown by " + Thread.currentThread().getName());
            }
            try {                                   
                wait(rest);
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
