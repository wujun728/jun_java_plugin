package com.jun.plugin.resources.convert.impl;

import com.jun.plugin.resources.convert.Convert;

/**
 * Created by Hong on 2017/12/27.
 */
public class CharConvert implements Convert<Character> {

    @Override
    public Character convert(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Character) {
            return ((Character) obj);
        } else {
            return String.valueOf(obj).toCharArray()[0];
        }
    }
}
