/*   
 * Project: OSMP
 * FileName: LongConverter.java
 * version: V1.0
 */
package com.osmp.utils.base;

public class LongConverter {
    /**
     * object转化为long
     * @param source 源object
     * @param defaultLong 默认值
     * @return
     */
    public final static Long object2Long(Object source,long defaultLong){
        if (source == null)
            return defaultLong;
        try {
            return Long.valueOf(source.toString());
        } catch (NumberFormatException e) {
        }
        return defaultLong;
    }
}
