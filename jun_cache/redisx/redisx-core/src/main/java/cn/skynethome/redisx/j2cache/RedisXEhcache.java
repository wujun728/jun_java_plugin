package cn.skynethome.redisx.j2cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisXEhcache implements CacheExpiredListener
{

    private static Logger logger = LoggerFactory.getLogger(RedisXEhcache.class);
    
    private CacheProvider ehcache = null;
    private Properties properties = null;
    


    public RedisXEhcache()
    {
        super();
        init();
    }
    
    
    private void init()
    {
        ehcache = new EhCacheProvider();
        properties = new Properties();
        
        String server_home = System.getProperty("server.home");

        InputStream in = null;
        
        if (!StringUtils.isEmpty(server_home))
        {

            String urlStr = "";

            urlStr = server_home + File.separator + "conf" + File.separator
                    + "properties/redisxCache.properties";
            
            try
            {
                in = new FileInputStream(new File(urlStr));
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/redisxCache.properties");
                       
        }
        
        try
        {
            properties.load(in);
            
            if (in != null)
            {
                in.close();
            }
        }
        catch (IOException e)
        {
            logger.error("error!", e);
        }
        
        ehcache.start(properties);
    }
    
    public final Cache getCache(String cache_name, boolean autoCreate)
    {
        return ehcache.buildCache(cache_name, autoCreate, this);

    }

  /*  private final Properties getProviderProperties(Properties props, CacheProvider provider) {
        Properties new_props = new Properties();
        Enumeration<Object> keys = props.keys();
        String prefix = provider.name() + '.';
        while(keys.hasMoreElements()){
            String key = (String)keys.nextElement();
            if(key.startsWith(prefix))
                new_props.setProperty(key.substring(prefix.length()), props.getProperty(key));
        }
        return new_props;
    }*/
    
    @Override
    public void notifyElementExpired(String region, Object key)
    {
        // TODO Auto-generated method stub

    }

}
