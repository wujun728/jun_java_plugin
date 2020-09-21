/*   
 * Project: OSMP
 * FileName: UpdateWithValConfigServiceAdapter.java
 * version: V1.0
 */
package com.osmp.intf.define.service.adapter;

import java.util.Map;

import com.osmp.intf.define.service.ConfigService;
/**
 * 携带更新值服务适配器
 * @author heyu
 *
 */
public abstract class UpdateWithValConfigServiceAdapter implements ConfigService {

    @Override
    public void update(String target) {}

    @Override
    public abstract void update(String target, String value);

    @Override
    public Object getData(String target, Map<String, Object> args) {
        return null;
    }

}
