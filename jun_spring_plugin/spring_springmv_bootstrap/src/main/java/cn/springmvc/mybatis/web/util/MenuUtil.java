package cn.springmvc.mybatis.web.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.springmvc.mybatis.common.constants.Constants;
import cn.springmvc.mybatis.entity.auth.Permission;
import cn.springmvc.mybatis.web.vo.PermissionVo;

/**
 * 菜单工具类
 * 
 * @author Wujun
 *
 */
public class MenuUtil implements Serializable {

    private static final long serialVersionUID = -2205059367852213007L;

    public static final String leaveBill = "leaveBill";
    public static final String leaveBill_home = "home";

    public static final String workflow = "workflow";
    public static final String workflow_deployHome = "deployHome";
    public static final String workflow_listTask = "listTask";

    public static final String user = "user";
    public static final String useradd = "register";
    public static final String upload = "upload";
    public static final String ajaxupload = "ajaxupload";
    public static final String springupload = "springupload";
    public static final String import_excel = "importexcel";
    public static final String download = "download";
    public static final String zipupload = "zipupload";
    public static final String search = "search";
    public static final String jquery_search = "jquerysearch";
    public static final String news = "news";
    public static final String newsadd = "newsadd";
    public static final String news_search = "newssearch";

    public static List<Permission> importPermissionData() {
        List<Permission> permis = new ArrayList<Permission>();

        permis.add(initPermission("业务管理", leaveBill, Constants.STATUS_VALID, null, 1, leaveBill));
        permis.add(initPermission("请假管理", leaveBill_home, Constants.STATUS_VALID, "activiti/home", 2, leaveBill));

        permis.add(initPermission("流程管理", workflow, Constants.STATUS_VALID, null, 3, workflow));
        permis.add(initPermission("部署管理", workflow_deployHome, Constants.STATUS_VALID, "activiti/deployHome", 4, workflow));
        permis.add(initPermission("任务管理", workflow_listTask, Constants.STATUS_VALID, "activiti/listTask", 5, workflow));

        permis.add(initPermission("文件上传", upload, Constants.STATUS_VALID, null, 7, upload));
        permis.add(initPermission("ajax上传文件", ajaxupload, Constants.STATUS_VALID, "upload/ajax", 8, upload));
        permis.add(initPermission("spring上传文件", springupload, Constants.STATUS_VALID, "upload/spring", 9, upload));
        permis.add(initPermission("Excel导入", import_excel, Constants.STATUS_VALID, "import/index", 10, upload));

        permis.add(initPermission("文件下载", download, Constants.STATUS_VALID, null, 101, download));
        permis.add(initPermission("zip下载", zipupload, Constants.STATUS_VALID, "download/zip", 102, download));

        permis.add(initPermission("用户管理", user, Constants.STATUS_VALID, null, 201, user));
        permis.add(initPermission("新增", useradd, Constants.STATUS_VALID, "user/register", 202, user));

        permis.add(initPermission("JQuery", search, Constants.STATUS_VALID, null, 301, search));
        permis.add(initPermission("快速查询", jquery_search, Constants.STATUS_VALID, "jq", 302, search));

        permis.add(initPermission("新闻", news, Constants.STATUS_VALID, null, 401, news));
        permis.add(initPermission("新增新闻", newsadd, Constants.STATUS_VALID, "news", 402, news));
        permis.add(initPermission("新增列表", news_search, Constants.STATUS_VALID, "news/list", 403, news));

        return permis;
    }

    private static Permission initPermission(String name, String key, Integer hide, String url, Integer sort, String parentKey) {
        Permission per = new Permission();
        per.setName(name);
        per.setKey(key);
        per.setHide(hide);
        per.setUrl(url);
        per.setSort(sort);
        per.setParentKey(parentKey);
        return per;
    }

    public static List<PermissionVo> getMenus(Collection<Permission> permissions) {

        if (permissions == null || permissions.size() == 0) {
            return null;
        }

        List<PermissionVo> pers = new ArrayList<PermissionVo>();

        // 筛选出一级菜单
        PermissionVo pvo = null;
        for (Permission per : permissions) {
            if (StringUtils.equals(per.getKey(), per.getParentKey())) {
                pvo = new PermissionVo();
                pvo.setId(per.getId());
                pvo.setName(per.getName());
                pvo.setHide(per.getHide());
                pvo.setKey(per.getKey());
                pvo.setParentKey(per.getParentKey());
                pvo.setUrl(per.getUrl());
                pers.add(pvo);
            }
        }

        List<PermissionVo> children = null;
        // 给一级菜单绑定对应的二级菜单
        for (PermissionVo vo : pers) {
            children = new ArrayList<PermissionVo>();
            for (Permission p : permissions) {
                if (StringUtils.equals(vo.getKey(), p.getParentKey()) && !StringUtils.equals(p.getKey(), p.getParentKey())) {
                    pvo = new PermissionVo();
                    pvo.setId(p.getId());
                    pvo.setName(p.getName());
                    pvo.setHide(p.getHide());
                    pvo.setKey(p.getKey());
                    pvo.setParentKey(p.getParentKey());
                    pvo.setUrl(p.getUrl());
                    children.add(pvo);
                }
            }
            vo.setChildren(children);
        }

        return pers;
    }

    public static Permission getUserCententMenu() {
        return null;
    }

}
