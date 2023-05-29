package com.jun.plugin.resources.convert.impl;

import com.jun.plugin.resources.convert.Convert;

/**
 * Created by Hong on 2017/12/27.
 */
public class DoubleConvert implements Convert<Double> {

    @Override
    public Double convert(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Number){
            return ((Number) obj).doubleValue();
        } else {
            return Double.valueOf(String.valueOf(obj));
        }
    }
}
