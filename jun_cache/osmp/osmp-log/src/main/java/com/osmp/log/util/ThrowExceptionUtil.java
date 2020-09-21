/*   
 * Project: OSMP
 * FileName: ThrowExceptionUtil.java
 * version: V1.0
 */
package com.osmp.log.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowExceptionUtil {
    public static String getStackTrace(Throwable t)  
    {  
        StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);  
      
        try{   
            t.printStackTrace(pw);  
            return sw.toString();  
        }finally{  
            pw.close();  
        }  
    } 
}
