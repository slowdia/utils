package org.sws.util.sqlmap;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.sws.util.entity.Entity;

import com.ibatis.common.util.PaginatedList;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapSession;
import com.ibatis.sqlmap.client.event.RowHandler;

public class SqlMap implements SqlMapClient {

	private SqlMapClient sqlMap;
	private String poolName ;

	public SqlMap(SqlMapClient sqlMap)
	{
		this.sqlMap = sqlMap ;
	}

	public SqlMap(SqlMapClient sqlMap, String poolName)
	{
		this.sqlMap = sqlMap ;
		this.poolName = poolName ;
	}

	@Override
	public int delete(String arg0, Object arg1) throws SQLException
	{
		return sqlMap.delete(arg0, arg1);
	}

	@Override
	public int executeBatch() throws SQLException
	{
		return sqlMap.executeBatch();
	}

	@Override
	public Object insert(String arg0, Object arg1) throws SQLException
	{
		return sqlMap.insert(arg0, arg1);
	}

	@Override
	public List<Entity> queryForList(String arg0, Object arg1) throws SQLException
	{
		List<Entity> result = new ArrayList<Entity>();
		List<?> list = sqlMap.queryForList(arg0, arg1);
		Entity temp = null;
		for(int i=0; i<list.size(); i++) {
			temp = new Entity();
			Object obj = list.get(i);
			temp.addEntity((HashMap)obj, null, null);
			result.add(temp);
		}
		return result;
	}

	@Override
	public List queryForList(String arg0, Object arg1, int arg2, int arg3) throws SQLException
	{
		List<Entity> result = new ArrayList<Entity>();
		List<?> list = sqlMap.queryForList(arg0, arg1, arg2, arg3);
		Entity temp = null;
		for(int i=0; i<list.size(); i++) {
			temp = new Entity();
			Object obj = list.get(i);
			temp.addEntity((HashMap)obj, null, null);
			result.add(temp);
		}
		return result;
	}

	@Override
	public Map queryForMap(String arg0, Object arg1, String arg2) throws SQLException
	{
		return sqlMap.queryForMap(arg0, arg1, arg2);
	}

	@Override
	public Map queryForMap(String arg0, Object arg1, String arg2, String arg3) throws SQLException
	{
		return sqlMap.queryForMap(arg0, arg1, arg2, arg3);
	}
	
	/**
	 * 맵 형태로 반환되는 데이터를 Entity로 변환하여 리턴
	 * @param arg0 쿼리 이름
	 * @param arg1 파라페터
	 * @param arg2
	 * @return
	 * @throws SQLException
	 */
	public Entity queryForEntity(String arg0, Object arg1, String arg2) throws SQLException
	{
		HashMap hMap = (HashMap)sqlMap.queryForMap(arg0, arg1, arg2);
		Entity entity = new Entity();
		entity.addEntity(hMap, null, null);
		return entity;
	}
	
	/**
	 * 맵 형태로 반환되는 데이터를 Entity로 변환하여 리턴
	 * @param arg0 쿼리 이름
	 * @param arg1 파라페터
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws SQLException
	 */
	public Entity queryForEntity(String arg0, Object arg1, String arg2, String arg3) throws SQLException
	{
		HashMap hMap = (HashMap)sqlMap.queryForMap(arg0, arg1, arg2, arg3);
		Entity entity = new Entity();
		entity.addEntity(hMap, null, null);
		return entity;
	}

	@Override
	public Object queryForObject(String arg0, Object arg1) throws SQLException
	{
		return sqlMap.queryForObject(arg0, arg1);
	}

	@Override
	public Object queryForObject(String arg0, Object arg1, Object arg2) throws SQLException
	{
		return sqlMap.queryForObject(arg0, arg1, arg2);
	}

	@Override
	public PaginatedList queryForPaginatedList(String arg0, Object arg1, int arg2) throws SQLException
	{
		return sqlMap.queryForPaginatedList(arg0, arg1, arg2);
	}

	@Override
	public void queryWithRowHandler(String arg0, Object arg1, RowHandler arg2) throws SQLException
	{
		sqlMap.queryWithRowHandler(arg0, arg1, arg2);
	}

	@Override
	public void startBatch() throws SQLException
	{
		sqlMap.startBatch();
	}

	@Override
	public int update(String arg0, Object arg1) throws SQLException
	{
		return sqlMap.update(arg0, arg1);
	}

	@Override
	public void commitTransaction() throws SQLException
	{
		sqlMap.commitTransaction();
	}

	@Override
	public void endTransaction() throws SQLException
	{
		sqlMap.endTransaction();
	}

	@Override
	public Connection getCurrentConnection() throws SQLException
	{
		return sqlMap.getCurrentConnection();
	}

	@Override
	public DataSource getDataSource()
	{
		return sqlMap.getDataSource();
	}

	@Override
	public Connection getUserConnection() throws SQLException
	{
		return sqlMap.getUserConnection();
	}

	@Override
	public void setUserConnection(Connection arg0) throws SQLException
	{
		sqlMap.setUserConnection(arg0);
	}

	@Override
	public void startTransaction() throws SQLException
	{
		sqlMap.startTransaction();
	}

	@Override
	public void startTransaction(int arg0) throws SQLException
	{
		sqlMap.startTransaction(arg0);
	}

	@Override
	public void flushDataCache()
	{
		sqlMap.flushDataCache();
	}

	@Override
	public void flushDataCache(String arg0)
	{
		sqlMap.flushDataCache(arg0);
	}

	@Override
	public SqlMapSession getSession()
	{
		return sqlMap.getSession();
	}

	@Override
	public SqlMapSession openSession()
	{
		return sqlMap.openSession();
	}

	@Override
	public SqlMapSession openSession(Connection arg0)
	{
		return sqlMap.openSession(arg0);
	}
}
