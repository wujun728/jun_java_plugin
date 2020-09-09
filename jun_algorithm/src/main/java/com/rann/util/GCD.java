package com.rann.util;

/**
 * Created by taoxiaoran on 16/3/31.
 */

// 公约数表示为f(a,b),并且有a>=b>0
// 欧几里德就给了我们一个很好的定理，f(a,b)=f(b,a%b)。
public class GCD {
    public int getGCD(int a, int b) {
        if (b > a) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (b == 0) {
            return a;
        }

        return getGCD(b, a % b);
    }
}
