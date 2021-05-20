package SingleThreadedExecution.A6_2;

public class EaterThread extends Thread {
    private String name;
    private final Pair pair;
    public EaterThread(String name, Pair pair) {
        this.name = name;
        this.pair = pair;
    }
    public void run() {
        while (true) {
            eat();
        }
    }
    public void eat() {
        synchronized (pair) {
            System.out.println(name + " takes up " + pair + ".");
            System.out.println(name + " is eating now, yam yam!");
            System.out.println(name + " puts down " + pair + ".");
        }
    }
}
