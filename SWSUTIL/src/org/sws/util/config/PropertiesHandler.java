package org.sws.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;


/**
 * ������Ƽ ���������� �а� ���µ� �ʿ��� �ֿ� �޼ҵ带 ����.
 * @author ago
 *
 */
public class PropertiesHandler implements ConfigHandler {

	/** ���� ����� */
	private Properties properties = new Properties();

	/** �������� ��ü��� */
	private String resourceName;
	
	private long lastModified;

	/**
	 * ������
	 * ���� ��ο� ���� ���������� �о ������Ƽ�� �ε���.
	 * @param resourceName ���������� ��ü���.
	 */
	public PropertiesHandler(String resourceName) throws Exception
	{
		this.resourceName = resourceName;
		this.load(null);
	}

	/**
	 * ������
	 * ���� ��ο� ���� ���������� �о ������Ƽ�� �ε���.
	 * @param resourceName ���������� ��ü���.
	 * @param charset ���������� ĳ���� ��.
	 */
	public PropertiesHandler(String resourceName, String charset) throws Exception
	{
		this.resourceName = resourceName;
		this.load(charset);
	}
	
	@Override
	public String getResourceName() {
		return resourceName;
	}
	
	@Override
	public long getLastModified() {
		return lastModified;
	}
	
	@Override
	public Properties getProperties() {
		return properties;
	}
	
	@Override
	public Enumeration<?> getEntry() {
		return properties.propertyNames();
	}
	
	@Override
	public void load(String charset) throws Exception
	{
		File file = new File(resourceName);
		if(charset == null || charset.equals("")) {
			properties.load(new FileInputStream(new File(resourceName)));
		}else {
			properties.load(new InputStreamReader(new FileInputStream(new File(resourceName)), charset));
		}
		lastModified = file.lastModified();
	}
	
	@Override
	public String getString(String key) {
		return (String)properties.get(key);
	}

	@Override
	public String getString(String key, String def) {
		if(((String)properties.get(key)) == null)
			return def;
		return (String)properties.get(key);
	}

	@Override
	public int getInt(String key)
	{
		int nVal = 0;
		String sValue = (String) properties.get(key);
		if(sValue == null) return 0;
		sValue = sValue.trim();
		try{
			nVal = Integer.parseInt(sValue);
		}catch(Exception e){}

		return nVal;
	}

	@Override
	public int getInt(String key, int def)
	{
		int nReturnVal = def;
		String sValue = (String) properties.get(key);
		if(sValue == null)
			return def;
		sValue = sValue.trim();
		try{
			nReturnVal = Integer.parseInt(sValue);
		}catch(Exception e){}

		return nReturnVal;
	}

	@Override
	public long getLong(String key)
	{
		long nVal = 0;
		String sValue = (String) properties.get(key);
		if(sValue == null) return 0;
		sValue = sValue.trim();
		try{
			nVal = Long.parseLong(sValue);
		}catch(Exception e){}

		return nVal;
	}

	@Override
	public long getLong(String key, long def)
	{
		long nVal = def;
		String sValue = (String) properties.get(key);
		if(sValue == null) return nVal;
		sValue = sValue.trim();
		try{
			nVal = Long.parseLong(sValue);
		}catch(Exception e){}

		return nVal;
	}

	@Override
	public boolean getBoolean(String key) {

		boolean b = false;
		String value = ((String)properties.get(key));
		if(value == null)
			return b;
		value = value.trim();
		if(value.equalsIgnoreCase("TRUE") || value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("Y")) {
			b = true;
		}else if(value.equalsIgnoreCase("FALSE") || value.equalsIgnoreCase("NO") || value.equalsIgnoreCase("N")) {
			b = false;
		}
		return b;
	}

	@Override
	public boolean getBoolean(String key, boolean def) {

		boolean b = def;
		String value = ((String)properties.get(key));
		if(value == null)
			return def;
		value = value.trim();
		if(value.equalsIgnoreCase("TRUE") || value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("Y")) {
			b = true;
		}else if(value.equalsIgnoreCase("FALSE") || value.equalsIgnoreCase("NO") || value.equalsIgnoreCase("N")) {
			b = false;
		}
		return b;
	}

	@Override
	public void setProperty(String key, String value) {
		properties.put(key, value);
	}

	@Override
	public void setProperty(String key, int value) {
		properties.put(key, value);
	}

	@Override
	public void setProperty(String key, long value) {
		properties.put(key, value);
	}

	@Override
	public void setProperty(String key, boolean value) {
		properties.put(key, value);
	}

	@Override
	public void remove(String key) {
		properties.remove(key);
	}

}