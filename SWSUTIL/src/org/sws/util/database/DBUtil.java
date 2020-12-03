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
	 * Database Connection�� �����Ѵ�.
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
	 * Statement ��ü�� �ݴ´�.
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
	 * PreparedStatement ��ü�� �ݴ´�.
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
	 * CallableStatement ��ü�� �ݴ´�.
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
	 * ResultSet ��ü�� �ݴ´�.
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
	 * Database jndi ���� ���� Connection ��ü�� ��´�.
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
	 * Database ���� ������ ���Ͽ� Connection ��ü�� ��´�.
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
	 * Ʈ������� �����Ѵ�.<br>
	 * setAutoCommit(false);
	 * @param conn
	 * @throws SQLException
	 */
	public static void startTransaction(Connection conn) throws SQLException
	{
		conn.setAutoCommit(false);
	}
	
	/**
	 * Ʈ��������� �����Ѵ�.<br>
	 * setAutoCommit(true)
	 * @param conn
	 * @throws SQLException
	 */
	public static void endTransaction(Connection conn) throws SQLException
	{
		conn.setAutoCommit(true);
	}
	
	/**
	 * Connection���� ������ ������ ���� commit�� �����Ѵ�.
	 * @param conn
	 * @throws SQLException
	 */
	public static void commit(Connection conn) throws SQLException
	{
		conn.commit();
	}
	
	/**
	 * Connection���� ������ ������ ���� rollback�� �����Ѵ�.
	 * @param conn
	 * @throws SQLException
	 */
	public static void rollback(Connection conn) throws SQLException
	{
		conn.rollback();
	}
}
