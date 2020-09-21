/*   
 * Project: OSMP
 * FileName: DataConfigServiceAdapter.java
 * version: V1.0
 */
package com.osmp.intf.define.service.adapter;

import java.util.Map;

import com.osmp.intf.define.service.ConfigService;
/**
 * 获取数据服务适配器
 * @author heyu
 *
 */
public abstract class DataConfigServiceAdapter implements ConfigService {

    @Override
    public void update(String target) {}
    
    @Override
    public void update(String target,String value){}
    
    @Override
    public abstract Object getData(String target, Map<String, Object> args);

}
