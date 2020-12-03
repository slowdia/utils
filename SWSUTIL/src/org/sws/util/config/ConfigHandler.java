package org.sws.util.config;

import java.util.Enumeration;
import java.util.Properties;


/**
 * ������Ƽ�� ���õ� ������ ����� ��ҿ� ��ü�� ������� ������ �� �� �ֵ��� ������ �������̽�
 * @author ago
 *
 */
public interface ConfigHandler {


	/** 
	 * ������Ƽ�� Ű ����Ʈ�� �Ѱ���
	 */
	public abstract Enumeration<?> getEntry();

	/** 
	 * ������Ƽ �������� ��θ� ����.
	 */
	public abstract String getResourceName();

	/** 
	 * ������Ƽ �������� ���� ���� �ð��� ����
	 */
	public abstract long getLastModified();

	/** 
	 * ������Ƽ ��ü�� ����.
	 */
	public abstract Properties getProperties();

	/**
	 * ������Ƽ ������ �ε��Ŵ
	 */
	public abstract void load(String charset) throws Exception;

	/**
	 * ������Ƽ�ȿ��� �׸��� ���� String���� ������
	 * @param key
	 * @return
	 */
	public abstract String getString(String key);
	
	/**
	 * ������Ƽ�ȿ��� �׸��� ���� String���� ������
	 * @param key
	 * @param def null ��ü ��
	 * @return
	 */
	public abstract String getString(String key, String def);
	
	/**
	 * ������Ƽ�ȿ��� �׸��� ���� ���ڷ� �ٲپ ������
	 * @param key
	 * @return
	 */
	public abstract int getInt(String key);
	
	/**
	 * ������Ƽ�ȿ��� �׸��� ���� ���ڷ� �ٲپ ������
	 * @param key
	 * @param def null ��ü ��
	 * @return
	 */
	public abstract int getInt(String key, int def);
	
	/**
	 * ������Ƽ�ȿ��� �׸��� ���� Long ������ �ٲپ ������
	 * @param key
	 * @return
	 */
	public abstract long getLong(String key);
	
	/**
	 * ������Ƽ�ȿ��� �׸��� ���� Long ������ �ٲپ ������
	 * @param key
	 * @param def null ��ü ��
	 * @return
	 */
	public abstract long getLong(String key, long def);

	/**
	 * ������Ƽ�ȿ��� �׸��� ���� boolean���� �ٲپ ������
	 * @param key
	 * @return
	 */
	public abstract boolean getBoolean(String key);
	
	/**
	 * ������Ƽ�ȿ��� �׸��� ���� boolean���� �ٲپ ������
	 * @param key
	 * @param def null ��ü ��
	 * @return
	 */
	public abstract boolean getBoolean(String key, boolean def);
	
	/**
	 * ������Ƽ�ȿ��� String �׸��� ���� �߰�
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, String value);
	
	/**
	 * ������Ƽ�ȿ��� int �׸��� ���� �߰�
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, int value);
	
	/**
	 * ������Ƽ�ȿ��� long �׸��� ���� �߰�
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, long value);
	
	/**
	 * ������Ƽ�ȿ��� boolean �׸��� ���� �߰�
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, boolean value);


	/**
	 * ������ ������Ƽ �׸��� ����
	 * @param key
	 */
	public abstract void remove(String key);

}