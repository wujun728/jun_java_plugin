/*   
 * Project: OSMP
 * FileName: LogConfigService.java
 * version: V1.0
 */
package com.osmp.log.core;

import com.osmp.intf.define.service.adapter.UpdateWithValConfigServiceAdapter;
import com.osmp.log.pool.LogPool;

/**
 * 日志运行状态更新服务
 * @author heyu
 *
 */
public class LogConfigService extends UpdateWithValConfigServiceAdapter {
    
    public void update(String target,String value) {
        if(LOG_SWITCH.equals(target)){
            //off为关闭日志,on开启
            if("off".equals(value)){
                LogPool.canLog = false;
            }else{
                LogPool.canLog = true;
            }
        }
    }

}
