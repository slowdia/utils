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
	 * BLOBB 형태의 컬럼 데이터를 읽어들여 byte 배열로 리턴한다.
	 * @param rs ResultSet 객체
	 * @param column BLOB 컬럼 명
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
	 * CLOB 형태의 컬럼 데이터를 읽어들여 String으로 리턴한다.
	 * @param rs ResultSet 객체
	 * @param column CLOB 컬럼 명
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
	 * Clob 객체로 부터 컨텐츠 String을 읽어서 리턴한다.
	 * @param clob Clob 객체
	 * @param bufferSize Clob 객체로 부터 읽을 버퍼 사이즈, 0보다 작거나 같으면 default : 2048
	 * @return
	 * @throws SQLException
	 */
	private String getClobString(Clob clob) throws SQLException
	{
		int bufferSize = 2048;
		
		StringBuffer sb = new StringBuffer();
		for(long pos = 1; pos < clob.length(); pos += bufferSize) {
			if((pos + bufferSize) > clob.length())
				bufferSize = (int)(clob.length() - pos);// 마지막 남은양 계산
			sb.append(clob.getSubString(pos, bufferSize));
		}
		return sb.toString();
	}
	
}
