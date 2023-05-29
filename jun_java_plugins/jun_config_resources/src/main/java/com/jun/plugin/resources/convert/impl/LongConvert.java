package com.jun.plugin.resources.convert.impl;

import com.jun.plugin.resources.convert.Convert;

/**
 * Created by Hong on 2017/12/27.
 */
public class LongConvert implements Convert<Long> {

    @Override
    public Long convert(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Number){
            return ((Number) obj).longValue();
        } else {
            return Long.valueOf(String.valueOf(obj));
        }
    }
}
