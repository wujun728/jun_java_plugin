package cn.skynethome.redisx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[FileUtil]  
  * 描述:[FileUtil - 文件处理类]
  * 创建人:[陆文斌]
  * 创建时间:[2016年12月5日 下午5:50:54]   
  * 修改人:[陆文斌]   
  * 修改时间:[2016年12月5日 下午5:50:54]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class FileUtil
{

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static Map<String, FileUtil> fileMap = new HashMap<String, FileUtil>();
    private Properties props = new Properties();

    private File file;

    public FileUtil()
    {

    }

    /**
     * 构造函数
     * @param propertiesName 文件名称
     */
    public FileUtil(String propertiesName)
    {

        if (fileMap.containsKey(propertiesName))
        {
            return;
        }

        if (!StringUtils.isEmpty(propertiesName) && propertiesName.toLowerCase().startsWith("classpath:"))
        {
            propertiesName = propertiesName.replaceFirst("classpath:", "");
            logger.info(String.format("read redis config file is classpath: [%s]", propertiesName));
        }
        else
        {
            logger.info(String.format("auto read redis config file is path: [%s]", propertiesName));
        }

        String server_home = System.getProperty("server.home");
        String urlStr = "";
        if (!StringUtils.isEmpty(server_home))
        {
            urlStr = server_home + File.separator + "conf" + File.separator + propertiesName;
            File file = new File(urlStr);
            this.propsLoad(file);
            logger.info(String.format("server.home is set and read redis config file is dir: [%s]", urlStr));
        }
        else
        {

            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName);
            this.propsLoad(in);
            // url = Thread.currentThread().getContextClassLoader()
            // .getResource(propertiesName);
            // if(null == url || "".equals(url))
            // {
            // try
            // {
            // throw new URLException(String.valueOf(url));
            // }
            // catch (URLException e)
            // {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
            // }
            // }
            // urlStr = url.toString();
            // if
            // (System.getProperty("os.name").toLowerCase().indexOf("windows")
            // >= 0)
            // {
            // urlStr = urlStr.replace("file:/", "");
            // urlStr = urlStr.replace("/", "\\");
            //
            // }
            // else
            // {
            // urlStr = urlStr.replace("file:/", "/");
            // //urlStr = urlStr.replace("/", "\\");
            // }
        }

        fileMap.put(propertiesName, this);

    }

    public static String processFileName(String propertiesName)
    {
        if (!StringUtils.isEmpty(propertiesName) && propertiesName.toLowerCase().startsWith("classpath:"))
        {
            propertiesName = propertiesName.replaceFirst("classpath:", "");
        }
        
        return propertiesName;
    }
    
    
    public static boolean remove(String fileName)
    {
        fileName = processFileName(fileName);
        
        if (fileMap.containsKey(fileName))
        {
            fileMap.remove(fileName);
            logger.info(String.format("remove properties...key of [%s]", fileName));
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 构造函数
     * @param file 文件
     */
    public FileUtil(File file)
    {
        this.propsLoad(file);
    }

    /**
     * 辅助方法，根据file创建工具对象
     * @param file
     */
    private void propsLoad(InputStream in)
    {
        try
        {
            props.load(in);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            // System.exit(-1);
            logger.error("error status", e);
        }
        catch (IOException e)
        {
            // System.exit(-1);
            e.printStackTrace();
            logger.error("error status", e);
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (IOException e)
            {
                logger.error("error!", e);
            }
        }
    }

    /**
     * 辅助方法，根据file创建工具对象
     * @param file
     */
    private void propsLoad(File file)
    {
        FileReader reader = null;
        try
        {
            this.file = file;
            reader = new FileReader(file);
            props.load(reader);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            // System.exit(-1);
            logger.error("error status", e);
        }
        catch (IOException e)
        {
            // System.exit(-1);
            e.printStackTrace();
            logger.error("error status", e);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                logger.error("error!", e);
            }
        }
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public String getKeyValue(String key)
    {
        return props.getProperty(key);
    }

    /**
     * 向properties文件写入键值对
     * @param keyName
     * @param keyValue
     */
    public void writeProperties(String keyName, String keyValue)
    {
        Map<String, String> propertiesMap = new HashMap<String, String>();
        propertiesMap.put(keyName, keyValue);
        batchWriteProperties(propertiesMap);
    }

    public static Long getPropertyValueLong(String fileName, String key)
    {
        String valueString = getPropertyValue(fileName, key);
        if (!StringUtils.isEmpty(valueString))
        {
            return Long.valueOf(valueString);
        }
        else
        {
            throw new NullPointerException();
        }
    }
    
    
    public static int getPropertyValueInt(String fileName, String key)
    {
        String valueString = getPropertyValue(fileName, key);
        if (!StringUtils.isEmpty(valueString))
        {
            return Integer.valueOf(valueString);
        }
        else
        {
            throw new NullPointerException();
        }
    }
    

    private static FileUtil getfileUtil(String key)
    {
        FileUtil fileUtil = null;
        String newkey = processFileName(key);
        
        if (fileMap.containsKey(newkey))
        {
            fileUtil = fileMap.get(newkey);

        }
        else
        {
            fileUtil = new FileUtil(key);
        }

        return fileUtil;
    }

    public static boolean getPropertyValueBoolean(String fileName, String key)
    {
        String valueString = getfileUtil(fileName).getKeyValue(key);
        if (!StringUtils.isEmpty(valueString))
        {
            return Boolean.valueOf(valueString);
        }
        else
        {
            return false;
        }
    }

    public static String getPropertyValue(String fileName, String key)
    {
        String valueString = getfileUtil(fileName).getKeyValue(key);

        if (!StringUtils.isEmpty(valueString))
        {
            return valueString;
        }
        else
        {
            return "";
        }
    }

    /**
     * 批量更新properties的键值对
     * @param propertiesMap
     */
    public void batchWriteProperties(Map<String, String> propertiesMap)
    {
        FileWriter writer = null;
        try
        {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            writer = new FileWriter(this.file);
            // OutputStream fos = new FileOutputStream(profilepath);
            for (String key : propertiesMap.keySet())
            {
                this.props.setProperty(key, propertiesMap.get(key));
            }
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            this.props.store(writer, "batch update");
        }
        catch (IOException e)
        {
            logger.error("error status", e);
        }
        finally
        {
            try
            {
                if (writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException e)
            {
                logger.error("error!", e);
            }
        }
    }

    /**
     * 更新properties文件
     * @param keyName 名称
     * @param keyValue 值
     */
    public void updateProperties(String keyName, String keyValue)
    {
        Map<String, String> propertiesMap = new HashMap<String, String>();
        propertiesMap.put(keyName, keyValue);
        this.batchUpdateProperties(propertiesMap);
    }

    /**
     * 批量更新键值对
     * @param propertiesMap
     */
    public void batchUpdateProperties(Map<String, String> propertiesMap)
    {
        BufferedWriter output = null;
        try
        {
            // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
            // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
            // OutputStream fos = new FileOutputStream(profilepath);
            output = new BufferedWriter(new FileWriter(this.file));
            for (String key : propertiesMap.keySet())
            {
                this.props.setProperty(key, propertiesMap.get(key));
            }
            // 以适合使用 load 方法加载到 Properties 表中的格式，
            // 将此 Properties 表中的属性列表（键和元素对）写入输出流
            props.store(output, "batch update ");
        }
        catch (IOException e)
        {
            logger.error("error status", e);
        }
        finally
        {
            try
            {
                if (output != null)
                {
                    output.close();
                }
            }
            catch (IOException e)
            {
                logger.error("error!", e);
            }

        }
    }

}
