package com.jun.plugin.resources.convert.impl;

import com.jun.plugin.resources.convert.Convert;

/**
 * Created by Hong on 2017/12/27.
 */
public class FloatConvert implements Convert<Float> {

    @Override
    public Float convert(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Number){
            return ((Number) obj).floatValue();
        } else {
            return Float.valueOf(String.valueOf(obj));
        }
    }
}
