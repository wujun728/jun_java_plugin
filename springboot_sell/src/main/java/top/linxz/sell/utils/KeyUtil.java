package top.linxz.sell.utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        System.currentTimeMillis();
        Integer number = random.nextInt(900000) + 1000000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
