package cn.skynethome.redisx.common;

import java.io.Serializable;

public class RedisXCacheBean implements Serializable
{
    
    private static final long serialVersionUID = 7152762855196057784L;

    private String option;
    
    private String type;
    
    private String key;
    
    
    
    public String getOption()
    {
        return option;
    }



    public void setOption(String option)
    {
        this.option = option;
    }



    



    public String getType()
    {
        return type;
    }



    public void setType(String type)
    {
        this.type = type;
    }



    public String getKey()
    {
        return key;
    }



    public void setKey(String key)
    {
        this.key = key;
    }



    public RedisXCacheBean()
    {
        super();
        // TODO Auto-generated constructor stub
    }



    public RedisXCacheBean(String option, String key,String type)
    {
        super();
        this.option = option;
        this.key = key;
        this.type = type;
    }

    
}
