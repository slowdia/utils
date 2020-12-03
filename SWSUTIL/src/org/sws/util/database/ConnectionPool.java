package org.sws.util.database;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;

public class ConnectionPool {

	/** ConnectionPool instance */
	static ConnectionPool instance = null;
	
	/** DBCP base URL */
	static final String DBCP_URL = "jdbc:apache:commons:dbcp:";

	private PoolingDriver driver = null;
	private GenericObjectPool connectionPool = null;
	
	public static ConnectionPool getInstance() throws Exception
	{
		if ( instance == null) {
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	/**
	 * connectionPool을 생성하여 dbcpName으로 등록한다.
	 * @param dbcpName 저장될 이름
	 * @param driver
	 * @param url
	 * @param user
	 * @param password
	 * @param maxCon
	 * @param idle
	 * @param wait
	 * @param validationQuery
	 * @throws ClassNotFoundException
	 */
	public void setConnectionPool(String dbcpName, String driver, String url, String user, String password
				, int maxActive, int maxIdle, int minIdle, int maxWait, String validationQuery) throws ClassNotFoundException
	{
		Class.forName(driver);
		connectionPool = new GenericObjectPool(null);
		connectionPool.setMaxActive(maxActive);
		connectionPool.setMaxIdle(maxIdle);
		connectionPool.setMinIdle(minIdle);
		connectionPool.setMaxWait(maxWait);
		connectionPool.setTestOnBorrow(true);
		if(validationQuery.equals("")) validationQuery = null;
		
		new PoolableConnectionFactory(
					new DriverManagerConnectionFactory(url, user, password)
					, connectionPool
					, null	/* Sstatement pool */
					, validationQuery	/* 커넥션 테스트 쿼리: 커넥션이 유효한지 테스트할 때 사용되는 쿼리 */
					, false	/* Read only 여부 */
					, true	/* Auto commit 여부 */
				);
		
		// Pooling을 위한 JDBC 드라이버 생성 및 등록
		if(this.driver == null) this.driver = new PoolingDriver();
		this.driver.registerPool(dbcpName, connectionPool);
		
	}
	
	/**
	 * Connection 객체를 얻는다.
	 * @param dbcpName
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection(String dbcpName) throws Exception
	{
		return DriverManager.getConnection(DBCP_URL + dbcpName);
	}

	/**
	 * Returns the cap on the total number of active instances from my pool.
	 */
	public int getMaxActive(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getMaxActive();
		else
			return 0;
	}

	/**
	 * Returns the cap on the number of "idle" instances in the pool.
	 */
	public int getMaxIdle(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getMaxIdle();
		else
			return 0;
	}

	/**
	 * Returns the minimum number of objects allowed
	 * in the pool before the evictor thread (if active) spawns new objects.
	 */
	public int getMinIdle(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getMinIdle();
		else
			return 0;
	}

	/**
	 * Return the number of instances currently borrowed from my pool (optional operation).
	 */
	public int getNumActive(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getNumActive();
		else
			return 0;
	}

	/**
	 * Return the number of instances currently idle in my pool (optional operation).
	 */
	public int getNumIdle(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getNumIdle();
		else
			return 0;
	}

	/**
	 * Returns the number of objects to examine during
	 * each run of the idle object evictor thread (if any).
	 */
	public int getNumTestsPerEvictionRun(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getNumTestsPerEvictionRun();
		else
			return 0;
	}

	/**
	 * Returns the maximum amount of time (in milliseconds) the borrowObject() method
	 * should block before throwing an exception when the pool is exhausted
	 * and the "when exhausted" action is WHEN_EXHAUSTED_BLOCK.
	 */
	public long getMaxWait(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getMaxWait();
		else
			return 0;
	}

	/**
	 * Returns the minimum amount of time an object may sit idle in the pool
	 * before it is eligable for eviction by the idle object evictor (if any).
	 */
	public long getMinEvictableIdleTimeMillis(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getMinEvictableIdleTimeMillis();
		else
			return 0;
	}

	/**
	 * 유휴 객체 축출 스레드의 실행과 일시정지 사이의 밀리세컨드를 얻음.<br>
	 * Returns the number of milliseconds to sleep between runs of the idle object evictor thread.
	 */
	public long getTimeBetweenEvictionRunsMillis(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getTimeBetweenEvictionRunsMillis();
		else
			return 0;
	}

	/**
	 * Returns the action to take when the borrowObject() method is invoked
	 * when the pool is exhausted (the maximum number of "active" objects has been reached).
	 */
	public byte getWhenExhaustedAction(String dbcpName) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			return connectionPool.getWhenExhaustedAction();
		else
			return 0;
	}

	/**
	 * 최대 실행되는 커넥션 수를 지정한다.
	 * @param dbcpName
	 * @param i
	 * @throws Exception
	 */
	public void setMaxActive(String dbcpName, int i) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			connectionPool.setMaxActive(i);
	}
	
	/**
	 * 최대 idle 커넥션 수를 지정한다.
	 * @param dbcpName
	 * @param i
	 * @throws Exception
	 */
	public void setMaxIdle(String dbcpName, int i) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			connectionPool.setMaxIdle(i);
	}
	
	/**
	 * 최소 idle 커넥션 수를 지정한다.
	 * @param dbcpName
	 * @param i
	 * @throws Exception
	 */
	public void setMinIdle(String dbcpName, int i) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			connectionPool.setMinIdle(i);
	}
	
	/**
	 * 최대 대기 중인 커넥션 수를 지정한다.
	 * @param dbcpName
	 * @param i
	 * @throws Exception
	 */
	public void setMaxWait(String dbcpName, long i) throws Exception
	{
		connectionPool = (GenericObjectPool)this.driver.getConnectionPool(dbcpName);
		if ( connectionPool != null )
			connectionPool.setMaxWait(i);
	}
	
}
