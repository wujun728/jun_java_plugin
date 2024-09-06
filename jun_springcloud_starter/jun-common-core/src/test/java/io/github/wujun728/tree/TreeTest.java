package io.github.wujun728.tree;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;

import java.util.*;


public class TreeTest {

    static String jsonstr = "  [\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 0,\n" +
            "            \"id\": 1,\n" +
            "            \"sort\": 1,\n" +
            "            \"title\": \"普通公告\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 0,\n" +
            "            \"id\": 2,\n" +
            "            \"sort\": 2,\n" +
            "            \"title\": \"紧急公告\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 0,\n" +
            "            \"id\": 3,\n" +
            "            \"sort\": 3,\n" +
            "            \"title\": \"防疫公告\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"createTime\": 0,\n" +
            "            \"children\": [],\n" +
            "            \"updateTime\": 0,\n" +
            "            \"pid\": 1,\n" +
            "            \"id\": 14,\n" +
            "            \"sort\": 1,\n" +
            "            \"title\": \"节假日公告\"\n" +
            "        }\n" +
            "    ] ";


    public static void main(String[] args) throws IllegalAccessException {
        JSONArray jsonArray = JSONUtil.parseArray(jsonstr);
        List<Map> datas = jsonArray.toList(Map.class);
//        StaticLog.info(JSONUtil.toJsonPrettyStr(jsonArray));
        Map parameters = new HashMap<>();
        String treeId = MapUtil.getStr(parameters, "id") == null ? "id" : MapUtil.getStr(parameters, "id");
        String treePid = MapUtil.getStr(parameters, "pid") == null ? "pid" : MapUtil.getStr(parameters, "pid");
        Object rootId = parameters.get("rootId") == null ? 0L : parameters.get("rootId");
        if (StrUtil.isNotEmpty(treePid)) {
            TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
            treeNodeConfig.setIdKey(treeId);
            treeNodeConfig.setParentIdKey(treePid);
            List<Tree<Long>> treeList = TreeUtil.build(datas, Long.valueOf(String.valueOf(rootId)), treeNodeConfig, (map, tree) -> {
                // 也可以使用 tree.setId(object.getId());等一些默认值
                tree.setId(Long.valueOf(String.valueOf(map.get("id"))));
                tree.setParentId(Long.valueOf(String.valueOf(map.get("pid"))));
                //tree.putExtra("name", object.getName());
                map.keySet().stream().forEach(k->{
                    if(!k.equals(treeId) && !k.equals(treePid) ){
                        tree.putExtra(String.valueOf(k),map.get(k));
                    }
                });
            });
            StaticLog.info(JSONUtil.toJsonPrettyStr(treeList));
        }
    }

}
