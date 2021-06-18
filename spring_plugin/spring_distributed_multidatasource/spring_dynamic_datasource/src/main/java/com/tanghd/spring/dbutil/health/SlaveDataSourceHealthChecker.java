package com.tanghd.spring.dbutil.health;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

public class SlaveDataSourceHealthChecker extends TimerTask {
    private CopyOnWriteArrayList<String> slaveKeys;
    private Map<Object, Object> dataSources;

    private List<String> failedSlaveKeys;
    private JdbcConnectionChecker checker;

    public SlaveDataSourceHealthChecker(CopyOnWriteArrayList<String> slaveKeys, Map<Object, Object> dataSources,
            JdbcConnectionChecker checker) {
        this.slaveKeys = slaveKeys;
        this.dataSources = dataSources;
        this.checker = checker;
        failedSlaveKeys = new ArrayList<String>();
    }

    @Override
    public void run() {
        List<String> down = new ArrayList<String>();
        List<String> up = new ArrayList<String>();
        for (String key : slaveKeys) {
            check(key, down, up);
        }

        for (String key : failedSlaveKeys) {
            check(key, down, up);
        }

        if (up.size() > 0) {
            slaveKeys.addAllAbsent(up);
        } else {
            slaveKeys.clear();
        }

        if (down.size() > 0) {
            failedSlaveKeys.clear();
            failedSlaveKeys.addAll(down);
        } else {
            failedSlaveKeys.clear();

        }
    }

    private void check(String key, List<String> down, List<String> up) {
        DataSource slave = (DataSource) dataSources.get(key);
        boolean alive = checker.isAlive(slave);
        if (alive) {
            up.add(key);
        } else {
            down.add(key);
        }
    }

}
