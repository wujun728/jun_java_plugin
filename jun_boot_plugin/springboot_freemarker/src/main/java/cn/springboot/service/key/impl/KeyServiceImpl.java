package cn.springboot.service.key.impl;

import java.util.ArrayList;
import java.util.List;

//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springboot.config.db.table.Key;
import cn.springboot.mapper.key.KeyMapper;
import cn.springboot.service.key.KeyService;

/** 
 * @Description 主键生成
 * @author Wujun
 * @date Apr 12, 2017 1:48:30 PM  
 */
@Service("keyService")
public class KeyServiceImpl implements KeyService {

    @Autowired
    private KeyMapper keyMapper;

    public void setKeyMapper(KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    @Override
    public List<Key> getTableValues(List<Key> keys) {
        List<Key> keyList = new ArrayList<Key>();
        try {
            keyList = keyMapper.getTableValues(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyList;
    }

    @Override
    public List<Key> getTables() {
        List<Key> keyList = new ArrayList<Key>();
        try {
            keyList = keyMapper.getTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyList;
    }
}
