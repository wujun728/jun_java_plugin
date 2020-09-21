/*   
 * Project: OSMP
 * FileName: CustomJsonFieldDesc.java
 * version: V1.0
 */
package com.osmp.http.client.util;

import java.lang.reflect.Field;

import com.alibaba.fastjson.annotation.JSONField;

public class CustomJsonFieldDesc {
    private Field field;
    private JSONField jasonField;
 
    public CustomJsonFieldDesc(Field field, JSONField jasonField) {
        super();
        this.field = field;
        this.jasonField = jasonField;
    }
 
    public Field getField() {
        return field;
    }
 
    public void setField(Field field) {
        this.field = field;
    }
 
    public JSONField getJasonField() {
        return jasonField;
    }
 
    public void setJasonField(JSONField jasonField) {
        this.jasonField = jasonField;
    }
}
