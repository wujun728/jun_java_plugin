package cn.kiiwii.framework.hibernate.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by zhong on 2017/1/7.
 */
@ConfigurationProperties(
        prefix = "spring.hibernate"
)
public class HibernatePropertes {

    private boolean showSql;
    private boolean formatSql;
    private String useSqlComments;
    private String dialect;
    private boolean useOuterJoin;
    private Hbm2ddl hbm2ddl;

    private HibernatePropertes.JDBC jdbc;
    private HibernatePropertes.Cache cache;
    private HibernatePropertes.Query query;

    private String[] PackagesToScan;

    enum Hbm2ddl{
        UPDATE("update"),CREATE("create"),CREATEDROP("create-drop"),VALIDATE("validate");
        private String value;
        Hbm2ddl(String value) {
            this.value = value;
        }
        @Override
        public String toString() {
            return value;
        }
    }
    public HibernatePropertes() {
        this.jdbc = new HibernatePropertes.JDBC();
        this.query = new HibernatePropertes.Query();
        this.cache = new HibernatePropertes.Cache();
    }

    public static class JDBC{

        private int fetchSize;
        private int batchSize;
        private boolean batchVersionedData;
        private String factoryClass;
        private String sqlExceptionConverter;
        private boolean useGetGeneratedKeys;
        private boolean useScrollableResultset;
        private boolean useStreamsForBinary;
        private boolean wrapResultSets;


        public int getFetchSize() {
            return fetchSize;
        }

        public void setFetchSize(int fetchSize) {
            this.fetchSize = fetchSize;
        }

        public int getBatchSize() {
            return batchSize;
        }

        public void setBatchSize(int batchSize) {
            this.batchSize = batchSize;
        }

        public boolean isBatchVersionedData() {
            return batchVersionedData;
        }

        public void setBatchVersionedData(boolean batchVersionedData) {
            this.batchVersionedData = batchVersionedData;
        }

        public String getFactoryClass() {
            return factoryClass;
        }

        public void setFactoryClass(String factoryClass) {
            this.factoryClass = factoryClass;
        }

        public String getSqlExceptionConverter() {
            return sqlExceptionConverter;
        }

        public void setSqlExceptionConverter(String sqlExceptionConverter) {
            this.sqlExceptionConverter = sqlExceptionConverter;
        }

        public boolean isUseGetGeneratedKeys() {
            return useGetGeneratedKeys;
        }

        public void setUseGetGeneratedKeys(boolean useGetGeneratedKeys) {
            this.useGetGeneratedKeys = useGetGeneratedKeys;
        }

        public boolean isUseScrollableResultset() {
            return useScrollableResultset;
        }

        public void setUseScrollableResultset(boolean useScrollableResultset) {
            this.useScrollableResultset = useScrollableResultset;
        }

        public boolean isUseStreamsForBinary() {
            return useStreamsForBinary;
        }

        public void setUseStreamsForBinary(boolean useStreamsForBinary) {
            this.useStreamsForBinary = useStreamsForBinary;
        }

        public boolean isWrapResultSets() {
            return wrapResultSets;
        }

        public void setWrapResultSets(boolean wrapResultSets) {
            this.wrapResultSets = wrapResultSets;
        }
    }

    public static class Cache{
        private boolean jndi;
        private String providerClass;
        private String providerConfigurationFileResourcePath;
        private String queryCacheFactory;
        private String regionPrefix;
        private boolean useMinimalPuts;
        private boolean useQueryCache;
        private boolean useSecondLevelCache;
        private boolean useStructuredEntries;

        public boolean isJndi() {
            return jndi;
        }

        public void setJndi(boolean jndi) {
            this.jndi = jndi;
        }

        public String getProviderClass() {
            return providerClass;
        }

        public void setProviderClass(String providerClass) {
            this.providerClass = providerClass;
        }

        public String getProviderConfigurationFileResourcePath() {
            return providerConfigurationFileResourcePath;
        }

        public void setProviderConfigurationFileResourcePath(String providerConfigurationFileResourcePath) {
            this.providerConfigurationFileResourcePath = providerConfigurationFileResourcePath;
        }

        public String getQueryCacheFactory() {
            return queryCacheFactory;
        }

        public void setQueryCacheFactory(String queryCacheFactory) {
            this.queryCacheFactory = queryCacheFactory;
        }

        public String getRegionPrefix() {
            return regionPrefix;
        }

        public void setRegionPrefix(String regionPrefix) {
            this.regionPrefix = regionPrefix;
        }

        public boolean isUseMinimalPuts() {
            return useMinimalPuts;
        }

        public void setUseMinimalPuts(boolean useMinimalPuts) {
            this.useMinimalPuts = useMinimalPuts;
        }

        public boolean isUseQueryCache() {
            return useQueryCache;
        }

        public void setUseQueryCache(boolean useQueryCache) {
            this.useQueryCache = useQueryCache;
        }

        public boolean isUseSecondLevelCache() {
            return useSecondLevelCache;
        }

        public void setUseSecondLevelCache(boolean useSecondLevelCache) {
            this.useSecondLevelCache = useSecondLevelCache;
        }

        public boolean isUseStructuredEntries() {
            return useStructuredEntries;
        }

        public void setUseStructuredEntries(boolean useStructuredEntries) {
            this.useStructuredEntries = useStructuredEntries;
        }
    }

    public static class Query{
        private String factoryClass;
        private String String;
        private boolean jpaqlStrictCompliance;
        private String substitutions;

        public java.lang.String getFactoryClass() {
            return factoryClass;
        }

        public void setFactoryClass(java.lang.String factoryClass) {
            this.factoryClass = factoryClass;
        }

        public java.lang.String getString() {
            return String;
        }

        public void setString(java.lang.String string) {
            String = string;
        }

        public boolean isJpaqlStrictCompliance() {
            return jpaqlStrictCompliance;
        }

        public void setJpaqlStrictCompliance(boolean jpaqlStrictCompliance) {
            this.jpaqlStrictCompliance = jpaqlStrictCompliance;
        }

        public java.lang.String getSubstitutions() {
            return substitutions;
        }

        public void setSubstitutions(java.lang.String substitutions) {
            this.substitutions = substitutions;
        }
    }


    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    public boolean isUseOuterJoin() {
        return useOuterJoin;
    }

    public void setUseOuterJoin(boolean useOuterJoin) {
        this.useOuterJoin = useOuterJoin;
    }

    public JDBC getJdbc() {
        return jdbc;
    }

    public void setJdbc(JDBC jdbc) {
        this.jdbc = jdbc;
    }

    public String getUseSqlComments() {
        return useSqlComments;
    }

    public void setUseSqlComments(String useSqlComments) {
        this.useSqlComments = useSqlComments;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Hbm2ddl getHbm2ddl() {
        return hbm2ddl;
    }

    public void setHbm2ddl(Hbm2ddl hbm2ddl) {
        this.hbm2ddl = hbm2ddl;
    }

    public String[] getPackagesToScan() {
        return PackagesToScan;
    }

    public void setPackagesToScan(String... packagesToScan) {
        PackagesToScan = packagesToScan;
    }

}
