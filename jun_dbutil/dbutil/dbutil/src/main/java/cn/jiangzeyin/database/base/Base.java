package cn.jiangzeyin.database.base;


import cn.jiangzeyin.database.config.DatabaseContextHolder;
import cn.jiangzeyin.database.config.SystemColumn;
import cn.jiangzeyin.system.SystemDbLog;
import cn.jiangzeyin.system.SystemSessionInfo;
import cn.jiangzeyin.util.ref.ReflectUtil;
import com.alibaba.druid.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 数据库操作公共
 *
 * @author jiangzeyin
 */
public abstract class Base<T> {
    private boolean isThrows; // 异常是否抛出
    private HashMap<String, Class<?>> refMap; // 外键表
    private HashMap<String, String> refWhere;
    private String refKey; // 外键列名
    private List<String> remove; // 排除不操作字段
    private String tag; // 数据库标示
    private Class<?> tclass; // 数据库对应class
    private int optUserId; // 操作人
    private long runTime;
    protected String runSql;

    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public HashMap<String, String> getRefWhere() {
        return refWhere;
    }


    /**
     * 创建时就获取操作人
     */
    public Base() {
        // TODO Auto-generated constructor stub
        setOptUserId(SystemSessionInfo.getUserId());
        runTime = System.currentTimeMillis();
    }

    protected void setRunSql(String runSql) {
        this.runSql = runSql;
    }

    protected void runEnd() {
        long time = System.currentTimeMillis() - runTime;
        if (time > 2 * 1000L) {
            String tagName = DatabaseContextHolder.getConnectionTagName();
            SystemDbLog.getInstance().warn(tagName + "执行时间过长：" + time + "  " + runSql);
        }
    }

    public int getOptUserId() {
        return optUserId;
    }

    public void setOptUserId(int optUserId) {
        this.optUserId = optUserId;
    }

    public Class<?> getTclass() {
        return getTclass(true);
    }

    public Class<?> getTclass(boolean getRef) {
        if (tclass == null && getRef) {
            tclass = ReflectUtil.getTClass(getClass());
        }
        return tclass;
    }

    public void setTclass(Class<?> tclass) {
        this.tclass = tclass;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<String> getRemove() {
        return remove;
    }

    /**
     * 设置排除获取字段
     *
     * @param remove 要移除的字段
     * @author jiangzeyin
     */
    public void setRemove(String... remove) {
        if (remove == null)
            return;
        List<String> remove_ = this.remove;
        //getRemove();
        if (remove_ == null) {
            remove_ = new LinkedList<>();
            this.remove = remove_;
        }
        for (String item : remove) {
            if (!remove_.contains(item))
                remove_.add(item.toLowerCase());
        }
    }

    public String getRefKey() {
        if (StringUtils.isEmpty(refKey))
            return SystemColumn.getDefaultRefKeyName();
        return refKey;
    }

    /**
     * 设置外键的列
     *
     * @param refKey 外键的列
     * @author jiangzeyin
     */
    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public HashMap<String, Class<?>> getRefMap() {
        return refMap;
    }


    /**
     * 添加外键 关系
     *
     * @param name     外键名称
     * @param refClass 外键类
     * @author jiangzeyin
     */
    public void putRefClass(String name, Class<?> refClass) {
        if (refMap == null)
            refMap = new HashMap<>();
        refMap.put(name.toLowerCase(), refClass);
    }

    public void putRefClass(String name, Class<?> refClass, String where) {
        if (refMap == null)
            refMap = new HashMap<>();
        if (refWhere == null)
            refWhere = new HashMap<>();
        refMap.put(name, refClass);
        refWhere.put(name, where);
    }

    public boolean isThrows() {
        return isThrows;
    }

    public void setThrows(boolean isThrows) {
        this.isThrows = isThrows;
    }

    /**
     * 处理异常
     *
     * @param t 异常信息
     * @author jiangzeyin
     */
    public void isThrows(Throwable t) {
        if (isThrows)
            throw new RuntimeException(t);
        else {
            SystemDbLog.getInstance().error("执行数据库操作", t);
        }
    }

    /**
     * 回收对象信息
     *
     * @author jiangzeyin
     */
    protected void recycling() {
        refMap = null;
        refKey = null;
        remove = null;
        tag = null;
        tclass = null;
        optUserId = 0;
    }
}
