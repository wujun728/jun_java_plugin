package org.ws.httphelper.common;

import java.util.Comparator;

/**
 * Created by Administrator on 15-12-14.
 */
public class MapKeyComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer integer, Integer t1) {
        return (integer - t1);
    }
}
