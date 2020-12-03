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
	 * connectionPool�� �����Ͽ� dbcpName���� ����Ѵ�.
	 * @param dbcpName ����� �̸�
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
					, validationQuery	/* Ŀ�ؼ� �׽�Ʈ ����: Ŀ�ؼ��� ��ȿ���� �׽�Ʈ�� �� ���Ǵ� ���� */
					, false	/* Read only ���� */
					, true	/* Auto commit ���� */
				);
		
		// Pooling�� ���� JDBC ����̹� ���� �� ���
		if(this.driver == null) this.driver = new PoolingDriver();
		this.driver.registerPool(dbcpName, connectionPool);
		
	}
	
	/**
	 * Connection ��ü�� ��´�.
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
	 * ���� ��ü ���� �������� ����� �Ͻ����� ������ �и������带 ����.<br>
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
	 * �ִ� ����Ǵ� Ŀ�ؼ� ���� �����Ѵ�.
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
	 * �ִ� idle Ŀ�ؼ� ���� �����Ѵ�.
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
	 * �ּ� idle Ŀ�ؼ� ���� �����Ѵ�.
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
	 * �ִ� ��� ���� Ŀ�ؼ� ���� �����Ѵ�.
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
