package com.lhb.lhbauth.jwt.demo.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Wujun
 * @description
 * @date 2018/12/25 0025 10:23
 */
public class RandomUtil {


    public static String randomString(int strLength) {
        Random rnd = ThreadLocalRandom.current();
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < strLength; i++) {
                ret.append(Integer.toString(rnd.nextInt(10)));
        }
        return ret.toString();
    }


}
