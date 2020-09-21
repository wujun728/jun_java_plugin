/*   
 * Project: OSMP
 * FileName: UpdateNoValConfigServiceAdapter.java
 * version: V1.0
 */
package com.osmp.intf.define.service.adapter;

import java.util.Map;

import com.osmp.intf.define.service.ConfigService;
/**
 * 无携带更新值服务适配器
 * @author heyu
 *
 */
public abstract class UpdateNoValConfigServiceAdapter implements ConfigService {

    @Override
    public abstract void update(String target);
    
    @Override
    public void update(String target,String value){}

    @Override
    public Object getData(String target, Map<String, Object> args) {
        return null;
    }

}
