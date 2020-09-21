package cn.skynethome.redisx.Test;

import cn.skynethome.redisx.RedisMasterSlaveUtil;

/**
  * 项目名称:[redisx]
  * 包:[cn.skynethome.redisx]    
  * 文件名称:[TestMasterSlave]  
  * 描述:[一句话描述该文件的作用]
  * 创建人:[陆文斌]
  * 创建时间:[2017年1月3日 上午11:43:53]   
  * 修改人:[陆文斌]   
  * 修改时间:[2017年1月3日 上午11:43:53]   
  * 修改备注:[说明本次修改内容]  
  * 版权所有:luwenbin006@163.com
  * 版本:[v1.0]
 */
public class TestMasterSlave
{
    public static void main(String[] args)
    {

        RedisMasterSlaveUtil.setObject("user",new User("user1","123456"));
        
        
        System.out.println(RedisMasterSlaveUtil.getObject("user",User.class).getName());
        
        RedisMasterSlaveUtil.del("user");
        
        System.out.println(RedisMasterSlaveUtil.getObject("user",User.class));
        

    }

    
}
