package com.tanghd.spring.dbutil.datasource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.tanghd.spring.dbutil.health.JdbcConnectionChecker;
import com.tanghd.spring.dbutil.health.SlaveDataSourceHealthChecker;

/**
 * 继承{@link org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource}
 * 配置主从数据源后，根据选择，返回对应的数据源。多个从库的情况下，会平均的分配从库，用于负载均衡。
 * 
 * @author Wujun
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicDataSource.class);
    private static final String DEFAULT = "master";
    private static final String SLAVE = "slave";

    private static final ThreadLocal<LinkedList<String>> datasourceHolder = new ThreadLocal<LinkedList<String>>() {

        @Override
        protected LinkedList<String> initialValue() {
            return new LinkedList<String>();
        }

    };

    private ScheduledExecutorService scheduleService = Executors.newSingleThreadScheduledExecutor();

    private DataSource master; // 主库，只允许有一个

    private List<DataSource> slaves; // 从库，允许有多个
    private CopyOnWriteArrayList<String> slaveKeys;

    private AtomicLong slaveCount = new AtomicLong();

    private boolean slaveHealthCheck;
    private int healthCheckPeriod;
    private JdbcConnectionChecker healthChecker;
    private SlaveDataSourceHealthChecker slaveChecker;
    private Map<Object, Object> dataSources = new HashMap<Object, Object>();

    /**
     * 初始化
     */
    @Override
    public void afterPropertiesSet() {
        if (null == master) {
            throw new IllegalArgumentException("Property 'master' is required");
        }
        dataSources.put(DEFAULT, master);
        if (null != slaves && slaves.size() > 0) {
            slaveKeys = new CopyOnWriteArrayList<String>();
            for (int i = 0; i < slaves.size(); i++) {
                String key = SLAVE + (i + 1);
                dataSources.put(key, slaves.get(i));
                slaveKeys.add(key);
            }
            startHealthChecker();
        }
        this.setDefaultTargetDataSource(master);
        this.setTargetDataSources(dataSources);
        super.afterPropertiesSet();
    }

    private void startHealthChecker() {
        if (slaveHealthCheck && null != healthChecker) {
            if (healthCheckPeriod <= 1) {
                healthCheckPeriod = 1;
            }
            slaveChecker = new SlaveDataSourceHealthChecker(slaveKeys, dataSources, healthChecker);
            scheduleService.scheduleAtFixedRate(slaveChecker, this.healthCheckPeriod, this.healthCheckPeriod,
                    TimeUnit.MINUTES);
        }
    }

    /**
     * 选择使用主库，并把选择放到当前ThreadLocal的栈顶
     */
    public static void useMaster() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("use datasource :" + datasourceHolder.get());
        }
        LinkedList<String> m = datasourceHolder.get();
        m.offerFirst(DEFAULT);
    }

    /**
     * 选择使用从库，并把选择放到当前ThreadLocal的栈顶
     */
    public static void useSlave() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("use datasource :" + datasourceHolder.get());
        }
        LinkedList<String> m = datasourceHolder.get();
        m.offerFirst(SLAVE);
    }

    /**
     * 重置当前栈
     */
    public static void reset() {
        LinkedList<String> m = datasourceHolder.get();
        if (LOG.isDebugEnabled()) {
            LOG.debug("reset datasource {}", m);
        }
        if (m.size() > 0) {
            m.poll();
        }
    }

    /**
     * 如果是选择使用从库，且从库的数量大于1，则通过取模来控制从库的负载,
     * 计算结果返回AbstractRoutingDataSource
     */
    @Override
    protected Object determineCurrentLookupKey() {
        LinkedList<String> m = datasourceHolder.get();
        String key = m.peekFirst() == null ? "" : m.peekFirst();
        if (LOG.isDebugEnabled()) {
            LOG.debug("currenty datasource :" + key);
        }
        if (null != key) {
            if (DEFAULT.equals(key)) {
                return key;
            } else if (SLAVE.equals(key)) {
                int index = -1;
                int slaveSize = slaveKeys.size();
                if (slaveSize > 1) {// Slave loadBalance
                    long c = slaveCount.incrementAndGet();
                    c = c % slaveSize;
                    index = (int) c;
                } else if (slaveSize == 0) {
                    return null;
                } else {
                    index = 0;
                }
                try {
                    return slaveKeys.get(index);
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public DataSource getMaster() {
        return master;
    }

    public List<DataSource> getSlaves() {
        return slaves;
    }

    public void setMaster(DataSource master) {
        this.master = master;
    }

    public void setSlaves(List<DataSource> slaves) {
        this.slaves = slaves;
    }

    public int getHealthCheckPeriod() {
        return healthCheckPeriod;
    }

    public void setHealthCheckPeriod(int healthCheckPeriod) {
        this.healthCheckPeriod = healthCheckPeriod;
    }

    public boolean isSlaveHealthCheck() {
        return slaveHealthCheck;
    }

    public void setSlaveHealthCheck(boolean slaveHealthCheck) {
        this.slaveHealthCheck = slaveHealthCheck;
    }

    public JdbcConnectionChecker getHealthChecker() {
        return healthChecker;
    }

    public void setHealthChecker(JdbcConnectionChecker healthChecker) {
        this.healthChecker = healthChecker;
    }

}
