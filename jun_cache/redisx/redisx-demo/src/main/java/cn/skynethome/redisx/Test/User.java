package cn.skynethome.redisx.Test;

import java.io.Serializable;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[User]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:44:09]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:44:09]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class User implements Serializable
{
    @Override
    public String toString()
    {
        return "User [name=" + name + ", pwd=" + pwd + "]";
    }

    /**
     * 
     */
    private static final long serialVersionUID = -7887891173075369532L;
    
    private String name;
    private String pwd;
    
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getPwd()
    {
        return pwd;
    }
    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }
    
    public User(String name, String pwd)
    {
        super();
        this.name = name;
        this.pwd = pwd;
    }
    
    

}
