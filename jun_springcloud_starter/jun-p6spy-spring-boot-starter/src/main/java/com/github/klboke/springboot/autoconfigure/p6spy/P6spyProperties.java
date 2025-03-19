package com.github.klboke.springboot.autoconfigure.p6spy;

import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.github.klboke.springboot.autoconfigure.p6spy.P6spyProperties.P6SPY_CONFIG_PREFIX;

/**
 * @author: kl @kailing.pub
 * @date: 2019/11/13
 */
@ConfigurationProperties(prefix = P6SPY_CONFIG_PREFIX)
public class P6spyProperties {

    public static final String P6SPY_CONFIG_PREFIX= "p6spy.config";
    /**
     *     # for flushing per statement
     *     # (default is false)
     */
    private String autoflush;
    /**
     *     # A comma separated list of JDBC drivers to load and register.
     *     # (default is empty)
     *     #
     *     # Note: This is normally only needed when using P6Spy in an
     *     # application server environment with a JNDI data source or when
     *     # using a JDBC driver that does not implement the JDBC 4.0 API
     *     # (specifically automatic registration).
     */
    private String driverlist;
    /**
     *     # name of logfile to use, note Windows users should make sure to use forward slashes in their pathname (e:/test/spy.log)
     *     # (used for com.p6spy.engine.spy.appender.FileLogger only)
     *     # (default is spy.log)
     */
    private String logfile;
    /**
     *     # class to use for formatting log messages (default is: com.p6spy.engine.spy.appender.SingleLineFormat)
     */
    private String logMessageFormat;
    /**
     *     # append to the p6spy log file. if this is set to false the
     *     # log file is truncated every time. (file logger only)
     *     # (default is true)
     */
    private String append;
    /**
     *     # sets the date format using Java's SimpleDateFormat routine.
     *     # In case property is not set, milliseconds since 1.1.1970 (unix time) is used (default
     */
    private String dateformat;
    /**
     *     # specifies the appender to use for logging
     *     # Please note: reload means forgetting all the previously set
     *     # settings (even those set during runtime - via JMX)
     *     # and starting with the clean table
     *     # (only the properties read from the configuration file)
     *     # (default is com.p6spy.engine.spy.appender.FileLogger)
     *     #appender=com.p6spy.engine.spy.appender.Slf4JLogger
     *     #appender=com.p6spy.engine.spy.appender.StdoutLogger
     *     #appender=com.p6spy.engine.spy.appender.FileLogger
     */
    private String appender;
    /**
     *     # Module list adapts the modular functionality of P6Spy.
     *     # Only modules listed are active.
     *     # (default is com.p6spy.engine.logging.P6LogFactory and
     *     # com.p6spy.engine.spy.P6SpyFactory)
     *     # Please note that the core module (P6SpyFactory) can't be
     *     # deactivated.
     *     # Unlike the other properties, activation of the changes on
     *     # this one requires reload.
     */
    private String modulelist;
    /**
     *     # prints a stack trace for every statement logged
     */
    private String stacktrace;
    /**
     *     # if stacktrace=true, specifies the stack trace to print
     */
    private String stacktraceclass;

    /**
     *     # determines if property file should be reloaded
     *     # Please note: reload means forgetting all the previously set
     *     # settings (even those set during runtime - via JMX)
     *     # and starting with the clean table
     *     # (default is false)
     *     #reloadproperties=false
     */
    private String reloadproperties;
    /**
     *     # determines how often should be reloaded in seconds
     *     # (default is 60)
     *     #reloadpropertiesinterval=60
     */
    private String reloadpropertiesinterval;
    /**
     *     # JNDI DataSource lookup                                        #
     *     #                                                               #
     *     # If you are using the DataSource support outside of an app     #
     *     # server, you will probably need to define the JNDI Context     #
     *     # environment.                                                  #
     *     #                                                               #
     *     # If the P6Spy code will be executing inside an app server then #
     *     # do not use these properties, and the DataSource lookup will   #
     *     # use the naming context defined by the app server.             #
     *     #                                                               #
     *     # The two standard elements of the naming environment are       #
     *     # jndicontextfactory and jndicontextproviderurl. If you need    #
     *     # additional elements, use the jndicontextcustom property.      #
     *     # You can define multiple properties in jndicontextcustom,      #
     *     # in name value pairs. Separate the name and value with a       #
     *     # semicolon, and separate the pairs with commas.                #
     *     #                                                               #
     *     # The example shown here is for a standalone program running on #
     *     # a machine that is also running JBoss, so the JNDI context     #
     *     # is configured for JBoss (3.0.4).                              #
     *     #                                                               #
     *     # (by default all these are empty)                              #
     *     #################################################################
     *     #jndicontextfactory=org.jnp.interfaces.NamingContextFactory
     *     #jndicontextproviderurl=localhost:1099
     *     #jndicontextcustom=java.naming.factory.url.pkgs;org.jboss.naming:org.jnp.interfaces
     */
    private String jndicontextfactory;
    /**
     *     # JNDI DataSource lookup                                        #
     *     #                                                               #
     *     # If you are using the DataSource support outside of an app     #
     *     # server, you will probably need to define the JNDI Context     #
     *     # environment.                                                  #
     *     #                                                               #
     *     # If the P6Spy code will be executing inside an app server then #
     *     # do not use these properties, and the DataSource lookup will   #
     *     # use the naming context defined by the app server.             #
     *     #                                                               #
     *     # The two standard elements of the naming environment are       #
     *     # jndicontextfactory and jndicontextproviderurl. If you need    #
     *     # additional elements, use the jndicontextcustom property.      #
     *     # You can define multiple properties in jndicontextcustom,      #
     *     # in name value pairs. Separate the name and value with a       #
     *     # semicolon, and separate the pairs with commas.                #
     *     #                                                               #
     *     # The example shown here is for a standalone program running on #
     *     # a machine that is also running JBoss, so the JNDI context     #
     *     # is configured for JBoss (3.0.4).                              #
     *     #                                                               #
     *     # (by default all these are empty)                              #
     *     #################################################################
     *     #jndicontextfactory=org.jnp.interfaces.NamingContextFactory
     *     #jndicontextproviderurl=localhost:1099
     *     #jndicontextcustom=java.naming.factory.url.pkgs;org.jboss.naming:org.jnp.interfaces
     */
    private String jndicontextproviderurl;
    /**
     *     # JNDI DataSource lookup                                        #
     *     #                                                               #
     *     # If you are using the DataSource support outside of an app     #
     *     # server, you will probably need to define the JNDI Context     #
     *     # environment.                                                  #
     *     #                                                               #
     *     # If the P6Spy code will be executing inside an app server then #
     *     # do not use these properties, and the DataSource lookup will   #
     *     # use the naming context defined by the app server.             #
     *     #                                                               #
     *     # The two standard elements of the naming environment are       #
     *     # jndicontextfactory and jndicontextproviderurl. If you need    #
     *     # additional elements, use the jndicontextcustom property.      #
     *     # You can define multiple properties in jndicontextcustom,      #
     *     # in name value pairs. Separate the name and value with a       #
     *     # semicolon, and separate the pairs with commas.                #
     *     #                                                               #
     *     # The example shown here is for a standalone program running on #
     *     # a machine that is also running JBoss, so the JNDI context     #
     *     # is configured for JBoss (3.0.4).                              #
     *     #                                                               #
     *     # (by default all these are empty)                              #
     *     #################################################################
     *     #jndicontextfactory=org.jnp.interfaces.NamingContextFactory
     *     #jndicontextproviderurl=localhost:1099
     *     #jndicontextcustom=java.naming.factory.url.pkgs;org.jboss.naming:org.jnp.interfaces
     */
    private String jndicontextcustom;
    /**
     *     # DataSource replacement                                        #
     *     #                                                               #
     *     # Replace the real DataSource class in your application server  #
     *     # configuration with the name com.p6spy.engine.spy.P6DataSource #
     *     # (that provides also connection pooling and xa support).       #
     *     # then add the JNDI name and class name of the real             #
     *     # DataSource here                                               #
     *     #                                                               #
     *     # Values set in this item cannot be reloaded using the          #
     *     # reloadproperties variable. Once it is loaded, it remains      #
     *     # in memory until the application is restarted.                 #
     *     #                                                               #
     *     #################################################################
     *     #realdatasource=/RealMySqlDS
     */
    private String realdatasource;
    /**
     *     #realdatasourceclass=com.mysql.jdbc.jdbc2.optional.MysqlDataSource
     */
    private String realdatasourceclass;
    /**
     *     # DataSource properties                                         #
     *     #                                                               #
     *     # If you are using the DataSource support to intercept calls    #
     *     # to a DataSource that requires properties for proper setup,    #
     *     # define those properties here. Use name value pairs, separate  #
     *     # the name and value with a semicolon, and separate the         #
     *     # pairs with commas.                                            #
     *     #                                                               #
     *     # The example shown here is for mysql                           #
     *     #                                                               #
     *     #################################################################
     *     #realdatasourceproperties=port;3306,serverName;myhost,databaseName;jbossdb,foo;bar
     */
    private String realdatasourceproperties;
    /**
     *     # Custom log message format used ONLY IF logMessageFormat is set to com.p6spy.engine.spy.appender.CustomLineFormat
     *     # default is %(currentTime)|%(executionTime)|%(category)|connection%(connectionId)|%(sqlSingleLine)
     *     # Available placeholders are:
     *     #   %(connectionId)            the id of the connection
     *     #   %(currentTime)             the current time expressing in milliseconds
     *     #   %(executionTime)           the time in milliseconds that the operation took to complete
     *     #   %(category)                the category of the operation
     *     #   %(effectiveSql)            the SQL statement as submitted to the driver
     *     #   %(effectiveSqlSingleLine)  the SQL statement as submitted to the driver, with all new lines removed
     *     #   %(sql)                     the SQL statement with all bind variables replaced with actual values
     *     #   %(sqlSingleLine)           the SQL statement with all bind variables replaced with actual values, with all new lines removed
     *     #customLogMessageFormat=%(currentTime)|%(executionTime)|%(category)|connection%(connectionId)|%(sqlSingleLine)
     */
    private String customLogMessageFormat;
    /**
     *     # format that is used for logging of the java.util.Date implementations (has to be compatible with java.text.SimpleDateFormat)
     *     # (default is yyyy-MM-dd'T'HH:mm:ss.SSSZ)
     *     #databaseDialectDateFormat=yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    private String databaseDialectDateFormat;
    /**
     *     # format that is used for logging of the java.sql.Timestamp implementations (has to be compatible with java.text.SimpleDateFormat)
     *     # (default is yyyy-MM-dd'T'HH:mm:ss.SSSZ)
     *     #databaseDialectTimestampFormat=yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    private String databaseDialectTimestampFormat;
    /**
     *     # format that is used for logging booleans, possible values: boolean, numeric
     *     # (default is boolean)
     *     #databaseDialectBooleanFormat=boolean
     */
    private String databaseDialectBooleanFormat;
    /**
     *     # whether to expose options via JMX or not
     *     # (default is true)
     *     #jmx=true
     */
    private String jmx;
    /**
     *     # if exposing options via jmx (see option: jmx), what should be the prefix used?
     *     # jmx naming pattern constructed is: com.p6spy(.<jmxPrefix>)?:name=<optionsClassName>
     *     # please note, if there is already such a name in use it would be unregistered first (the last registered wins)
     *     # (default is none)
     *     #jmxPrefix=
     */
    private String jmxPrefix;


    public String getAutoflush() {
        return autoflush;
    }

    public void setAutoflush(String autoflush) {
        this.autoflush = autoflush;
    }

    public String getDriverlist() {
        return driverlist;
    }

    public void setDriverlist(String driverlist) {
        this.driverlist = driverlist;
    }

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public String getLogMessageFormat() {
        return logMessageFormat;
    }

    public void setLogMessageFormat(String logMessageFormat) {
        this.logMessageFormat = logMessageFormat;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getDateformat() {
        return dateformat;
    }

    public void setDateformat(String dateformat) {
        this.dateformat = dateformat;
    }

    public String getAppender() {
        return appender;
    }

    public void setAppender(String appender) {
        this.appender = appender;
    }

    public String getModulelist() {
        return modulelist;
    }

    public void setModulelist(String modulelist) {
        this.modulelist = modulelist;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }

    public String getStacktraceclass() {
        return stacktraceclass;
    }

    public void setStacktraceclass(String stacktraceclass) {
        this.stacktraceclass = stacktraceclass;
    }

    public String getReloadproperties() {
        return reloadproperties;
    }

    public void setReloadproperties(String reloadproperties) {
        this.reloadproperties = reloadproperties;
    }

    public String getReloadpropertiesinterval() {
        return reloadpropertiesinterval;
    }

    public void setReloadpropertiesinterval(String reloadpropertiesinterval) {
        this.reloadpropertiesinterval = reloadpropertiesinterval;
    }

    public String getJndicontextfactory() {
        return jndicontextfactory;
    }

    public void setJndicontextfactory(String jndicontextfactory) {
        this.jndicontextfactory = jndicontextfactory;
    }

    public String getJndicontextproviderurl() {
        return jndicontextproviderurl;
    }

    public void setJndicontextproviderurl(String jndicontextproviderurl) {
        this.jndicontextproviderurl = jndicontextproviderurl;
    }

    public String getJndicontextcustom() {
        return jndicontextcustom;
    }

    public void setJndicontextcustom(String jndicontextcustom) {
        this.jndicontextcustom = jndicontextcustom;
    }

    public String getRealdatasource() {
        return realdatasource;
    }

    public void setRealdatasource(String realdatasource) {
        this.realdatasource = realdatasource;
    }

    public String getRealdatasourceclass() {
        return realdatasourceclass;
    }

    public void setRealdatasourceclass(String realdatasourceclass) {
        this.realdatasourceclass = realdatasourceclass;
    }

    public String getRealdatasourceproperties() {
        return realdatasourceproperties;
    }

    public void setRealdatasourceproperties(String realdatasourceproperties) {
        this.realdatasourceproperties = realdatasourceproperties;
    }

    public String getCustomLogMessageFormat() {
        return customLogMessageFormat;
    }

    public void setCustomLogMessageFormat(String customLogMessageFormat) {
        this.customLogMessageFormat = customLogMessageFormat;
    }

    public String getDatabaseDialectDateFormat() {
        return databaseDialectDateFormat;
    }

    public void setDatabaseDialectDateFormat(String databaseDialectDateFormat) {
        this.databaseDialectDateFormat = databaseDialectDateFormat;
    }

    public String getDatabaseDialectTimestampFormat() {
        return databaseDialectTimestampFormat;
    }

    public void setDatabaseDialectTimestampFormat(String databaseDialectTimestampFormat) {
        this.databaseDialectTimestampFormat = databaseDialectTimestampFormat;
    }

    public String getDatabaseDialectBooleanFormat() {
        return databaseDialectBooleanFormat;
    }

    public void setDatabaseDialectBooleanFormat(String databaseDialectBooleanFormat) {
        this.databaseDialectBooleanFormat = databaseDialectBooleanFormat;
    }

    public String getJmx() {
        return jmx;
    }

    public void setJmx(String jmx) {
        this.jmx = jmx;
    }

    public String getJmxPrefix() {
        return jmxPrefix;
    }

    public void setJmxPrefix(String jmxPrefix) {
        this.jmxPrefix = jmxPrefix;
    }

}
