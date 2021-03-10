/**
 * Copyright (c) 2015-2017.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oschina.j2cache.hibernate4.log;

import org.hibernate.LockMode;
import org.hibernate.cache.CacheException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.dialect.spi.DialectResolver;
import org.hibernate.engine.jndi.JndiNameException;
import org.hibernate.engine.loading.internal.CollectionLoadContext;
import org.hibernate.engine.loading.internal.EntityLoadContext;
import org.hibernate.engine.spi.CollectionKey;
import org.hibernate.id.IntegralDataTypeHolder;
import org.hibernate.internal.CoreMessageLogger;
import org.hibernate.type.BasicType;
import org.hibernate.type.SerializationException;
import org.hibernate.type.Type;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;

import javax.annotation.Generated;
import javax.naming.NameNotFoundException;
import javax.transaction.SystemException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.SQLWarning;
import java.util.Hashtable;

@Generated(value = "org.jboss.logging.processor.generator.model.MessageLoggerImplementor", date = "2015-01-06T12:13:01-0800")
public class J2CacheMessageLogger_$logger extends DelegatingBasicLogger implements Serializable, J2CacheMessageLogger, CoreMessageLogger, BasicLogger {

    private final static long serialVersionUID = 1L;
    private final static String FQCN = J2CacheMessageLogger_$logger.class.getName();
    private final static String jdbcAutoCommitFalseBreaksEjb3Spec = "HHH000144: %s = false breaks the EJB3 specification";
    private final static String illegalPropertyGetterArgument = "HHH000122: IllegalArgumentException in class: %s, getter method of property: %s";
    private final static String unableToMarkForRollbackOnPersistenceException = "HHH000337: Unable to mark for rollback on PersistenceException: ";
    private final static String unableToLoadConfiguration = "HHH020004: A configurationResourceName was set to %s but the resource could not be loaded from the classpath. J2Cache will configure itself using defaults.";
    private final static String hql = "HHH000117: HQL: %s, time: %sms, rows: %s";
    private final static String logicalConnectionReleasingPhysicalConnection = "HHH000163: Logical connection releasing its physical connection";
    private final static String unexpectedLiteralTokenType = "HHH000380: Unexpected literal token type [%s] passed for numeric processing";
    private final static String logicalConnectionClosed = "HHH000162: *** Logical connection closed ***";
    private final static String unableToAccessSessionFactory = "HHH000272: Error while accessing session factory with JNDI name %s";
    private final static String factoryBoundToJndiName = "HHH000094: Bound factory to JNDI name: %s";
    private final static String unregisteredStatement = "HHH000387: ResultSet's statement was not registered";
    private final static String unableToUpdateHiValue = "HHH000375: Could not update hi value in: %s";
    private final static String naturalIdCacheHits = "HHH000439: NaturalId cache hits: %s";
    private final static String unableToBindFactoryToJndi = "HHH000277: Could not bind factory to JNDI";
    private final static String usingUuidHexGenerator = "HHH000409: Using %s which does not generate IETF RFC 4122 compliant UUID values; consider using %s instead";
    private final static String unableToLocateConfigFile = "HHH000330: Unable to locate config file: %s";
    private final static String unableToGetDatabaseMetadata = "HHH000319: Could not get database metadata";
    private final static String unableToCloseSessionDuringRollback = "HHH000295: Could not close session during rollback";
    private final static String entityAnnotationOnNonRoot = "HHH000081: @org.hibernate.annotations.Entity used on a non root entity: ignored for %s";
    private final static String duplicateGeneratorTable = "HHH000070: Duplicate generator table: %s";
    private final static String unableToReleaseCacheLock = "HHH000353: Could not release a cache lock : %s";
    private final static String exceptionInSubResolver = "HHH000089: Sub-resolver threw unexpected exception, continuing to next : %s";
    private final static String compositeIdClassDoesNotOverrideEquals = "HHH000038: Composite-id class does not override equals(): %s";
    private final static String unableToRetrieveTypeInfoResultSet = "HHH000362: Unable to retrieve type info result set : %s";
    private final static String deprecatedOracle9Dialect = "HHH000063: The Oracle9Dialect dialect has been deprecated; use either Oracle9iDialect or Oracle10gDialect instead";
    private final static String guidGenerated = "HHH000113: GUID identifier generated: %s";
    private final static String providerClassDeprecated = "HHH000208: %s has been deprecated in favor of %s; that provider will be used instead.";
    private final static String unableToUpdateQueryHiValue = "HHH000376: Could not updateQuery hi value in: %s";
    private final static String unableToCloseIterator = "HHH000289: Unable to close iterator";
    private final static String readingCachedMappings = "HHH000219: Reading mappings from cache file: %s";
    private final static String disablingContextualLOBCreationSinceConnectionNull = "HHH000422: Disabling contextual LOB creation as connection was null";
    private final static String schemaUpdateComplete = "HHH000232: Schema update complete";
    private final static String entityManagerFactoryAlreadyRegistered = "HHH000436: Entity manager factory name (%s) is already registered.  If entity manager will be clustered or passivated, specify a unique value for property '%s'";
    private final static String unableToCleanUpCallableStatement = "HHH000281: Unable to clean up callable statement";
    private final static String entitiesInserted = "HHH000078: Entities inserted: %s";
    private final static String namedQueryError = "HHH000177: Error in named query: %s";
    private final static String usingStreams = "HHH000407: Using java.io streams to persist binary types";
    private final static String alreadySessionBound = "HHH000002: Already session bound on call to bind(); make sure you clean up your sessions!";
    private final static String ignoringUnrecognizedQueryHint = "HHH000121: Ignoring unrecognized query hint [%s]";
    private final static String duplicateJoins = "HHH000072: Duplicate joins for class: %s";
    private final static String unableToResolveMappingFile = "HHH000360: Unable to resolve mapping file [%s]";
    private final static String disallowingInsertStatementComment = "HHH000067: Disallowing insert statement comment for select-identity due to Oracle driver bug";
    private final static String loggingStatistics = "HHH000161: Logging statistics....";
    private final static String unableToClosePooledConnection = "HHH000293: Problem closing pooled connection";
    private final static String noPersistentClassesFound = "HHH000183: no persistent classes found for query class: %s";
    private final static String rollbackFromBackgroundThread = "HHH000451: Transaction afterCompletion called by a background thread; delaying afterCompletion processing until the original thread can handle it. [status=%s]";
    private final static String resolvedSqlTypeDescriptorForDifferentSqlCode = "HHH000419: Resolved SqlTypeDescriptor is for a different SQL code. %s has sqlCode=%s; type override %s has sqlCode=%s";
    private final static String unableToReleaseIsolatedConnection = "HHH000356: Unable to release isolated connection [%s]";
    private final static String unableToCloseJar = "HHH000290: Could not close jar: %s";
    private final static String unableToCreateProxyFactory = "HHH000305: Could not create proxy factory for:%s";
    private final static String unableToDetermineLockModeValue = "HHH000311: Unable to determine lock mode value : %s -> %s";
    private final static String duplicateMetadata = "HHH000074: Found more than one <persistence-unit-metadata>, subsequent ignored";
    private final static String orderByAnnotationIndexedCollection = "HHH000189: @OrderBy not allowed for an indexed collection, annotation ignored.";
    private final static String schemaExportComplete = "HHH000230: Schema export complete";
    private final static String configuredSessionFactory = "HHH000041: Configured SessionFactory: %s";
    private final static String settersOfLazyClassesCannotBeFinal = "HHH000243: Setters of lazy classes cannot be final: %s.%s";
    private final static String unableToCreateSchema = "HHH000306: Error creating schema ";
    private final static String overridingTransactionStrategyDangerous = "HHH000193: Overriding %s is dangerous, this might break the EJB3 specification implementation";
    private final static String deprecatedManyToManyOuterJoin = "HHH000454: The outer-join attribute on <many-to-many> has been deprecated. Instead of outer-join=\"false\", use lazy=\"extra\" with <map>, <set>, <bag>, <idbag>, or <list>, which will only initialize entities (not as a proxy) as needed.";
    private final static String unableToReadColumnValueFromResultSet = "HHH000349: Could not read column value from result set: %s; %s";
    private final static String unableToLocateConfiguredSchemaNameResolver = "HHH000331: Unable to locate configured schema name resolver class [%s] %s";
    private final static String collectionsUpdated = "HHH000036: Collections updated: %s";
    private final static String writeLocksNotSupported = "HHH000416: Write locks via update not supported for non-versioned entities [%s]";
    private final static String unsuccessfulCreate = "HHH000389: Unsuccessful: %s";
    private final static String invalidOnDeleteAnnotation = "HHH000136: Inapropriate use of @OnDelete on entity, annotation ignored: %s";
    private final static String jndiInitialContextProperties = "HHH000154: JNDI InitialContext properties:%s";
    private final static String secondLevelCacheMisses = "HHH000238: Second level cache misses: %s";
    private final static String exceptionInAfterTransactionCompletionInterceptor = "HHH000087: Exception in interceptor afterTransactionCompletion()";
    private final static String readOnlyCacheConfiguredForMutableEntity = "HHH020007: read-only cache configured for mutable entity [%s]";
    private final static String unableToPerformJdbcCommit = "HHH000345: JDBC commit failed";
    private final static String unableToExecuteBatch = "HHH000315: Exception executing batch [%s]";
    private final static String javassistEnhancementFailed = "HHH000142: Javassist Enhancement failed: %s";
    private final static String naturalIdCacheMisses = "HHH000440: NaturalId cache misses: %s";
    private final static String entityManagerClosedBySomeoneElse = "HHH000082: Entity Manager closed by someone else (%s must not be used)";
    private final static String propertiesLoaded = "HHH000205: Loaded properties from resource hibernate.properties: %s";
    private final static String unableToCloseStream = "HHH000296: IOException occurred closing stream";
    private final static String invalidDiscriminatorAnnotation = "HHH000133: Discriminator column has to be defined in the root entity, it will be ignored in subclass: %s";
    private final static String jdbcUrlNotSpecified = "HHH000152: JDBC URL was not specified by property %s";
    private final static String entityMappedAsNonAbstract = "HHH000084: Entity [%s] is abstract-class/interface explicitly mapped as non-abstract; be sure to supply entity-names";
    private final static String duplicateListener = "HHH000073: entity-listener duplication, first event definition will be used: %s";
    private final static String processingPersistenceUnitInfoName = "HHH000204: Processing PersistenceUnitInfo [\n\tname: %s\n\t...]";
    private final static String statementsClosed = "HHH000252: Statements closed: %s";
    private final static String hibernateConnectionPoolSize = "HHH000115: Hibernate connection pool size: %s (min=%s)";
    private final static String unableToInstantiateConfiguredSchemaNameResolver = "HHH000320: Unable to instantiate configured schema name resolver [%s] %s";
    private final static String unableToObtainInitialContext = "HHH000343: Could not obtain initial context";
    private final static String collectionsFetched = "HHH000032: Collections fetched (minimize this): %s";
    private final static String pooledOptimizerReportedInitialValue = "HHH000201: Pooled optimizer source reported [%s] as the initial value; use of 1 or greater highly recommended";
    private final static String factoryUnboundFromJndiName = "HHH000097: Unbound factory from JNDI name: %s";
    private final static String naturalIdMaxQueryTime = "HHH000441: Max NaturalId query time: %sms";
    private final static String statementsPrepared = "HHH000253: Statements prepared: %s";
    private final static String unableToStopService = "HHH000369: Error stopping service [%s] : %s";
    private final static String unregisteredResultSetWithoutStatement = "HHH000386: ResultSet had no statement associated with it, but was not yet registered";
    private final static String unableToRollbackConnection = "HHH000363: Unable to rollback connection on exception [%s]";
    private final static String transactions = "HHH000266: Transactions: %s";
    private final static String forcingTableUse = "HHH000107: Forcing table use for sequence-style generator due to pooled optimizer selection where db does not support pooled sequences";
    private final static String cacheProvider = "HHH000024: Cache provider: %s";
    private final static String unableToLogSqlWarnings = "HHH000335: Unable to log SQLWarnings : %s";
    private final static String unableToCompleteSchemaUpdate = "HHH000299: Could not complete schema update";
    private final static String unableToReleaseBatchStatement = "HHH000352: Unable to release batch statement...";
    private final static String sqlWarning = "HHH000247: SQL Error: %s, SQLState: %s";
    private final static String unexpectedRowCounts = "HHH000381: JDBC driver did not return the expected number of row counts";
    private final static String stoppingService = "HHH000255: Stopping service";
    private final static String invalidTableAnnotation = "HHH000139: Illegal use of @Table in a subclass of a SINGLE_TABLE hierarchy: %s";
    private final static String containsJoinFetchedCollection = "HHH000051: Ignoring bag join fetch [%s] due to prior collection join fetch";
    private final static String unableToRunSchemaUpdate = "HHH000366: Error running schema update";
    private final static String unableToAccessEjb3Configuration = "HHH000271: Naming exception occurred accessing Ejb3Configuration";
    private final static String readingMappingsFromResource = "HHH000221: Reading mappings from resource: %s";
    private final static String unableToObjectConnectionMetadata = "HHH000339: Could not obtain connection metadata: %s";
    private final static String definingFlushBeforeCompletionIgnoredInHem = "HHH000059: Defining %s=true ignored in HEM";
    private final static String unableToCloseConnection = "HHH000284: Error closing connection";
    private final static String foundMappingDocument = "HHH000109: Found mapping document in jar: %s";
    private final static String deprecatedForceDescriminatorAnnotation = "HHH000062: @ForceDiscriminator is deprecated use @DiscriminatorOptions instead.";
    private final static String unableToWrapResultSet = "HHH000377: Error wrapping result set";
    private final static String unableToReleaseContext = "HHH000354: Unable to release initial context: %s";
    private final static String tableFound = "HHH000261: Table found: %s";
    private final static String setManagerLookupClass = "HHH000426: You should set hibernate.transaction.jta.platform if cache is enabled";
    private final static String version = "HHH000412: Hibernate Core {%s}";
    private final static String unableToQueryDatabaseMetadata = "HHH000347: Unable to query java.sql.DatabaseMetaData";
    private final static String bytecodeProvider = "HHH000021: Bytecode provider name : %s";
    private final static String writingGeneratedSchemaToFile = "HHH000417: Writing generated schema to file: %s";
    private final static String noAppropriateConnectionProvider = "HHH000181: No appropriate connection provider encountered, assuming application will be supplying connections";
    private final static String unableToConstructCurrentSessionContext = "HHH000302: Unable to construct current session context [%s]";
    private final static String honoringOptimizerSetting = "HHH000116: Config specified explicit optimizer of [%s], but [%s=%s; honoring optimizer setting";
    private final static String unableToCloseInitialContext = "HHH000285: Error closing InitialContext [%s]";
    private final static String hsqldbSupportsOnlyReadCommittedIsolation = "HHH000118: HSQLDB supports only READ_UNCOMMITTED isolation";
    private final static String noDefaultConstructor = "HHH000182: No default (no-argument) constructor for class: %s (class must be instantiated by Interceptor)";
    private final static String unableToCloseInputFiles = "HHH000286: Error closing input files: %s";
    private final static String synchronizationAlreadyRegistered = "HHH000259: Synchronization [%s] was already registered";
    private final static String invalidEditOfReadOnlyItem = "HHH000134: Application attempted to edit read only item: %s";
    private final static String connectionsObtained = "HHH000048: Connections obtained: %s";
    private final static String scopingTypesToSessionFactoryAfterAlreadyScoped = "HHH000233: Scoping types to session factory %s after already scoped %s";
    private final static String deprecatedManyToManyFetch = "HHH000455: The fetch attribute on <many-to-many> has been deprecated. Instead of fetch=\"select\", use lazy=\"extra\" with <map>, <set>, <bag>, <idbag>, or <list>, which will only initialize entities (not as a proxy) as needed.";
    private final static String sessionsClosed = "HHH000241: Sessions closed: %s";
    private final static String entitiesUpdated = "HHH000080: Entities updated: %s";
    private final static String runningSchemaValidator = "HHH000229: Running schema validator";
    private final static String jdbcRollbackFailed = "HHH000151: JDBC rollback failed";
    private final static String namingExceptionAccessingFactory = "HHH000178: Naming exception occurred accessing factory: %s";
    private final static String runningHbm2ddlSchemaExport = "HHH000227: Running hbm2ddl schema export";
    private final static String startingUpdateTimestampsCache = "HHH000250: Starting update timestamps cache at region: %s";
    private final static String alternateServiceRole = "HHH000450: Encountered request for Service by non-primary service role [%s -> %s]; please update usage";
    private final static String unableToLocateMBeanServer = "HHH000332: Unable to locate MBeanServer on JMX service shutdown";
    private final static String connectionProperties = "HHH000046: Connection properties: %s";
    private final static String batchContainedStatementsOnRelease = "HHH000010: On release of batch it still contained JDBC statements";
    private final static String usingReflectionOptimizer = "HHH000406: Using bytecode reflection optimizer";
    private final static String unableToLoadScannedClassOrResource = "HHH000452: Exception while loading a class or resource found during scanning";
    private final static String unableToCloseOutputFile = "HHH000291: Error closing output file: %s";
    private final static String invalidSubStrategy = "HHH000138: Mixing inheritance strategy in a entity hierarchy is not allowed, ignoring sub strategy in: %s";
    private final static String callingJoinTransactionOnNonJtaEntityManager = "HHH000027: Calling joinTransaction() on a non JTA EntityManager";
    private final static String attemptToRestartAlreadyStartedJ2CacheProvider = "HHH020001: Attempt to restart an already started J2CacheRegionFactory.  Use sessionFactory.close() between repeated calls to buildSessionFactory. Using previously created J2CacheRegionFactory. If this behaviour is required, consider using org.hibernate.cache.j2cache.SingletonJ2CacheRegionFactory.";
    private final static String maxQueryTime = "HHH000173: Max query time: %sms";
    private final static String unableToSetTransactionToRollbackOnly = "HHH000367: Could not set transaction to rollback only";
    private final static String propertyNotFound = "HHH000207: Property %s not found in class but described in <mapping-file/> (possible typo error)";
    private final static String duplicateImport = "HHH000071: Duplicate import: %s -> %s";
    private final static String firstOrMaxResultsSpecifiedWithCollectionFetch = "HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!";
    private final static String unableToToggleAutoCommit = "HHH000372: Could not toggle autocommit";
    private final static String nonCompliantMapConversion = "HHH000449: @Convert annotation applied to Map attribute [%s] did not explicitly specify attributeName using 'key'/'value' as required by spec; attempting to DoTheRightThing";
    private final static String immutableAnnotationOnNonRoot = "HHH000124: @Immutable used on a non root entity: ignored for %s";
    private final static String noColumnsSpecifiedForIndex = "HHH000432: There were not column names specified for index %s on table %s";
    private final static String usingFollowOnLocking = "HHH000444: Encountered request for locking however dialect reports that database prefers locking be done in a separate select (follow-on locking); results will be locked after initial query executes";
    private final static String unableToAccessTypeInfoResultSet = "HHH000273: Error accessing type info result set : %s";
    private final static String optimisticLockFailures = "HHH000187: Optimistic lock failures: %s";
    private final static String lazyPropertyFetchingAvailable = "HHH000157: Lazy property fetching available for: %s";
    private final static String unsupportedNamedParameters = "HHH000456: Named parameters are used for a callable statement, but database metadata indicates named parameters are not supported.";
    private final static String failSafeEntitiesCleanup = "HHH000101: Fail-safe cleanup (entities) : %s";
    private final static String unableToDeserializeCache = "HHH000307: Could not deserialize cache file: %s : %s";
    private final static String factoryUnboundFromName = "HHH000098: A factory was unbound from name: %s";
    private final static String legacyTransactionManagerStrategy = "HHH000428: Encountered legacy TransactionManagerLookup specified; convert to newer %s contract specified via %s setting";
    private final static String unsupportedAfterStatement = "HHH000390: Overriding release mode as connection provider does not support 'after_statement'";
    private final static String packageNotFound = "HHH000194: Package not found or wo package-info.java: %s";
    private final static String embedXmlAttributesNoLongerSupported = "HHH000446: embed-xml attributes were intended to be used for DOM4J entity mode. Since that entity mode has been removed, embed-xml attributes are no longer supported and should be removed from mappings.";
    private final static String parsingXmlError = "HHH000196: Error parsing XML (%s) : %s";
    private final static String illegalPropertySetterArgument = "HHH000123: IllegalArgumentException in class: %s, setter method of property: %s";
    private final static String expired = "HHH000092: An item was expired by the cache while it was locked (increase your cache timeout): %s";
    private final static String indexes = "HHH000126: Indexes: %s";
    private final static String containerProvidingNullPersistenceUnitRootUrl = "HHH000050: Container is providing a null PersistenceUnitRootUrl: discovery impossible";
    private final static java.lang.String logUnexpectedSessionInCollectionNotConnected = "HHH000470: An unexpected session is defined for a collection, but the collection is not connected to that session. A persistent collection may only be associated with one session at a time. Overwriting session. %s";
    private final static String tooManyInExpressions = "HHH000443: Dialect [%s] limits the number of elements in an IN predicate to %s entries.  However, the given parameter list [%s] contained %s entries, which will likely cause failures to execute the query in the database";
    private final static String timestampCacheHits = "HHH000434: update timestamps cache hits: %s";
    private final static String unableToSynchronizeDatabaseStateWithSession = "HHH000371: Could not synchronize database state with session: %s";
    private final static String foreignKeys = "HHH000108: Foreign keys: %s";
    private final static String entitiesDeleted = "HHH000076: Entities deleted: %s";
    private final static String splitQueries = "HHH000245: Manipulation query [%s] resulted in [%s] split queries";
    private final static String usingDriver = "HHH000401: using driver [%s] at URL [%s]";
    private final static String hydratingEntitiesCount = "HHH000119: On EntityLoadContext#clear, hydratingEntities contained [%s] entries";
    private final static String successfulTransactions = "HHH000258: Successful transactions: %s";
    private final static String renamedProperty = "HHH000225: Property [%s] has been renamed to [%s]; update your properties appropriately";
    private final static String runningHbm2ddlSchemaUpdate = "HHH000228: Running hbm2ddl schema update";
    private final static String unableToCloseOutputStream = "HHH000292: IOException occurred closing output stream";
    private final static String unableToExecuteResolver = "HHH000316: Error executing resolver [%s] : %s";
    private final static String unableToConfigureSqlExceptionConverter = "HHH000301: Unable to configure SQLExceptionConverter : %s";
    private final static String unableToDestroyQueryCache = "HHH000309: Unable to destroy query cache: %s: %s";
    private final static String synchronizationFailed = "HHH000260: Exception calling user Synchronization [%s] : %s";
    private final static String unableToLocateUuidGenerationStrategy = "HHH000334: Unable to locate requested UUID generation strategy class : %s";
    private final static String disablingContextualLOBCreationSinceOldJdbcVersion = "HHH000423: Disabling contextual LOB creation as JDBC driver reported JDBC version [%s] less than 4";
    private final static String autoFlushWillNotWork = "HHH000008: JTASessionContext being used with JDBCTransactionFactory; auto-flush will not operate correctly with getCurrentSession()";
    private final static String usingTimestampWorkaround = "HHH000408: Using workaround for JVM bug in java.sql.Timestamp";
    private final static String parsingXmlWarning = "HHH000198: Warning parsing XML (%s) : %s";
    private final static String processEqualityExpression = "HHH000203: processEqualityExpression() : No expression to process!";
    private final static String startTime = "HHH000251: Start time: %s";
    private final static String unableToReleaseTypeInfoResultSet = "HHH000357: Unable to release type info result set";
    private final static String unableToRetrieveCache = "HHH000361: Unable to retreive cache from JNDI [%s]: %s";
    private final static String rdmsOs2200Dialect = "HHH000218: RDMSOS2200Dialect version: 1.0";
    private final static String unableToParseMetadata = "HHH000344: Could not parse the package-level metadata [%s]";
    private final static String unableToLocateCustomOptimizerClass = "HHH000321: Unable to interpret specified optimizer [%s], falling back to noop";
    private final static String unableToDetermineTransactionStatus = "HHH000312: Could not determine transaction status";
    private final static String sortAnnotationIndexedCollection = "HHH000244: @Sort not allowed for an indexed collection, annotation ignored.";
    private final static String unknownOracleVersion = "HHH000384: Unknown Oracle major version [%s]";
    private final static String unableToJoinTransaction = "HHH000326: Cannot join transaction: do not override %s";
    private final static String unableToStopHibernateService = "HHH000368: Exception while stopping service";
    private final static String instantiatingExplicitConnectionProvider = "HHH000130: Instantiating explicit connection provider: %s";
    private final static String readOnlyCacheConfiguredForMutableCollection = "HHH000222: read-only cache configured for mutable collection [%s]";
    private final static String loadingCollectionKeyNotFound = "HHH000159: In CollectionLoadContext#endLoadingCollections, localLoadingCollectionKeys contained [%s], but no LoadingCollectionEntry was found in loadContexts";
    private final static String multipleValidationModes = "HHH000448: 'javax.persistence.validation.mode' named multiple values : %s";
    private final static String unableToUnbindFactoryFromJndi = "HHH000374: Could not unbind factory from JNDI";
    private final static String unableToTransformClass = "HHH000373: Unable to transform class: %s";
    private final static String unableToCloseInputStream = "HHH000287: Could not close input stream";
    private final static String typeRegistrationOverridesPrevious = "HHH000270: Type registration [%s] overrides previous : %s";
    private final static String jdbcIsolationLevel = "HHH000149: JDBC isolation level: %s";
    private final static String persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly = "HHH000200: Persistence provider caller does not implement the EJB3 spec correctly.PersistenceUnitInfo.getNewTempClassLoader() is null.";
    private final static String queryCacheMisses = "HHH000214: Query cache misses: %s";
    private final static String collectionsRecreated = "HHH000034: Collections recreated: %s";
    private final static String unableToBuildSessionFactoryUsingMBeanClasspath = "HHH000280: Could not build SessionFactory using the MBean classpath - will try again using client classpath: %s";
    private final static String incompleteMappingMetadataCacheProcessing = "HHH000125: Mapping metadata cache was not completely processed";
    private final static String unableToConstructSqlExceptionConverter = "HHH000303: Unable to construct instance of specified SQLExceptionConverter : %s";
    private final static String collectionsRemoved = "HHH000035: Collections removed: %s";
    private final static String invalidArrayElementType = "HHH000132: Array element type error\n%s";
    private final static String unableToObtainConnectionMetadata = "HHH000341: Could not obtain connection metadata : %s";
    private final static String unableToReadHiValue = "HHH000350: Could not read a hi value - you need to populate the table: %s";
    private final static String schemaExportUnsuccessful = "HHH000231: Schema export unsuccessful";
    private final static String undeterminedH2Version = "HHH000431: Unable to determine H2 database version, certain features may not work";
    private final static String deprecatedOracleDialect = "HHH000064: The OracleDialect dialect has been deprecated; use Oracle8iDialect instead";
    private final static String applyingExplicitDiscriminatorColumnForJoined = "HHH000457: Joined inheritance hierarchy [%1$s] defined explicit @DiscriminatorColumn.  Legacy Hibernate behavior was to ignore the @DiscriminatorColumn.  However, as part of issue HHH-6911 we now apply the explicit @DiscriminatorColumn.  If you would prefer the legacy behavior, enable the `%2$s` setting (%2$s=true)";
    private final static String unableToLoadDerbyDriver = "HHH000328: Unable to load/access derby driver class sysinfo to check versions : %s";
    private final static String queryCachePuts = "HHH000215: Query cache puts: %s";
    private final static String unableToCloseSessionButSwallowingError = "HHH000425: Could not close session; swallowing exception[%s] as transaction completed";
    private final static String unableToInstantiateOptimizer = "HHH000322: Unable to instantiate specified optimizer [%s], falling back to noop";
    private final static String configuringFromFile = "HHH000042: Configuring from file: %s";
    private final static String noSessionFactoryWithJndiName = "HHH000184: No session factory with JNDI name %s";
    private final static java.lang.String logCannotUnsetUnexpectedSessionInCollection = "HHH000471: Cannot unset session in a collection because an unexpected session is defined. A persistent collection may only be associated with one session at a time. %s";
    private final static String closing = "HHH000031: Closing";
    private final static String preparedStatementAlreadyInBatch = "HHH000202: PreparedStatement was already in the batch, [%s].";
    private final static String cannotResolveNonNullableTransientDependencies = "HHH000437: Attempting to save one or more entities that have a non-nullable association with an unsaved transient entity. The unsaved transient entity must be saved in an operation prior to saving these dependent entities.\n\tUnsaved transient entity: (%s)\n\tDependent entities: (%s)\n\tNon-nullable association(s): (%s)";
    private final static String unableToDetermineTransactionStatusAfterCommit = "HHH000313: Could not determine transaction status after commit";
    private final static String unableToRollbackJta = "HHH000365: JTA rollback failed";
    private final static String unknownSqlServerVersion = "HHH000385: Unknown Microsoft SQL Server major version [%s] using SQL Server 2000 dialect";
    private final static String localLoadingCollectionKeysCount = "HHH000160: On CollectionLoadContext#cleanup, localLoadingCollectionKeys contained [%s] entries";
    private final static String unableToCloseStreamError = "HHH000297: Could not close stream on hibernate.properties: %s";
    private final static String jdbcDriverNotSpecified = "HHH000148: No JDBC Driver class was specified by property %s";
    private final static String subResolverException = "HHH000257: sub-resolver threw unexpected exception, continuing to next : %s";
    private final static String cachedFileNotFound = "HHH000023: I/O reported cached file could not be found : %s : %s";
    private final static String unableToInstantiateUuidGenerationStrategy = "HHH000325: Unable to instantiate UUID generation strategy class : %s";
    private final static String transactionStartedOnNonRootSession = "HHH000267: Transaction started on non-root session";
    private final static String jaccContextId = "HHH000140: JACC contextID: %s";
    private final static String gettersOfLazyClassesCannotBeFinal = "HHH000112: Getters of lazy classes cannot be final: %s.%s";
    private final static String entitiesLoaded = "HHH000079: Entities loaded: %s";
    private final static String unsuccessful = "HHH000388: Unsuccessful: %s";
    private final static String unsupportedOracleVersion = "HHH000394: Oracle 11g is not yet fully supported; using Oracle 10g dialect";
    private final static String serviceProperties = "HHH000240: Service properties: %s";
    private final static String unsupportedMultiTableBulkHqlJpaql = "HHH000393: The %s.%s.%s version of H2 implements temporary table creation such that it commits current transaction; multi-table, bulk hql/jpaql will not work properly";
    private final static String entityIdentifierValueBindingExists = "HHH000429: Setting entity-identifier value binding where one already existed : %s.";
    private final static String unableToLoadCommand = "HHH000327: Error performing load command : %s";
    private final static String unableToPerformManagedFlush = "HHH000346: Error during managed flush [%s]";
    private final static String secondLevelCacheHits = "HHH000237: Second level cache hits: %s";
    private final static String usingDialect = "HHH000400: Using dialect: %s";
    private final static String unableToDropTemporaryIdTable = "HHH000314: Unable to drop temporary id table after use [%s]";
    private final static String unableToFindJ2CacheConfiguration = "HHH020003: Could not find a specific j2cache configuration for cache named [%s]; using defaults.";
    private final static String usingAstQueryTranslatorFactory = "HHH000397: Using ASTQueryTranslatorFactory";
    private final static String unableToReleaseCreatedMBeanServer = "HHH000355: Unable to release created MBeanServer : %s";
    private final static String invalidJndiName = "HHH000135: Invalid JNDI name: %s";
    private final static String failSafeCollectionsCleanup = "HHH000100: Fail-safe cleanup (collections) : %s";
    private final static String needsLimit = "HHH000180: FirstResult/maxResults specified on polymorphic query; applying in memory!";
    private final static String fetchingDatabaseMetadata = "HHH000102: Fetching database metadata";
    private final static String configuringFromUrl = "HHH000044: Configuring from URL: %s";
    private final static String timestampCachePuts = "HHH000433: update timestamps cache puts: %s";
    private final static String explicitSkipLockedLockCombo = "HHH000447: Explicit use of UPGRADE_SKIPLOCKED in lock() calls is not recommended; use normal UPGRADE locking instead";
    private final static String forcingContainerResourceCleanup = "HHH000106: Forcing container resource cleanup on transaction completion";
    private final static String deprecatedUuidGenerator = "HHH000065: DEPRECATED : use [%s] instead with custom [%s] implementation";
    private final static String unableToFindConfiguration = "HHH020002: Could not find configuration [%s]; using defaults.";
    private final static String autoCommitMode = "HHH000006: Autocommit mode: %s";
    private final static String willNotRegisterListeners = "HHH000414: Property hibernate.search.autoregister_listeners is set to false. No attempt will be made to register Hibernate Search event listeners.";
    private final static String tableNotFound = "HHH000262: Table not found: %s";
    private final static String unableToLoadProperties = "HHH000329: Problem loading properties from hibernate.properties";
    private final static String queryCacheHits = "HHH000213: Query cache hits: %s";
    private final static String unableToCommitJta = "HHH000298: JTA commit failed";
    private final static String unableToDestroyUpdateTimestampsCache = "HHH000310: Unable to destroy update timestamps cache: %s: %s";
    private final static String unableToCloseInputStreamForResource = "HHH000288: Could not close input stream for %s";
    private final static String unableToReadClass = "HHH000348: Unable to read class: %s";
    private final static String deprecatedDerbyDialect = "HHH000430: The DerbyDialect dialect has been deprecated; use one of the version-specific dialects instead";
    private final static String addingOverrideFor = "HHH000418: Adding override for %s: %s";
    private final static String unableToCopySystemProperties = "HHH000304: Could not copy system properties, system properties will be ignored";
    private final static String incompatibleCacheValueMode = "HHH020005: The default cache value mode for this Ehcache configuration is \"identity\". This is incompatible with clustered Hibernate caching - the value mode has therefore been switched to \"serialization\"";
    private final static String secondLevelCachePuts = "HHH000239: Second level cache puts: %s";
    private final static String ignoringTableGeneratorConstraints = "HHH000120: Ignoring unique constraints specified on table generator [%s]";
    private final static String exceptionHeaderFound = "HHH000085: %s %s found";
    private final static String columns = "HHH000037: Columns: %s";
    private final static String validatorNotFound = "HHH000410: Hibernate Validator not found: ignoring";
    private final static String startingQueryCache = "HHH000248: Starting query cache at region: %s";
    private final static String usingDefaultIdGeneratorSegmentValue = "HHH000398: Explicit segment value for id generator [%s.%s] suggested; using default [%s]";
    private final static String unableToRemoveBagJoinFetch = "HHH000358: Unable to erase previously added bag join fetch";
    private final static String expectedType = "HHH000091: Expected type: %s, actual value: %s";
    private final static String failed = "HHH000099: an assertion failure occured (this may indicate a bug in Hibernate, but is more likely due to unsafe use of the session): %s";
    private final static String entitiesFetched = "HHH000077: Entities fetched (minimize this): %s";
    private final static String parsingXmlErrorForFile = "HHH000197: Error parsing XML: %s(%s) %s";
    private final static String queriesExecuted = "HHH000210: Queries executed to database: %s";
    private final static String invalidPrimaryKeyJoinColumnAnnotation = "HHH000137: Root entity should not hold an PrimaryKeyJoinColum(s), will be ignored";
    private final static String proxoolProviderClassNotFound = "HHH000209: proxool properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.";
    private final static String naturalIdCachePuts = "HHH000438: NaturalId cache puts: %s";
    private final static String handlingTransientEntity = "HHH000114: Handling transient entity in delete processing";
    private final static String creatingSubcontextInfo = "HHH000053: Creating subcontext: %s";
    private final static String startingServiceAtJndiName = "HHH000249: Starting service at JNDI name: %s";
    private final static String aliasSpecificLockingWithFollowOnLocking = "HHH000445: Alias-specific lock modes requested, which is not currently supported with follow-on locking; all acquired locks will be [%s]";
    private final static String configuringFromResource = "HHH000043: Configuring from resource: %s";
    private final static String sessionsOpened = "HHH000242: Sessions opened: %s";
    private final static String unableToLogWarnings = "HHH000336: Could not log warnings";
    private final static String configurationResource = "HHH000040: Configuration resource: %s";
    private final static String unableToRollbackIsolatedTransaction = "HHH000364: Unable to rollback isolated transaction on error [%s] : [%s]";
    private final static String parsingXmlWarningForFile = "HHH000199: Warning parsing XML: %s(%s) %s";
    private final static String typeDefinedNoRegistrationKeys = "HHH000269: Type [%s] defined no registration keys; ignoring";
    private final static String jndiNameDoesNotHandleSessionFactoryReference = "HHH000155: JNDI name %s does not handle a session factory reference";
    private final static String unableToCleanUpPreparedStatement = "HHH000282: Unable to clean up prepared statement";
    private final static String unableToSwitchToMethodUsingColumnIndex = "HHH000370: Exception switching from method: [%s] to a method using the column index. Reverting to using: [%<s]";
    private final static String unknownIngresVersion = "HHH000383: Unknown Ingres major version [%s]; using Ingres 9.2 dialect";
    private final static String disablingContextualLOBCreationSinceCreateClobFailed = "HHH000424: Disabling contextual LOB creation as createClob() method threw error : %s";
    private final static String missingArguments = "HHH000174: Function template anticipated %s arguments, but %s arguments encountered";
    private final static String flushes = "HHH000105: Flushes: %s";
    private final static String unableToFindPersistenceXmlInClasspath = "HHH000318: Could not find any META-INF/persistence.xml file in the classpath";
    private final static String transactionStrategy = "HHH000268: Transaction strategy: %s";
    private final static String compositeIdClassDoesNotOverrideHashCode = "HHH000039: Composite-id class does not override hashCode(): %s";
    private final static String unableToBindEjb3ConfigurationToJndi = "HHH000276: Could not bind Ejb3Configuration to JNDI";
    private final static String propertiesNotFound = "HHH000206: hibernate.properties not found";
    private final static String searchingForMappingDocuments = "HHH000235: Searching for mapping documents in jar: %s";
    private final static String configuringFromXmlDocument = "HHH000045: Configuring from XML document";
    private final static String incompatibleCacheValueModePerCache = "HHH020006: The value mode for the cache[%s] is \"identity\". This is incompatible with clustered Hibernate caching - the value mode has therefore been switched to \"serialization\"";
    private final static String unableToDiscoverOsgiService = "HHH000453: Exception while discovering OSGi service implementations : %s";
    private final static String exceptionInBeforeTransactionCompletionInterceptor = "HHH000088: Exception in interceptor beforeTransactionCompletion()";
    private final static String softLockedCacheExpired = "HHH020008: Cache[%s] Key[%s] Lockable[%s]\nA soft-locked cache entry was expired by the underlying Ehcache. If this happens regularly you should consider increasing the cache timeouts and/or capacity limits";
    private final static String unableToObtainConnectionToQueryMetadata = "HHH000342: Could not obtain connection to query metadata : %s";
    private final static String collectionsLoaded = "HHH000033: Collections loaded: %s";
    private final static String usingOldDtd = "HHH000404: Don't use old DTDs, read the Hibernate 3.x Migration Guide!";
    private final static String exceptionHeaderNotFound = "HHH000086: %s No %s found";
    private final static String unableToReadOrInitHiValue = "HHH000351: Could not read or init a hi value";
    private final static String missingEntityAnnotation = "HHH000175: Class annotated @org.hibernate.annotations.Entity but not javax.persistence.Entity (most likely a user error): %s";
    private final static String updatingSchema = "HHH000396: Updating schema";
    private final static String unsupportedIngresVersion = "HHH000391: Ingres 10 is not yet fully supported; using Ingres 9.3 dialect";
    private final static String unableToCloseSession = "HHH000294: Could not close session";
    private final static String timestampCacheMisses = "HHH000435: update timestamps cache misses: %s";
    private final static String duplicateGeneratorName = "HHH000069: Duplicate generator name %s";
    private final static String unableToResolveAggregateFunction = "HHH000359: Could not resolve aggregate function [%s]; using standard definition";
    private final static String unableToDestroyCache = "HHH000308: Unable to destroy cache: %s";
    private final static String usingDefaultTransactionStrategy = "HHH000399: Using default transaction strategy (direct JDBC transactions)";
    private final static String unableToApplyConstraints = "HHH000274: Unable to apply constraints on DDL for %s";
    private final static String c3p0ProviderClassNotFound = "HHH000022: c3p0 properties were encountered, but the %s provider class was not found on the classpath; these properties are going to be ignored.";
    private final static String unsupportedInitialValue = "HHH000392: Hibernate does not support SequenceGenerator.initialValue() unless '%s' set";
    private final static String warningsCreatingTempTable = "HHH000413: Warnings creating temp table : %s";
    private final static String usingHibernateBuiltInConnectionPool = "HHH000402: Using Hibernate built-in connection pool (not for production use!)";
    private final static String unableToCompleteSchemaValidation = "HHH000300: Could not complete schema validation";
    private final static String cleaningUpConnectionPool = "HHH000030: Cleaning up connection pool [%s]";
    private final static String recognizedObsoleteHibernateNamespace = "HHH000223: Recognized obsolete hibernate namespace %s. Use namespace %s instead. Refer to Hibernate 3.6 Migration Guide!";
    private final static String creatingPooledLoOptimizer = "HHH000467: Creating pooled optimizer (lo) with [incrementSize=%s; returnClass=%s]";
    private final static String unableToBuildEnhancementMetamodel = "HHH000279: Unable to build enhancement metamodel for %s";
    private final static String JavaSqlTypesMappedSameCodeMultipleTimes = "HHH000141: java.sql.Types mapped the same code [%s] multiple times; was [%s]; now [%s]";
    private final static String requiredDifferentProvider = "HHH000226: Required a different provider: %s";
    private final static String unableToObjectConnectionToQueryMetadata = "HHH000340: Could not obtain connection to query metadata: %s";
    private final static String naturalIdQueriesExecuted = "HHH000442: NaturalId queries executed to database: %s";
    private final static String unsupportedProperty = "HHH000395: Usage of obsolete property: %s no longer supported, use: %s";
    private final static String closingUnreleasedBatch = "HHH000420: Closing un-released batch";
    private final static String unableToMarkForRollbackOnTransientObjectException = "HHH000338: Unable to mark for rollback on TransientObjectException: ";
    private final static String unableToBindValueToParameter = "HHH000278: Could not bind value '%s' to parameter: %s; %s";
    private final static String disablingContextualLOBCreation = "HHH000421: Disabling contextual LOB creation as %s is true";
    private final static String factoryJndiRename = "HHH000096: A factory was renamed from [%s] to [%s] in JNDI";
    private final static String unableToCleanupTemporaryIdTable = "HHH000283: Unable to cleanup temporary id table after use [%s]";
    private final static String couldNotBindJndiListener = "HHH000127: Could not bind JNDI listener";
    private final static String unknownBytecodeProvider = "HHH000382: unrecognized bytecode provider [%s], using javassist by default";
    private final static String unableToWriteCachedFile = "HHH000378: I/O reported error writing cached file : %s: %s";
    private final static String narrowingProxy = "HHH000179: Narrowing proxy to %s - this operation breaks ==";
    private final static String readingMappingsFromFile = "HHH000220: Reading mappings from file: %s";

    public J2CacheMessageLogger_$logger(final Logger log) {
        super(log);
    }

    public final void jdbcAutoCommitFalseBreaksEjb3Spec(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, jdbcAutoCommitFalseBreaksEjb3Spec$str(), arg0);
    }

    protected String jdbcAutoCommitFalseBreaksEjb3Spec$str() {
        return jdbcAutoCommitFalseBreaksEjb3Spec;
    }

    public final void illegalPropertyGetterArgument(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, illegalPropertyGetterArgument$str(), arg0, arg1);
    }

    protected String illegalPropertyGetterArgument$str() {
        return illegalPropertyGetterArgument;
    }

    public final void unableToMarkForRollbackOnPersistenceException(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToMarkForRollbackOnPersistenceException$str());
    }

    protected String unableToMarkForRollbackOnPersistenceException$str() {
        return unableToMarkForRollbackOnPersistenceException;
    }

    public final void unableToLoadConfiguration(final String configurationResourceName) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLoadConfiguration$str(), configurationResourceName);
    }

    protected String unableToLoadConfiguration$str() {
        return unableToLoadConfiguration;
    }

    public final void hql(final String arg0, final Long arg1, final Long arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, hql$str(), arg0, arg1, arg2);
    }

    protected String hql$str() {
        return hql;
    }

    public final void logicalConnectionReleasingPhysicalConnection() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, logicalConnectionReleasingPhysicalConnection$str());
    }

    protected String logicalConnectionReleasingPhysicalConnection$str() {
        return logicalConnectionReleasingPhysicalConnection;
    }

    public final void unexpectedLiteralTokenType(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unexpectedLiteralTokenType$str(), arg0);
    }

    protected String unexpectedLiteralTokenType$str() {
        return unexpectedLiteralTokenType;
    }

    public final void logicalConnectionClosed() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, logicalConnectionClosed$str());
    }

    protected String logicalConnectionClosed$str() {
        return logicalConnectionClosed;
    }

    public final void unableToAccessSessionFactory(final String arg0, final javax.naming.NamingException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), unableToAccessSessionFactory$str(), arg0);
    }

    protected String unableToAccessSessionFactory$str() {
        return unableToAccessSessionFactory;
    }

    public final void factoryBoundToJndiName(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, factoryBoundToJndiName$str(), arg0);
    }

    protected String factoryBoundToJndiName$str() {
        return factoryBoundToJndiName;
    }

    public final void unregisteredStatement() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, unregisteredStatement$str());
    }

    protected String unregisteredStatement$str() {
        return unregisteredStatement;
    }

    public final Object unableToUpdateHiValue(final String arg0) {
        Object result = String.format(unableToUpdateHiValue$str(), arg0);
        return result;
    }

    protected String unableToUpdateHiValue$str() {
        return unableToUpdateHiValue;
    }

    public final void naturalIdCacheHits(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, naturalIdCacheHits$str(), arg0);
    }

    protected String naturalIdCacheHits$str() {
        return naturalIdCacheHits;
    }

    public final void unableToBindFactoryToJndi(final org.hibernate.engine.jndi.JndiException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToBindFactoryToJndi$str());
    }

    protected String unableToBindFactoryToJndi$str() {
        return unableToBindFactoryToJndi;
    }

    public final void usingUuidHexGenerator(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, usingUuidHexGenerator$str(), arg0, arg1);
    }

    protected String usingUuidHexGenerator$str() {
        return usingUuidHexGenerator;
    }

    public final String unableToLocateConfigFile(final String arg0) {
        String result = String.format(unableToLocateConfigFile$str(), arg0);
        return result;
    }

    protected String unableToLocateConfigFile$str() {
        return unableToLocateConfigFile;
    }

    public final void unableToGetDatabaseMetadata(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToGetDatabaseMetadata$str());
    }

    protected String unableToGetDatabaseMetadata$str() {
        return unableToGetDatabaseMetadata;
    }

    public final void unableToCloseSessionDuringRollback(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToCloseSessionDuringRollback$str());
    }

    protected String unableToCloseSessionDuringRollback$str() {
        return unableToCloseSessionDuringRollback;
    }

    public final void entityAnnotationOnNonRoot(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, entityAnnotationOnNonRoot$str(), arg0);
    }

    protected String entityAnnotationOnNonRoot$str() {
        return entityAnnotationOnNonRoot;
    }

    public final void duplicateGeneratorTable(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, duplicateGeneratorTable$str(), arg0);
    }

    protected String duplicateGeneratorTable$str() {
        return duplicateGeneratorTable;
    }

    public final void unableToReleaseCacheLock(final CacheException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToReleaseCacheLock$str(), arg0);
    }

    protected String unableToReleaseCacheLock$str() {
        return unableToReleaseCacheLock;
    }

    public final void exceptionInSubResolver(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, exceptionInSubResolver$str(), arg0);
    }

    protected String exceptionInSubResolver$str() {
        return exceptionInSubResolver;
    }

    public final void compositeIdClassDoesNotOverrideEquals(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, compositeIdClassDoesNotOverrideEquals$str(), arg0);
    }

    protected String compositeIdClassDoesNotOverrideEquals$str() {
        return compositeIdClassDoesNotOverrideEquals;
    }

    public final void unableToRetrieveTypeInfoResultSet(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToRetrieveTypeInfoResultSet$str(), arg0);
    }

    protected String unableToRetrieveTypeInfoResultSet$str() {
        return unableToRetrieveTypeInfoResultSet;
    }

    public final void deprecatedOracle9Dialect() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedOracle9Dialect$str());
    }

    protected String deprecatedOracle9Dialect$str() {
        return deprecatedOracle9Dialect;
    }

    public final void guidGenerated(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, guidGenerated$str(), arg0);
    }

    protected String guidGenerated$str() {
        return guidGenerated;
    }

    public final void providerClassDeprecated(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, providerClassDeprecated$str(), arg0, arg1);
    }

    protected String providerClassDeprecated$str() {
        return providerClassDeprecated;
    }

    public final void unableToUpdateQueryHiValue(final String arg0, final java.sql.SQLException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), unableToUpdateQueryHiValue$str(), arg0);
    }

    protected String unableToUpdateQueryHiValue$str() {
        return unableToUpdateQueryHiValue;
    }

    public final void unableToCloseIterator(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), (arg0), unableToCloseIterator$str());
    }

    protected String unableToCloseIterator$str() {
        return unableToCloseIterator;
    }

    public final void readingCachedMappings(final File arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, readingCachedMappings$str(), arg0);
    }

    protected String readingCachedMappings$str() {
        return readingCachedMappings;
    }

    public final void disablingContextualLOBCreationSinceConnectionNull() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, disablingContextualLOBCreationSinceConnectionNull$str());
    }

    protected String disablingContextualLOBCreationSinceConnectionNull$str() {
        return disablingContextualLOBCreationSinceConnectionNull;
    }

    public final void schemaUpdateComplete() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, schemaUpdateComplete$str());
    }

    protected String schemaUpdateComplete$str() {
        return schemaUpdateComplete;
    }

    public final void entityManagerFactoryAlreadyRegistered(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, entityManagerFactoryAlreadyRegistered$str(), arg0, arg1);
    }

    protected String entityManagerFactoryAlreadyRegistered$str() {
        return entityManagerFactoryAlreadyRegistered;
    }

    public final void unableToCleanUpCallableStatement(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToCleanUpCallableStatement$str());
    }

    protected String unableToCleanUpCallableStatement$str() {
        return unableToCleanUpCallableStatement;
    }

    public final void entitiesInserted(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, entitiesInserted$str(), arg0);
    }

    protected String entitiesInserted$str() {
        return entitiesInserted;
    }

    public final void namedQueryError(final String arg0, final org.hibernate.HibernateException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), namedQueryError$str(), arg0);
    }

    protected String namedQueryError$str() {
        return namedQueryError;
    }

    public final void usingStreams() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingStreams$str());
    }

    protected String usingStreams$str() {
        return usingStreams;
    }

    public final void alreadySessionBound() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, alreadySessionBound$str());
    }

    protected String alreadySessionBound$str() {
        return alreadySessionBound;
    }

    public final void ignoringUnrecognizedQueryHint(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, ignoringUnrecognizedQueryHint$str(), arg0);
    }

    protected String ignoringUnrecognizedQueryHint$str() {
        return ignoringUnrecognizedQueryHint;
    }

    public final void duplicateJoins(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, duplicateJoins$str(), arg0);
    }

    protected String duplicateJoins$str() {
        return duplicateJoins;
    }

    public final void unableToResolveMappingFile(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToResolveMappingFile$str(), arg0);
    }

    protected String unableToResolveMappingFile$str() {
        return unableToResolveMappingFile;
    }

    public final void disallowingInsertStatementComment() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, disallowingInsertStatementComment$str());
    }

    protected String disallowingInsertStatementComment$str() {
        return disallowingInsertStatementComment;
    }

    public final void loggingStatistics() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, loggingStatistics$str());
    }

    protected String loggingStatistics$str() {
        return loggingStatistics;
    }

    public final void unableToClosePooledConnection(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToClosePooledConnection$str());
    }

    protected String unableToClosePooledConnection$str() {
        return unableToClosePooledConnection;
    }

    public final void noPersistentClassesFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, noPersistentClassesFound$str(), arg0);
    }

    protected String noPersistentClassesFound$str() {
        return noPersistentClassesFound;
    }

    public final void rollbackFromBackgroundThread(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, rollbackFromBackgroundThread$str(), arg0);
    }

    protected String rollbackFromBackgroundThread$str() {
        return rollbackFromBackgroundThread;
    }

    public final void resolvedSqlTypeDescriptorForDifferentSqlCode(final String arg0, final String arg1, final String arg2, final String arg3) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, resolvedSqlTypeDescriptorForDifferentSqlCode$str(), arg0, arg1, arg2, arg3);
    }

    protected String resolvedSqlTypeDescriptorForDifferentSqlCode$str() {
        return resolvedSqlTypeDescriptorForDifferentSqlCode;
    }

    public final void unableToReleaseIsolatedConnection(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToReleaseIsolatedConnection$str(), arg0);
    }

    protected String unableToReleaseIsolatedConnection$str() {
        return unableToReleaseIsolatedConnection;
    }

    public final void unableToCloseJar(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToCloseJar$str(), arg0);
    }

    protected String unableToCloseJar$str() {
        return unableToCloseJar;
    }

    public final void unableToCreateProxyFactory(final String arg0, final org.hibernate.HibernateException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg1), unableToCreateProxyFactory$str(), arg0);
    }

    protected String unableToCreateProxyFactory$str() {
        return unableToCreateProxyFactory;
    }

    public final void unableToDetermineLockModeValue(final String arg0, final Object arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToDetermineLockModeValue$str(), arg0, arg1);
    }

    protected String unableToDetermineLockModeValue$str() {
        return unableToDetermineLockModeValue;
    }

    public final void duplicateMetadata() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, duplicateMetadata$str());
    }

    protected String duplicateMetadata$str() {
        return duplicateMetadata;
    }

    public final void orderByAnnotationIndexedCollection() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, orderByAnnotationIndexedCollection$str());
    }

    protected String orderByAnnotationIndexedCollection$str() {
        return orderByAnnotationIndexedCollection;
    }

    public final void schemaExportComplete() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, schemaExportComplete$str());
    }

    protected String schemaExportComplete$str() {
        return schemaExportComplete;
    }

    public final void configuredSessionFactory(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, configuredSessionFactory$str(), arg0);
    }

    protected String configuredSessionFactory$str() {
        return configuredSessionFactory;
    }

    public final void settersOfLazyClassesCannotBeFinal(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, settersOfLazyClassesCannotBeFinal$str(), arg0, arg1);
    }

    protected String settersOfLazyClassesCannotBeFinal$str() {
        return settersOfLazyClassesCannotBeFinal;
    }

    public final void unableToCreateSchema(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToCreateSchema$str());
    }

    protected String unableToCreateSchema$str() {
        return unableToCreateSchema;
    }

    public final void overridingTransactionStrategyDangerous(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, overridingTransactionStrategyDangerous$str(), arg0);
    }

    protected String overridingTransactionStrategyDangerous$str() {
        return overridingTransactionStrategyDangerous;
    }

    public final void deprecatedManyToManyOuterJoin() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedManyToManyOuterJoin$str());
    }

    protected String deprecatedManyToManyOuterJoin$str() {
        return deprecatedManyToManyOuterJoin;
    }

    public final void unableToReadColumnValueFromResultSet(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToReadColumnValueFromResultSet$str(), arg0, arg1);
    }

    protected String unableToReadColumnValueFromResultSet$str() {
        return unableToReadColumnValueFromResultSet;
    }

    public final void unableToLocateConfiguredSchemaNameResolver(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLocateConfiguredSchemaNameResolver$str(), arg0, arg1);
    }

    protected String unableToLocateConfiguredSchemaNameResolver$str() {
        return unableToLocateConfiguredSchemaNameResolver;
    }

    public final void collectionsUpdated(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, collectionsUpdated$str(), arg0);
    }

    protected String collectionsUpdated$str() {
        return collectionsUpdated;
    }

    public final void writeLocksNotSupported(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, writeLocksNotSupported$str(), arg0);
    }

    protected String writeLocksNotSupported$str() {
        return writeLocksNotSupported;
    }

    public final void unsuccessfulCreate(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unsuccessfulCreate$str(), arg0);
    }

    protected String unsuccessfulCreate$str() {
        return unsuccessfulCreate;
    }

    public final void invalidOnDeleteAnnotation(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, invalidOnDeleteAnnotation$str(), arg0);
    }

    protected String invalidOnDeleteAnnotation$str() {
        return invalidOnDeleteAnnotation;
    }

    public final void jndiInitialContextProperties(final Hashtable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, jndiInitialContextProperties$str(), arg0);
    }

    protected String jndiInitialContextProperties$str() {
        return jndiInitialContextProperties;
    }

    public final void secondLevelCacheMisses(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, secondLevelCacheMisses$str(), arg0);
    }

    protected String secondLevelCacheMisses$str() {
        return secondLevelCacheMisses;
    }

    public final void exceptionInAfterTransactionCompletionInterceptor(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), exceptionInAfterTransactionCompletionInterceptor$str());
    }

    protected String exceptionInAfterTransactionCompletionInterceptor$str() {
        return exceptionInAfterTransactionCompletionInterceptor;
    }

    public final void readOnlyCacheConfiguredForMutableEntity(final String entityName) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, readOnlyCacheConfiguredForMutableEntity$str(), entityName);
    }

    protected String readOnlyCacheConfiguredForMutableEntity$str() {
        return readOnlyCacheConfiguredForMutableEntity;
    }

    public final String unableToPerformJdbcCommit() {
        String result = String.format(unableToPerformJdbcCommit$str());
        return result;
    }

    protected String unableToPerformJdbcCommit$str() {
        return unableToPerformJdbcCommit;
    }

    public final void unableToExecuteBatch(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToExecuteBatch$str(), arg0);
    }

    protected String unableToExecuteBatch$str() {
        return unableToExecuteBatch;
    }

    public final String javassistEnhancementFailed(final String arg0) {
        String result = String.format(javassistEnhancementFailed$str(), arg0);
        return result;
    }

    protected String javassistEnhancementFailed$str() {
        return javassistEnhancementFailed;
    }

    public final void naturalIdCacheMisses(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, naturalIdCacheMisses$str(), arg0);
    }

    protected String naturalIdCacheMisses$str() {
        return naturalIdCacheMisses;
    }

    public final void entityManagerClosedBySomeoneElse(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, entityManagerClosedBySomeoneElse$str(), arg0);
    }

    protected String entityManagerClosedBySomeoneElse$str() {
        return entityManagerClosedBySomeoneElse;
    }

    public final void propertiesLoaded(final java.util.Properties arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, propertiesLoaded$str(), arg0);
    }

    protected String propertiesLoaded$str() {
        return propertiesLoaded;
    }

    public final void unableToCloseStream(final java.io.IOException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToCloseStream$str());
    }

    protected String unableToCloseStream$str() {
        return unableToCloseStream;
    }

    public final void invalidDiscriminatorAnnotation(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, invalidDiscriminatorAnnotation$str(), arg0);
    }

    protected String invalidDiscriminatorAnnotation$str() {
        return invalidDiscriminatorAnnotation;
    }

    public final String jdbcUrlNotSpecified(final String arg0) {
        String result = String.format(jdbcUrlNotSpecified$str(), arg0);
        return result;
    }

    protected String jdbcUrlNotSpecified$str() {
        return jdbcUrlNotSpecified;
    }

    public final void entityMappedAsNonAbstract(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, entityMappedAsNonAbstract$str(), arg0);
    }

    protected String entityMappedAsNonAbstract$str() {
        return entityMappedAsNonAbstract;
    }

    public final void duplicateListener(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, duplicateListener$str(), arg0);
    }

    protected String duplicateListener$str() {
        return duplicateListener;
    }

    public final void processingPersistenceUnitInfoName(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, processingPersistenceUnitInfoName$str(), arg0);
    }

    protected String processingPersistenceUnitInfoName$str() {
        return processingPersistenceUnitInfoName;
    }

    public final void statementsClosed(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, statementsClosed$str(), arg0);
    }

    protected String statementsClosed$str() {
        return statementsClosed;
    }

    public final void hibernateConnectionPoolSize(final int arg0, final int arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, hibernateConnectionPoolSize$str(), arg0, arg1);
    }

    protected String hibernateConnectionPoolSize$str() {
        return hibernateConnectionPoolSize;
    }

    public final void unableToInstantiateConfiguredSchemaNameResolver(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToInstantiateConfiguredSchemaNameResolver$str(), arg0, arg1);
    }

    protected String unableToInstantiateConfiguredSchemaNameResolver$str() {
        return unableToInstantiateConfiguredSchemaNameResolver;
    }

    public final void unableToObtainInitialContext(final javax.naming.NamingException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToObtainInitialContext$str());
    }

    protected String unableToObtainInitialContext$str() {
        return unableToObtainInitialContext;
    }

    public final void collectionsFetched(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, collectionsFetched$str(), arg0);
    }

    protected String collectionsFetched$str() {
        return collectionsFetched;
    }

    public final void pooledOptimizerReportedInitialValue(final IntegralDataTypeHolder arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, pooledOptimizerReportedInitialValue$str(), arg0);
    }

    protected String pooledOptimizerReportedInitialValue$str() {
        return pooledOptimizerReportedInitialValue;
    }

    public final void factoryUnboundFromJndiName(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, factoryUnboundFromJndiName$str(), arg0);
    }

    protected String factoryUnboundFromJndiName$str() {
        return factoryUnboundFromJndiName;
    }

    public final void naturalIdMaxQueryTime(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, naturalIdMaxQueryTime$str(), arg0);
    }

    protected String naturalIdMaxQueryTime$str() {
        return naturalIdMaxQueryTime;
    }

    public final void statementsPrepared(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, statementsPrepared$str(), arg0);
    }

    protected String statementsPrepared$str() {
        return statementsPrepared;
    }

    public final void unableToStopService(final Class arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToStopService$str(), arg0, arg1);
    }

    protected String unableToStopService$str() {
        return unableToStopService;
    }

    public final void unregisteredResultSetWithoutStatement() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unregisteredResultSetWithoutStatement$str());
    }

    protected String unregisteredResultSetWithoutStatement$str() {
        return unregisteredResultSetWithoutStatement;
    }

    public final void unableToRollbackConnection(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToRollbackConnection$str(), arg0);
    }

    protected String unableToRollbackConnection$str() {
        return unableToRollbackConnection;
    }

    public final void transactions(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, transactions$str(), arg0);
    }

    protected String transactions$str() {
        return transactions;
    }

    public final void forcingTableUse() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, forcingTableUse$str());
    }

    protected String forcingTableUse$str() {
        return forcingTableUse;
    }

    public final void cacheProvider(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, cacheProvider$str(), arg0);
    }

    protected String cacheProvider$str() {
        return cacheProvider;
    }

    public final void unableToLogSqlWarnings(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLogSqlWarnings$str(), arg0);
    }

    protected String unableToLogSqlWarnings$str() {
        return unableToLogSqlWarnings;
    }

    public final void unableToCompleteSchemaUpdate(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToCompleteSchemaUpdate$str());
    }

    protected String unableToCompleteSchemaUpdate$str() {
        return unableToCompleteSchemaUpdate;
    }

    public final void unableToReleaseBatchStatement() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToReleaseBatchStatement$str());
    }

    protected String unableToReleaseBatchStatement$str() {
        return unableToReleaseBatchStatement;
    }

    public final void sqlWarning(final int arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, sqlWarning$str(), arg0, arg1);
    }

    protected String sqlWarning$str() {
        return sqlWarning;
    }

    public final void unexpectedRowCounts() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unexpectedRowCounts$str());
    }

    protected String unexpectedRowCounts$str() {
        return unexpectedRowCounts;
    }

    public final void stoppingService() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, stoppingService$str());
    }

    protected String stoppingService$str() {
        return stoppingService;
    }

    public final void invalidTableAnnotation(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, invalidTableAnnotation$str(), arg0);
    }

    protected String invalidTableAnnotation$str() {
        return invalidTableAnnotation;
    }

    public final void containsJoinFetchedCollection(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, containsJoinFetchedCollection$str(), arg0);
    }

    protected String containsJoinFetchedCollection$str() {
        return containsJoinFetchedCollection;
    }

    public final void unableToRunSchemaUpdate(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToRunSchemaUpdate$str());
    }

    protected String unableToRunSchemaUpdate$str() {
        return unableToRunSchemaUpdate;
    }

    public final void unableToAccessEjb3Configuration(final javax.naming.NamingException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToAccessEjb3Configuration$str());
    }

    protected String unableToAccessEjb3Configuration$str() {
        return unableToAccessEjb3Configuration;
    }

    public final void readingMappingsFromResource(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, readingMappingsFromResource$str(), arg0);
    }

    protected String readingMappingsFromResource$str() {
        return readingMappingsFromResource;
    }

    public final void unableToObjectConnectionMetadata(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToObjectConnectionMetadata$str(), arg0);
    }

    protected String unableToObjectConnectionMetadata$str() {
        return unableToObjectConnectionMetadata;
    }

    public final void definingFlushBeforeCompletionIgnoredInHem(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, definingFlushBeforeCompletionIgnoredInHem$str(), arg0);
    }

    protected String definingFlushBeforeCompletionIgnoredInHem$str() {
        return definingFlushBeforeCompletionIgnoredInHem;
    }

    public final void unableToCloseConnection(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToCloseConnection$str());
    }

    protected String unableToCloseConnection$str() {
        return unableToCloseConnection;
    }

    public final void foundMappingDocument(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, foundMappingDocument$str(), arg0);
    }

    protected String foundMappingDocument$str() {
        return foundMappingDocument;
    }

    public final void deprecatedForceDescriminatorAnnotation() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedForceDescriminatorAnnotation$str());
    }

    protected String deprecatedForceDescriminatorAnnotation$str() {
        return deprecatedForceDescriminatorAnnotation;
    }

    public final void unableToWrapResultSet(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), (arg0), unableToWrapResultSet$str());
    }

    protected String unableToWrapResultSet$str() {
        return unableToWrapResultSet;
    }

    public final void unableToReleaseContext(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToReleaseContext$str(), arg0);
    }

    protected String unableToReleaseContext$str() {
        return unableToReleaseContext;
    }

    public final void tableFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, tableFound$str(), arg0);
    }

    protected String tableFound$str() {
        return tableFound;
    }

    public final void setManagerLookupClass() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, setManagerLookupClass$str());
    }

    protected String setManagerLookupClass$str() {
        return setManagerLookupClass;
    }

    public final void version(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, version$str(), arg0);
    }

    protected String version$str() {
        return version;
    }

    public final String unableToQueryDatabaseMetadata() {
        String result = String.format(unableToQueryDatabaseMetadata$str());
        return result;
    }

    protected String unableToQueryDatabaseMetadata$str() {
        return unableToQueryDatabaseMetadata;
    }

    public final void bytecodeProvider(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, bytecodeProvider$str(), arg0);
    }

    protected String bytecodeProvider$str() {
        return bytecodeProvider;
    }

    public final void writingGeneratedSchemaToFile(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, writingGeneratedSchemaToFile$str(), arg0);
    }

    protected String writingGeneratedSchemaToFile$str() {
        return writingGeneratedSchemaToFile;
    }

    public final void noAppropriateConnectionProvider() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, noAppropriateConnectionProvider$str());
    }

    protected String noAppropriateConnectionProvider$str() {
        return noAppropriateConnectionProvider;
    }

    public final void unableToConstructCurrentSessionContext(final String arg0, final Throwable arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), unableToConstructCurrentSessionContext$str(), arg0);
    }

    protected String unableToConstructCurrentSessionContext$str() {
        return unableToConstructCurrentSessionContext;
    }

    public final void honoringOptimizerSetting(final String arg0, final String arg1, final int arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, honoringOptimizerSetting$str(), arg0, arg1, arg2);
    }

    protected String honoringOptimizerSetting$str() {
        return honoringOptimizerSetting;
    }

    public final void unableToCloseInitialContext(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToCloseInitialContext$str(), arg0);
    }

    protected String unableToCloseInitialContext$str() {
        return unableToCloseInitialContext;
    }

    public final void hsqldbSupportsOnlyReadCommittedIsolation() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, hsqldbSupportsOnlyReadCommittedIsolation$str());
    }

    protected String hsqldbSupportsOnlyReadCommittedIsolation$str() {
        return hsqldbSupportsOnlyReadCommittedIsolation;
    }

    public final void noDefaultConstructor(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, noDefaultConstructor$str(), arg0);
    }

    protected String noDefaultConstructor$str() {
        return noDefaultConstructor;
    }

    public final void unableToCloseInputFiles(final String arg0, final java.io.IOException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), unableToCloseInputFiles$str(), arg0);
    }

    protected String unableToCloseInputFiles$str() {
        return unableToCloseInputFiles;
    }

    public final void synchronizationAlreadyRegistered(final javax.transaction.Synchronization arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, synchronizationAlreadyRegistered$str(), arg0);
    }

    protected String synchronizationAlreadyRegistered$str() {
        return synchronizationAlreadyRegistered;
    }

    public final void invalidEditOfReadOnlyItem(final Object arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, invalidEditOfReadOnlyItem$str(), arg0);
    }

    protected String invalidEditOfReadOnlyItem$str() {
        return invalidEditOfReadOnlyItem;
    }

    public final void connectionsObtained(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, connectionsObtained$str(), arg0);
    }

    protected String connectionsObtained$str() {
        return connectionsObtained;
    }

    public final void scopingTypesToSessionFactoryAfterAlreadyScoped(final org.hibernate.engine.spi.SessionFactoryImplementor arg0, final org.hibernate.engine.spi.SessionFactoryImplementor arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, scopingTypesToSessionFactoryAfterAlreadyScoped$str(), arg0, arg1);
    }

    protected String scopingTypesToSessionFactoryAfterAlreadyScoped$str() {
        return scopingTypesToSessionFactoryAfterAlreadyScoped;
    }

    public final void deprecatedManyToManyFetch() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedManyToManyFetch$str());
    }

    protected String deprecatedManyToManyFetch$str() {
        return deprecatedManyToManyFetch;
    }

    public final void sessionsClosed(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, sessionsClosed$str(), arg0);
    }

    protected String sessionsClosed$str() {
        return sessionsClosed;
    }

    public final void entitiesUpdated(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, entitiesUpdated$str(), arg0);
    }

    protected String entitiesUpdated$str() {
        return entitiesUpdated;
    }

    public final void runningSchemaValidator() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, runningSchemaValidator$str());
    }

    protected String runningSchemaValidator$str() {
        return runningSchemaValidator;
    }

    public final String jdbcRollbackFailed() {
        String result = String.format(jdbcRollbackFailed$str());
        return result;
    }

    protected String jdbcRollbackFailed$str() {
        return jdbcRollbackFailed;
    }

    public final void namingExceptionAccessingFactory(final javax.naming.NamingException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, namingExceptionAccessingFactory$str(), arg0);
    }

    protected String namingExceptionAccessingFactory$str() {
        return namingExceptionAccessingFactory;
    }

    public final void runningHbm2ddlSchemaExport() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, runningHbm2ddlSchemaExport$str());
    }

    protected String runningHbm2ddlSchemaExport$str() {
        return runningHbm2ddlSchemaExport;
    }

    public final void startingUpdateTimestampsCache(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, startingUpdateTimestampsCache$str(), arg0);
    }

    protected String startingUpdateTimestampsCache$str() {
        return startingUpdateTimestampsCache;
    }

    public final void alternateServiceRole(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, alternateServiceRole$str(), arg0, arg1);
    }

    protected String alternateServiceRole$str() {
        return alternateServiceRole;
    }

    public final void unableToLocateMBeanServer() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLocateMBeanServer$str());
    }

    protected String unableToLocateMBeanServer$str() {
        return unableToLocateMBeanServer;
    }

    public final void connectionProperties(final java.util.Properties arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, connectionProperties$str(), arg0);
    }

    protected String connectionProperties$str() {
        return connectionProperties;
    }

    public final void batchContainedStatementsOnRelease() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, batchContainedStatementsOnRelease$str());
    }

    protected String batchContainedStatementsOnRelease$str() {
        return batchContainedStatementsOnRelease;
    }

    public final void usingReflectionOptimizer() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingReflectionOptimizer$str());
    }

    protected String usingReflectionOptimizer$str() {
        return usingReflectionOptimizer;
    }

    public final void unableToLoadScannedClassOrResource(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToLoadScannedClassOrResource$str());
    }

    protected String unableToLoadScannedClassOrResource$str() {
        return unableToLoadScannedClassOrResource;
    }

    public final void unableToCloseOutputFile(final String arg0, final java.io.IOException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), unableToCloseOutputFile$str(), arg0);
    }

    protected String unableToCloseOutputFile$str() {
        return unableToCloseOutputFile;
    }

    public final void invalidSubStrategy(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, invalidSubStrategy$str(), arg0);
    }

    protected String invalidSubStrategy$str() {
        return invalidSubStrategy;
    }

    public final void callingJoinTransactionOnNonJtaEntityManager() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, callingJoinTransactionOnNonJtaEntityManager$str());
    }

    protected String callingJoinTransactionOnNonJtaEntityManager$str() {
        return callingJoinTransactionOnNonJtaEntityManager;
    }

    public final void attemptToRestartAlreadyStartedJ2CacheProvider() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, attemptToRestartAlreadyStartedJ2CacheProvider$str());
    }

    protected String attemptToRestartAlreadyStartedJ2CacheProvider$str() {
        return attemptToRestartAlreadyStartedJ2CacheProvider;
    }

    public final void maxQueryTime(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, maxQueryTime$str(), arg0);
    }

    protected String maxQueryTime$str() {
        return maxQueryTime;
    }

    public final void unableToSetTransactionToRollbackOnly(final SystemException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToSetTransactionToRollbackOnly$str());
    }

    protected String unableToSetTransactionToRollbackOnly$str() {
        return unableToSetTransactionToRollbackOnly;
    }

    public final void propertyNotFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, propertyNotFound$str(), arg0);
    }

    protected String propertyNotFound$str() {
        return propertyNotFound;
    }

    public final void duplicateImport(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, duplicateImport$str(), arg0, arg1);
    }

    protected String duplicateImport$str() {
        return duplicateImport;
    }

    public final void firstOrMaxResultsSpecifiedWithCollectionFetch() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, firstOrMaxResultsSpecifiedWithCollectionFetch$str());
    }

    protected String firstOrMaxResultsSpecifiedWithCollectionFetch$str() {
        return firstOrMaxResultsSpecifiedWithCollectionFetch;
    }

    public final void unableToToggleAutoCommit(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToToggleAutoCommit$str());
    }

    protected String unableToToggleAutoCommit$str() {
        return unableToToggleAutoCommit;
    }

    public final void nonCompliantMapConversion(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, nonCompliantMapConversion$str(), arg0);
    }

    protected String nonCompliantMapConversion$str() {
        return nonCompliantMapConversion;
    }

    public final void immutableAnnotationOnNonRoot(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, immutableAnnotationOnNonRoot$str(), arg0);
    }

    protected String immutableAnnotationOnNonRoot$str() {
        return immutableAnnotationOnNonRoot;
    }

    public final void noColumnsSpecifiedForIndex(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, noColumnsSpecifiedForIndex$str(), arg0, arg1);
    }

    protected String noColumnsSpecifiedForIndex$str() {
        return noColumnsSpecifiedForIndex;
    }

    public final void usingFollowOnLocking() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, usingFollowOnLocking$str());
    }

    protected String usingFollowOnLocking$str() {
        return usingFollowOnLocking;
    }

    public final void unableToAccessTypeInfoResultSet(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToAccessTypeInfoResultSet$str(), arg0);
    }

    protected String unableToAccessTypeInfoResultSet$str() {
        return unableToAccessTypeInfoResultSet;
    }

    public final void optimisticLockFailures(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, optimisticLockFailures$str(), arg0);
    }

    protected String optimisticLockFailures$str() {
        return optimisticLockFailures;
    }

    public final void lazyPropertyFetchingAvailable(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, lazyPropertyFetchingAvailable$str(), arg0);
    }

    protected String lazyPropertyFetchingAvailable$str() {
        return lazyPropertyFetchingAvailable;
    }

    public final void unsupportedNamedParameters() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedNamedParameters$str());
    }

    protected String unsupportedNamedParameters$str() {
        return unsupportedNamedParameters;
    }

    public final void failSafeEntitiesCleanup(final EntityLoadContext arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, failSafeEntitiesCleanup$str(), arg0);
    }

    protected String failSafeEntitiesCleanup$str() {
        return failSafeEntitiesCleanup;
    }

    public final void unableToDeserializeCache(final String arg0, final SerializationException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToDeserializeCache$str(), arg0, arg1);
    }

    protected String unableToDeserializeCache$str() {
        return unableToDeserializeCache;
    }

    public final void factoryUnboundFromName(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, factoryUnboundFromName$str(), arg0);
    }

    protected String factoryUnboundFromName$str() {
        return factoryUnboundFromName;
    }

    public final void legacyTransactionManagerStrategy(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, legacyTransactionManagerStrategy$str(), arg0, arg1);
    }

    protected String legacyTransactionManagerStrategy$str() {
        return legacyTransactionManagerStrategy;
    }

    public final void unsupportedAfterStatement() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedAfterStatement$str());
    }

    protected String unsupportedAfterStatement$str() {
        return unsupportedAfterStatement;
    }

    public final void packageNotFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, packageNotFound$str(), arg0);
    }

    protected String packageNotFound$str() {
        return packageNotFound;
    }

    public final void embedXmlAttributesNoLongerSupported() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, embedXmlAttributesNoLongerSupported$str());
    }

    protected String embedXmlAttributesNoLongerSupported$str() {
        return embedXmlAttributesNoLongerSupported;
    }

    public final void parsingXmlError(final int arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, parsingXmlError$str(), arg0, arg1);
    }

    protected String parsingXmlError$str() {
        return parsingXmlError;
    }

    public final void illegalPropertySetterArgument(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, illegalPropertySetterArgument$str(), arg0, arg1);
    }

    protected String illegalPropertySetterArgument$str() {
        return illegalPropertySetterArgument;
    }

    public final void expired(final Object arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, expired$str(), arg0);
    }

    protected String expired$str() {
        return expired;
    }

    public final void indexes(final java.util.Set arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, indexes$str(), arg0);
    }

    protected String indexes$str() {
        return indexes;
    }

    public final void containerProvidingNullPersistenceUnitRootUrl() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, containerProvidingNullPersistenceUnitRootUrl$str());
    }

    protected String containerProvidingNullPersistenceUnitRootUrl$str() {
        return containerProvidingNullPersistenceUnitRootUrl;
    }

    public final void tooManyInExpressions(final String arg0, final int arg1, final String arg2, final int arg3) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, tooManyInExpressions$str(), arg0, arg1, arg2, arg3);
    }

    protected String tooManyInExpressions$str() {
        return tooManyInExpressions;
    }

    public final void timestampCacheHits(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, timestampCacheHits$str(), arg0);
    }

    protected String timestampCacheHits$str() {
        return timestampCacheHits;
    }

    public final void unableToSynchronizeDatabaseStateWithSession(final org.hibernate.HibernateException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToSynchronizeDatabaseStateWithSession$str(), arg0);
    }

    protected String unableToSynchronizeDatabaseStateWithSession$str() {
        return unableToSynchronizeDatabaseStateWithSession;
    }

    public final void foreignKeys(final java.util.Set arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, foreignKeys$str(), arg0);
    }

    protected String foreignKeys$str() {
        return foreignKeys;
    }

    public final void entitiesDeleted(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, entitiesDeleted$str(), arg0);
    }

    protected String entitiesDeleted$str() {
        return entitiesDeleted;
    }

    public final void splitQueries(final String arg0, final int arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, splitQueries$str(), arg0, arg1);
    }

    protected String splitQueries$str() {
        return splitQueries;
    }

    public final void usingDriver(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingDriver$str(), arg0, arg1);
    }

    protected String usingDriver$str() {
        return usingDriver;
    }

    public final void hydratingEntitiesCount(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, hydratingEntitiesCount$str(), arg0);
    }

    protected String hydratingEntitiesCount$str() {
        return hydratingEntitiesCount;
    }

    public final void successfulTransactions(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, successfulTransactions$str(), arg0);
    }

    protected String successfulTransactions$str() {
        return successfulTransactions;
    }

    public final void renamedProperty(final Object arg0, final Object arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, renamedProperty$str(), arg0, arg1);
    }

    protected String renamedProperty$str() {
        return renamedProperty;
    }

    public final void runningHbm2ddlSchemaUpdate() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, runningHbm2ddlSchemaUpdate$str());
    }

    protected String runningHbm2ddlSchemaUpdate$str() {
        return runningHbm2ddlSchemaUpdate;
    }

    public final void unableToCloseOutputStream(final java.io.IOException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToCloseOutputStream$str());
    }

    protected String unableToCloseOutputStream$str() {
        return unableToCloseOutputStream;
    }

    public final void unableToExecuteResolver(final DialectResolver arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToExecuteResolver$str(), arg0, arg1);
    }

    protected String unableToExecuteResolver$str() {
        return unableToExecuteResolver;
    }

    public final void unableToConfigureSqlExceptionConverter(final org.hibernate.HibernateException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToConfigureSqlExceptionConverter$str(), arg0);
    }

    protected String unableToConfigureSqlExceptionConverter$str() {
        return unableToConfigureSqlExceptionConverter;
    }

    public final void unableToDestroyQueryCache(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToDestroyQueryCache$str(), arg0, arg1);
    }

    protected String unableToDestroyQueryCache$str() {
        return unableToDestroyQueryCache;
    }

    public final void synchronizationFailed(final javax.transaction.Synchronization arg0, final Throwable arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, synchronizationFailed$str(), arg0, arg1);
    }

    protected String synchronizationFailed$str() {
        return synchronizationFailed;
    }

    public final void unableToLocateUuidGenerationStrategy(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLocateUuidGenerationStrategy$str(), arg0);
    }

    protected String unableToLocateUuidGenerationStrategy$str() {
        return unableToLocateUuidGenerationStrategy;
    }

    public final void disablingContextualLOBCreationSinceOldJdbcVersion(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, disablingContextualLOBCreationSinceOldJdbcVersion$str(), arg0);
    }

    protected String disablingContextualLOBCreationSinceOldJdbcVersion$str() {
        return disablingContextualLOBCreationSinceOldJdbcVersion;
    }

    public final void autoFlushWillNotWork() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, autoFlushWillNotWork$str());
    }

    protected String autoFlushWillNotWork$str() {
        return autoFlushWillNotWork;
    }

    public final void usingTimestampWorkaround() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingTimestampWorkaround$str());
    }

    protected String usingTimestampWorkaround$str() {
        return usingTimestampWorkaround;
    }

    public final void parsingXmlWarning(final int arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, parsingXmlWarning$str(), arg0, arg1);
    }

    protected String parsingXmlWarning$str() {
        return parsingXmlWarning;
    }

    public final void processEqualityExpression() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, processEqualityExpression$str());
    }

    protected String processEqualityExpression$str() {
        return processEqualityExpression;
    }

    public final void startTime(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, startTime$str(), arg0);
    }

    protected String startTime$str() {
        return startTime;
    }

    public final void unableToReleaseTypeInfoResultSet() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToReleaseTypeInfoResultSet$str());
    }

    protected String unableToReleaseTypeInfoResultSet$str() {
        return unableToReleaseTypeInfoResultSet;
    }

    public final void unableToRetrieveCache(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToRetrieveCache$str(), arg0, arg1);
    }

    protected String unableToRetrieveCache$str() {
        return unableToRetrieveCache;
    }

    public final void rdmsOs2200Dialect() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, rdmsOs2200Dialect$str());
    }

    protected String rdmsOs2200Dialect$str() {
        return rdmsOs2200Dialect;
    }

    public final void unableToParseMetadata(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToParseMetadata$str(), arg0);
    }

    protected String unableToParseMetadata$str() {
        return unableToParseMetadata;
    }

    public final void unableToLocateCustomOptimizerClass(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLocateCustomOptimizerClass$str(), arg0);
    }

    protected String unableToLocateCustomOptimizerClass$str() {
        return unableToLocateCustomOptimizerClass;
    }

    public final String unableToDetermineTransactionStatus() {
        String result = String.format(unableToDetermineTransactionStatus$str());
        return result;
    }

    protected String unableToDetermineTransactionStatus$str() {
        return unableToDetermineTransactionStatus;
    }

    public final void sortAnnotationIndexedCollection() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, sortAnnotationIndexedCollection$str());
    }

    protected String sortAnnotationIndexedCollection$str() {
        return sortAnnotationIndexedCollection;
    }

    public final void unknownOracleVersion(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unknownOracleVersion$str(), arg0);
    }

    protected String unknownOracleVersion$str() {
        return unknownOracleVersion;
    }

    public final void unableToJoinTransaction(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToJoinTransaction$str(), arg0);
    }

    protected String unableToJoinTransaction$str() {
        return unableToJoinTransaction;
    }

    public final void unableToStopHibernateService(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToStopHibernateService$str());
    }

    protected String unableToStopHibernateService$str() {
        return unableToStopHibernateService;
    }

    public final void instantiatingExplicitConnectionProvider(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, instantiatingExplicitConnectionProvider$str(), arg0);
    }

    protected String instantiatingExplicitConnectionProvider$str() {
        return instantiatingExplicitConnectionProvider;
    }

    public final void readOnlyCacheConfiguredForMutableCollection(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, readOnlyCacheConfiguredForMutableCollection$str(), arg0);
    }

    protected String readOnlyCacheConfiguredForMutableCollection$str() {
        return readOnlyCacheConfiguredForMutableCollection;
    }

    public final void loadingCollectionKeyNotFound(final CollectionKey arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, loadingCollectionKeyNotFound$str(), arg0);
    }

    protected String loadingCollectionKeyNotFound$str() {
        return loadingCollectionKeyNotFound;
    }

    public final void multipleValidationModes(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, multipleValidationModes$str(), arg0);
    }

    protected String multipleValidationModes$str() {
        return multipleValidationModes;
    }

    public final void unableToUnbindFactoryFromJndi(final org.hibernate.engine.jndi.JndiException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToUnbindFactoryFromJndi$str());
    }

    protected String unableToUnbindFactoryFromJndi$str() {
        return unableToUnbindFactoryFromJndi;
    }

    public final void unableToTransformClass(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToTransformClass$str(), arg0);
    }

    protected String unableToTransformClass$str() {
        return unableToTransformClass;
    }

    public final void unableToCloseInputStream(final java.io.IOException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToCloseInputStream$str());
    }

    protected String unableToCloseInputStream$str() {
        return unableToCloseInputStream;
    }

    public final void typeRegistrationOverridesPrevious(final String arg0, final Type arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, typeRegistrationOverridesPrevious$str(), arg0, arg1);
    }

    protected String typeRegistrationOverridesPrevious$str() {
        return typeRegistrationOverridesPrevious;
    }

    public final void jdbcIsolationLevel(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, jdbcIsolationLevel$str(), arg0);
    }

    protected String jdbcIsolationLevel$str() {
        return jdbcIsolationLevel;
    }

    public final void persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly$str());
    }

    protected String persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly$str() {
        return persistenceProviderCallerDoesNotImplementEjb3SpecCorrectly;
    }

    public final void queryCacheMisses(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, queryCacheMisses$str(), arg0);
    }

    protected String queryCacheMisses$str() {
        return queryCacheMisses;
    }

    public final void collectionsRecreated(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, collectionsRecreated$str(), arg0);
    }

    protected String collectionsRecreated$str() {
        return collectionsRecreated;
    }

    public final void unableToBuildSessionFactoryUsingMBeanClasspath(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToBuildSessionFactoryUsingMBeanClasspath$str(), arg0);
    }

    protected String unableToBuildSessionFactoryUsingMBeanClasspath$str() {
        return unableToBuildSessionFactoryUsingMBeanClasspath;
    }

    public final void incompleteMappingMetadataCacheProcessing() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, incompleteMappingMetadataCacheProcessing$str());
    }

    protected String incompleteMappingMetadataCacheProcessing$str() {
        return incompleteMappingMetadataCacheProcessing;
    }

    public final void unableToConstructSqlExceptionConverter(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToConstructSqlExceptionConverter$str(), arg0);
    }

    protected String unableToConstructSqlExceptionConverter$str() {
        return unableToConstructSqlExceptionConverter;
    }

    public final void collectionsRemoved(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, collectionsRemoved$str(), arg0);
    }

    protected String collectionsRemoved$str() {
        return collectionsRemoved;
    }

    public final void invalidArrayElementType(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, invalidArrayElementType$str(), arg0);
    }

    protected String invalidArrayElementType$str() {
        return invalidArrayElementType;
    }

    public final void unableToObtainConnectionMetadata(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToObtainConnectionMetadata$str(), arg0);
    }

    protected String unableToObtainConnectionMetadata$str() {
        return unableToObtainConnectionMetadata;
    }

    public final String unableToReadHiValue(final String arg0) {
        String result = String.format(unableToReadHiValue$str(), arg0);
        return result;
    }

    protected String unableToReadHiValue$str() {
        return unableToReadHiValue;
    }

    public final void schemaExportUnsuccessful(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), schemaExportUnsuccessful$str());
    }

    protected String schemaExportUnsuccessful$str() {
        return schemaExportUnsuccessful;
    }

    public final void undeterminedH2Version() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, undeterminedH2Version$str());
    }

    protected String undeterminedH2Version$str() {
        return undeterminedH2Version;
    }

    public final void deprecatedOracleDialect() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedOracleDialect$str());
    }

    protected String deprecatedOracleDialect$str() {
        return deprecatedOracleDialect;
    }

    public final void applyingExplicitDiscriminatorColumnForJoined(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, applyingExplicitDiscriminatorColumnForJoined$str(), arg0, arg1);
    }

    protected String applyingExplicitDiscriminatorColumnForJoined$str() {
        return applyingExplicitDiscriminatorColumnForJoined;
    }

    public final void unableToLoadDerbyDriver(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToLoadDerbyDriver$str(), arg0);
    }

    protected String unableToLoadDerbyDriver$str() {
        return unableToLoadDerbyDriver;
    }

    public final void queryCachePuts(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, queryCachePuts$str(), arg0);
    }

    protected String queryCachePuts$str() {
        return queryCachePuts;
    }

    public final void unableToCloseSessionButSwallowingError(final org.hibernate.HibernateException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToCloseSessionButSwallowingError$str(), arg0);
    }

    protected String unableToCloseSessionButSwallowingError$str() {
        return unableToCloseSessionButSwallowingError;
    }

    public final void unableToInstantiateOptimizer(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToInstantiateOptimizer$str(), arg0);
    }

    protected String unableToInstantiateOptimizer$str() {
        return unableToInstantiateOptimizer;
    }

    public final void configuringFromFile(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, configuringFromFile$str(), arg0);
    }

    protected String configuringFromFile$str() {
        return configuringFromFile;
    }

    public final void noSessionFactoryWithJndiName(final String arg0, final NameNotFoundException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), noSessionFactoryWithJndiName$str(), arg0);
    }

    protected String noSessionFactoryWithJndiName$str() {
        return noSessionFactoryWithJndiName;
    }

    public final void closing() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, closing$str());
    }

    protected String closing$str() {
        return closing;
    }

    public final void preparedStatementAlreadyInBatch(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, preparedStatementAlreadyInBatch$str(), arg0);
    }

    protected String preparedStatementAlreadyInBatch$str() {
        return preparedStatementAlreadyInBatch;
    }

    public final void cannotResolveNonNullableTransientDependencies(final String arg0, final java.util.Set arg1, final java.util.Set arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, cannotResolveNonNullableTransientDependencies$str(), arg0, arg1, arg2);
    }

    protected String cannotResolveNonNullableTransientDependencies$str() {
        return cannotResolveNonNullableTransientDependencies;
    }

    public final String unableToDetermineTransactionStatusAfterCommit() {
        String result = String.format(unableToDetermineTransactionStatusAfterCommit$str());
        return result;
    }

    protected String unableToDetermineTransactionStatusAfterCommit$str() {
        return unableToDetermineTransactionStatusAfterCommit;
    }

    public final String unableToRollbackJta() {
        String result = String.format(unableToRollbackJta$str());
        return result;
    }

    protected String unableToRollbackJta$str() {
        return unableToRollbackJta;
    }

    public final void unknownSqlServerVersion(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unknownSqlServerVersion$str(), arg0);
    }

    protected String unknownSqlServerVersion$str() {
        return unknownSqlServerVersion;
    }

    public final void localLoadingCollectionKeysCount(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, localLoadingCollectionKeysCount$str(), arg0);
    }

    protected String localLoadingCollectionKeysCount$str() {
        return localLoadingCollectionKeysCount;
    }

    public final void unableToCloseStreamError(final java.io.IOException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToCloseStreamError$str(), arg0);
    }

    protected String unableToCloseStreamError$str() {
        return unableToCloseStreamError;
    }

    public final void jdbcDriverNotSpecified(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, jdbcDriverNotSpecified$str(), arg0);
    }

    protected String jdbcDriverNotSpecified$str() {
        return jdbcDriverNotSpecified;
    }

    public final void subResolverException(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, subResolverException$str(), arg0);
    }

    protected String subResolverException$str() {
        return subResolverException;
    }

    public final void cachedFileNotFound(final String arg0, final FileNotFoundException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, cachedFileNotFound$str(), arg0, arg1);
    }

    protected String cachedFileNotFound$str() {
        return cachedFileNotFound;
    }

    public final void unableToInstantiateUuidGenerationStrategy(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToInstantiateUuidGenerationStrategy$str(), arg0);
    }

    protected String unableToInstantiateUuidGenerationStrategy$str() {
        return unableToInstantiateUuidGenerationStrategy;
    }

    public final void transactionStartedOnNonRootSession() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, transactionStartedOnNonRootSession$str());
    }

    protected String transactionStartedOnNonRootSession$str() {
        return transactionStartedOnNonRootSession;
    }

    public final void jaccContextId(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, jaccContextId$str(), arg0);
    }

    protected String jaccContextId$str() {
        return jaccContextId;
    }

    public final void gettersOfLazyClassesCannotBeFinal(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, gettersOfLazyClassesCannotBeFinal$str(), arg0, arg1);
    }

    protected String gettersOfLazyClassesCannotBeFinal$str() {
        return gettersOfLazyClassesCannotBeFinal;
    }

    public final void entitiesLoaded(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, entitiesLoaded$str(), arg0);
    }

    protected String entitiesLoaded$str() {
        return entitiesLoaded;
    }

    public final void unsuccessful(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unsuccessful$str(), arg0);
    }

    protected String unsuccessful$str() {
        return unsuccessful;
    }

    public final void unsupportedOracleVersion() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedOracleVersion$str());
    }

    protected String unsupportedOracleVersion$str() {
        return unsupportedOracleVersion;
    }

    public final void serviceProperties(final java.util.Properties arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, serviceProperties$str(), arg0);
    }

    protected String serviceProperties$str() {
        return serviceProperties;
    }

    public final void unsupportedMultiTableBulkHqlJpaql(final int arg0, final int arg1, final int arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedMultiTableBulkHqlJpaql$str(), arg0, arg1, arg2);
    }

    protected String unsupportedMultiTableBulkHqlJpaql$str() {
        return unsupportedMultiTableBulkHqlJpaql;
    }

    public final void entityIdentifierValueBindingExists(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, entityIdentifierValueBindingExists$str(), arg0);
    }

    protected String entityIdentifierValueBindingExists$str() {
        return entityIdentifierValueBindingExists;
    }

    public final void unableToLoadCommand(final org.hibernate.HibernateException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToLoadCommand$str(), arg0);
    }

    protected String unableToLoadCommand$str() {
        return unableToLoadCommand;
    }

    public final void unableToPerformManagedFlush(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToPerformManagedFlush$str(), arg0);
    }

    protected String unableToPerformManagedFlush$str() {
        return unableToPerformManagedFlush;
    }

    public final void secondLevelCacheHits(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, secondLevelCacheHits$str(), arg0);
    }

    protected String secondLevelCacheHits$str() {
        return secondLevelCacheHits;
    }

    public final void usingDialect(final Dialect arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingDialect$str(), arg0);
    }

    protected String usingDialect$str() {
        return usingDialect;
    }

    public final void unableToDropTemporaryIdTable(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToDropTemporaryIdTable$str(), arg0);
    }

    protected String unableToDropTemporaryIdTable$str() {
        return unableToDropTemporaryIdTable;
    }

    public final void unableToFindJ2CacheConfiguration(final String name) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToFindJ2CacheConfiguration$str(), name);
    }

    protected String unableToFindJ2CacheConfiguration$str() {
        return unableToFindJ2CacheConfiguration;
    }

    public final void usingAstQueryTranslatorFactory() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingAstQueryTranslatorFactory$str());
    }

    protected String usingAstQueryTranslatorFactory$str() {
        return usingAstQueryTranslatorFactory;
    }

    public final void unableToReleaseCreatedMBeanServer(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToReleaseCreatedMBeanServer$str(), arg0);
    }

    protected String unableToReleaseCreatedMBeanServer$str() {
        return unableToReleaseCreatedMBeanServer;
    }

    public final void invalidJndiName(final String arg0, final JndiNameException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), invalidJndiName$str(), arg0);
    }

    protected String invalidJndiName$str() {
        return invalidJndiName;
    }

    public final void failSafeCollectionsCleanup(final CollectionLoadContext arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, failSafeCollectionsCleanup$str(), arg0);
    }

    protected String failSafeCollectionsCleanup$str() {
        return failSafeCollectionsCleanup;
    }

    public final void needsLimit() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, needsLimit$str());
    }

    protected String needsLimit$str() {
        return needsLimit;
    }

    public final void fetchingDatabaseMetadata() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, fetchingDatabaseMetadata$str());
    }

    protected String fetchingDatabaseMetadata$str() {
        return fetchingDatabaseMetadata;
    }

    public final void configuringFromUrl(final URL arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, configuringFromUrl$str(), arg0);
    }

    protected String configuringFromUrl$str() {
        return configuringFromUrl;
    }

    public final void timestampCachePuts(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, timestampCachePuts$str(), arg0);
    }

    protected String timestampCachePuts$str() {
        return timestampCachePuts;
    }

    public final void explicitSkipLockedLockCombo() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, explicitSkipLockedLockCombo$str());
    }

    protected String explicitSkipLockedLockCombo$str() {
        return explicitSkipLockedLockCombo;
    }

    public final void forcingContainerResourceCleanup() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, forcingContainerResourceCleanup$str());
    }

    protected String forcingContainerResourceCleanup$str() {
        return forcingContainerResourceCleanup;
    }

    public final void deprecatedUuidGenerator(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedUuidGenerator$str(), arg0, arg1);
    }

    protected String deprecatedUuidGenerator$str() {
        return deprecatedUuidGenerator;
    }

    public final void unableToFindConfiguration(final String name) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToFindConfiguration$str(), name);
    }

    protected String unableToFindConfiguration$str() {
        return unableToFindConfiguration;
    }

    public final void autoCommitMode(final boolean arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, autoCommitMode$str(), arg0);
    }

    protected String autoCommitMode$str() {
        return autoCommitMode;
    }

    public final void willNotRegisterListeners() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, willNotRegisterListeners$str());
    }

    protected String willNotRegisterListeners$str() {
        return willNotRegisterListeners;
    }

    public final void tableNotFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, tableNotFound$str(), arg0);
    }

    protected String tableNotFound$str() {
        return tableNotFound;
    }

    public final void unableToLoadProperties() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToLoadProperties$str());
    }

    protected String unableToLoadProperties$str() {
        return unableToLoadProperties;
    }

    public final void queryCacheHits(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, queryCacheHits$str(), arg0);
    }

    protected String queryCacheHits$str() {
        return queryCacheHits;
    }

    public final String unableToCommitJta() {
        String result = String.format(unableToCommitJta$str());
        return result;
    }

    protected String unableToCommitJta$str() {
        return unableToCommitJta;
    }

    public final void unableToDestroyUpdateTimestampsCache(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToDestroyUpdateTimestampsCache$str(), arg0, arg1);
    }

    protected String unableToDestroyUpdateTimestampsCache$str() {
        return unableToDestroyUpdateTimestampsCache;
    }

    public final void unableToCloseInputStreamForResource(final String arg0, final java.io.IOException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg1), unableToCloseInputStreamForResource$str(), arg0);
    }

    protected String unableToCloseInputStreamForResource$str() {
        return unableToCloseInputStreamForResource;
    }

    public final void unableToReadClass(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToReadClass$str(), arg0);
    }

    protected String unableToReadClass$str() {
        return unableToReadClass;
    }

    public final void deprecatedDerbyDialect() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, deprecatedDerbyDialect$str());
    }

    protected String deprecatedDerbyDialect$str() {
        return deprecatedDerbyDialect;
    }

    public final void addingOverrideFor(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, addingOverrideFor$str(), arg0, arg1);
    }

    protected String addingOverrideFor$str() {
        return addingOverrideFor;
    }

    public final void unableToCopySystemProperties() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToCopySystemProperties$str());
    }

    protected String unableToCopySystemProperties$str() {
        return unableToCopySystemProperties;
    }

    public final void incompatibleCacheValueMode() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, incompatibleCacheValueMode$str());
    }

    protected String incompatibleCacheValueMode$str() {
        return incompatibleCacheValueMode;
    }

    public final void secondLevelCachePuts(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, secondLevelCachePuts$str(), arg0);
    }

    protected String secondLevelCachePuts$str() {
        return secondLevelCachePuts;
    }

    public final void ignoringTableGeneratorConstraints(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, ignoringTableGeneratorConstraints$str(), arg0);
    }

    protected String ignoringTableGeneratorConstraints$str() {
        return ignoringTableGeneratorConstraints;
    }

    public final void exceptionHeaderFound(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, exceptionHeaderFound$str(), arg0, arg1);
    }

    protected String exceptionHeaderFound$str() {
        return exceptionHeaderFound;
    }

    public final void columns(final java.util.Set arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, columns$str(), arg0);
    }

    protected String columns$str() {
        return columns;
    }

    public final void validatorNotFound() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, validatorNotFound$str());
    }

    protected String validatorNotFound$str() {
        return validatorNotFound;
    }

    public final void startingQueryCache(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, startingQueryCache$str(), arg0);
    }

    protected String startingQueryCache$str() {
        return startingQueryCache;
    }

    public final void usingDefaultIdGeneratorSegmentValue(final String arg0, final String arg1, final String arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingDefaultIdGeneratorSegmentValue$str(), arg0, arg1, arg2);
    }

    protected String usingDefaultIdGeneratorSegmentValue$str() {
        return usingDefaultIdGeneratorSegmentValue;
    }

    public final void unableToRemoveBagJoinFetch() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToRemoveBagJoinFetch$str());
    }

    protected String unableToRemoveBagJoinFetch$str() {
        return unableToRemoveBagJoinFetch;
    }

    public final void expectedType(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, expectedType$str(), arg0, arg1);
    }

    protected String expectedType$str() {
        return expectedType;
    }

    public final void failed(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, failed$str(), arg0);
    }

    protected String failed$str() {
        return failed;
    }

    public final void entitiesFetched(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, entitiesFetched$str(), arg0);
    }

    protected String entitiesFetched$str() {
        return entitiesFetched;
    }

    public final void parsingXmlErrorForFile(final String arg0, final int arg1, final String arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, parsingXmlErrorForFile$str(), arg0, arg1, arg2);
    }

    protected String parsingXmlErrorForFile$str() {
        return parsingXmlErrorForFile;
    }

    public final void queriesExecuted(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, queriesExecuted$str(), arg0);
    }

    protected String queriesExecuted$str() {
        return queriesExecuted;
    }

    public final void invalidPrimaryKeyJoinColumnAnnotation() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, invalidPrimaryKeyJoinColumnAnnotation$str());
    }

    protected String invalidPrimaryKeyJoinColumnAnnotation$str() {
        return invalidPrimaryKeyJoinColumnAnnotation;
    }

    public final void proxoolProviderClassNotFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, proxoolProviderClassNotFound$str(), arg0);
    }

    protected String proxoolProviderClassNotFound$str() {
        return proxoolProviderClassNotFound;
    }

    public final void naturalIdCachePuts(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, naturalIdCachePuts$str(), arg0);
    }

    protected String naturalIdCachePuts$str() {
        return naturalIdCachePuts;
    }

    public final void handlingTransientEntity() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, handlingTransientEntity$str());
    }

    protected String handlingTransientEntity$str() {
        return handlingTransientEntity;
    }

    public final void creatingSubcontextInfo(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, creatingSubcontextInfo$str(), arg0);
    }

    protected String creatingSubcontextInfo$str() {
        return creatingSubcontextInfo;
    }

    public final void startingServiceAtJndiName(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, startingServiceAtJndiName$str(), arg0);
    }

    protected String startingServiceAtJndiName$str() {
        return startingServiceAtJndiName;
    }

    public final void aliasSpecificLockingWithFollowOnLocking(final LockMode arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, aliasSpecificLockingWithFollowOnLocking$str(), arg0);
    }

    protected String aliasSpecificLockingWithFollowOnLocking$str() {
        return aliasSpecificLockingWithFollowOnLocking;
    }

    public final void configuringFromResource(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, configuringFromResource$str(), arg0);
    }

    protected String configuringFromResource$str() {
        return configuringFromResource;
    }

    public final void sessionsOpened(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, sessionsOpened$str(), arg0);
    }

    protected String sessionsOpened$str() {
        return sessionsOpened;
    }

    public final void unableToLogWarnings(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToLogWarnings$str());
    }

    protected String unableToLogWarnings$str() {
        return unableToLogWarnings;
    }

    public final void configurationResource(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, configurationResource$str(), arg0);
    }

    protected String configurationResource$str() {
        return configurationResource;
    }

    public final void unableToRollbackIsolatedTransaction(final Exception arg0, final Exception arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToRollbackIsolatedTransaction$str(), arg0, arg1);
    }

    protected String unableToRollbackIsolatedTransaction$str() {
        return unableToRollbackIsolatedTransaction;
    }

    public final void parsingXmlWarningForFile(final String arg0, final int arg1, final String arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, parsingXmlWarningForFile$str(), arg0, arg1, arg2);
    }

    protected String parsingXmlWarningForFile$str() {
        return parsingXmlWarningForFile;
    }

    public final void typeDefinedNoRegistrationKeys(final BasicType arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, typeDefinedNoRegistrationKeys$str(), arg0);
    }

    protected String typeDefinedNoRegistrationKeys$str() {
        return typeDefinedNoRegistrationKeys;
    }

    public final void jndiNameDoesNotHandleSessionFactoryReference(final String arg0, final ClassCastException arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg1), jndiNameDoesNotHandleSessionFactoryReference$str(), arg0);
    }

    protected String jndiNameDoesNotHandleSessionFactoryReference$str() {
        return jndiNameDoesNotHandleSessionFactoryReference;
    }

    public final void unableToCleanUpPreparedStatement(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToCleanUpPreparedStatement$str());
    }

    protected String unableToCleanUpPreparedStatement$str() {
        return unableToCleanUpPreparedStatement;
    }

    public final void unableToSwitchToMethodUsingColumnIndex(final Method arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToSwitchToMethodUsingColumnIndex$str(), arg0);
    }

    protected String unableToSwitchToMethodUsingColumnIndex$str() {
        return unableToSwitchToMethodUsingColumnIndex;
    }

    public final void unknownIngresVersion(final int arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unknownIngresVersion$str(), arg0);
    }

    protected String unknownIngresVersion$str() {
        return unknownIngresVersion;
    }

    public final void disablingContextualLOBCreationSinceCreateClobFailed(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, disablingContextualLOBCreationSinceCreateClobFailed$str(), arg0);
    }

    protected String disablingContextualLOBCreationSinceCreateClobFailed$str() {
        return disablingContextualLOBCreationSinceCreateClobFailed;
    }

    public final void missingArguments(final int arg0, final int arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, missingArguments$str(), arg0, arg1);
    }

    protected String missingArguments$str() {
        return missingArguments;
    }

    public final void flushes(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, flushes$str(), arg0);
    }

    protected String flushes$str() {
        return flushes;
    }

    public final void unableToFindPersistenceXmlInClasspath() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToFindPersistenceXmlInClasspath$str());
    }

    protected String unableToFindPersistenceXmlInClasspath$str() {
        return unableToFindPersistenceXmlInClasspath;
    }

    public final void transactionStrategy(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, transactionStrategy$str(), arg0);
    }

    protected String transactionStrategy$str() {
        return transactionStrategy;
    }

    public final void compositeIdClassDoesNotOverrideHashCode(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, compositeIdClassDoesNotOverrideHashCode$str(), arg0);
    }

    protected String compositeIdClassDoesNotOverrideHashCode$str() {
        return compositeIdClassDoesNotOverrideHashCode;
    }

    public final void unableToBindEjb3ConfigurationToJndi(final org.hibernate.engine.jndi.JndiException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg0), unableToBindEjb3ConfigurationToJndi$str());
    }

    protected String unableToBindEjb3ConfigurationToJndi$str() {
        return unableToBindEjb3ConfigurationToJndi;
    }

    public final void propertiesNotFound() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, propertiesNotFound$str());
    }

    protected String propertiesNotFound$str() {
        return propertiesNotFound;
    }

    public final void searchingForMappingDocuments(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, searchingForMappingDocuments$str(), arg0);
    }

    protected String searchingForMappingDocuments$str() {
        return searchingForMappingDocuments;
    }

    public final void configuringFromXmlDocument() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, configuringFromXmlDocument$str());
    }

    protected String configuringFromXmlDocument$str() {
        return configuringFromXmlDocument;
    }

    public final void incompatibleCacheValueModePerCache(final String cacheName) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, incompatibleCacheValueModePerCache$str(), cacheName);
    }

    protected String incompatibleCacheValueModePerCache$str() {
        return incompatibleCacheValueModePerCache;
    }

    public final void unableToDiscoverOsgiService(final String arg0, final Exception arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg1), unableToDiscoverOsgiService$str(), arg0);
    }

    protected String unableToDiscoverOsgiService$str() {
        return unableToDiscoverOsgiService;
    }

    public final void exceptionInBeforeTransactionCompletionInterceptor(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), exceptionInBeforeTransactionCompletionInterceptor$str());
    }

    protected String exceptionInBeforeTransactionCompletionInterceptor$str() {
        return exceptionInBeforeTransactionCompletionInterceptor;
    }

    public final void softLockedCacheExpired(final String regionName, final Object key, final String lock) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, softLockedCacheExpired$str(), regionName, key, lock);
    }

    protected String softLockedCacheExpired$str() {
        return softLockedCacheExpired;
    }

    public final void unableToObtainConnectionToQueryMetadata(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToObtainConnectionToQueryMetadata$str(), arg0);
    }

    protected String unableToObtainConnectionToQueryMetadata$str() {
        return unableToObtainConnectionToQueryMetadata;
    }

    public final void collectionsLoaded(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, collectionsLoaded$str(), arg0);
    }

    protected String collectionsLoaded$str() {
        return collectionsLoaded;
    }

    public final void usingOldDtd() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, usingOldDtd$str());
    }

    protected String usingOldDtd$str() {
        return usingOldDtd;
    }

    public final void exceptionHeaderNotFound(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, exceptionHeaderNotFound$str(), arg0, arg1);
    }

    protected String exceptionHeaderNotFound$str() {
        return exceptionHeaderNotFound;
    }

    public final void unableToReadOrInitHiValue(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToReadOrInitHiValue$str());
    }

    protected String unableToReadOrInitHiValue$str() {
        return unableToReadOrInitHiValue;
    }

    public final void missingEntityAnnotation(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, missingEntityAnnotation$str(), arg0);
    }

    protected String missingEntityAnnotation$str() {
        return missingEntityAnnotation;
    }

    public final void updatingSchema() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, updatingSchema$str());
    }

    protected String updatingSchema$str() {
        return updatingSchema;
    }

    public final void unsupportedIngresVersion() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedIngresVersion$str());
    }

    protected String unsupportedIngresVersion$str() {
        return unsupportedIngresVersion;
    }

    public final void unableToCloseSession(final org.hibernate.HibernateException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToCloseSession$str());
    }

    protected String unableToCloseSession$str() {
        return unableToCloseSession;
    }

    public final void timestampCacheMisses(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, timestampCacheMisses$str(), arg0);
    }

    protected String timestampCacheMisses$str() {
        return timestampCacheMisses;
    }

    public final void duplicateGeneratorName(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, duplicateGeneratorName$str(), arg0);
    }

    protected String duplicateGeneratorName$str() {
        return duplicateGeneratorName;
    }

    public final void unableToResolveAggregateFunction(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToResolveAggregateFunction$str(), arg0);
    }

    protected String unableToResolveAggregateFunction$str() {
        return unableToResolveAggregateFunction;
    }

    public final void unableToDestroyCache(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToDestroyCache$str(), arg0);
    }

    protected String unableToDestroyCache$str() {
        return unableToDestroyCache;
    }

    public final void usingDefaultTransactionStrategy() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, usingDefaultTransactionStrategy$str());
    }

    protected String usingDefaultTransactionStrategy$str() {
        return usingDefaultTransactionStrategy;
    }

    public final void unableToApplyConstraints(final String arg0, final Exception arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), (arg1), unableToApplyConstraints$str(), arg0);
    }

    protected String unableToApplyConstraints$str() {
        return unableToApplyConstraints;
    }

    public final void c3p0ProviderClassNotFound(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, c3p0ProviderClassNotFound$str(), arg0);
    }

    protected String c3p0ProviderClassNotFound$str() {
        return c3p0ProviderClassNotFound;
    }

    public final void unsupportedInitialValue(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedInitialValue$str(), arg0);
    }

    protected String unsupportedInitialValue$str() {
        return unsupportedInitialValue;
    }

    public final void warningsCreatingTempTable(final SQLWarning arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, warningsCreatingTempTable$str(), arg0);
    }

    protected String warningsCreatingTempTable$str() {
        return warningsCreatingTempTable;
    }

    public final void usingHibernateBuiltInConnectionPool() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, usingHibernateBuiltInConnectionPool$str());
    }

    protected String usingHibernateBuiltInConnectionPool$str() {
        return usingHibernateBuiltInConnectionPool;
    }

    public final void unableToCompleteSchemaValidation(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToCompleteSchemaValidation$str());
    }

    protected String unableToCompleteSchemaValidation$str() {
        return unableToCompleteSchemaValidation;
    }

    public final void cleaningUpConnectionPool(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, cleaningUpConnectionPool$str(), arg0);
    }

    protected String cleaningUpConnectionPool$str() {
        return cleaningUpConnectionPool;
    }

    public final void recognizedObsoleteHibernateNamespace(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, recognizedObsoleteHibernateNamespace$str(), arg0, arg1);
    }

    protected String recognizedObsoleteHibernateNamespace$str() {
        return recognizedObsoleteHibernateNamespace;
    }

    public final void creatingPooledLoOptimizer(final int arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, creatingPooledLoOptimizer$str(), arg0, arg1);
    }

    public final void logUnexpectedSessionInCollectionNotConnected(final java.lang.String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, logUnexpectedSessionInCollectionNotConnected$str(), arg0);
    }

    protected java.lang.String logUnexpectedSessionInCollectionNotConnected$str() {
        return logUnexpectedSessionInCollectionNotConnected;
    }

    public final void logCannotUnsetUnexpectedSessionInCollection(final java.lang.String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, logCannotUnsetUnexpectedSessionInCollection$str(), arg0);
    }

    protected java.lang.String logCannotUnsetUnexpectedSessionInCollection$str() {
        return logCannotUnsetUnexpectedSessionInCollection;
    }

    protected String creatingPooledLoOptimizer$str() {
        return creatingPooledLoOptimizer;
    }

    public final void unableToBuildEnhancementMetamodel(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), null, unableToBuildEnhancementMetamodel$str(), arg0);
    }

    protected String unableToBuildEnhancementMetamodel$str() {
        return unableToBuildEnhancementMetamodel;
    }

    public final void JavaSqlTypesMappedSameCodeMultipleTimes(final int arg0, final String arg1, final String arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, JavaSqlTypesMappedSameCodeMultipleTimes$str(), arg0, arg1, arg2);
    }

    protected String JavaSqlTypesMappedSameCodeMultipleTimes$str() {
        return JavaSqlTypesMappedSameCodeMultipleTimes;
    }

    public final void requiredDifferentProvider(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, requiredDifferentProvider$str(), arg0);
    }

    protected String requiredDifferentProvider$str() {
        return requiredDifferentProvider;
    }

    public final void unableToObjectConnectionToQueryMetadata(final java.sql.SQLException arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToObjectConnectionToQueryMetadata$str(), arg0);
    }

    protected String unableToObjectConnectionToQueryMetadata$str() {
        return unableToObjectConnectionToQueryMetadata;
    }

    public final void naturalIdQueriesExecuted(final long arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, naturalIdQueriesExecuted$str(), arg0);
    }

    protected String naturalIdQueriesExecuted$str() {
        return naturalIdQueriesExecuted;
    }

    public final void unsupportedProperty(final Object arg0, final Object arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unsupportedProperty$str(), arg0, arg1);
    }

    protected String unsupportedProperty$str() {
        return unsupportedProperty;
    }

    public final void closingUnreleasedBatch() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, closingUnreleasedBatch$str());
    }

    protected String closingUnreleasedBatch$str() {
        return closingUnreleasedBatch;
    }

    public final void unableToMarkForRollbackOnTransientObjectException(final Exception arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.ERROR), (arg0), unableToMarkForRollbackOnTransientObjectException$str());
    }

    protected String unableToMarkForRollbackOnTransientObjectException$str() {
        return unableToMarkForRollbackOnTransientObjectException;
    }

    public final void unableToBindValueToParameter(final String arg0, final int arg1, final String arg2) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, unableToBindValueToParameter$str(), arg0, arg1, arg2);
    }

    protected String unableToBindValueToParameter$str() {
        return unableToBindValueToParameter;
    }

    public final void disablingContextualLOBCreation(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, disablingContextualLOBCreation$str(), arg0);
    }

    protected String disablingContextualLOBCreation$str() {
        return disablingContextualLOBCreation;
    }

    public final void factoryJndiRename(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, factoryJndiRename$str(), arg0, arg1);
    }

    protected String factoryJndiRename$str() {
        return factoryJndiRename;
    }

    public final void unableToCleanupTemporaryIdTable(final Throwable arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToCleanupTemporaryIdTable$str(), arg0);
    }

    protected String unableToCleanupTemporaryIdTable$str() {
        return unableToCleanupTemporaryIdTable;
    }

    public final void couldNotBindJndiListener() {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.DEBUG), null, couldNotBindJndiListener$str());
    }

    protected String couldNotBindJndiListener$str() {
        return couldNotBindJndiListener;
    }

    public final void unknownBytecodeProvider(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unknownBytecodeProvider$str(), arg0);
    }

    protected String unknownBytecodeProvider$str() {
        return unknownBytecodeProvider;
    }

    public final void unableToWriteCachedFile(final String arg0, final String arg1) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, unableToWriteCachedFile$str(), arg0, arg1);
    }

    protected String unableToWriteCachedFile$str() {
        return unableToWriteCachedFile;
    }

    public final void narrowingProxy(final Class arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.WARN), null, narrowingProxy$str(), arg0);
    }

    protected String narrowingProxy$str() {
        return narrowingProxy;
    }

    public final void readingMappingsFromFile(final String arg0) {
        super.log.logf(FQCN, (org.jboss.logging.Logger.Level.INFO), null, readingMappingsFromFile$str(), arg0);
    }

    protected String readingMappingsFromFile$str() {
        return readingMappingsFromFile;
    }

}
