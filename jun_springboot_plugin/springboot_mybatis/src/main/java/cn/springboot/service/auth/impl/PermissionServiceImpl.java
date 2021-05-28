package cn.springboot.service.auth.impl;

import cn.springboot.common.exception.BusinessException;
import cn.springboot.config.shiro.vo.PermissionVo;
import cn.springboot.config.table.FactoryAboutKey;
import cn.springboot.config.table.TablesPKEnum;
import cn.springboot.mapper.auth.PermissionMapper;
import cn.springboot.model.auth.Permission;
import cn.springboot.service.auth.PermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    private PermissionVo convertToVo(Permission per) {
        PermissionVo pvo = new PermissionVo();
        pvo.setId(per.getId());
        pvo.setName(per.getName());
        pvo.setCssClass(per.getCssClass());
        pvo.setUrl(per.getUrl());
        pvo.setKey(per.getKey());
        pvo.setParentKey(per.getParentKey());
        pvo.setHide(per.getHide());
        pvo.setLev(per.getLev());
        pvo.setSort(per.getSort());
        return pvo;
    }

    @Override
    public List<PermissionVo> getPermissions(String userId) {
        List<Permission> permissions = permissionMapper.findPermissionByUserId(userId);

        if (CollectionUtils.isEmpty(permissions)) {
            return null;
        }

        if (CollectionUtils.isNotEmpty(permissions)) {

            Map<String, PermissionVo> oneMap = new LinkedHashMap<String, PermissionVo>();// 一级菜单
            Map<String, PermissionVo> twoMap = new LinkedHashMap<String, PermissionVo>();// 二级菜单
            Map<String, PermissionVo> threeMap = new LinkedHashMap<String, PermissionVo>();// 三级菜单

            String parentKey = null, key = null;
            Integer lev = null;
            PermissionVo child = null, parent = null, permissionVo = null;
            for (Permission p : permissions) {
                key = p.getKey();
                lev = p.getLev();
                permissionVo = convertToVo(p);
                if (1 == lev) {// 判断是不是模块
                    oneMap.put(key, permissionVo);
                }
                if (2 == lev) {// 判断是不是菜单分类
                    twoMap.put(key, permissionVo);
                }
                if (3 == lev) {// 判断是不是菜单
                    threeMap.put(key, permissionVo);
                }
            }

            List<PermissionVo> vos = null;

            // 迭代所有3级菜单， 把3级菜单挂在2级菜单分类下面去
            for (Entry<String, PermissionVo> vo : threeMap.entrySet()) {
                child = vo.getValue();// 3级菜单
                parentKey = child.getParentKey();// 获取3级菜单对应的2级菜单KEY，即父节点KEY
                if (twoMap.containsKey(parentKey)) {// 校验当前拿到的2级菜单KEY在twoMap集合中有没有
                    parent = twoMap.get(parentKey);// 获取对应的2级菜单

                    vos = parent.getChildren();// 获取2级菜单下3级菜单集合
                    if (CollectionUtils.isEmpty(vos)) {
                        vos = new ArrayList<>();
                    }
                    vos.add(child);// 将3级菜单挂在2级菜单下去
                    parent.setChildren(vos);
                    twoMap.put(parentKey, parent);
                }
            }

            // 迭代所有2级菜单， 把2级菜单挂在1级菜单分类下面去
            for (Entry<String, PermissionVo> vo : twoMap.entrySet()) {
                child = vo.getValue();// 2级菜单
                parentKey = child.getParentKey();// 获取2级菜单对应的1级菜单KEY，即父节点KEY
                if (oneMap.containsKey(parentKey)) {// 校验当前拿到的1级菜单KEY在oneMap集合中有没有
                    parent = oneMap.get(parentKey);// 获取对应的1级菜单

                    vos = parent.getChildren();// 获取1级菜单下2级菜单集合
                    if (CollectionUtils.isEmpty(vos)) {
                        vos = new ArrayList<PermissionVo>();
                    }
                    vos.add(child);// 将2级菜单挂在1级菜单下去
                    parent.setChildren(vos);
                    oneMap.put(parentKey, parent);
                }
            }

            List<PermissionVo> permissionVos = new ArrayList<PermissionVo>();
            permissionVos.addAll(oneMap.values());
            return permissionVos;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void addPermission(Permission permission) {
        if (permission == null || StringUtils.isBlank(permission.getKey()) || StringUtils.isBlank(permission.getName())) {
            throw new BusinessException("permission-fail","## 创建菜单出错；菜单项数据不完整，无法进行创建。");
        }
        Permission p = permissionMapper.findPermissionByKey(permission.getKey());
        if(p!=null)
            throw new BusinessException("permission-fail","#创建菜单出错;菜单Key已经存在,key="+permission.getKey());
        permission.setId(FactoryAboutKey.getPK(TablesPKEnum.T_SYS_PERMISSION));
        permissionMapper.insert(permission);
    }

}
