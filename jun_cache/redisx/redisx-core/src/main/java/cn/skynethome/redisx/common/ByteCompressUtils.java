package cn.skynethome.redisx.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;



/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx.common]    
  * 文件名称:[ByteCompressUtils]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:36:01]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:36:01]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class ByteCompressUtils
{
    
    /***
     * 压缩GZip
     * 
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data)
    {
        byte[] b = null;
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(bos);
            gzip.write(data);
            gzip.finish();
            gzip.close();
            b = bos.toByteArray();
            bos.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return b;
    }

    /***
     * 解压GZip
     * 
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data)
    {
        byte[] b = null;
        try
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1)
            {
                baos.write(buf, 0, num);
            }
            b = baos.toByteArray();
            baos.flush();
            baos.close();
            gzip.close();
            bis.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return b;
    }
    
   

}
