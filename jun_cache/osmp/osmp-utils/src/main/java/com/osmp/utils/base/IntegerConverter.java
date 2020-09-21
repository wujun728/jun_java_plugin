/*   
 * Project: OSMP
 * FileName: IntegerConverter.java
 * version: V1.0
 */
package com.osmp.utils.base;

public class IntegerConverter {
    /**
     * object转化为int
     * @param source 源object
     * @param defaultInt 默认值
     * @return
     */
    public final static Integer object2Integer(Object source,int defaultInt){
        if (source == null)
            return defaultInt;
        try {
            return Integer.valueOf(source.toString());
        } catch (NumberFormatException e) {
        }
        return defaultInt;
    }
}
