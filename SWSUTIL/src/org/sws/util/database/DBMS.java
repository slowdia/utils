package org.sws.util.database;

public class DBMS {

	/**
	 * ORACLE database<br>
	 * driver = oracle.jdbc.driver.OracleDriver<br>
	 * url = jdbc:oracle:thin:@localhost:SID;
	 */
	public static final int ORACLE = 1;

	/**
	 * MSSQL database<br>
	 * driver = com.microsoft.sqlserver.jdbc.SQLServerDriver<br>
	 * url = jdbc:sqlserver://localhost:1433;databaseName=dbname;<br>
	 * <br>구<br>
	 * driver = com.microsoft.jdbc.sqlserver.SQLServerDriver<br>
	 * url = jdbc:Microsoft:sqlserver://localhost:1433;databasename=dbname<br>
	 */
	public static final int MS_SQL = 2;

	/**
	 * MYSQL database<br>
	 * driver = com.mysql.jdbc.Driver<br>
	 * url = jdbc:mysql://localhost:3306/dbname;
	 */
	public static final int MYSQL = 3;

}
