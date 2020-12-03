package org.sws.util.config;

import java.io.File;

import org.sws.util.common.FileUtil;
import org.sws.util.entity.Entity;


/**
 * ���� ���� ����
 * @author ago
 *
 */
public class ConfigManager {

	/** ���� ����� */
	private static Entity configs;
	
	/** ConfigManager ��ü */
	private static ConfigManager instance;
	
	/**
	 * �⺻ ������
	 */
	private ConfigManager() {
		configs = new Entity();
	}

	/**
	 * ConfigManager �ν�ź���� ���, �̱���
	 * @return ConfigManager ��ü
	 */
	public static ConfigManager getInstance()
	{
		if(instance == null) {
			return new ConfigManager();
		}
		return instance;
	}

	/**
	 * Ű�� �ش��ϴ� ������Ƽ�� ����
	 * @param key ���� Ű
	 * @param config ���� ������
	 * @return
	 */
	public void setConfig(String key, ConfigHandler value)
	{
		configs.setValue(key, value);
	}

	/**
	 * Ű�� �̸����� ���� config �� �����Ѵ�.
	 * @param key ���� Ű
	 * @return
	 */
	public void remove(String key)
	{
		configs.remove(key);
	}

	/**
	 * Ű�� �ش��ϴ� ������Ƽ�� ����
	 * @param key �������� Ű��.
	 * @return ConfigHandler ��ü.
	 */
	public ConfigHandler getConfig(String key)
	{
		Object obj = configs.getObject(key);
		if(obj == null) {
			return null;
		}else {
			return (ConfigHandler)obj;
		}
	}
	
	/**
	 * ������ �о� ���� �̸��� Ű���Ͽ� config�� �����Ѵ�.
	 * @param file
	 * @throws Exception
	 */
	public void load(String file) throws Exception
	{
		ConfigHandler config = new PropertiesHandler(file);
		setConfig(FileUtil.getBaseName(file), config);
	}
	
	/**
	 * �̹� �ε�� ������Ƽ�� �����Ѵ�.
	 * @param key ���� Ű �̸�
	 * @throws Exception
	 */
	public boolean reload(String key) throws Exception
	{
		ConfigHandler config = getConfig(key);
		if(config == null) {
			return false;
		}
		String file = config.getResourceName();
		if(new File(file).lastModified() == config.getLastModified()) {
			return true;
		}
		config = new PropertiesHandler(file);
		setConfig(key, config);
		return true;
	}
	
}
