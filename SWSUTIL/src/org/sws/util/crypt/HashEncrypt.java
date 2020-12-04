package org.sws.util.crypt;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * 단방향 해시 암호화
 * @author ago
 *
 */
public class HashEncrypt {
	
	/** default : SHA-256*/
	private static final String DEFAULT_ALGORITHM = "SHA-256";
	
	/**
	 * 기본 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 byte[]
	 * @return 암호화 된 데이터 byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(byte[] input) throws Exception
	{
		return getHash(input, DEFAULT_ALGORITHM);
	}
	
	/**
	 * 지정된 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 byte[]
	 * @param algorithm 적용할 해시 알고리즘
	 * @return 암호화 된 데이터 byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(byte[] input, String algorithm) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance(algorithm);
		return md.digest(input);
	}

	/**
	 * 기본 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 InputStream
	 * @return 암호화 된 데이터 byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(InputStream input) throws Exception 
	{
		return getHash(input, DEFAULT_ALGORITHM);
	}

	/**
	 * 지정된 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 InputStream
	 * @param algorithm 적용할 해시 알고리즘
	 * @return 암호화 된 데이터 byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(InputStream input, String algorithm) throws Exception 
	{
		MessageDigest md = MessageDigest.getInstance(algorithm);
		int read = -1;
		byte[] buffer = new byte[1024];
		while ((read = input.read(buffer)) != -1) {
			md.update(buffer, 0, read);
		}
		return md.digest();
	}
	
	/**
	 * 기본 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 byte[]
	 * @return 암호화 된 데이터 String
	 * @throws Exception
	 */
	public static String getHashString(byte[] input) throws Exception
	{
		return getHashString(input, DEFAULT_ALGORITHM);
	}
	
	/**
	 * 지정된 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 byte[]
	 * @param algorithm 적용할 해시 알고리즘
	 * @return 암호화 된 데이터 String
	 * @throws Exception
	 */
	public static String getHashString(byte[] input, String algorithm) throws Exception
	{
		byte[] hash = getHash(input, algorithm);
		StringBuffer sb = new StringBuffer(); 
		for (int i = 0; i < hash.length; i++) { 
			 sb.append(Integer.toString((hash[i] & 0xf0) >> 4, 16)); 
			 sb.append(Integer.toString(hash[i] & 0x0f, 16));
		} 
		return sb.toString();
	}
	
	/**
	 * 기본 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 String
	 * @return 암호화 된 데이터 String
	 * @throws Exception
	 */
	public static String getHashString(String input) throws Exception
	{
		return getHashString(input.getBytes(), DEFAULT_ALGORITHM);
	}
	
	/**
	 * 지정된 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 String
	 * @param algorithm 적용할 해시 알고리즘
	 * @return 암호화 된 데이터 String
	 * @throws Exception
	 */
	public static String getHashString(String input, String algorithm) throws Exception
	{
		return getHashString(input.getBytes(), algorithm);
	}
	
	/**
	 * 기본 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 String
	 * @param charset input String의 캐릭터 셋
	 * @return 암호화 된 데이터 String
	 * @throws Exception
	 */
	public static String getHashHexString(String input, String charset) throws Exception
	{
		return getHashString(input.getBytes(charset), DEFAULT_ALGORITHM);
	}
	
	/**
	 * 지정된 알고리즘을 이용하여 해쉬 암호화한다.
	 * @param input 암호화 할 소스 String
	 * @param charset input String의 캐릭터 셋
	 * @param algorithm 적용할 해시 알고리즘
	 * @return 암호화 된 데이터 String
	 * @throws Exception
	 */
	public static String getHashHexString(String input, String charset, String algorithm) throws Exception
	{
		return getHashString(input.getBytes(charset), algorithm);
	}
	
}
