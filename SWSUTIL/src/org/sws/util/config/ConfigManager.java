package org.sws.util.config;

import java.io.File;

import org.sws.util.common.FileUtil;
import org.sws.util.entity.Entity;


/**
 * 설정 정보 관리
 * @author ago
 *
 */
public class ConfigManager {

	/** 설정 저장소 */
	private static Entity configs;
	
	/** ConfigManager 객체 */
	private static ConfigManager instance;
	
	/**
	 * 기본 생성자
	 */
	private ConfigManager() {
		configs = new Entity();
	}

	/**
	 * ConfigManager 인스탄스를 얻기, 싱글톤
	 * @return ConfigManager 객체
	 */
	public static ConfigManager getInstance()
	{
		if(instance == null) {
			return new ConfigManager();
		}
		return instance;
	}

	/**
	 * 키에 해당하는 프로퍼티를 얻음
	 * @param key 설정 키
	 * @param config 설정 데이터
	 * @return
	 */
	public void setConfig(String key, ConfigHandler value)
	{
		configs.setValue(key, value);
	}

	/**
	 * 키를 이름으로 갖는 config 를 제거한다.
	 * @param key 설정 키
	 * @return
	 */
	public void remove(String key)
	{
		configs.remove(key);
	}

	/**
	 * 키에 해당하는 프로퍼티를 얻음
	 * @param key 설정파일 키값.
	 * @return ConfigHandler 객체.
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
	 * 파일을 읽어 파일 이름을 키로하여 config를 저장한다.
	 * @param file
	 * @throws Exception
	 */
	public void load(String file) throws Exception
	{
		ConfigHandler config = new PropertiesHandler(file);
		setConfig(FileUtil.getBaseName(file), config);
	}
	
	/**
	 * 이미 로드된 프로퍼티를 갱신한다.
	 * @param key 설정 키 이름
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
