/*   
 * Project: OSMP
 * FileName: OsmpManagedService.java
 * version: V1.0
 */
package com.osmp.config.manager;

import java.util.Dictionary;
import java.util.Enumeration;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import com.osmp.intf.define.service.ConfigService;

public class OsmpManagedService implements ManagedService {
    private final String LOG_SWITCH_NODE = "log_switch";
    private final String MMS_SWITCH_NODE = "mms_switch";
    private final String MAIL_SWITCH_NODE = "mail_switch";

    private ConfigServiceManager serviceManager;
    
    public void setServiceManager(ConfigServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    public void updated(Dictionary properties) throws ConfigurationException {
        if(properties == null || properties.isEmpty()){
            return;
        }
        Enumeration<String> keys = properties.keys();
        if(keys == null) return;
        while(keys.hasMoreElements()){
            String node_key = keys.nextElement();
            Object node_val_obj = properties.get(node_key);
            String node_val = node_val_obj == null ? null : node_val_obj.toString();
            notice(node_key,node_val);
        }
        
    }
    
    private void notice(String key,String value){
        if(serviceManager == null) return;
        switch(key){
            case LOG_SWITCH_NODE:{
                serviceManager.update( ConfigService.LOG_SWITCH, value);
                break;
            }
            case MMS_SWITCH_NODE:{
                serviceManager.update( ConfigService.MMS_SWITCH, value);
                break;
            }
            case MAIL_SWITCH_NODE:{
                serviceManager.update( ConfigService.MAIL_SWITCH, value);
                break;
            }
        }
    }

}
