package com.ibeetl.admin.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeetl.admin.core.dao.CoreDictDao;
import com.ibeetl.admin.core.entity.CoreDict;
import com.ibeetl.admin.core.util.enums.DelFlagEnum;

/**
 * 描述: 字典 service，包含常规字典和级联字典的操作。
 * @author Wujun
 */
@Service
@Transactional
public class CoreDictService extends CoreBaseService<CoreDict> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreDictService.class);

    @Autowired
    private CoreDictDao dictDao;

    @Autowired
    CorePlatformService platformService;
    
    @Autowired 
    CoreDictService self ;

 
    /**
     * 根据类型获取字典集合
     * @param type 字典类型，
     * @return List
     */
    @Cacheable(value = CorePlatformService.DICT_CACHE_TYPE)
    public List<CoreDict> findAllByType(String type) {
        return dictDao.findAllList(type);
    }
    
    /**
     * 级联字典查询，必须提供一个字典类型
     * @param group
     * @param value
     * @return
     */
    @Cacheable(value = CorePlatformService.DICT_CACHE_CHILDREN)
    public List<CoreDict> findAllByGroup(String type,String value) {
       List<CoreDict> list = self.findAllByType(type);
       return  _search(list,value);
        
    }
    
    /**
     * 级联字段下一级的字段列表
     * @param parentValue
     * @return
     */
    @Cacheable(value = CorePlatformService.DICT_CACHE_CHILDREN)
    public List<CoreDict> findChildByParent(Long id) {
        return dictDao.findChildByParent(id);
    }

    @Cacheable(value = CorePlatformService.DICT_CACHE_VALUE)
    public CoreDict findCoreDict(String type,String value) {
       List<CoreDict> list = self.findAllByGroup(type, value);
       if(list==null) {
           return null;
       }
       for(CoreDict dict:list) {
           if(dict.getValue().equals(value)) {
               return dict;
           }
       }
 	  
 	   return null;
    }
    
    /*通过名字反向查找数据字典，通常用于excel导入*/
    public Map<String,CoreDict> mapDictByName(String type){
        List<CoreDict> list = self.findAllByType(type);
        Map<String,CoreDict> map = new HashMap<String,CoreDict>();
        for(CoreDict dict:list) {
            map.put(dict.getName(), dict);
        }
        return  map;
    }
    
   
    
   /*递归查找*/ 
    private List<CoreDict> _search(List<CoreDict> list,String value) {
        for(CoreDict dict:list) {
            if(dict.getValue().equals(value)) {
                return list;
            }else {
                List<CoreDict> children = findChildByParent(dict.getId());
                if(children.isEmpty()) {
                    continue;
                }else {
                    List<CoreDict> ret = _search(children,value);
                    if(ret!=null) {
                        return ret;
                    }
                }
                
            }
        }
        return null;
    }

   
    /**
     * 查询字段类型列表
     * @return
     */
    public List<Map<String, String>> findTypeList() {
        return dictDao.findTypeList(DelFlagEnum.NORMAL.getValue());
    }

   

    
  
}
