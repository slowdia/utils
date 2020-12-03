package org.sws.util.database;

import org.apache.log4j.Logger;

public class Converter {

	static Logger logger = Logger.getLogger(Converter.class);
	
	/**
	 * DBMS���� �ð� �÷��� ��� �����͸� ��� ���� SQL������ ����� �ش�.<br>
	 * date/datetime/timestamp => yyyy-mm-dd hh24:mi:ss ���·� �����ϴ� ����
	 * @param column
	 * @param dbmsType
	 * @return ex> ORACLE : TO_CHAR(COLUMN,'yyyy-mm-dd hh24:mi:ss')
	 */
	public static String getDatetime(String column, int dbmsType)
	{
		StringBuffer sb = new StringBuffer();
		if(dbmsType == DBMS.ORACLE)
		{
			sb.append("TO_CHAR(").append(column).append(",'yyyy-mm-dd hh24:mi:ss')");
		}
		else if(dbmsType == DBMS.MS_SQL)
		{
			sb.append("CONVERT(varchar(19),").append(column).append(",20)");
		}
		else if(dbmsType == DBMS.MYSQL)
		{
			sb.append("DATE_FORMAT(").append(column).append(",'%Y-%m-%d %H:%i:%s')");
		}
		return sb.toString();
	}
	
	/**
	 * DBMS���� ��¥ ��Ʈ���� DB�� �����ϱ� ���� SQL������ �����.<br>
	 * yyyy-mm-dd hh24:mi:ss => date/datetime/timestamp ���·� �����ϴ� ����
	 * @param strDate
	 * @param dbmsType
	 * @return ex> ORACLE : TO_DATE('2018-01-01 12:59:59','yyyy-mm-dd hh24:mi:ss')
	 */
	public static String toDatetime(String strDate, int dbmsType)
	{
		StringBuffer sb = new StringBuffer();
		if(dbmsType == DBMS.ORACLE)
		{
			sb.append("TO_DATE('").append(strDate).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		else if(dbmsType == DBMS.MS_SQL)
		{
			sb.append("'").append(strDate).append("'");
		}
		else if(dbmsType == DBMS.MYSQL)
		{
			sb.append("DATE_FORMAT('").append(strDate).append("', '%y-%m-%d %h:%i:%s')");
		}
		return sb.toString();
	}
	
	/**
	 * DBMS���� ���糯¥�� ��� ���� SQL������ �����.<br>
	 * SYSDATE, GETDATE(), NOW() ���
	 * @param dbmsType
	 * @return ex> ORACLE : SYSDATE
	 */
	public static String getDatabaseDate(int dbmsType)
	{
		String str = "";
		if(dbmsType == DBMS.ORACLE)
		{
			str = "SYSDATE";
		}
		else if(dbmsType == DBMS.MS_SQL)
		{
			str = "GETDATE()";
		}
		else if(dbmsType == DBMS.MYSQL)
		{
			str = "NOW()";
		}
		return str;
	}
}
