package cn.springboot.config.db.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.springboot.service.key.KeyService;

/** 
 * @Description 生成主键：在项目启动时，将库里的主键id全部查询出来，放到本地内存中(集合)。 <br/>
 * 主键实例：2017 0001 0001 0000 001 <br/>
 * 1-4位：机器码(应用服务器) 5-8位：表代码code 9-19位：增长id
 * @author Wujun
 * @date Apr 12, 2017 1:59:40 PM  
 */
@Component
public class FactoryAboutKey {

    private static final Logger log = LoggerFactory.getLogger(FactoryAboutKey.class);

    public static ConcurrentHashMap<String, String> keyFinalList = new ConcurrentHashMap<String, String>();

    @Autowired
    private KeyService keyService;

    @Value("${machineCode:2017}") // 若没取到机器码，则默认0001
    private String machineCode;

    /**
     * 初始化 <br/>
     * 1.从配置文件读取表名和代码的键值对,防御 <br/>
     * 2.获取机器码 <br/>
     * 3.用 （机器码、表代码、11位字符串）组装主键
     */
    @PostConstruct
    public void init() throws Exception {

        // 数据库当中获取表主名及对应主键名
        List<Key> tblNamelistFromSuorce = keyService.getTables();
        Map<String, String> keyMapFromSuorce = new HashMap<>();
        for (Key k : tblNamelistFromSuorce) {
            keyMapFromSuorce.put(k.getTableName(), k.getId());
        }

        // 从配置读取的key对象
        Map<String, Key> keyMapFromEnum = TablesPKEnum.getTables();
        Set<String> listForTblExist = keyMapFromEnum.keySet();

        // 判断表名是否在数据库中存在
        Key key = null;
        List<Key> keys = new ArrayList<>();
        for (String tableName : listForTblExist) {
            if (!keyMapFromSuorce.containsKey(tableName)) {
                throw new Exception(tableName + "表名在数据库中不存在或TablesPKEnum类中没做相应的配置");
            }
            key = keyMapFromEnum.get(tableName);
            key.setId(keyMapFromSuorce.get(tableName));
            keys.add(key);
        }

        // 拼装主键id
        List<Key> keyQueryFromDatasource = keyService.getTableValues(keys);
        if (log.isDebugEnabled()) {
            log.debug("## keyQueryFromDatasource.size={}", keyQueryFromDatasource.size());
        }
        for (Key k : keyQueryFromDatasource) {
            String newId = "";
            if (StringUtils.isBlank(k.getId())) {
                if(!keyMapFromEnum.containsKey(k.getTableName())) {
                    throw new Exception(k.getTableName() + "表名在TablesPKEnum类中没做相应的配置");
                }
                newId = machineCode + keyMapFromEnum.get(k.getTableName()).getTableCode() + "00000000000";
            } else if (k.getId().length() < 19) {
                newId = machineCode + k.getId();
                for (int i = newId.length(); i < 19; i++) {
                    newId = newId + "0";
                }
            } else if (k.getId().length() > 19) {
                newId = k.getId().substring(0, 19);
            } else {
                newId = k.getId();
            }
            keyFinalList.put(k.getTableName(), newId);
        }
    }

    private static Lock lock = new ReentrantLock();// 锁对象

    /**
     * 
     * 根据表名从本地内存读取所对应的id
     * @param pk 表枚举配置项
     * @return 表新产生的ID
     */
    public static String getPK(TablesPKEnum pk) {
        boolean isloap = true;
        String finalId = "";
        while (isloap) {
            lock.lock();
            String code = keyFinalList.get(pk.getTableName());
            if (StringUtils.isEmpty(code)) {
                log.error("## 表={} ， 配置文件中(key.properties)不存在相对应的键值对!", pk.getTableName());
            } else {
                String machineCode = code.substring(0, 4);
                String keyValue = code.substring(4, 8);
                String idValue = code.substring(8, code.length());
                long id = Long.valueOf(idValue);
                id++;
                int idLenth = String.valueOf(id).length();

                String containZero = "";
                for (int i = 0; i < idValue.length() - idLenth; i++) {
                    containZero += "0";
                }
                containZero += String.valueOf(id);
                finalId = machineCode + keyValue + containZero;
                keyFinalList.replace(pk.getTableName(), finalId);
            }

            isloap = false;
            lock.unlock();
        }
        return finalId;
    }
}