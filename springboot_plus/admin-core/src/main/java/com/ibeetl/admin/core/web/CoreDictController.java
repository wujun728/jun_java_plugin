package com.ibeetl.admin.core.web;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibeetl.admin.core.entity.CoreDict;
import com.ibeetl.admin.core.service.CoreDictService;
import com.ibeetl.admin.core.service.CorePlatformService;

@Controller
@SuppressWarnings("unchecked")
public class CoreDictController {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final String MODEL = "/core/dict";

    @Autowired
    CorePlatformService platformService;

    @Autowired
    CoreDictService dictService;

 

    /**
     * 查看字典类型对应的列表
     * @param type
     * @return
     */
    @RequestMapping(MODEL + "/view.json")
    @ResponseBody
    public JsonResult<List<CoreDict>> view(String type) {
        List<CoreDict> list = dictService.findAllByType(type);
        return  JsonResult.success(list);
    }
    
    /**
     * 查看字典值的子字典
     * @param value
     * @return
     */
  
    @RequestMapping(MODEL + "/viewChildren.json")
    @ResponseBody
    public JsonResult<List<CoreDict>> viewChild(String group,String value) {
        CoreDict dict = dictService.findCoreDict(group,value);
        List<CoreDict> list = dictService.findChildByParent(dict.getId());
        return  JsonResult.success(list);
    }
    
    

}
