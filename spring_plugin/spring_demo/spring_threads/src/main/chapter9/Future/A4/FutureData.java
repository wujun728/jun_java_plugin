package Future.A4;

import java.lang.reflect.InvocationTargetException;

public class FutureData implements Data {
    private RealData realdata = null;       
    private InvocationTargetException exception = null;
    private boolean ready = false;          
    public synchronized void setRealData(RealData realdata) {
        if (ready) {                        
            return;                         
        }
        this.realdata = realdata;           
        this.ready = true;                  
        notifyAll();
    }
    public synchronized void setException(Throwable throwable) {
        if (ready) {
            return;
        }
        this.exception = new InvocationTargetException(throwable);
        this.ready = true;
        notifyAll();
    }
    public synchronized String getContent() throws InvocationTargetException {
        while (!ready) {                    
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        if (exception != null) {
            throw exception;
        }
        return realdata.getContent();       
    }
}
