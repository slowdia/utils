package org.sws.util.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.BaseTypeHandler;

import oracle.sql.CLOB;

public class OracleUtil extends BaseTypeHandler {
	
	/**
	 * BLOBB ������ �÷� �����͸� �о�鿩 byte �迭�� �����Ѵ�.
	 * @param rs ResultSet ��ü
	 * @param column BLOB �÷� ��
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public static byte[] getBLOB(ResultSet rs, String column) throws SQLException, IOException
	{
		ByteArrayOutputStream bs = new ByteArrayOutputStream(); 
		InputStream is = rs.getBinaryStream(column);
		if(is==null){
			return null;
		}

		byte[] buf = new byte[1024];
		int readcnt;
		while ((readcnt = is.read(buf, 0, 1024)) != -1) {
			bs.write(buf, 0, readcnt);
		}
		is.close();

		return bs.toByteArray();
	}

	/**
	 * CLOB ������ �÷� �����͸� �о�鿩 String���� �����Ѵ�.
	 * @param rs ResultSet ��ü
	 * @param column CLOB �÷� ��
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
//	public static String getCLOB(ResultSet rs, String column) throws SQLException, IOException
//	{
//		Reader reader = rs.getCharacterStream(column);
//		if(reader == null){
//			return "";
//		}
//
//		StringBuffer sb = new StringBuffer();
//		char[] buf = new char[1024];
//		int readcnt;
//		while ((readcnt = reader.read(buf, 0, 1024)) != -1) {
//			sb.append(buf, 0, readcnt);
//		}
//		reader.close();
//
//		return sb.toString();
//	}

	@Override
	public String getResult(ResultSet rs, String column) throws SQLException
	{
		if (rs.wasNull()) {
			return null;
		} else {
			Clob clob = rs.getClob(column);
			return getClobString(clob);
		}
	}

	@Override
	public String getResult(ResultSet rs, int columnIndex) throws SQLException
	{
		if (rs.wasNull()) {
			return null;
		} else {
			Clob clob = rs.getClob(columnIndex);
			return getClobString(clob);
		}
	}

	@Override
	public String getResult(CallableStatement cstmt, int columnIndex) throws SQLException
	{
		if (cstmt.wasNull()) {
			return null;
		} else {
			Clob clob = cstmt.getClob(columnIndex);
			return getClobString(clob);
		}
	}

	@Override
	public void setParameter(PreparedStatement pstmt, int columnIndex, Object data, String jdbcType) throws SQLException
	{
		String value = (String)data;
		if (value != null) {
			CLOB clob = CLOB.createTemporary(pstmt.getConnection(), true, CLOB.DURATION_SESSION);
			clob.putChars(1, value.toCharArray());
			pstmt.setClob(columnIndex, clob);
		} else {
			pstmt.setString(columnIndex, null);
		}
	}

	@Override
	public Object valueOf(String s)
	{
		return s;
	}
	
	/**
	 * Clob ��ü�� ���� ������ String�� �о �����Ѵ�.
	 * @param clob Clob ��ü
	 * @param bufferSize Clob ��ü�� ���� ���� ���� ������, 0���� �۰ų� ������ default : 2048
	 * @return
	 * @throws SQLException
	 */
	private String getClobString(Clob clob) throws SQLException
	{
		int bufferSize = 2048;
		
		StringBuffer sb = new StringBuffer();
		for(long pos = 1; pos < clob.length(); pos += bufferSize) {
			if((pos + bufferSize) > clob.length())
				bufferSize = (int)(clob.length() - pos);// ������ ������ ���
			sb.append(clob.getSubString(pos, bufferSize));
		}
		return sb.toString();
	}
	
}
