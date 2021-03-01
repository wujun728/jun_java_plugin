import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {


    public static void main(String[] args) {
        int i=8;
        System.out.println(i++);

        int j = 7;
        j+=1;
        System.out.println(j);

        int k =9;
        System.out.println(++k);
    }
}
