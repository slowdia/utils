/* ---------------------------------------------------------------------
 * @(#)ConversionUtil.java
 * @Modifier   Kim Jung-sub
 * @Creator    Kim Jung-sub
 * @version    1.0
 * @date       2005-11-24
 * ---------------------------------------------------------------------
 */

package org.sws.util.sqlmap;

import java.util.HashMap;

import org.sws.util.entity.Entity;

import com.ibatis.sqlmap.client.SqlMapClient;

public abstract class SqlConfigManager
{
	private static Entity hmSql = new Entity();
	
	/**
	 * 생성자
	 * @param sqlMapName SqlMapClient 이름
	 * @param param DB 정보
	 */
	public SqlConfigManager(String sqlMapName, HashMap<String, String> param) {
		hmSql.setValue(sqlMapName, setSqlMapClient(sqlMapName, param));
	}
	
	/**
	 * SqlMapClient 를 생성한다.
	 * @param sqlMapName SqlMapClient 이름
	 * @param param DB 정보
	 * @return
	 */
	public abstract SqlMapClient setSqlMapClient(String sqlMapName, HashMap<String, String> param);

	/**
	 * 이름으로 저장된 SqlMapClient를 반환한다.
	 * @param sqlMapName
	 * @return
	 */
    public static SqlMapClient getSqlMapClient(String sqlMapName)
    {
        return (SqlMapClient)hmSql.getObject(sqlMapName);
    }
}
