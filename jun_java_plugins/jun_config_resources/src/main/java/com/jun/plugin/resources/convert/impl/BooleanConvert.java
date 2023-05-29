package com.jun.plugin.resources.convert.impl;

import com.jun.plugin.resources.convert.Convert;

/**
 * Created by Hong on 2017/12/27.
 */
public class BooleanConvert implements Convert<Boolean> {

    @Override
    public Boolean convert(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Boolean){
            return (Boolean) obj;
        } else {
            return Boolean.valueOf(String.valueOf(obj));
        }
    }
}
