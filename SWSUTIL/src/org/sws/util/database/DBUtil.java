package org.sws.util.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DBUtil {

	static Logger logger = Logger.getLogger(DBUtil.class);
	
	/**
	 * Database Connection을 해제한다.
	 * @param conn
	 */
	public static void close(Connection conn) {
		if(conn != null)
			try {
				conn.close(); conn = null;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
	}
	
	/**
	 * Statement 객체를 닫는다.
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		if(stmt != null)
			try {
				stmt.close(); stmt = null;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
	}
	
	/**
	 * PreparedStatement 객체를 닫는다.
	 * @param pstmt
	 */
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null)
			try {
				pstmt.close(); pstmt = null;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
	}
	
	/**
	 * CallableStatement 객체를 닫는다.
	 * @param pstmt
	 */
	public static void close(CallableStatement cstmt) {
		if(cstmt != null)
			try {
				cstmt.close(); cstmt = null;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
	}
	
	/**
	 * ResultSet 객체를 닫는다.
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		if(rs != null)
			try {
				rs.close(); rs = null;
			} catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
	}
	
	/**
	 * Database jndi 명을 통해 Connection 객체를 얻는다.
	 * @param jndi
	 * @return
	 * @throws SQLException
	 * @throws NamingException
	 */
	public static Connection getConnection(String jndi) throws SQLException, NamingException
	{
		Context ctx = new InitialContext();
		DataSource ds = (DataSource)ctx.lookup(jndi);
		return ds.getConnection();
	}
	
	/**
	 * Database 접속 정보를 통하여 Connection 객체를 얻는다.
	 * @param driver
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection(String driver, String url, String user, String password
			) throws SQLException, ClassNotFoundException
	{
		Class.forName(driver);
		return DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * 트랜잭션을 시작한다.<br>
	 * setAutoCommit(false);
	 * @param conn
	 * @throws SQLException
	 */
	public static void startTransaction(Connection conn) throws SQLException
	{
		conn.setAutoCommit(false);
	}
	
	/**
	 * 트랜잭션을을 종료한다.<br>
	 * setAutoCommit(true)
	 * @param conn
	 * @throws SQLException
	 */
	public static void endTransaction(Connection conn) throws SQLException
	{
		conn.setAutoCommit(true);
	}
	
	/**
	 * Connection에서 실행한 쿼리에 대해 commit을 실행한다.
	 * @param conn
	 * @throws SQLException
	 */
	public static void commit(Connection conn) throws SQLException
	{
		conn.commit();
	}
	
	/**
	 * Connection에서 실행한 쿼리에 대해 rollback을 실행한다.
	 * @param conn
	 * @throws SQLException
	 */
	public static void rollback(Connection conn) throws SQLException
	{
		conn.rollback();
	}
}
