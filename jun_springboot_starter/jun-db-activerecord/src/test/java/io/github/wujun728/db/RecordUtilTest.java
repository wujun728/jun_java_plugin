package io.github.wujun728.db;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import io.github.wujun728.db.record.Record;
import io.github.wujun728.db.test.User;
import io.github.wujun728.db.utils.MapKit;
import io.github.wujun728.db.utils.RecordUtil;

import java.util.*;

public class RecordUtilTest {

    public static void main(String[] args) {
        testSingle();
        testList();
    }
    public static void testList() {
        Map map = new HashMap<>();
        map.put("id", 1);
        map.put("userName", "zhangsan");
        map.put("firstAdress", "fdafdsa111");
        map.put("age", 111);
        map.put("createData", new Date());
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList.add(map);

        List<Record> record1ss = RecordUtil.mapToRecords(mapList);
        printLog("mapToRecords record1ss = ", record1ss);

        List<Map<String, Object>> map1ss = RecordUtil.recordToMaps(record1ss);
        printLog("recordToMaps map1ss = ", map1ss);

        List<User> user1ss = MapKit.mapToBeans(map1ss, User.class);
        printLog("mapToBeans user1ss = ", user1ss);

        List<User> user22ss = RecordUtil.recordToBeans(record1ss, User.class);
        printLog("recordToBeans user22ss  = ", user22ss);

        List<Map<String, Object>> map22ss = MapKit.beanToMaps(user1ss);
        printLog("beanToMaps map22ss = ", map22ss);

        List<Record> record33 = RecordUtil.beanToRecords(user1ss);
        printLog("beanToRecords record33 = ", record33);
    }
    public static void testSingle() {
        Map map = new HashMap<>();
        map.put("id", 1);
        map.put("userName", "zhangsan");
        map.put("firstAdress", "fdafdsa111");
        map.put("age", 111);
        map.put("createData", new Date());
        Record record1 = RecordUtil.mapToRecord(map);
        printLog("mapToRecord record1 = ", record1);

        Map map1 = RecordUtil.recordToMap(record1);
        printLog("recordToMap map1 = ", map1);

        User user1 = MapKit.mapToBean(map1, User.class);
        printLog("mapToBean user1 = ", user1);

        User user22 = RecordUtil.recordToBean(record1, User.class);
        printLog("recordToBean user22  = ", user22);

        Map map22 = MapKit.beanToMap(user1);
        printLog("beanToMap user1 = ", map22);

        Record record33 = RecordUtil.beanToRecord(user1);
        printLog("beanToRecord user1 = ", record33);
    }

    static void printLog(String info, Object obj) {
        StaticLog.info(info + JSONUtil.toJsonPrettyStr(JSONUtil.toJsonStr(obj,
                JSONConfig.create().setDateFormat("yyyy-MM-dd HH:mm:ss").setIgnoreNullValue(false))));
    }
}
