import java.util.Random;
import org.junit.Test;

public class TestInverse {
    /**
     * 测试整型数组的逆置
     */
    @Test
    public void testInteger() {
        Integer[] array = new Integer[7];
        Random ra = new Random();
        for (int i = 0; i < 7; i++) {
            array[i] = ra.nextInt(10);
        }
        InverseArray<Integer> ia = new InverseArray<Integer>();
        ia.printArray(array);
        ia.inverse(array);
        ia.printArray(array);
    }
}
