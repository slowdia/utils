package org.sws.util.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.sws.util.common.StringUtil;

/**
 * 
 * @author ago
 *
 */
public class Entity extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static boolean ignoreCaseKey = true;
	
	/**
	 * default Constructor<br>
	 * Key is ignoreCase
	 */
	public Entity() {
		ignoreCaseKey = true;
	}
	
	/**
	 * Entity Constructor
	 * @param ignoreCase 키값의 대소문자를 동일 처리 여부, true:동일, false:대소문자구분
	 */
	public Entity(boolean ignoreCase) {
		ignoreCaseKey = ignoreCase;
	}
	
	/**
	 * 키를 대소문자 구분을 적용하여 재생성
	 * @param key
	 * @return
	 */
	public String remakeKey(String key) {
		if(ignoreCaseKey) {
			key = key.toUpperCase();
		}
		return key;
	}

	/**
	 * 기존 Entity에 paramemter Entity 데이터를 추가한다.
	 * @param param
	 */
	public void addEntity(Entity param)
	{
		Set keySet = param.entrySet();
		Object lists[] = keySet.toArray();
		String key = null;
		Object value = null;
		for (int i = 0; i < lists.length; i++) {
			key = (String)(((Map.Entry)lists[i]).getKey());
			value = param.get(key);
			setValue(key, value);
		}
	}

	/**
	 * Hashtable 데이터를 Entity에 추가한다.
	 * @param hashtable
	 * @param fromCharset
	 * @param toCharset
	 */
	public void addEntity(Hashtable<String, Object> hashtable, String fromCharset, String toCharset)
	{
		if(hashtable == null ) return ;
		Set keySet = hashtable.entrySet();
		Object lists[] = keySet.toArray();
		String key = null;
		Object value = null;
		for (int i = 0; i < lists.length; i++) {
			key = (String)(((Map.Entry)lists[i]).getKey());
			value = hashtable.get(key);
			if(value != null && value instanceof String){
				try {
					value = StringUtil.convert(value.toString(), fromCharset, toCharset);
				}catch(Exception e) {}
			}
			setValue(key, value);
		}
	}

	/**
	 * HashMap 데이터를 Entity에 추가한다.
	 * @param hashMap
	 * @param fromCharset
	 * @param toCharset
	 */
	public void addEntity(HashMap<String, Object> hashMap, String fromCharset, String toCharset)
	{
		if(hashMap == null ) return ;
		Set keySet = hashMap.entrySet();
		Object lists[] = keySet.toArray();
		String key = null;
		Object value = null;
		for (int i = 0; i < lists.length; i++) {
			key = (String)(((Map.Entry)lists[i]).getKey());
			value = hashMap.get(key);
			if(value != null && value instanceof String){
				try {
					value = StringUtil.convert(value.toString(), fromCharset, toCharset);
				}catch(Exception e) {}
			}
			setValue(key, value);
		}
	}

	/**
	 * ResultSet 에서 컬럼 이름을 key로 해서 그 값을 Entity에 저장한다.
	 * @param rs
	 * @throws SQLException
	 */
	public void addResultSet(ResultSet rs) throws Exception
	{
		ResultSetMetaData md = rs.getMetaData();
		int size = md.getColumnCount();
		String columnName = null;
		for (int i = 1; i <= size; i++) {
			columnName = md.getColumnName(i);
			if (columnName != null) {
				setValue(columnName, rs.getString(i));
			}
		}
	}

	/**
	 * ResultSet 에서 컬럼 이름을 key로 해서 그 값을 Entity 에 저장
	 * @param rs
	 * @param fromCharset DB 데이터 캐릭터 셋
	 * @param toCharset 변경될 캐릭터 셋
	 * @throws Exception
	 */
	public void addResultSet(ResultSet rs, String fromCharset, String toCharset) throws Exception
	{
		ResultSetMetaData md = rs.getMetaData();
		int size = md.getColumnCount();
		String columnName = null;
		for (int i = 1; i <= size; i++) {
			columnName = md.getColumnName(i);
			if (columnName != null) {
				setValue(columnName, StringUtil.convert(rs.getString(i), fromCharset, toCharset));
			}
		}
	}
	
	/**
	 * HttpServletRequest 의 파라메터들을 Entity에 입력한다.
	 * @param request HttpServletRequest
	 * @throws Exception
	 */
    public void addRequestParameter(HttpServletRequest request) throws Exception
    {
        Enumeration enumeration = request.getParameterNames();
        String key = null;
        String[] values = null;
        while (enumeration.hasMoreElements()) {
            key = (String)enumeration.nextElement();
            values = request.getParameterValues(key);
            if (values.length == 1) {
        		setValue(key, values[0]);
            }else {
                setValue(key, values);
            }
        }
    }
	
	/**
	 * HttpServletRequest 의 파라메터들을 Entity에 입력한다.
	 * @param request HttpServletRequest
	 * @param fromCharset 현재 데이터의 캐릭터 셋
	 * @param toCharset 변경 될 캐릭터 셋
	 * @throws Exception
	 */
    public void addRequestParameter(HttpServletRequest request, String fromCharset, String toCharset) throws Exception
    {
        Enumeration enumeration = request.getParameterNames();
        String key = null;
        String[] values = null;
        while (enumeration.hasMoreElements()) {
            key = (String)enumeration.nextElement();
            values = request.getParameterValues(key);
            if (values.length == 1) {
        		setValue(key, StringUtil.convert(values[0], fromCharset, toCharset));
            }else {
                String[] temps = new String[values.length];
                for (int i=0 ; i < values.length ; i++) {
            		temps[i] = StringUtil.convert(values[i], fromCharset, toCharset);
                }
                setValue(key, temps);
            }
        }
    }
	
	/**
	 * String 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value)
	{
		if(value != null) put(remakeKey(key), value);
	}
	
	/**
	 * String 배열 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String values[])
	{
		if(values != null) put(remakeKey(key), values);
	}
	
	/**
	 * 바이트 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, byte value)
	{
		put(remakeKey(key), Byte.toString(value));
	}
	
	/**
	 * 바이트 배열 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, byte[] values)
	{
		if(values != null) put(remakeKey(key), new String(values));
	}
	
	/**
	 * BLOB 데이터를 입력한다.<br>
	 * BLOB 처리시 byte 배열 그대로 입력
	 * @param key
	 * @param value
	 */
	public void setBlobValue(String key, byte[] values)
	{
		if(values != null) put(remakeKey(key), values);
	}
	
	/**
	 * char 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, char value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * char 배열 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, char[] values)
	{
		if(values != null) put(remakeKey(key), new String(values));
	}
	
	/**
	 * boolean 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, boolean value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * short 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, short value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * int 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, int value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * float 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, float value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * double 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, double value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * long 데이터를 String으로 변환하여 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, long value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * Date 데이터를 String 형태호 변환하여 입력한다.<br>
	 * format : yyyy-MM-dd HH24:mi:ss
	 * @param key
	 * @param value
	 */
	public void setValue(String key, java.util.Date value)
	{
		if (value != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			put(key, formatter.format(value));
		}
	}
	
	/**
	 * Vector 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Vector<Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * ArrayList 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, ArrayList<Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * Hashtable 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Hashtable<String, Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * HashMap 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, HashMap<String, Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * Entity 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Entity value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * Object 데이터를 입력한다.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Object value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * String 형태의 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public String getString(String key)
	{
		String value = null;
		Object obj = null;
		try {
			obj = get(remakeKey(key));
			if (obj instanceof String) {
				value = (String) obj;
			}else if (obj instanceof String[]) {
				value = ((String[]) obj)[0];
			}else {
				value = "";
			}
		}catch (Exception e) {
			value = "";
		}
		return value;
	}
	
	/**
	 * String 배열 형태의 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public String[] getStrings(String key)
	{
		String values[] = null;
		Object obj = null;
		try {
			obj = get(remakeKey(key));
			if (obj instanceof String) {
				values = new String[1];
				values[0] = (String) obj;
			}else {
				values = (String[])obj;
			}
		}catch (Exception e) {
		}
		return values;
	}
	
	/**
	 * byte 형태의 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public byte getByte(String key)
	{
		byte yResult = (byte)0;
		try {
			yResult = Byte.parseByte((String)get(remakeKey(key)));
		}catch (Exception e) {
		}
		return yResult;
	}
	
	/**
	 * byte 배열 형태의 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public byte[] getBytes(String key)
	{
		byte[] yResults = null;
		try {
			yResults = ((String)get(remakeKey(key))).getBytes();
		}catch (Exception e) {
		}
		return yResults;
	}
	
	/**
	 * char 형태의 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public char getChar(String key)
	{
		char cResult = (char)0;
		try {
			cResult = ((String)get(remakeKey(key))).charAt(0);
		}catch (Exception e) {
		}
		return cResult;
	}
	
	/**
	 * char 배열 형태의 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public char[] getChars(String key)
	{
		char[] cResults = null;
		try {
			cResults = ((String)get(remakeKey(key))).toCharArray();
		}catch (Exception e) {
		}
		return cResults;
	}
	
	/**
	 * boolean 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key)
	{
		boolean bResult = false;
		try {
			String sVal = (String)get(remakeKey(key));
			if (sVal.equalsIgnoreCase("TRUE") || sVal.equalsIgnoreCase("YES") || sVal.equalsIgnoreCase("Y")) {
				bResult = true;
			}else if (sVal.equalsIgnoreCase("FALSE") || sVal.equalsIgnoreCase("NO") || sVal.equalsIgnoreCase("N")) {
				bResult = false;
			}else {
				bResult = Boolean.getBoolean(sVal);
			}
		}
		catch (Exception e) {
		}
		return bResult;
	}
	
	/**
	 * short 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public short getShort(String key)
	{
		short tResult = 0;
		try {
			tResult = Short.parseShort((String)get(remakeKey(key)));
		}catch (Exception e) {
		}
		return tResult;
	}
	
	/**
	 * int 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public int getInt(String key)
	{
		int iResult = 0;
		try {
			iResult = Integer.parseInt((String)get(remakeKey(key)));
		}catch (Exception e) {
		}
		return iResult;
	}
	
	/**
	 * float 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public float getFloat(String key)
	{
		float fResult = 0;
		try {
			fResult = Float.parseFloat((String)get(remakeKey(key)));
		}catch (Exception e) {
		}
		return fResult;
	}
	
	/**
	 * double 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public double getDouble(String key)
	{
		double dResult = 0;
		try {
			dResult = Double.parseDouble((String)get(remakeKey(key)));
		}catch (Exception e) {
		}
		return dResult;
	}
	
	/**
	 * long 데이터를 얻는다.
	 * @param key
	 * @return
	 */
	public long getLong(String key)
	{
		long lResult = 0;
		try {
			lResult = Long.parseLong((String)get(remakeKey(key)));
		}catch (Exception e) {
		}
		return lResult;
	}
	
	/**
	 * Date 데이터를 얻는다.<br>
	 * format : yyyy-MM-dd HH24:mi:ss
	 * @param key
	 * @return
	 */
	public java.util.Date getDate(String key)
	{
		java.util.Date result = null;
		try {
			String sDate = (String)get(remakeKey(key));
			if(sDate == null)
				result = new java.util.Date();
			else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ParsePosition pos = new ParsePosition(0);
				result = formatter.parse(sDate, pos);
			}
		}catch (Exception e) {
			result = new java.util.Date();
		}
		return result;
	}
	
	/**
	 * Date 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @param format 날짜 출력 포맷
	 * @return
	 */
	public java.util.Date getDate(String key, String format)
	{
		java.util.Date result = null;
		try {
			String sDate = (String)get(remakeKey(key));
			if(sDate == null)
				result = new java.util.Date();
			else {
				SimpleDateFormat formatter = new SimpleDateFormat(format);
				ParsePosition pos = new ParsePosition(0);
				result = formatter.parse(sDate, pos);
			}
		}catch (Exception e) {
			result = new java.util.Date();
		}
		return result;
	}
	
	/**
	 * Vector 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @return
	 */
	public Vector<Object> getVector(String key)
	{
		Vector<Object> result = null;
		try {
			result = (Vector<Object>)get(remakeKey(key));
			if (result == null)
				result = new Vector<Object>();
		}catch (Exception e) {
			result = new Vector<Object>();
		}
		return result;
	}
	
	/**
	 * ArrayList 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @return
	 */
	public ArrayList<Object> getArrayList(String key)
	{
		ArrayList<Object> result = null;
		try {
			result = (ArrayList<Object>)get(remakeKey(key));
			if (result == null)
				result = new ArrayList<Object>();
		}catch (Exception e) {
			result = new ArrayList<Object>();
		}
		return result;
	}
	
	/**
	 * ArrayList 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @return
	 */
	public Hashtable<String, Object> getHashtable(String key)
	{
		Hashtable<String, Object> value = null;
		try {
			value = (Hashtable<String, Object>)get(remakeKey(key));
			if (value == null)
				value = new Hashtable<String, Object>();
		}catch (Exception e) {
			value = new Hashtable<String, Object>();
		}
		return value;
	}
	
	/**
	 * HashMap 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @return
	 */
	public HashMap<String, Object> getHashMap(String key) {
		HashMap<String, Object> value = null;
		try {
			value = (HashMap<String, Object>)get(remakeKey(key));
			if (value == null)
				value = new HashMap<String, Object>();
		}catch (Exception e) {
			value = new HashMap<String, Object>();
		}
		return value;
	}
	
	/**
	 * Entity 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @return
	 */
	public Entity getEntity(String key)
	{
		Entity value = null;
		try {
			value = (Entity)get(remakeKey(key));
			if (value == null)
				value = new Entity();
		}catch (Exception e) {
			value = new Entity();
		}
		return value;
	}
	
	/**
	 * Entity 데이터를 얻는다.
	 * @param key 데이터 추출 키
	 * @return
	 */
	public Object getObject(String key)
	{
		Object value = null;
		try {
			value = get(remakeKey(key));
			if (value == null)
				value = new Object();
		}catch (Exception e) {
			value = new Object();
		}
		return value;
	}
	
	/**
	 * 키로 저장된 데이터를 엔티티에서 제거한다.
	 * @param key
	 */
	public void remove(String key)
	{
		super.remove(remakeKey(key));
	}
	
	/**
	 * 검색값을 데이터로 일치하는 엔티티의 첫번째 키를 찾는다.
	 * @param searchValue
	 * @return
	 */
	public String getKey(String searchValue)
	{
		String sResult = null;
		
		Set keySet = entrySet();
		Object lists[] = keySet.toArray();
		String key = null;
		Object value = null;
		for (int i = 0; i < lists.length; i++) {
			key = (String)(((Map.Entry)lists[i]).getKey());
			value = get(key);
			if (value instanceof String && ((String)value).trim().equals(searchValue)) {
				sResult = (String)key;
				break;
			}
		}
		return sResult;
	}
	
	/**
	 * 검색값을 데이터로 일치하는 엔티티의 키 리스트를 찾는다.
	 * @param searchValue 검색 데이터
	 * @return
	 */
	public ArrayList<String> getKeys(String searchValue)
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Set keySet = entrySet();
		Object lists[] = keySet.toArray();
		String key = null;
		Object value = null;
		for (int i = 0; i < lists.length; i++) {
			key = (String)(((Map.Entry)lists[i]).getKey());
			value = get(key);
			if (value instanceof String && ((String)value).trim().equals(searchValue)) {
				result.add(key);
				break;
			}
		}
		return result;
	}
	
	/**
	 * 엔티티의 키 리스트를 찾는다.
	 * @return
	 */
	public ArrayList<String> getKeys()
	{
		ArrayList<String> result = new ArrayList<String>();
		
		Set keySet = entrySet();
		Object lists[] = keySet.toArray();
		String key = null;
		for (int i = 0; i < lists.length; i++) {
			key = (String)(((Map.Entry)lists[i]).getKey());
			result.add(key);
		}
		return result;
	}
	
}
