package org.supercall.controller.sys;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.supercall.common.CommonUtility;
import org.supercall.common.IListPlugin;
import org.supercall.dao.SysConfigMapper;
import org.supercall.domainModel.ResultMessage;
import org.supercall.domainModel.framework.SimpleGridConfigModel;
import org.supercall.models.SysConfig;
import org.supercall.models.SysConfigExample;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RequestMapping("/admin/simplegrid")
@RestController
public class SimpleGridController {
    @Resource
    SysConfigMapper sysConfigMapper;

    SysConfig loadConfig(String uid) {
        SysConfigExample configExample = new SysConfigExample();
        configExample.createCriteria().andUidEqualTo(uid);
        SysConfig configEntity = sysConfigMapper.selectByExampleWithBLOBs(configExample).get(0);
        return configEntity;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    HashMap<String, Object> index(
            HttpServletRequest request,
            String uid) throws ClassNotFoundException {
        SysConfig configEntity = loadConfig(uid);
        SimpleGridConfigModel simpleGridConfigModel = new Gson().fromJson(configEntity.getMetaconfig(), SimpleGridConfigModel.class);
        IListPlugin extendMapper = (IListPlugin) CommonUtility.applicationContext.getBean(Class.forName(simpleGridConfigModel.getEntityName() + "Extend"));
        HashMap<String, Object> map = CommonUtility.buildSimpleList(extendMapper, request,simpleGridConfigModel);
        return map;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    ResultMessage delete(
            HttpServletRequest request,
            String uid,
            Integer id) {
        try {
            SysConfig configEntity = loadConfig(uid);
            SimpleGridConfigModel simpleGridConfigModel = new Gson().fromJson(configEntity.getMetaconfig(), SimpleGridConfigModel.class);
            Object mapper = CommonUtility.applicationContext.getBean(Class.forName(simpleGridConfigModel.getEntityName()));
            mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", Integer.class).invoke(mapper, id);
            return new ResultMessage(true, "", "");
        } catch (Exception ex) {
            return new ResultMessage(false, "", "");
        }
    }
}
