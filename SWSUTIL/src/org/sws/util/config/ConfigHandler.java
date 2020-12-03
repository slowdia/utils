package org.sws.util.config;

import java.util.Enumeration;
import java.util.Properties;


/**
 * 프로퍼티와 관련된 내용이 저장된 장소와 매체에 상관없이 동작을 할 수 있도록 정의한 인터페이스
 * @author ago
 *
 */
public interface ConfigHandler {


	/** 
	 * 프로퍼티의 키 리스트를 넘겨줌
	 */
	public abstract Enumeration<?> getEntry();

	/** 
	 * 프로퍼티 설정파일 경로를 얻음.
	 */
	public abstract String getResourceName();

	/** 
	 * 프로퍼티 설정파일 최종 수정 시간을 얻음
	 */
	public abstract long getLastModified();

	/** 
	 * 프로퍼티 객체를 얻음.
	 */
	public abstract Properties getProperties();

	/**
	 * 프로퍼티 파일을 로드시킴
	 */
	public abstract void load(String charset) throws Exception;

	/**
	 * 프로퍼티안에서 항목의 값을 String으로 가져옴
	 * @param key
	 * @return
	 */
	public abstract String getString(String key);
	
	/**
	 * 프로퍼티안에서 항목의 값을 String으로 가져옴
	 * @param key
	 * @param def null 대체 값
	 * @return
	 */
	public abstract String getString(String key, String def);
	
	/**
	 * 프로퍼티안에서 항목의 값을 숫자로 바꾸어서 가져옴
	 * @param key
	 * @return
	 */
	public abstract int getInt(String key);
	
	/**
	 * 프로퍼티안에서 항목의 값을 숫자로 바꾸어서 가져옴
	 * @param key
	 * @param def null 대체 값
	 * @return
	 */
	public abstract int getInt(String key, int def);
	
	/**
	 * 프로퍼티안에서 항목의 값을 Long 형으로 바꾸어서 가져옴
	 * @param key
	 * @return
	 */
	public abstract long getLong(String key);
	
	/**
	 * 프로퍼티안에서 항목의 값을 Long 형으로 바꾸어서 가져옴
	 * @param key
	 * @param def null 대체 값
	 * @return
	 */
	public abstract long getLong(String key, long def);

	/**
	 * 프로퍼티안에서 항목의 값을 boolean으로 바꾸어서 가져옴
	 * @param key
	 * @return
	 */
	public abstract boolean getBoolean(String key);
	
	/**
	 * 프로퍼티안에서 항목의 값을 boolean으로 바꾸어서 가져옴
	 * @param key
	 * @param def null 대체 값
	 * @return
	 */
	public abstract boolean getBoolean(String key, boolean def);
	
	/**
	 * 프로퍼티안에서 String 항목의 값을 추가
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, String value);
	
	/**
	 * 프로퍼티안에서 int 항목의 값을 추가
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, int value);
	
	/**
	 * 프로퍼티안에서 long 항목의 값을 추가
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, long value);
	
	/**
	 * 프로퍼티안에서 boolean 항목의 값을 추가
	 * @param key
	 * @param value
	 */
	public abstract void setProperty(String key, boolean value);


	/**
	 * 설정된 프로퍼티 항목을 제거
	 * @param key
	 */
	public abstract void remove(String key);

}