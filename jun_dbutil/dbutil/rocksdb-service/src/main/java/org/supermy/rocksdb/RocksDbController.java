package org.supermy.rocksdb;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomStringUtils;
import org.rocksdb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * rocksdb 数据的文件存储
 */
//@RestController
@Controller
@RequestMapping("/rocksdb")
public class RocksDbController {
    private static final Logger log = LoggerFactory.getLogger(RocksDbController.class);

    @Autowired
    private RocksDB db;

    @Autowired
    private Gson gson;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    Greeting sayHello(@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    /**
     * 设定json 数据
     *
     * @param value
     * @RequestBody
     */
    @RequestMapping(value = "/set", method = RequestMethod.GET)
    public
    @ResponseBody
    CommonJson<HashMap> set(@RequestParam(value = "key", required = false, defaultValue = "key1") String key, @RequestParam String value) {
        log.debug("set...{}  = ...{}", key, value);
        HashMap<String, String> obj = new HashMap<String, String>();

        try {
            if (value != null) {  // value == null if key1 does not exist in db.
                db.put(key.getBytes(), value.getBytes());
            }
        } catch (RocksDBException e) {
            e.printStackTrace();

            obj.put("cause", e.getLocalizedMessage());
            return new CommonJson<HashMap>(false, "设置数据失败!", obj);

        }
        obj.put(key, value);

        return new CommonJson<HashMap>(true, "设置数据成功!", obj);
    }


    /**
     * 获取json 数据
     *
     * @param value
     * @RequestBody
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    CommonJson<HashMap> get(@RequestParam(value = "key", required = false, defaultValue = "key1") String key) {
        log.debug("set......{}", key);
        HashMap<String, String> obj = new HashMap<String, String>();

        String value = "数据不存在";
        try {
            byte[] value1 = db.get(key.getBytes());
            //db.put();
            if (value1 != null) {
                value = new String(value1);
            }
//            if (value != null) {  // value == null if key1 does not exist in db.
//                db.put(key.getBytes(), value);
//            }
            // --db.remove(key1);
        } catch (RocksDBException e) {
            e.printStackTrace();

            obj.put("cause", e.getLocalizedMessage());
            return new CommonJson<HashMap>(false, "获取数据失败!", obj);

        }
        obj.put(key, value);

        return new CommonJson<HashMap>(true, "获取数据成功!", obj);

    }

    /**
     * 删除数据
     *
     * @param value
     * @RequestBody
     */
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public
    @ResponseBody
    CommonJson<HashMap> del(@RequestParam(value = "key", required = false, defaultValue = "key1") String key) {
        HashMap<String, String> obj = new HashMap<String, String>();

        log.debug("set......{}", key);
        try {
            db.singleDelete(key.getBytes());
        } catch (RocksDBException e) {
            e.printStackTrace();

            obj.put("cause", e.getLocalizedMessage());
            return new CommonJson<HashMap>(false, "删除数据失败!", obj);

        }
        obj.put(key, "");

        return new CommonJson<HashMap>(true, "删除数据成功!", obj);


    }


    /**
     * 生成数据json 数据  flume 插入数据；200万数据 每批 1万 1分30秒
     *
     * @param value
     * @RequestBody
     */
    @RequestMapping(value = "/gendata", method = RequestMethod.GET)
    public
    @ResponseBody
    CommonJson<String> gendata() throws RocksDBException {

        HashMap<String, String> obj = new HashMap<String, String>();

        try (final WriteOptions writeOpt = new WriteOptions()) {
            try (final WriteBatch batch = new WriteBatch()) {
                for (int ii = 0; ii < 10; ii++) {
                    for (int i = 0; i < 10000; i++) {
                        String key = "1" + RandomStringUtils.randomNumeric(10);
                        batch.put(key.getBytes(), "1".getBytes());
                    }
                    db.write(writeOpt, batch);
                }
            }

        } catch (RocksDBException e) {
            e.printStackTrace();

            obj.put("cause", e.getLocalizedMessage());
            return new CommonJson<String>(false, "设置数据失败!", e.getLocalizedMessage());
        }

        return new CommonJson<String>(true, "设置数据成功!", "批量配置数据完成");
    }


    /**
     * 服务器状态
     *
     * @return
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public
    @ResponseBody
    String status() throws RocksDBException {
        log.debug("status......{}");
        String stats = db.getProperty("rocksdb.stats");
        System.out.println(stats);
        return stats;
    }

    /**
     * 数据备份
     *
     * @return
     */
    @RequestMapping(value = "/backdb", method = RequestMethod.GET)
    public
    @ResponseBody
    String backdb(@RequestParam(value = "backdbpath", required = false, defaultValue = "backdata") String backdbpath)
            throws RocksDBException {
        log.debug("back db ......{}",backdbpath);
        HashMap<String, Object> obj = new HashMap<String, Object>();
        // Create two backups

        try (final Options opt = new Options().setCreateIfMissing(true);
             final BackupableDBOptions bopt = new BackupableDBOptions(backdbpath);
             final BackupEngine be = BackupEngine.open(opt.getEnv(), bopt)) {
            be.createNewBackup(db, false);
            //obj.put("backdb1",be.getBackupInfo());
            be.createNewBackup(db, true);
            obj.put("backdbinfo",be.getBackupInfo());
            //verifyNumberOfValidBackups(be, 2);
        }

        obj.put("backdbpath", backdbpath);
        //return new CommonJson<String>(true, "设置备份成功!", gson.toJson(obj));
        return gson.toJson(new CommonJson<HashMap<String, Object>>(true, "设置备份成功!", obj));

    }

    /**
     * 数据备份删除
     *
     * @return
     */
    @RequestMapping(value = "/delbackdb", method = RequestMethod.GET)
    public
    @ResponseBody
    String delbackdb(@RequestParam(value = "backid", required = false, defaultValue = "1") String backid,
                     @RequestParam(value = "backdbpath", required = false, defaultValue = "backdata") String backdbpath)
            throws RocksDBException {
        log.debug("back db ......{}",backid);
        log.debug("back db ......{}",backdbpath);

        HashMap<String, Object> obj = new HashMap<String, Object>();

        try(final Options opt = new Options().setCreateIfMissing(true);
            final BackupableDBOptions bopt = new BackupableDBOptions(backdbpath);
            final BackupEngine be = BackupEngine.open(opt.getEnv(), bopt)) {


            be.deleteBackup(new Integer(backid));
            obj.put("backdbinfo",be.getBackupInfo());

        }

        obj.put("backdbpath", backdbpath);
        obj.put("backid", backid);

        return gson.toJson(new CommonJson<HashMap<String, Object>>(true, "删除指定备份成功!", obj));

    }

    /**
     * 备份清理，保留备份数量 amount
     *
     * @return
     */
    @RequestMapping(value = "/purebackdb", method = RequestMethod.GET)
    public
    @ResponseBody
    String purebackdb(@RequestParam(value = "amount", required = false, defaultValue = "1") String amount,
                     @RequestParam(value = "backdbpath", required = false, defaultValue = "backdata") String backdbpath)
            throws RocksDBException {
        log.debug("back db ......{}",amount);
        log.debug("back db ......{}",backdbpath);

        HashMap<String, Object> obj = new HashMap<String, Object>();

        try(final Options opt = new Options().setCreateIfMissing(true);
            final BackupableDBOptions bopt = new BackupableDBOptions(backdbpath);
            final BackupEngine be = BackupEngine.open(opt.getEnv(), bopt)) {

            be.purgeOldBackups(new Integer(amount));

            obj.put("backdbinfo",be.getBackupInfo());

        }

        obj.put("backdbpath", backdbpath);
        obj.put("backid", amount);

        return gson.toJson(new CommonJson<HashMap<String, Object>>(true, "清除备份成功!", obj));

    }


    /**
     * 备份恢复
     *
     * @return
     */
    @RequestMapping(value = "/restorbackdb", method = RequestMethod.GET)
    public
    @ResponseBody
    String restorebackdb(@RequestParam(value = "dbpath", required = false, defaultValue = "data") String dbpath,
                      @RequestParam(value = "backdbpath", required = false, defaultValue = "backdata") String backdbpath)
            throws RocksDBException {
        log.debug("restore back db ......{}",backdbpath);

        db.close();
        db = null;

        HashMap<String, Object> obj = new HashMap<String, Object>();

        try(final Options opt = new Options().setCreateIfMissing(true);
            final BackupableDBOptions bopt = new BackupableDBOptions(backdbpath);
            final BackupEngine be = BackupEngine.open(opt.getEnv(), bopt);
            final RestoreOptions ropts = new RestoreOptions(false)) {

            be.restoreDbFromLatestBackup(dbpath,backdbpath, ropts);

            obj.put("backdbinfo",be.getBackupInfo());

            db = RocksDB.open(opt, dbpath);  //// FIXME: 17/5/26 打开的选项不是手动定义的

        }

        obj.put("backdbpath", backdbpath);
        obj.put("dbpath", dbpath);




        return gson.toJson(new CommonJson<HashMap<String, Object>>(true, "恢复最新备份成功!", obj));

    }


    /**
     * 恢复指定备份
     *
     * @return
     */
    @RequestMapping(value = "/restorbackdbid", method = RequestMethod.GET)
    public
    @ResponseBody
    String restorebackdbid(@RequestParam(value = "dbpath", required = false, defaultValue = "data") String dbpath,
                           @RequestParam(value = "backid", required = false, defaultValue = "1") String backid,
                         @RequestParam(value = "backdbpath", required = false, defaultValue = "backdata") String backdbpath)
            throws RocksDBException {
        log.debug("restore back db ......{}",backdbpath);


        db.close();
        db = null;

        HashMap<String, Object> obj = new HashMap<String, Object>();

        try(final Options opt = new Options().setCreateIfMissing(true);
            final BackupableDBOptions bopt = new BackupableDBOptions(backdbpath);
            final BackupEngine be = BackupEngine.open(opt.getEnv(), bopt);
            final RestoreOptions ropts = new RestoreOptions(false)) {

            be.restoreDbFromLatestBackup(backdbpath,dbpath, ropts);
            be.restoreDbFromBackup(new Integer(backid),
                    backdbpath,
                    dbpath,

                    new RestoreOptions(false));

            obj.put("backdbinfo",be.getBackupInfo());

            db = RocksDB.open(opt, dbpath);

        }

        obj.put("backdbpath", backdbpath);
        obj.put("dbpath", dbpath);
        obj.put("backid", backid);




        return gson.toJson(new CommonJson<HashMap<String, Object>>(true, "恢复指定备份成功!", obj));

    }
}


