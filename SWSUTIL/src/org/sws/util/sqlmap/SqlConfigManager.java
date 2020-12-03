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
	 * ������
	 * @param sqlMapName SqlMapClient �̸�
	 * @param param DB ����
	 */
	public SqlConfigManager(String sqlMapName, HashMap<String, String> param) {
		hmSql.setValue(sqlMapName, setSqlMapClient(sqlMapName, param));
	}
	
	/**
	 * SqlMapClient �� �����Ѵ�.
	 * @param sqlMapName SqlMapClient �̸�
	 * @param param DB ����
	 * @return
	 */
	public abstract SqlMapClient setSqlMapClient(String sqlMapName, HashMap<String, String> param);

	/**
	 * �̸����� ����� SqlMapClient�� ��ȯ�Ѵ�.
	 * @param sqlMapName
	 * @return
	 */
    public static SqlMapClient getSqlMapClient(String sqlMapName)
    {
        return (SqlMapClient)hmSql.getObject(sqlMapName);
    }
}
