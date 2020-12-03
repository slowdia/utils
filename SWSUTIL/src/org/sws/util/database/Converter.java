package org.sws.util.database;

import org.apache.log4j.Logger;

public class Converter {

	static Logger logger = Logger.getLogger(Converter.class);
	
	/**
	 * DBMS별로 시간 컬럼의 출력 데이터를 얻기 위한 SQL구문을 만들어 준다.<br>
	 * date/datetime/timestamp => yyyy-mm-dd hh24:mi:ss 형태로 변경하는 구문
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
	 * DBMS별로 날짜 스트링을 DB에 삽입하기 위한 SQL구문을 만든다.<br>
	 * yyyy-mm-dd hh24:mi:ss => date/datetime/timestamp 형태로 변경하는 구문
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
	 * DBMS별로 현재날짜를 얻기 위한 SQL구문을 만든다.<br>
	 * SYSDATE, GETDATE(), NOW() 등등
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
