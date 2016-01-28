package com.mysql.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.Executor;

import com.mysql.jdbc.log.Log;

public class RSReplicationConnection implements Connection, PingTarget {
	protected Connection currentConnection;
	protected Connection masterConnection;
	protected Connection slavesConnection;
	private Properties slaveProperties;
	private Properties masterProperties;
	private NonRegisteringDriver driver;

	protected RSReplicationConnection() {
	}

	public RSReplicationConnection(Properties masterProperties, Properties slaveProperties) throws SQLException {
		this.driver = new NonRegisteringDriver();
		this.slaveProperties = slaveProperties;
		this.masterProperties = masterProperties;

		initializeMasterConnection();
		initializeSlaveConnection();

		this.currentConnection = this.masterConnection;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		getCurrentConnection().abort(executor);
	}

	@Override
	public void abortInternal() throws SQLException {
		getCurrentConnection().abortInternal();
	}

	@Override
	public synchronized void changeUser(String userName, String newPassword) throws SQLException {
		this.masterConnection.changeUser(userName, newPassword);
		this.slavesConnection.changeUser(userName, newPassword);
	}

	@Override
	public void checkClosed() throws SQLException {
		getCurrentConnection().checkClosed();
	}

	@Override
	public synchronized void clearHasTriedMaster() {
		this.masterConnection.clearHasTriedMaster();
		this.slavesConnection.clearHasTriedMaster();
	}

	@Override
	public void clearWarnings() throws SQLException {
		getCurrentConnection().clearWarnings();
	}

	@Override
	public PreparedStatement clientPrepareStatement(String sql) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().clientPrepareStatement(sql);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement clientPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().clientPrepareStatement(sql, autoGenKeyIndex);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().clientPrepareStatement(sql, resultSetType,
				resultSetConcurrency);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement clientPrepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().clientPrepareStatement(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement clientPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().clientPrepareStatement(sql, autoGenKeyIndexes);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement clientPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().clientPrepareStatement(sql, autoGenKeyColNames);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public synchronized void close() throws SQLException {
		this.masterConnection.close();
		this.slavesConnection.close();
	}

	@Override
	public void commit() throws SQLException {
		getCurrentConnection().commit();
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public java.sql.Statement createStatement() throws SQLException {
		java.sql.Statement stmt = getCurrentConnection().createStatement();
		((Statement) stmt).setPingTarget(this);

		return stmt;
	}

	@Override
	public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		java.sql.Statement stmt = getCurrentConnection().createStatement(resultSetType, resultSetConcurrency);

		((Statement) stmt).setPingTarget(this);

		return stmt;
	}

	@Override
	public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		java.sql.Statement stmt = getCurrentConnection().createStatement(resultSetType, resultSetConcurrency,
				resultSetHoldability);

		((Statement) stmt).setPingTarget(this);

		return stmt;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized void doPing() throws SQLException {
		boolean isMasterConn = isMasterConnection();
		if (this.masterConnection != null) {
			try {
				this.masterConnection.ping();
			} catch (SQLException e) {
				if (isMasterConn) {
					throw e;
				}
			}
		}

		if (this.slavesConnection != null)
			try {
				this.slavesConnection.ping();
			} catch (SQLException e) {
				try {
					initializeSlaveConnection();
				} catch (SQLException initE) {
				}
				if (!isMasterConn)
					throw e;
			}
	}

	@Override
	public String exposeAsXml() throws SQLException {
		return getCurrentConnection().exposeAsXml();
	}

	@Override
	public int getActiveStatementCount() {
		return getCurrentConnection().getActiveStatementCount();
	}

	@Override
	public boolean getAllowLoadLocalInfile() {
		return getCurrentConnection().getAllowLoadLocalInfile();
	}

	@Override
	public boolean getAllowMasterDownConnections() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAllowMultiQueries() {
		return getCurrentConnection().getAllowMultiQueries();
	}

	@Override
	public boolean getAllowNanAndInf() {
		return getCurrentConnection().getAllowNanAndInf();
	}

	@Override
	public boolean getAllowUrlInLocalInfile() {
		return getCurrentConnection().getAllowUrlInLocalInfile();
	}

	@Override
	public boolean getAlwaysSendSetIsolation() {
		return getCurrentConnection().getAlwaysSendSetIsolation();
	}

	@Override
	public String getAuthenticationPlugins() {
		return getCurrentConnection().getAuthenticationPlugins();
	}

	@Override
	public boolean getAutoClosePStmtStreams() {
		return getCurrentConnection().getAutoClosePStmtStreams();
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return getCurrentConnection().getAutoCommit();
	}

	@Override
	public boolean getAutoDeserialize() {
		return getCurrentConnection().getAutoDeserialize();
	}

	@Override
	public boolean getAutoGenerateTestcaseScript() {
		return getCurrentConnection().getAutoGenerateTestcaseScript();
	}

	@Override
	public int getAutoIncrementIncrement() {
		return getCurrentConnection().getAutoIncrementIncrement();
	}

	@Override
	public boolean getAutoReconnectForPools() {
		return getCurrentConnection().getAutoReconnectForPools();
	}

	@Override
	public boolean getAutoSlowLog() {
		return getCurrentConnection().getAutoSlowLog();
	}

	@Override
	public boolean getBlobsAreStrings() {
		return getCurrentConnection().getBlobsAreStrings();
	}

	@Override
	public int getBlobSendChunkSize() {
		return getCurrentConnection().getBlobSendChunkSize();
	}

	@Override
	public boolean getCacheCallableStatements() {
		return getCurrentConnection().getCacheCallableStatements();
	}

	@Override
	public boolean getCacheCallableStmts() {
		return getCurrentConnection().getCacheCallableStmts();
	}

	@Override
	public boolean getCachePreparedStatements() {
		return getCurrentConnection().getCachePreparedStatements();
	}

	@Override
	public boolean getCachePrepStmts() {
		return getCurrentConnection().getCachePrepStmts();
	}

	@Override
	public boolean getCacheResultSetMetadata() {
		return getCurrentConnection().getCacheResultSetMetadata();
	}

	@Override
	public boolean getCacheServerConfiguration() {
		return getCurrentConnection().getCacheServerConfiguration();
	}

	@Override
	public int getCallableStatementCacheSize() {
		return getCurrentConnection().getCallableStatementCacheSize();
	}

	@Override
	public int getCallableStmtCacheSize() {
		return getCurrentConnection().getCallableStmtCacheSize();
	}

	@Override
	public boolean getCapitalizeTypeNames() {
		return getCurrentConnection().getCapitalizeTypeNames();
	}

	@Override
	public String getCatalog() throws SQLException {
		return getCurrentConnection().getCatalog();
	}

	@Override
	public String getCharacterSetResults() {
		return getCurrentConnection().getCharacterSetResults();
	}

	@Override
	public String getClientCertificateKeyStorePassword() {
		return getCurrentConnection().getClientCertificateKeyStorePassword();
	}

	@Override
	public String getClientCertificateKeyStoreType() {
		return getCurrentConnection().getClientCertificateKeyStoreType();
	}

	@Override
	public String getClientCertificateKeyStoreUrl() {
		return getCurrentConnection().getClientCertificateKeyStoreUrl();
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientInfoProvider() {
		return getCurrentConnection().getClientInfoProvider();
	}

	@Override
	public boolean getClobberStreamingResults() {
		return getCurrentConnection().getClobberStreamingResults();
	}

	@Override
	public String getClobCharacterEncoding() {
		return getCurrentConnection().getClobCharacterEncoding();
	}

	@Override
	public boolean getCompensateOnDuplicateKeyUpdateCounts() {
		return getCurrentConnection().getCompensateOnDuplicateKeyUpdateCounts();
	}

	@Override
	public String getConnectionAttributes() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConnectionCollation() {
		return getCurrentConnection().getConnectionCollation();
	}

	@Override
	public String getConnectionLifecycleInterceptors() {
		return getCurrentConnection().getConnectionLifecycleInterceptors();
	}

	@Override
	public Object getConnectionMutex() {
		return getCurrentConnection().getConnectionMutex();
	}

	@Override
	public int getConnectTimeout() {
		return getCurrentConnection().getConnectTimeout();
	}

	@Override
	public boolean getContinueBatchOnError() {
		return getCurrentConnection().getContinueBatchOnError();
	}

	@Override
	public boolean getCreateDatabaseIfNotExist() {
		return getCurrentConnection().getCreateDatabaseIfNotExist();
	}

	public synchronized Connection getCurrentConnection() {
		return this.currentConnection;
	}

	@Override
	public String getDefaultAuthenticationPlugin() {
		return getCurrentConnection().getDefaultAuthenticationPlugin();
	}

	@Override
	public int getDefaultFetchSize() {
		return getCurrentConnection().getDefaultFetchSize();
	}

	@Override
	public String getDisabledAuthenticationPlugins() {
		return getCurrentConnection().getDisabledAuthenticationPlugins();
	}

	@Override
	public boolean getDisconnectOnExpiredPasswords() {
		return getCurrentConnection().getDisconnectOnExpiredPasswords();
	}

	@Override
	public boolean getDontTrackOpenResources() {
		return getCurrentConnection().getDontTrackOpenResources();
	}

	@Override
	public boolean getDumpMetadataOnColumnNotFound() {
		return getCurrentConnection().getDumpMetadataOnColumnNotFound();
	}

	@Override
	public boolean getDumpQueriesOnException() {
		return getCurrentConnection().getDumpQueriesOnException();
	}

	@Override
	public boolean getDynamicCalendars() {
		return getCurrentConnection().getDynamicCalendars();
	}

	@Override
	public boolean getElideSetAutoCommits() {
		return getCurrentConnection().getElideSetAutoCommits();
	}

	@Override
	public boolean getEmptyStringsConvertToZero() {
		return getCurrentConnection().getEmptyStringsConvertToZero();
	}

	@Override
	public boolean getEmulateLocators() {
		return getCurrentConnection().getEmulateLocators();
	}

	@Override
	public boolean getEmulateUnsupportedPstmts() {
		return getCurrentConnection().getEmulateUnsupportedPstmts();
	}

	@Override
	public boolean getEnablePacketDebug() {
		return getCurrentConnection().getEnablePacketDebug();
	}

	@Override
	public boolean getEnableQueryTimeouts() {
		return getCurrentConnection().getEnableQueryTimeouts();
	}

	@Override
	public String getEncoding() {
		return getCurrentConnection().getEncoding();
	}

	@Override
	public ExceptionInterceptor getExceptionInterceptor() {
		return getCurrentConnection().getExceptionInterceptor();
	}

	@Override
	public String getExceptionInterceptors() {
		return getCurrentConnection().getExceptionInterceptors();
	}

	@Override
	public boolean getExplainSlowQueries() {
		return getCurrentConnection().getExplainSlowQueries();
	}

	@Override
	public boolean getFailOverReadOnly() {
		return getCurrentConnection().getFailOverReadOnly();
	}

	@Override
	public boolean getFunctionsNeverReturnBlobs() {
		return getCurrentConnection().getFunctionsNeverReturnBlobs();
	}

	@Override
	public boolean getGatherPerfMetrics() {
		return getCurrentConnection().getGatherPerfMetrics();
	}

	@Override
	public boolean getGatherPerformanceMetrics() {
		return getCurrentConnection().getGatherPerformanceMetrics();
	}

	@Override
	public boolean getGenerateSimpleParameterMetadata() {
		return getCurrentConnection().getGenerateSimpleParameterMetadata();
	}

	@Override
	public boolean getGetProceduresReturnsFunctions() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getHoldability() throws SQLException {
		return getCurrentConnection().getHoldability();
	}

	@Override
	public boolean getHoldResultsOpenOverStatementClose() {
		return getCurrentConnection().getHoldResultsOpenOverStatementClose();
	}

	@Override
	public String getHost() {
		return getCurrentConnection().getHost();
	}

	@Override
	public long getIdleFor() {
		return getCurrentConnection().getIdleFor();
	}

	@Override
	public boolean getIgnoreNonTxTables() {
		return getCurrentConnection().getIgnoreNonTxTables();
	}

	@Override
	public boolean getIncludeInnodbStatusInDeadlockExceptions() {
		return getCurrentConnection().getIncludeInnodbStatusInDeadlockExceptions();
	}

	@Override
	public boolean getIncludeThreadDumpInDeadlockExceptions() {
		return getCurrentConnection().getIncludeThreadDumpInDeadlockExceptions();
	}

	@Override
	public boolean getIncludeThreadNamesAsStatementComment() {
		return getCurrentConnection().getIncludeThreadNamesAsStatementComment();
	}

	@Override
	public int getInitialTimeout() {
		return getCurrentConnection().getInitialTimeout();
	}

	@Override
	public boolean getInteractiveClient() {
		return getCurrentConnection().getInteractiveClient();
	}

	@Override
	public boolean getIsInteractiveClient() {
		return getCurrentConnection().getIsInteractiveClient();
	}

	@Override
	public boolean getJdbcCompliantTruncation() {
		return getCurrentConnection().getJdbcCompliantTruncation();
	}

	@Override
	public boolean getJdbcCompliantTruncationForReads() {
		return getCurrentConnection().getJdbcCompliantTruncationForReads();
	}

	@Override
	public String getLargeRowSizeThreshold() {
		return getCurrentConnection().getLargeRowSizeThreshold();
	}

	@Override
	public String getLoadBalanceAutoCommitStatementRegex() {
		return getCurrentConnection().getLoadBalanceAutoCommitStatementRegex();
	}

	@Override
	public int getLoadBalanceAutoCommitStatementThreshold() {
		return getCurrentConnection().getLoadBalanceAutoCommitStatementThreshold();
	}

	@Override
	public int getLoadBalanceBlacklistTimeout() {
		return getCurrentConnection().getLoadBalanceBlacklistTimeout();
	}

	@Override
	public String getLoadBalanceConnectionGroup() {
		return getCurrentConnection().getLoadBalanceConnectionGroup();
	}

	@Override
	public boolean getLoadBalanceEnableJMX() {
		return getCurrentConnection().getLoadBalanceEnableJMX();
	}

	@Override
	public String getLoadBalanceExceptionChecker() {
		return this.currentConnection.getLoadBalanceExceptionChecker();
	}

	@Override
	public int getLoadBalancePingTimeout() {
		return getCurrentConnection().getLoadBalancePingTimeout();
	}

	@Override
	public String getLoadBalanceSQLExceptionSubclassFailover() {
		return this.currentConnection.getLoadBalanceSQLExceptionSubclassFailover();
	}

	@Override
	public String getLoadBalanceSQLStateFailover() {
		return this.currentConnection.getLoadBalanceSQLStateFailover();
	}

	@Override
	public String getLoadBalanceStrategy() {
		return getCurrentConnection().getLoadBalanceStrategy();
	}

	@Override
	public boolean getLoadBalanceValidateConnectionOnSwapServer() {
		return getCurrentConnection().getLoadBalanceValidateConnectionOnSwapServer();
	}

	@Override
	public String getLocalSocketAddress() {
		return getCurrentConnection().getLocalSocketAddress();
	}

	@Override
	public int getLocatorFetchBufferSize() {
		return getCurrentConnection().getLocatorFetchBufferSize();
	}

	@Override
	public Log getLog() throws SQLException {
		return getCurrentConnection().getLog();
	}

	@Override
	public String getLogger() {
		return getCurrentConnection().getLogger();
	}

	@Override
	public String getLoggerClassName() {
		return getCurrentConnection().getLoggerClassName();
	}

	@Override
	public boolean getLogSlowQueries() {
		return getCurrentConnection().getLogSlowQueries();
	}

	@Override
	public boolean getLogXaCommands() {
		return getCurrentConnection().getLogXaCommands();
	}

	@Override
	public boolean getMaintainTimeStats() {
		return getCurrentConnection().getMaintainTimeStats();
	}

	public synchronized Connection getMasterConnection() {
		return this.masterConnection;
	}

	@Override
	public int getMaxAllowedPacket() {
		return getCurrentConnection().getMaxAllowedPacket();
	}

	@Override
	public int getMaxQuerySizeToLog() {
		return getCurrentConnection().getMaxQuerySizeToLog();
	}

	@Override
	public int getMaxReconnects() {
		return getCurrentConnection().getMaxReconnects();
	}

	@Override
	public int getMaxRows() {
		return getCurrentConnection().getMaxRows();
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return getCurrentConnection().getMetaData();
	}

	@Override
	public int getMetadataCacheSize() {
		return getCurrentConnection().getMetadataCacheSize();
	}

	@Override
	public int getNetTimeoutForStreamingResults() {
		return getCurrentConnection().getNetTimeoutForStreamingResults();
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return getCurrentConnection().getNetworkTimeout();
	}

	@Override
	public boolean getNoAccessToProcedureBodies() {
		return getCurrentConnection().getNoAccessToProcedureBodies();
	}

	@Override
	public boolean getNoDatetimeStringSync() {
		return getCurrentConnection().getNoDatetimeStringSync();
	}

	@Override
	public boolean getNoTimezoneConversionForTimeType() {
		return getCurrentConnection().getNoTimezoneConversionForTimeType();
	}

	@Override
	public boolean getNullCatalogMeansCurrent() {
		return getCurrentConnection().getNullCatalogMeansCurrent();
	}

	@Override
	public boolean getNullNamePatternMatchesAll() {
		return getCurrentConnection().getNullNamePatternMatchesAll();
	}

	@Override
	public boolean getOverrideSupportsIntegrityEnhancementFacility() {
		return getCurrentConnection().getOverrideSupportsIntegrityEnhancementFacility();
	}

	@Override
	public int getPacketDebugBufferSize() {
		return getCurrentConnection().getPacketDebugBufferSize();
	}

	@Override
	public boolean getPadCharsWithSpace() {
		return getCurrentConnection().getPadCharsWithSpace();
	}

	@Override
	public boolean getParanoid() {
		return getCurrentConnection().getParanoid();
	}

	@Override
	public String getParseInfoCacheFactory() {
		return getCurrentConnection().getParseInfoCacheFactory();
	}

	@Override
	public String getPasswordCharacterEncoding() {
		return getCurrentConnection().getPasswordCharacterEncoding();
	}

	@Override
	public boolean getPedantic() {
		return getCurrentConnection().getPedantic();
	}

	@Override
	public boolean getPinGlobalTxToPhysicalConnection() {
		return getCurrentConnection().getPinGlobalTxToPhysicalConnection();
	}

	@Override
	public boolean getPopulateInsertRowWithDefaultValues() {
		return getCurrentConnection().getPopulateInsertRowWithDefaultValues();
	}

	@Override
	public int getPreparedStatementCacheSize() {
		return getCurrentConnection().getPreparedStatementCacheSize();
	}

	@Override
	public int getPreparedStatementCacheSqlLimit() {
		return getCurrentConnection().getPreparedStatementCacheSqlLimit();
	}

	@Override
	public int getPrepStmtCacheSize() {
		return getCurrentConnection().getPrepStmtCacheSize();
	}

	@Override
	public int getPrepStmtCacheSqlLimit() {
		return getCurrentConnection().getPrepStmtCacheSqlLimit();
	}

	@Override
	public boolean getProcessEscapeCodesForPrepStmts() {
		return getCurrentConnection().getProcessEscapeCodesForPrepStmts();
	}

	@Override
	public String getProfilerEventHandler() {
		return getCurrentConnection().getProfilerEventHandler();
	}

	@Override
	public boolean getProfileSql() {
		return getCurrentConnection().getProfileSql();
	}

	@Override
	public boolean getProfileSQL() {
		return getCurrentConnection().getProfileSQL();
	}

	@Override
	public Properties getProperties() {
		Properties props = new Properties();
		props.putAll(this.masterConnection.getProperties());
		props.putAll(this.slavesConnection.getProperties());

		return props;
	}

	@Override
	public String getPropertiesTransform() {
		return getCurrentConnection().getPropertiesTransform();
	}

	@Override
	public int getQueriesBeforeRetryMaster() {
		return getCurrentConnection().getQueriesBeforeRetryMaster();
	}

	@Override
	public boolean getQueryTimeoutKillsConnection() {
		return getCurrentConnection().getQueryTimeoutKillsConnection();
	}

	@Override
	public boolean getReconnectAtTxEnd() {
		return getCurrentConnection().getReconnectAtTxEnd();
	}

	@Override
	public boolean getRelaxAutoCommit() {
		return getCurrentConnection().getRelaxAutoCommit();
	}

	@Override
	public boolean getReplicationEnableJMX() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getReportMetricsIntervalMillis() {
		return getCurrentConnection().getReportMetricsIntervalMillis();
	}

	@Override
	public boolean getRequireSSL() {
		return getCurrentConnection().getRequireSSL();
	}

	@Override
	public String getResourceId() {
		return getCurrentConnection().getResourceId();
	}

	@Override
	public int getResultSetSizeThreshold() {
		return getCurrentConnection().getResultSetSizeThreshold();
	}

	@Override
	public boolean getRetainStatementAfterResultSetClose() {
		return getCurrentConnection().getRetainStatementAfterResultSetClose();
	}

	@Override
	public int getRetriesAllDown() {
		return getCurrentConnection().getRetriesAllDown();
	}

	@Override
	public boolean getRewriteBatchedStatements() {
		return getCurrentConnection().getRewriteBatchedStatements();
	}

	@Override
	public boolean getRollbackOnPooledClose() {
		return getCurrentConnection().getRollbackOnPooledClose();
	}

	@Override
	public boolean getRoundRobinLoadBalance() {
		return getCurrentConnection().getRoundRobinLoadBalance();
	}

	@Override
	public boolean getRunningCTS13() {
		return getCurrentConnection().getRunningCTS13();
	}

	@Override
	public String getSchema() throws SQLException {
		return getCurrentConnection().getSchema();
	}

	@Override
	public int getSecondsBeforeRetryMaster() {
		return getCurrentConnection().getSecondsBeforeRetryMaster();
	}

	@Override
	public int getSelfDestructOnPingMaxOperations() {
		return getCurrentConnection().getSelfDestructOnPingMaxOperations();
	}

	@Override
	public int getSelfDestructOnPingSecondsLifetime() {
		return getCurrentConnection().getSelfDestructOnPingSecondsLifetime();
	}

	@Override
	public String getServerCharacterEncoding() {
		return getCurrentConnection().getServerCharacterEncoding();
	}

	@Override
	public String getServerConfigCacheFactory() {
		return getCurrentConnection().getServerConfigCacheFactory();
	}

	@Override
	public String getServerTimezone() {
		return getCurrentConnection().getServerTimezone();
	}

	@Override
	public TimeZone getServerTimezoneTZ() {
		return getCurrentConnection().getServerTimezoneTZ();
	}

	@Override
	public String getSessionVariables() {
		return getCurrentConnection().getSessionVariables();
	}

	public synchronized Connection getSlavesConnection() {
		return this.slavesConnection;
	}

	@Override
	public int getSlowQueryThresholdMillis() {
		return getCurrentConnection().getSlowQueryThresholdMillis();
	}

	@Override
	public long getSlowQueryThresholdNanos() {
		return getCurrentConnection().getSlowQueryThresholdNanos();
	}

	@Override
	public String getSocketFactory() {
		return getCurrentConnection().getSocketFactory();
	}

	@Override
	public String getSocketFactoryClassName() {
		return getCurrentConnection().getSocketFactoryClassName();
	}

	@Override
	public int getSocketTimeout() {
		return getCurrentConnection().getSocketTimeout();
	}

	@Override
	public String getStatementComment() {
		return getCurrentConnection().getStatementComment();
	}

	@Override
	public String getStatementInterceptors() {
		return getCurrentConnection().getStatementInterceptors();
	}

	@Override
	public boolean getStrictFloatingPoint() {
		return getCurrentConnection().getStrictFloatingPoint();
	}

	@Override
	public boolean getStrictUpdates() {
		return getCurrentConnection().getStrictUpdates();
	}

	@Override
	public boolean getTcpKeepAlive() {
		return getCurrentConnection().getTcpKeepAlive();
	}

	@Override
	public boolean getTcpNoDelay() {
		return getCurrentConnection().getTcpNoDelay();
	}

	@Override
	public int getTcpRcvBuf() {
		return getCurrentConnection().getTcpRcvBuf();
	}

	@Override
	public int getTcpSndBuf() {
		return getCurrentConnection().getTcpSndBuf();
	}

	@Override
	public int getTcpTrafficClass() {
		return getCurrentConnection().getTcpTrafficClass();
	}

	@Override
	public boolean getTinyInt1isBit() {
		return getCurrentConnection().getTinyInt1isBit();
	}

	@Override
	public boolean getTraceProtocol() {
		return getCurrentConnection().getTraceProtocol();
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return getCurrentConnection().getTransactionIsolation();
	}

	@Override
	public boolean getTransformedBitIsBoolean() {
		return getCurrentConnection().getTransformedBitIsBoolean();
	}

	@Override
	public boolean getTreatUtilDateAsTimestamp() {
		return getCurrentConnection().getTreatUtilDateAsTimestamp();
	}

	@Override
	public String getTrustCertificateKeyStorePassword() {
		return getCurrentConnection().getTrustCertificateKeyStorePassword();
	}

	@Override
	public String getTrustCertificateKeyStoreType() {
		return getCurrentConnection().getTrustCertificateKeyStoreType();
	}

	@Override
	public String getTrustCertificateKeyStoreUrl() {
		return getCurrentConnection().getTrustCertificateKeyStoreUrl();
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return getCurrentConnection().getTypeMap();
	}

	@Override
	public boolean getUltraDevHack() {
		return getCurrentConnection().getUltraDevHack();
	}

	@Override
	public boolean getUseAffectedRows() {
		return getCurrentConnection().getUseAffectedRows();
	}

	@Override
	public boolean getUseBlobToStoreUTF8OutsideBMP() {
		return getCurrentConnection().getUseBlobToStoreUTF8OutsideBMP();
	}

	@Override
	public boolean getUseColumnNamesInFindColumn() {
		return getCurrentConnection().getUseColumnNamesInFindColumn();
	}

	@Override
	public boolean getUseCompression() {
		return getCurrentConnection().getUseCompression();
	}

	@Override
	public String getUseConfigs() {
		return getCurrentConnection().getUseConfigs();
	}

	@Override
	public boolean getUseCursorFetch() {
		return getCurrentConnection().getUseCursorFetch();
	}

	@Override
	public boolean getUseDirectRowUnpack() {
		return getCurrentConnection().getUseDirectRowUnpack();
	}

	@Override
	public boolean getUseDynamicCharsetInfo() {
		return getCurrentConnection().getUseDynamicCharsetInfo();
	}

	@Override
	public boolean getUseFastDateParsing() {
		return getCurrentConnection().getUseFastDateParsing();
	}

	@Override
	public boolean getUseFastIntParsing() {
		return getCurrentConnection().getUseFastIntParsing();
	}

	@Override
	public boolean getUseGmtMillisForDatetimes() {
		return getCurrentConnection().getUseGmtMillisForDatetimes();
	}

	@Override
	public boolean getUseHostsInPrivileges() {
		return getCurrentConnection().getUseHostsInPrivileges();
	}

	@Override
	public boolean getUseInformationSchema() {
		return getCurrentConnection().getUseInformationSchema();
	}

	@Override
	public boolean getUseJDBCCompliantTimezoneShift() {
		return getCurrentConnection().getUseJDBCCompliantTimezoneShift();
	}

	@Override
	public boolean getUseJvmCharsetConverters() {
		return getCurrentConnection().getUseJvmCharsetConverters();
	}

	@Override
	public boolean getUseLegacyDatetimeCode() {
		return getCurrentConnection().getUseLegacyDatetimeCode();
	}

	@Override
	public boolean getUseLocalSessionState() {
		return getCurrentConnection().getUseLocalSessionState();
	}

	@Override
	public boolean getUseLocalTransactionState() {
		return getCurrentConnection().getUseLocalTransactionState();
	}

	@Override
	public boolean getUseNanosForElapsedTime() {
		return getCurrentConnection().getUseNanosForElapsedTime();
	}

	@Override
	public boolean getUseOldAliasMetadataBehavior() {
		return getCurrentConnection().getUseOldAliasMetadataBehavior();
	}

	@Override
	public boolean getUseOldUTF8Behavior() {
		return getCurrentConnection().getUseOldUTF8Behavior();
	}

	@Override
	public boolean getUseOnlyServerErrorMessages() {
		return getCurrentConnection().getUseOnlyServerErrorMessages();
	}

	@Override
	public boolean getUseReadAheadInput() {
		return getCurrentConnection().getUseReadAheadInput();
	}

	@Override
	public boolean getUseServerPreparedStmts() {
		return getCurrentConnection().getUseServerPreparedStmts();
	}

	@Override
	public boolean getUseServerPrepStmts() {
		return getCurrentConnection().getUseServerPrepStmts();
	}

	@Override
	public boolean getUseSqlStateCodes() {
		return getCurrentConnection().getUseSqlStateCodes();
	}

	@Override
	public boolean getUseSSL() {
		return getCurrentConnection().getUseSSL();
	}

	@Override
	public boolean getUseSSPSCompatibleTimezoneShift() {
		return getCurrentConnection().getUseSSPSCompatibleTimezoneShift();
	}

	@Override
	public boolean getUseStreamLengthsInPrepStmts() {
		return getCurrentConnection().getUseStreamLengthsInPrepStmts();
	}

	@Override
	public boolean getUseTimezone() {
		return getCurrentConnection().getUseTimezone();
	}

	@Override
	public boolean getUseUltraDevWorkAround() {
		return getCurrentConnection().getUseUltraDevWorkAround();
	}

	@Override
	public boolean getUseUnbufferedInput() {
		return getCurrentConnection().getUseUnbufferedInput();
	}

	@Override
	public boolean getUseUnicode() {
		return getCurrentConnection().getUseUnicode();
	}

	@Override
	public boolean getUseUsageAdvisor() {
		return getCurrentConnection().getUseUsageAdvisor();
	}

	@Override
	public String getUtf8OutsideBmpExcludedColumnNamePattern() {
		return getCurrentConnection().getUtf8OutsideBmpExcludedColumnNamePattern();
	}

	@Override
	public String getUtf8OutsideBmpIncludedColumnNamePattern() {
		return getCurrentConnection().getUtf8OutsideBmpIncludedColumnNamePattern();
	}

	@Override
	public boolean getVerifyServerCertificate() {
		return getCurrentConnection().getVerifyServerCertificate();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return getCurrentConnection().getWarnings();
	}

	@Override
	public boolean getYearIsDateType() {
		return getCurrentConnection().getYearIsDateType();
	}

	@Override
	public String getZeroDateTimeBehavior() {
		return getCurrentConnection().getZeroDateTimeBehavior();
	}

	@Override
	public boolean hasSameProperties(Connection c) {
		return (this.masterConnection.hasSameProperties(c)) && (this.slavesConnection.hasSameProperties(c));
	}

	@Override
	public boolean hasTriedMaster() {
		return getCurrentConnection().hasTriedMaster();
	}

	@Override
	public void initializeExtension(Extension ex) throws SQLException {
		getCurrentConnection().initializeExtension(ex);
	}

	private void initializeMasterConnection() throws SQLException {
		StringBuffer masterUrl = new StringBuffer("jdbc:mysql://");

		String masterHost = this.masterProperties.getProperty("HOST");

		if (masterHost != null) {
			masterUrl.append(masterHost);
		}

		String masterDb = this.masterProperties.getProperty("DBNAME");

		masterUrl.append("/");

		if (masterDb != null) {
			masterUrl.append(masterDb);
		}

		this.masterConnection = ((Connection) this.driver.connect(masterUrl.toString(), this.masterProperties));
	}

	private void initializeSlaveConnection() throws SQLException {
		StringBuffer slaveUrl = new StringBuffer("jdbc:mysql:loadbalance://");

		int numHosts = Integer.parseInt(this.slaveProperties.getProperty("NUM_HOSTS"));

		for (int i = 1; i <= numHosts; i++) {
			String slaveHost = this.slaveProperties.getProperty("HOST." + i);

			if (slaveHost != null) {
				if (i > 1) {
					slaveUrl.append(',');
				}
				slaveUrl.append(slaveHost);
			}

		}

		String slaveDb = this.slaveProperties.getProperty("DBNAME");

		slaveUrl.append("/");

		if (slaveDb != null) {
			slaveUrl.append(slaveDb);
		}

		this.slaveProperties.setProperty("roundRobinLoadBalance", "true");
		this.slaveProperties.setProperty("autoReconnect", "true");
		this.slaveProperties.setProperty("failOverReadOnly", "false");

		this.slavesConnection = ((Connection) this.driver.connect(slaveUrl.toString(), this.slaveProperties));

		this.slavesConnection.setReadOnly(true);
	}

	@Override
	public boolean isAbonormallyLongQuery(long millisOrNanos) {
		return getCurrentConnection().isAbonormallyLongQuery(millisOrNanos);
	}

	@Override
	public boolean isClosed() throws SQLException {
		return getCurrentConnection().isClosed();
	}

	@Override
	public boolean isInGlobalTx() {
		return getCurrentConnection().isInGlobalTx();
	}

	@Override
	public boolean isMasterConnection() {
		return this.currentConnection == this.masterConnection;
	}

	@Override
	public boolean isNoBackslashEscapesSet() {
		return getCurrentConnection().isNoBackslashEscapesSet();
	}

	@Override
	public synchronized boolean isReadOnly() throws SQLException {
		return this.currentConnection == this.slavesConnection;
	}

	@Override
	public boolean isSameResource(Connection c) {
		return getCurrentConnection().isSameResource(c);
	}

	@Override
	public boolean isServerLocal() throws SQLException {
		return getCurrentConnection().isServerLocal();
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean lowerCaseTableNames() {
		return getCurrentConnection().lowerCaseTableNames();
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		setReadOnly(sql);

		return getCurrentConnection().nativeSQL(sql);
	}

	@Override
	public boolean parserKnowsUnicode() {
		return getCurrentConnection().parserKnowsUnicode();
	}

	@Override
	public synchronized void ping() throws SQLException {
		try {
			this.masterConnection.ping();
		} catch (SQLException e) {
			if (isMasterConnection())
				throw e;
		}
		try {
			this.slavesConnection.ping();
		} catch (SQLException e) {
			if (!isMasterConnection())
				throw e;
		}
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		setReadOnly(sql);

		return getCurrentConnection().prepareCall(sql);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		setReadOnly(sql);

		return getCurrentConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		setReadOnly(sql);

		return getCurrentConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().prepareStatement(sql);

		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().prepareStatement(sql, autoGeneratedKeys);

		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);

		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().prepareStatement(sql, resultSetType, resultSetConcurrency,
				resultSetHoldability);

		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().prepareStatement(sql, columnIndexes);

		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().prepareStatement(sql, columnNames);

		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		getCurrentConnection().releaseSavepoint(savepoint);
	}

	@Override
	public void reportQueryTime(long millisOrNanos) {
		getCurrentConnection().reportQueryTime(millisOrNanos);
	}

	@Override
	public void resetServerState() throws SQLException {
		getCurrentConnection().resetServerState();
	}

	@Override
	public void rollback() throws SQLException {
		getCurrentConnection().rollback();
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		getCurrentConnection().rollback(savepoint);
	}

	@Override
	public PreparedStatement serverPrepareStatement(String sql) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().serverPrepareStatement(sql);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement serverPrepareStatement(String sql, int autoGenKeyIndex) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().serverPrepareStatement(sql, autoGenKeyIndex);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().serverPrepareStatement(sql, resultSetType,
				resultSetConcurrency);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement serverPrepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().serverPrepareStatement(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement serverPrepareStatement(String sql, int[] autoGenKeyIndexes) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().serverPrepareStatement(sql, autoGenKeyIndexes);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public PreparedStatement serverPrepareStatement(String sql, String[] autoGenKeyColNames) throws SQLException {
		setReadOnly(sql);

		PreparedStatement pstmt = getCurrentConnection().serverPrepareStatement(sql, autoGenKeyColNames);
		((Statement) pstmt).setPingTarget(this);

		return pstmt;
	}

	@Override
	public void setAllowLoadLocalInfile(boolean property) {
	}

	@Override
	public void setAllowMasterDownConnections(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAllowMultiQueries(boolean property) {
	}

	@Override
	public void setAllowNanAndInf(boolean flag) {
	}

	@Override
	public void setAllowUrlInLocalInfile(boolean flag) {
	}

	@Override
	public void setAlwaysSendSetIsolation(boolean flag) {
	}

	@Override
	public void setAuthenticationPlugins(String authenticationPlugins) {
		getCurrentConnection().setAuthenticationPlugins(authenticationPlugins);
	}

	@Override
	public void setAutoClosePStmtStreams(boolean flag) {
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		getCurrentConnection().setAutoCommit(autoCommit);
	}

	@Override
	public void setAutoDeserialize(boolean flag) {
	}

	@Override
	public void setAutoGenerateTestcaseScript(boolean flag) {
	}

	@Override
	public void setAutoReconnect(boolean flag) {
	}

	@Override
	public void setAutoReconnectForConnectionPools(boolean property) {
	}

	@Override
	public void setAutoReconnectForPools(boolean flag) {
	}

	@Override
	public void setAutoSlowLog(boolean flag) {
	}

	@Override
	public void setBlobsAreStrings(boolean flag) {
	}

	@Override
	public void setBlobSendChunkSize(String value) throws SQLException {
	}

	@Override
	public void setCacheCallableStatements(boolean flag) {
	}

	@Override
	public void setCacheCallableStmts(boolean flag) {
	}

	@Override
	public void setCachePreparedStatements(boolean flag) {
	}

	@Override
	public void setCachePrepStmts(boolean flag) {
	}

	@Override
	public void setCacheResultSetMetadata(boolean property) {
	}

	@Override
	public void setCacheServerConfiguration(boolean flag) {
	}

	@Override
	public void setCallableStatementCacheSize(int size) {
	}

	@Override
	public void setCallableStmtCacheSize(int cacheSize) {
	}

	@Override
	public void setCapitalizeDBMDTypes(boolean property) {
	}

	@Override
	public void setCapitalizeTypeNames(boolean flag) {
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		getCurrentConnection().setCatalog(catalog);
	}

	@Override
	public void setCharacterEncoding(String encoding) {
	}

	@Override
	public void setCharacterSetResults(String characterSet) {
	}

	@Override
	public void setClientCertificateKeyStorePassword(String value) {
	}

	@Override
	public void setClientCertificateKeyStoreType(String value) {
	}

	@Override
	public void setClientCertificateKeyStoreUrl(String value) {
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfoProvider(String classname) {
	}

	@Override
	public void setClobberStreamingResults(boolean flag) {
	}

	@Override
	public void setClobCharacterEncoding(String encoding) {
	}

	@Override
	public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) {
	}

	@Override
	public void setConnectionCollation(String collation) {
	}

	@Override
	public void setConnectionLifecycleInterceptors(String interceptors) {
	}

	@Override
	public void setConnectTimeout(int timeoutMs) {
	}

	@Override
	public void setContinueBatchOnError(boolean property) {
	}

	@Override
	public void setCreateDatabaseIfNotExist(boolean flag) {
	}

	@Override
	public void setDefaultAuthenticationPlugin(String defaultAuthenticationPlugin) {
		getCurrentConnection().setDefaultAuthenticationPlugin(defaultAuthenticationPlugin);
	}

	@Override
	public void setDefaultFetchSize(int n) {
	}

	@Override
	public void setDetectServerPreparedStmts(boolean property) {
	}

	@Override
	public void setDisabledAuthenticationPlugins(String disabledAuthenticationPlugins) {
		getCurrentConnection().setDisabledAuthenticationPlugins(disabledAuthenticationPlugins);
	}

	@Override
	public void setDisconnectOnExpiredPasswords(boolean disconnectOnExpiredPasswords) {
		getCurrentConnection().setDisconnectOnExpiredPasswords(disconnectOnExpiredPasswords);
	}

	@Override
	public void setDontTrackOpenResources(boolean flag) {
	}

	@Override
	public void setDumpMetadataOnColumnNotFound(boolean flag) {
	}

	@Override
	public void setDumpQueriesOnException(boolean flag) {
	}

	@Override
	public void setDynamicCalendars(boolean flag) {
	}

	@Override
	public void setElideSetAutoCommits(boolean flag) {
	}

	@Override
	public void setEmptyStringsConvertToZero(boolean flag) {
	}

	@Override
	public void setEmulateLocators(boolean property) {
	}

	@Override
	public void setEmulateUnsupportedPstmts(boolean flag) {
	}

	@Override
	public void setEnablePacketDebug(boolean flag) {
	}

	@Override
	public void setEnableQueryTimeouts(boolean flag) {
	}

	@Override
	public void setEncoding(String property) {
	}

	@Override
	public void setExceptionInterceptors(String exceptionInterceptors) {
		getCurrentConnection().setExceptionInterceptors(exceptionInterceptors);
	}

	@Override
	public void setExplainSlowQueries(boolean flag) {
	}

	@Override
	public void setFailedOver(boolean flag) {
		getCurrentConnection().setFailedOver(flag);
	}

	@Override
	public void setFailOverReadOnly(boolean flag) {
	}

	@Override
	public void setFunctionsNeverReturnBlobs(boolean flag) {
	}

	@Override
	public void setGatherPerfMetrics(boolean flag) {
	}

	@Override
	public void setGatherPerformanceMetrics(boolean flag) {
	}

	@Override
	public void setGenerateSimpleParameterMetadata(boolean flag) {
	}

	@Override
	public void setGetProceduresReturnsFunctions(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		getCurrentConnection().setHoldability(holdability);
	}

	@Override
	public void setHoldResultsOpenOverStatementClose(boolean flag) {
	}

	@Override
	public void setIgnoreNonTxTables(boolean property) {
	}

	@Override
	public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) {
	}

	@Override
	public void setIncludeThreadDumpInDeadlockExceptions(boolean flag) {
		getCurrentConnection().setIncludeThreadDumpInDeadlockExceptions(flag);
	}

	@Override
	public void setIncludeThreadNamesAsStatementComment(boolean flag) {
		getCurrentConnection().setIncludeThreadNamesAsStatementComment(flag);
	}

	@Override
	public void setInGlobalTx(boolean flag) {
		getCurrentConnection().setInGlobalTx(flag);
	}

	@Override
	public void setInitialTimeout(int property) {
	}

	@Override
	public void setInteractiveClient(boolean property) {
	}

	@Override
	public void setIsInteractiveClient(boolean property) {
	}

	@Override
	public void setJdbcCompliantTruncation(boolean flag) {
	}

	@Override
	public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) {
	}

	@Override
	public void setLargeRowSizeThreshold(String value) {
	}

	@Override
	public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) {
		getCurrentConnection().setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex);
	}

	@Override
	public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold)
			throws SQLException {
		getCurrentConnection().setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold);
	}

	@Override
	public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) throws SQLException {
		getCurrentConnection().setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout);
	}

	@Override
	public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) {
		this.currentConnection.setLoadBalanceConnectionGroup(loadBalanceConnectionGroup);
	}

	@Override
	public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) {
		this.currentConnection.setLoadBalanceEnableJMX(loadBalanceEnableJMX);
	}

	@Override
	public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) {
		this.currentConnection.setLoadBalanceExceptionChecker(loadBalanceExceptionChecker);
	}

	@Override
	public void setLoadBalancePingTimeout(int loadBalancePingTimeout) throws SQLException {
		getCurrentConnection().setLoadBalancePingTimeout(loadBalancePingTimeout);
	}

	@Override
	public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) {
		this.currentConnection.setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover);
	}

	@Override
	public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) {
		this.currentConnection.setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover);
	}

	@Override
	public void setLoadBalanceStrategy(String strategy) {
	}

	@Override
	public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) {
		getCurrentConnection().setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer);
	}

	@Override
	public void setLocalSocketAddress(String address) {
	}

	@Override
	public void setLocatorFetchBufferSize(String value) throws SQLException {
	}

	@Override
	public void setLogger(String property) {
	}

	@Override
	public void setLoggerClassName(String className) {
	}

	@Override
	public void setLogSlowQueries(boolean flag) {
	}

	@Override
	public void setLogXaCommands(boolean flag) {
	}

	@Override
	public void setMaintainTimeStats(boolean flag) {
	}

	@Override
	public void setMaxQuerySizeToLog(int sizeInBytes) {
	}

	@Override
	public void setMaxReconnects(int property) {
	}

	@Override
	public void setMaxRows(int property) {
	}

	@Override
	public void setMetadataCacheSize(int value) {
	}

	@Override
	public void setNetTimeoutForStreamingResults(int value) {
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		getCurrentConnection().setNetworkTimeout(executor, milliseconds);
	}

	@Override
	public void setNoAccessToProcedureBodies(boolean flag) {
	}

	@Override
	public void setNoDatetimeStringSync(boolean flag) {
	}

	@Override
	public void setNoTimezoneConversionForTimeType(boolean flag) {
	}

	@Override
	public void setNullCatalogMeansCurrent(boolean value) {
	}

	@Override
	public void setNullNamePatternMatchesAll(boolean value) {
	}

	@Override
	public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) {
	}

	@Override
	public void setPacketDebugBufferSize(int size) {
	}

	@Override
	public void setPadCharsWithSpace(boolean flag) {
	}

	@Override
	public void setParanoid(boolean property) {
	}

	@Override
	public void setParseInfoCacheFactory(String factoryClassname) {
		getCurrentConnection().setParseInfoCacheFactory(factoryClassname);
	}

	@Override
	public void setPasswordCharacterEncoding(String characterSet) {
		getCurrentConnection().setPasswordCharacterEncoding(characterSet);
	}

	@Override
	public void setPedantic(boolean property) {
	}

	@Override
	public void setPinGlobalTxToPhysicalConnection(boolean flag) {
	}

	@Override
	public void setPopulateInsertRowWithDefaultValues(boolean flag) {
	}

	@Override
	public void setPreferSlaveDuringFailover(boolean flag) {
		getCurrentConnection().setPreferSlaveDuringFailover(flag);
	}

	@Override
	public void setPreparedStatementCacheSize(int cacheSize) {
	}

	@Override
	public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) {
	}

	@Override
	public void setPrepStmtCacheSize(int cacheSize) {
	}

	@Override
	public void setPrepStmtCacheSqlLimit(int sqlLimit) {
	}

	@Override
	public void setProcessEscapeCodesForPrepStmts(boolean flag) {
	}

	@Override
	public void setProfilerEventHandler(String handler) {
	}

	@Override
	public void setProfileSql(boolean property) {
	}

	@Override
	public void setProfileSQL(boolean flag) {
	}

	@Override
	public void setPropertiesTransform(String value) {
	}

	@Override
	public void setProxy(MySQLConnection proxy) {
		getCurrentConnection().setProxy(proxy);
	}

	@Override
	public void setQueriesBeforeRetryMaster(int property) {
	}

	@Override
	public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) {
		getCurrentConnection().setQueryTimeoutKillsConnection(queryTimeoutKillsConnection);
	}

	@Override
	public synchronized void setReadOnly(boolean readOnly) throws SQLException {
		if (readOnly) {
			if (this.currentConnection != this.slavesConnection) {
				switchToSlavesConnection();
			}
		} else if (this.currentConnection != this.masterConnection)
			switchToMasterConnection();
	}

	private synchronized void setReadOnly(String sql) throws SQLException {
		setReadOnly(currentConnection.getAutoCommit() ? sql.trim().toLowerCase().startsWith("select") : false);
	}

	@Override
	public void setReconnectAtTxEnd(boolean property) {
	}

	@Override
	public void setRelaxAutoCommit(boolean property) {
	}

	@Override
	public void setReplicationEnableJMX(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReportMetricsIntervalMillis(int millis) {
	}

	@Override
	public void setRequireSSL(boolean property) {
	}

	@Override
	public void setResourceId(String resourceId) {
	}

	@Override
	public void setResultSetSizeThreshold(int threshold) {
	}

	@Override
	public void setRetainStatementAfterResultSetClose(boolean flag) {
	}

	@Override
	public void setRetriesAllDown(int retriesAllDown) throws SQLException {
		getCurrentConnection().setRetriesAllDown(retriesAllDown);
	}

	@Override
	public void setRewriteBatchedStatements(boolean flag) {
	}

	@Override
	public void setRollbackOnPooledClose(boolean flag) {
	}

	@Override
	public void setRoundRobinLoadBalance(boolean flag) {
	}

	@Override
	public void setRunningCTS13(boolean flag) {
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return getCurrentConnection().setSavepoint();
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		return getCurrentConnection().setSavepoint(name);
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		getCurrentConnection().setSchema(schema);
	}

	@Override
	public void setSecondsBeforeRetryMaster(int property) {
	}

	@Override
	public void setSelfDestructOnPingMaxOperations(int maxOperations) {
	}

	@Override
	public void setSelfDestructOnPingSecondsLifetime(int seconds) {
	}

	@Override
	public void setServerConfigCacheFactory(String factoryClassname) {
		getCurrentConnection().setServerConfigCacheFactory(factoryClassname);
	}

	@Override
	public void setServerTimezone(String property) {
	}

	@Override
	public void setSessionVariables(String variables) {
	}

	@Override
	public void setSlowQueryThresholdMillis(int millis) {
	}

	@Override
	public void setSlowQueryThresholdNanos(long nanos) {
	}

	@Override
	public void setSocketFactory(String name) {
	}

	@Override
	public void setSocketFactoryClassName(String property) {
	}

	@Override
	public void setSocketTimeout(int property) {
	}

	@Override
	public synchronized void setStatementComment(String comment) {
		this.masterConnection.setStatementComment(comment);
		this.slavesConnection.setStatementComment(comment);
	}

	@Override
	public void setStatementInterceptors(String value) {
	}

	@Override
	public void setStrictFloatingPoint(boolean property) {
	}

	@Override
	public void setStrictUpdates(boolean property) {
	}

	@Override
	public void setTcpKeepAlive(boolean flag) {
	}

	@Override
	public void setTcpNoDelay(boolean flag) {
	}

	@Override
	public void setTcpRcvBuf(int bufSize) {
	}

	@Override
	public void setTcpSndBuf(int bufSize) {
	}

	@Override
	public void setTcpTrafficClass(int classFlags) {
	}

	@Override
	public void setTinyInt1isBit(boolean flag) {
	}

	@Override
	public void setTraceProtocol(boolean flag) {
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		getCurrentConnection().setTransactionIsolation(level);
	}

	@Override
	public void setTransformedBitIsBoolean(boolean flag) {
	}

	@Override
	public void setTreatUtilDateAsTimestamp(boolean flag) {
	}

	@Override
	public void setTrustCertificateKeyStorePassword(String value) {
	}

	@Override
	public void setTrustCertificateKeyStoreType(String value) {
	}

	@Override
	public void setTrustCertificateKeyStoreUrl(String value) {
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
	}

	@Override
	public void setUltraDevHack(boolean flag) {
	}

	@Override
	public void setUseAffectedRows(boolean flag) {
	}

	@Override
	public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) {
	}

	@Override
	public void setUseColumnNamesInFindColumn(boolean flag) {
	}

	@Override
	public void setUseCompression(boolean property) {
	}

	@Override
	public void setUseConfigs(String configs) {
	}

	@Override
	public void setUseCursorFetch(boolean flag) {
	}

	@Override
	public void setUseDirectRowUnpack(boolean flag) {
	}

	@Override
	public void setUseDynamicCharsetInfo(boolean flag) {
	}

	@Override
	public void setUseFastDateParsing(boolean flag) {
	}

	@Override
	public void setUseFastIntParsing(boolean flag) {
	}

	@Override
	public void setUseGmtMillisForDatetimes(boolean flag) {
	}

	@Override
	public void setUseHostsInPrivileges(boolean property) {
	}

	@Override
	public void setUseInformationSchema(boolean flag) {
	}

	@Override
	public void setUseJDBCCompliantTimezoneShift(boolean flag) {
	}

	@Override
	public void setUseJvmCharsetConverters(boolean flag) {
	}

	@Override
	public void setUseLegacyDatetimeCode(boolean flag) {
	}

	@Override
	public void setUseLocalSessionState(boolean flag) {
	}

	@Override
	public void setUseLocalTransactionState(boolean flag) {
	}

	@Override
	public void setUseNanosForElapsedTime(boolean flag) {
	}

	@Override
	public void setUseOldAliasMetadataBehavior(boolean flag) {
	}

	@Override
	public void setUseOldUTF8Behavior(boolean flag) {
	}

	@Override
	public void setUseOnlyServerErrorMessages(boolean flag) {
	}

	@Override
	public void setUseReadAheadInput(boolean flag) {
	}

	@Override
	public void setUseServerPreparedStmts(boolean flag) {
	}

	@Override
	public void setUseServerPrepStmts(boolean flag) {
	}

	@Override
	public void setUseSqlStateCodes(boolean flag) {
	}

	@Override
	public void setUseSSL(boolean property) {
	}

	@Override
	public void setUseSSPSCompatibleTimezoneShift(boolean flag) {
	}

	@Override
	public void setUseStreamLengthsInPrepStmts(boolean property) {
	}

	@Override
	public void setUseTimezone(boolean property) {
	}

	@Override
	public void setUseUltraDevWorkAround(boolean property) {
	}

	@Override
	public void setUseUnbufferedInput(boolean flag) {
	}

	@Override
	public void setUseUnicode(boolean flag) {
	}

	@Override
	public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) {
	}

	@Override
	public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) {
	}

	@Override
	public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) {
	}

	@Override
	public void setVerifyServerCertificate(boolean flag) {
	}

	@Override
	public void setYearIsDateType(boolean flag) {
	}

	@Override
	public void setZeroDateTimeBehavior(String behavior) {
	}

	@Override
	public void shutdownServer() throws SQLException {
		getCurrentConnection().shutdownServer();
	}

	@Override
	public boolean supportsIsolationLevel() {
		return getCurrentConnection().supportsIsolationLevel();
	}

	@Override
	public boolean supportsQuotedIdentifiers() {
		return getCurrentConnection().supportsQuotedIdentifiers();
	}

	@Override
	public boolean supportsTransactions() {
		return getCurrentConnection().supportsTransactions();
	}

	private synchronized void swapConnections(Connection switchToConnection, Connection switchFromConnection)
			throws SQLException {
		String switchFromCatalog = switchFromConnection.getCatalog();
		String switchToCatalog = switchToConnection.getCatalog();

		if ((switchToCatalog != null) && (!switchToCatalog.equals(switchFromCatalog)))
			switchToConnection.setCatalog(switchFromCatalog);
		else if (switchFromCatalog != null) {
			switchToConnection.setCatalog(switchFromCatalog);
		}

		boolean switchToAutoCommit = switchToConnection.getAutoCommit();
		boolean switchFromConnectionAutoCommit = switchFromConnection.getAutoCommit();

		if (switchFromConnectionAutoCommit != switchToAutoCommit) {
			switchToConnection.setAutoCommit(switchFromConnectionAutoCommit);
		}

		int switchToIsolation = switchToConnection.getTransactionIsolation();

		int switchFromIsolation = switchFromConnection.getTransactionIsolation();

		if (switchFromIsolation != switchToIsolation) {
			switchToConnection.setTransactionIsolation(switchFromIsolation);
		}

		this.currentConnection = switchToConnection;
	}

	private synchronized void switchToMasterConnection() throws SQLException {
		swapConnections(this.masterConnection, this.slavesConnection);
	}

	private synchronized void switchToSlavesConnection() throws SQLException {
		swapConnections(this.slavesConnection, this.masterConnection);
		this.slavesConnection.setReadOnly(true);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean useUnbufferedInput() {
		return getCurrentConnection().useUnbufferedInput();
	}

	@Override
	public boolean versionMeetsMinimum(int major, int minor, int subminor) throws SQLException {
		return getCurrentConnection().versionMeetsMinimum(major, minor, subminor);
	}
}