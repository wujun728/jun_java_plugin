package chapter4;

/**
 * Created by 13 on 2017/5/6.
 */
public class DeadLock extends Thread {
    protected Object tool;
    static Object fork1 = new Object();
    static Object fork2 = new Object();

    public DeadLock(Object object) {
        this.tool = object;

        if (tool == fork1) {
            this.setName("��ѧ��A");
        }
        if (tool == fork2) {
            this.setName("��ѧ��B");
        }
    }

    public void run() {
        if (tool == fork1) {
            synchronized (fork1) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (fork2) {
                    System.out.println("��ѧ��A��ʼ�Է���");
                }
            }
        }
        if (tool == fork2) {
            synchronized (fork2) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (fork1) {
                    System.out.println("��ѧ��B��ʼ�Է���");
                }
            }
        }
    }


    public static void main(String args[]) throws InterruptedException {
        DeadLock ddA = new DeadLock(fork1);
        DeadLock ddB = new DeadLock(fork2);

        ddA.start();
        ddB.start();

        Thread.sleep(1000);
    }
}
