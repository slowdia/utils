package org.sws.util.crypt;

import java.io.InputStream;
import java.security.MessageDigest;

/**
 * �ܹ��� �ؽ� ��ȣȭ
 * @author ago
 *
 */
public class HashEncrypt {
	
	/** default : SHA-256*/
	private static final String DEFAULT_ALGORITHM = "SHA-256";
	
	/**
	 * �⺻ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� byte[]
	 * @return ��ȣȭ �� ������ byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(byte[] input) throws Exception
	{
		return getHash(input, DEFAULT_ALGORITHM);
	}
	
	/**
	 * ������ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� byte[]
	 * @param algorithm ������ �ؽ� �˰���
	 * @return ��ȣȭ �� ������ byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(byte[] input, String algorithm) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance(algorithm);
		return md.digest(input);
	}

	/**
	 * �⺻ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� InputStream
	 * @return ��ȣȭ �� ������ byte[]
	 * @throws Exception
	 */
	public static byte[] getHash(InputStream input) throws Exception 
	{
		return getHash(input, DEFAULT_ALGORITHM);
	}

	/**
	 * ������ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� InputStream
	 * @param algorithm ������ �ؽ� �˰���
	 * @return ��ȣȭ �� ������ byte[]
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
	 * �⺻ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� byte[]
	 * @return ��ȣȭ �� ������ String
	 * @throws Exception
	 */
	public static String getHashString(byte[] input) throws Exception
	{
		return getHashString(input, DEFAULT_ALGORITHM);
	}
	
	/**
	 * ������ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� byte[]
	 * @param algorithm ������ �ؽ� �˰���
	 * @return ��ȣȭ �� ������ String
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
	 * �⺻ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� String
	 * @return ��ȣȭ �� ������ String
	 * @throws Exception
	 */
	public static String getHashString(String input) throws Exception
	{
		return getHashString(input.getBytes(), DEFAULT_ALGORITHM);
	}
	
	/**
	 * ������ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� String
	 * @param algorithm ������ �ؽ� �˰���
	 * @return ��ȣȭ �� ������ String
	 * @throws Exception
	 */
	public static String getHashString(String input, String algorithm) throws Exception
	{
		return getHashString(input.getBytes(), algorithm);
	}
	
	/**
	 * �⺻ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� String
	 * @param charset input String�� ĳ���� ��
	 * @return ��ȣȭ �� ������ String
	 * @throws Exception
	 */
	public static String getHashHexString(String input, String charset) throws Exception
	{
		return getHashString(input.getBytes(charset), DEFAULT_ALGORITHM);
	}
	
	/**
	 * ������ �˰����� �̿��Ͽ� �ؽ� ��ȣȭ�Ѵ�.
	 * @param input ��ȣȭ �� �ҽ� String
	 * @param charset input String�� ĳ���� ��
	 * @param algorithm ������ �ؽ� �˰���
	 * @return ��ȣȭ �� ������ String
	 * @throws Exception
	 */
	public static String getHashHexString(String input, String charset, String algorithm) throws Exception
	{
		return getHashString(input.getBytes(charset), algorithm);
	}
	
}
