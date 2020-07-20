package klg.common.utils;

/** 
 * @see 获取操作系统名称 
 * @author Herman.Xiong 
 * @date 2014年4月22日 13:36:14 
 * @version V1.0 
 * @since jdk 1.6 
 */  
public class OSUtil {  
    private static final boolean osIsMacOsX;  
    private static final boolean osIsWindows;  
    private static final boolean osIsWindowsXP;  
    private static final boolean osIsWindows2003;  
    private static final boolean osIsWindowsVista;  
    private static final boolean osIsLinux;  
    private static final boolean osIsWindowsWin7;  
    private static final boolean osIsWindowsWin8;  
  
    static {  
        String os = System.getProperty("os.name");  
        if (os != null)  
            os = os.toLowerCase();  
        osIsMacOsX = "mac os x".equals(os);  
        osIsWindows = os != null && os.indexOf("windows") != -1;  
        osIsWindowsXP = "windows xp".equals(os);  
        osIsWindows2003 = "windows 2003".equals(os);  
        osIsWindowsVista = "windows vista".equals(os);  
        osIsLinux = os != null && os.indexOf("linux") != -1;  
        osIsWindowsWin7 = os !=null && os.indexOf("windows 7") != -1;  
        osIsWindowsWin8 = os !=null && os.indexOf("windows 8") != -1;  
    }  
  
    public static boolean isMacOSX() {  
        return osIsMacOsX;  
    }  
  
    public static boolean isWindows() {  
        return osIsWindows;  
    }  
  
    public static boolean isWindowsXP() {  
        return osIsWindowsXP;  
    }  
  
    public static boolean isWindows2003() {  
        return osIsWindows2003;  
    }  
  
    public static boolean isWindowsVista() {  
        return osIsWindowsVista;  
    }  
  
    public static boolean isLinux() {  
        return osIsLinux;  
    }  
  
    public static boolean IsWindowsWin7(){  
        return osIsWindowsWin7;  
    }  
      
    public static boolean IsWindowsWin8(){  
        return osIsWindowsWin8;  
    }  
}  
