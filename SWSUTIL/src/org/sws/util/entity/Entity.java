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
	 * @param ignoreCase Ű���� ��ҹ��ڸ� ���� ó�� ����, true:����, false:��ҹ��ڱ���
	 */
	public Entity(boolean ignoreCase) {
		ignoreCaseKey = ignoreCase;
	}
	
	/**
	 * Ű�� ��ҹ��� ������ �����Ͽ� �����
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
	 * ���� Entity�� paramemter Entity �����͸� �߰��Ѵ�.
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
	 * Hashtable �����͸� Entity�� �߰��Ѵ�.
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
	 * HashMap �����͸� Entity�� �߰��Ѵ�.
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
	 * ResultSet ���� �÷� �̸��� key�� �ؼ� �� ���� Entity�� �����Ѵ�.
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
	 * ResultSet ���� �÷� �̸��� key�� �ؼ� �� ���� Entity �� ����
	 * @param rs
	 * @param fromCharset DB ������ ĳ���� ��
	 * @param toCharset ����� ĳ���� ��
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
	 * HttpServletRequest �� �Ķ���͵��� Entity�� �Է��Ѵ�.
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
	 * HttpServletRequest �� �Ķ���͵��� Entity�� �Է��Ѵ�.
	 * @param request HttpServletRequest
	 * @param fromCharset ���� �������� ĳ���� ��
	 * @param toCharset ���� �� ĳ���� ��
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
	 * String �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String value)
	{
		if(value != null) put(remakeKey(key), value);
	}
	
	/**
	 * String �迭 �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, String values[])
	{
		if(values != null) put(remakeKey(key), values);
	}
	
	/**
	 * ����Ʈ �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, byte value)
	{
		put(remakeKey(key), Byte.toString(value));
	}
	
	/**
	 * ����Ʈ �迭 �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, byte[] values)
	{
		if(values != null) put(remakeKey(key), new String(values));
	}
	
	/**
	 * BLOB �����͸� �Է��Ѵ�.<br>
	 * BLOB ó���� byte �迭 �״�� �Է�
	 * @param key
	 * @param value
	 */
	public void setBlobValue(String key, byte[] values)
	{
		if(values != null) put(remakeKey(key), values);
	}
	
	/**
	 * char �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, char value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * char �迭 �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, char[] values)
	{
		if(values != null) put(remakeKey(key), new String(values));
	}
	
	/**
	 * boolean �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, boolean value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * short �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, short value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * int �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, int value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * float �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, float value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * double �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, double value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * long �����͸� String���� ��ȯ�Ͽ� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, long value)
	{
		put(remakeKey(key), String.valueOf(value));
	}
	
	/**
	 * Date �����͸� String ����ȣ ��ȯ�Ͽ� �Է��Ѵ�.<br>
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
	 * Vector �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Vector<Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * ArrayList �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, ArrayList<Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * Hashtable �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Hashtable<String, Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * HashMap �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, HashMap<String, Object> value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * Entity �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Entity value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * Object �����͸� �Է��Ѵ�.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Object value)
	{
		put(remakeKey(key), value);
	}
	
	/**
	 * String ������ �����͸� ��´�.
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
	 * String �迭 ������ �����͸� ��´�.
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
	 * byte ������ �����͸� ��´�.
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
	 * byte �迭 ������ �����͸� ��´�.
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
	 * char ������ �����͸� ��´�.
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
	 * char �迭 ������ �����͸� ��´�.
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
	 * boolean �����͸� ��´�.
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
	 * short �����͸� ��´�.
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
	 * int �����͸� ��´�.
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
	 * float �����͸� ��´�.
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
	 * double �����͸� ��´�.
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
	 * long �����͸� ��´�.
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
	 * Date �����͸� ��´�.<br>
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
	 * Date �����͸� ��´�.
	 * @param key ������ ���� Ű
	 * @param format ��¥ ��� ����
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
	 * Vector �����͸� ��´�.
	 * @param key ������ ���� Ű
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
	 * ArrayList �����͸� ��´�.
	 * @param key ������ ���� Ű
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
	 * ArrayList �����͸� ��´�.
	 * @param key ������ ���� Ű
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
	 * HashMap �����͸� ��´�.
	 * @param key ������ ���� Ű
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
	 * Entity �����͸� ��´�.
	 * @param key ������ ���� Ű
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
	 * Entity �����͸� ��´�.
	 * @param key ������ ���� Ű
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
	 * Ű�� ����� �����͸� ��ƼƼ���� �����Ѵ�.
	 * @param key
	 */
	public void remove(String key)
	{
		super.remove(remakeKey(key));
	}
	
	/**
	 * �˻����� �����ͷ� ��ġ�ϴ� ��ƼƼ�� ù��° Ű�� ã�´�.
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
	 * �˻����� �����ͷ� ��ġ�ϴ� ��ƼƼ�� Ű ����Ʈ�� ã�´�.
	 * @param searchValue �˻� ������
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
	 * ��ƼƼ�� Ű ����Ʈ�� ã�´�.
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
