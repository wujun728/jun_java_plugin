package com.github.ghsea.dbtracer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Util {

    public static <K, V> String toString(Map<K, V> map) {
        StringBuffer sb = new StringBuffer();
        if (map.isEmpty()) {
            sb.append("{}");
        } else {
            sb.append("{");
            Set<Entry<K, V>> entrySet = map.entrySet();
            int idx = 0;
            for (Entry<K, V> entry : entrySet) {
                idx++;
                sb.append(entry.getKey()).append(":").append(entry.getValue());
                if (idx < entrySet.size()) {
                    sb.append(",");
                }
            }
            sb.append("}");
        }

        return sb.toString();
    }

    public static <V> String toString(Collection<V> list) {
        StringBuffer sb = new StringBuffer();
        if (list.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[");
            int idx = 0;
            for (V item : list) {
                idx++;
                sb.append(item.toString());
                if (idx < list.size()) {
                    sb.append(";");
                }
            }
            sb.append("]");
        }

        return sb.toString();
    }

    public static String parseTime(Date date) {
        DateFormat format = new SimpleDateFormat("YYYY-YY-MM hh:mm:ss", Locale.CHINA);
        return format.format(date);
    }
    
    public static void main(String[] args){
        System.out.println(parseTime(new Date()));
    }
    
}
