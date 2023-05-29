package com.jun.plugin.resources.convert.impl;

import com.jun.plugin.resources.convert.Convert;

/**
 * Created by Hong on 2017/12/27.
 */
public class IntegerConvert implements Convert<Integer> {

    @Override
    public Integer convert(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Number){
            return ((Number) obj).intValue();
        } else {
            return Integer.valueOf(String.valueOf(obj));
        }
    }
}
