package com.jun.plugin.common.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JUNUtil {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


//    @ResponseBody
//    @RequestMapping(value = "authMenu", method = RequestMethod.GET)
//    public List<Map<String, Object>> authMenu(String roleId) {
//        List<Menu> list = menuService.findMenuByRoleId(roleId);
//        List<Map<String, Object>> child  = getChild("0",list);
//        return child;
//    }
//
//    public List<Map<String,Object>> getChild(String pid , List<Menu> allList){
//        List<Map<String,Object>> childList = new ArrayList<>();//用于保存子节点的list
//        for(Menu ms : allList){
//            if(pid.equals(ms.getParentId())){//判断传入的父id是否等于自身的，如果是，就说明自己是子节点
//                Map<String,Object> map = new HashMap<>();
//                map.put("id",ms.getId());
//                map.put("pId", ms.getParentId());
//                map.put("mate",ms.getName());
//                map.put("hasChildren",false);
//                map.put("ChildNodes", new Object[]{});
//                childList.add(map); //加入子节点
//            }
//        }
//        for(Map<String,Object> map : childList){//遍历子节点，继续递归判断每个子节点是否还含有子节点
//            List<Map<String,Object>> tList = getChild(String.valueOf(map.get("id"))  , allList);
//            if(!tList.isEmpty()){
//                map.put("hasChildren",true);
//            }
//            map.put("ChildNodes" , tList);
//        }
//        return childList;
//    }
}
