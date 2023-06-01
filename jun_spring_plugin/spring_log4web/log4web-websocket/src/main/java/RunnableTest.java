import java.util.Date;

/**
 * Created by wenbin on 2017/6/28.
 */
public class RunnableTest implements Runnable {
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                System.out.println(new Date());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start(){
        new Thread(this).start();
    }
}
