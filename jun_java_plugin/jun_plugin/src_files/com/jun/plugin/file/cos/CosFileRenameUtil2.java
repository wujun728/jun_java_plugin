package com.jun.plugin.file.cos;

import java.io.File;  
import java.util.Date;  
  
import com.oreilly.servlet.multipart.FileRenamePolicy;  
  
/** 
 * 文件重命名 
 * @author MuLing 
 * 
 */  
public class CosFileRenameUtil2 implements FileRenamePolicy {  
  
    public File rename(File file) {  
        String body = "";  
        String ext = "";  
        Date date = new Date();  
        int pot = file.getName().lastIndexOf(".");//取得文件名和后缀分割点  
        if (pot != -1) {//说明后缀存在  
            body = date.getTime() + "";//采用时间的形式命名  
            ext = file.getName().substring(pot);//截取后缀名  
        } else {  
            body = (new Date()).getTime() + "";  
            ext = "";  
        }  
        String newName = body + ext;  
        file = new File(file.getParent(), newName);//对文件进行重命名  
        return file;  
  
    }  
  
}  