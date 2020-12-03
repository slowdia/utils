package org.sws.util.common;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.text.StringEscapeUtils;

public class StringUtil extends StringEscapeUtils {
	
	/**
	 * ���ڸ� �ƽ�Ű �ڵ�� ��ȯ�Ѵ�.
	 * @param ch ��ȯ�� ����
	 * @return int Ascii �ڵ�
	 */
	public static int charToAscii(char ch)
	{
		return (int)ch;
	}
	
	/**
	 * Ascii�ڵ带 ���ڷ� ��ȯ
	 * @param ascii ���ڷ� ��ȯ�� Ascii �ڵ�
	 * @return String ����
	 */
	public static String asciiToChar(int ascii)
	{
		return String.valueOf((char)ascii) ;		
	}
	
	/**
	 * array�� ��� String�� coupler�� �����Ѵ�.
	 * @param array String ����Ʈ
	 * @param coupler ������
	 * @return
	 */
	public static String concate(ArrayList<String> array, String coupler)
	{
		if(array == null) return "";
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.size(); i++){
			if(i > 0 && coupler != null) sb.append(coupler);
				sb.append(array.get(i));
		}

		return sb.toString();
	}
	
	/**
	 * array�� ��� String�� delimiter�� �����Ѵ�.
	 * @param array String �迭
	 * @param coupler ������
	 * @return
	 */
	public static String concate(String[] array, String coupler)
	{
		if(array == null) return "";
		
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < array.length; i++){
			if(i > 0 && coupler != null) sb.append(coupler);
				sb.append(array[i]);
		}

		return sb.toString();
	}
	
	/**
	 * �迭 ���� ã������ ������Ʈ�� ���ԵǾ��ִ��� Ȯ��
	 * @param array String �迭
	 * @param str ã�����ϴ� ���ڿ�
	 * @return
	 */
	public static boolean contains(String[] array, String str)
	{
		boolean b = false;
		if(array == null || str == null) return b;
		for(String s: array) {
			if(s.equals(str)) {
				b = true;
				break;
			}
		}
		return b;
	}
	
	/**
	 * ���ڿ��� ĳ���� ���� �����Ѵ�.
	 * @param src �ҽ� ���ڿ�
	 * @param fromCharset ���� ���� charset
	 * @param toCharset ����� ���� charset
	 * @return
	 */
	public static String convert(String src, String fromCharset, String toCharset) throws Exception
	{
		if(fromCharset.toUpperCase().equals(toCharset.toUpperCase())) {
			return src ;
		}
		byte[] b = null;
		if(fromCharset == null || "".equals(fromCharset)) {
			b = src.getBytes();
		}else {
			b = src.getBytes(fromCharset);
		}
		if(toCharset == null || "".equals(toCharset)) {
			src = new String(b);
		}else {
			src = new String(b, toCharset);
		}
		return src;
	}
	
	/**
	 * �ҽ� ���ڿ��� �˻� �� ���ڿ��� ��� ���ԵǾ����� Ȯ��
	 * @param src �ҽ� ���ڿ�
	 * @param search �˻� ���ڿ�
	 * @return
	 */
	public static int getCount(String src, String search)
	{
		int count = 0;
		int pos = 0;
		for(pos = src.indexOf(search, pos); pos > -1; pos = src.indexOf(search, pos+search.length())) {
			count ++;
		}
		return count;
	}

	/**
	 * 16���� ���ڿ��� ����Ʈ �迭�� ��ȯ�Ѵ�.
	 * @param hex String
	 * @return byte[]
	 */
	public static byte[] getHexBytes(String hex)
	{
		if (hex == null || hex.length() == 0) {
			return null;
		}
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}
	
	/**
	 * ����Ʈ �迭�� 16������ ��ȯ�Ͽ� String���� ��ȯ�Ѵ�.
	 * @param src ��ȯ �� �ҽ� byte[]
	 * @return ��ȯ�� String
	 */
	public static String getHexString(byte[] src)
	{
		StringBuffer sb = new StringBuffer(src.length * 2); 
		for (int i = 0; i < src.length; i++) { 
			 sb.append(Integer.toString((src[i] & 0xf0) >> 4, 16)); 
			 sb.append(Integer.toString(src[i] & 0x0f, 16));
		} 
		return sb.toString();
	}
	
	/**
	 * ���� ���ڸ� ������ ���̸�ŭ �����Ͽ� ����, ���� �ҹ��� + ����
	 * @param len ������ ���� ����
	 * @return
	 */
	public static String getRandomString(int length)
	{
		StringBuffer sb = new StringBuffer(); 
		Random random = new Random(); 
		char args[] = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9'};
		for(int i = 0; i < length; i++) { 
			sb.append(args[random.nextInt(args.length)]);
		} 
		return sb.toString(); 
	}

	/**
	 * �����±׿� ���� �±׷� �ο��� ���ڸ���Ʈ�� ��ȯ�Ѵ�.
	 *
	 * @param src ��� ���ڿ�
	 * @param startTag ���� �±�
	 * @param endTag ���� �±�
	 * @return String ����Ʈ
	 */
	public static ArrayList<String> getWrappedText(String src, String startTag, String endTag) {

		return getWrappedText(src, startTag, endTag, 0);
	}

	/**
	 * �����±׿� ���� �±׷� �ο��� ���ڸ���Ʈ�� ��ȯ�Ѵ�. ���� �±׿� ���� �±� �� ����
	 *
	 * @param src ��� ���ڿ�
	 * @param startTag ���� �±�
	 * @param endTag ���� �±�
	 * @param startIndex �˻� ���� ��ġ
	 * @return String ����Ʈ
	 */
	public static ArrayList<String> getWrappedText(String src, String startTag, String endTag, int startIndex) {

		ArrayList<String> array = new ArrayList<String>();
		String str = "";
		int startTagPos = 0;
		int endTagPos = 0;
		
		while (startIndex < src.length()){
			startTagPos = src.indexOf(startTag, startIndex);
			if(startTagPos < 0) break;
			endTagPos = src.indexOf(endTag, (startTagPos + startTag.length()));
			if(endTagPos < 0) break;
			
			str = src.substring(startTagPos + startTag.length(), endTagPos);
			if(str.lastIndexOf(startTag) > 0) {
				str = str.substring(str.lastIndexOf(startTag) + startTag.length());
			}
			array.add(str);
			
			startIndex = endTagPos + endTag.length();
		}
		
		return array;
	}
	
	/**
	 * �ҽ� ���ڿ����� ���� ���ڿ��� ���� ���ڿ� ������ ���ڸ� ��� ���� �Ѵ�. ���� ���ڿ��� ���� ���ڿ��� �����Ͽ� ����
	 * @param src �ҽ� ���ڿ�
	 * @param startStr ���� ���ڿ�
	 * @param endStr ���� ���ڿ�
	 * @return
	 */
	public static String removeAll(String src, String startStr, String endStr)
	{
		return removeAll(src, startStr, endStr, true);
	}
	
	/**
	 * �ҽ� ���ڿ����� ���� ���ڿ��� ���� ���ڿ� ������ ���ڸ� ��� ���� �Ѵ�.
	 * @param src �ҽ� ���ڿ�
	 * @param startStr ���� ���ڿ�
	 * @param endStr ���� ���ڿ�
	 * @param includeSearch ���� ���ڿ��� ���� ���ڿ��� �����Ͽ� ������ ������ ����
	 * @return
	 */
	public static String removeAll(String src, String startStr, String endStr, boolean includeSearch)
	{
		StringBuffer sb = null;
		int startLen = startStr.length();
		int endLen = endStr.length();
		int startPos = src.indexOf(startStr);
		int endPos = src.indexOf(endStr, startPos + startLen);
		while(startPos > -1 && endPos > -1) {
			sb = new StringBuffer();
			sb.append(src.substring(0, startPos + (includeSearch ? 0 : startLen)))
			  .append(src.substring(endPos + (includeSearch ? endLen : 0)));
			src = sb.toString();
			startPos = src.indexOf(startStr, endPos + endLen);
			endPos = src.indexOf(endStr, startPos + startLen);
		}
		return src;
	}
	
	/**
	 * ���� ���ڿ����� ó�� ã�� Ư�� ���ڸ� ������
	 * @param src �ҽ� ���ڿ�
	 * @param findStr ã�� ���ڿ�
	 * @param replace ��ü�� ���ڿ�
	 * @return
	 */
	public static String replace(String src, String findStr, String replace)
	{
		StringBuffer sb = new StringBuffer();
		int j = 0;
		int i = src.indexOf(findStr, j);
		if(i != -1) {
			sb.append(src.substring(j, i));
			sb.append(replace);
			j = i + findStr.length();
		}

		if (j < src.length()) {
			sb.append(src.substring(j));
		}
		return sb.toString();
	}
	
	/**
	 * ���� ���ڿ����� Ư�� ���ڸ� ������
	 * @param src �ҽ� ���ڿ�
	 * @param findStr ã�� ���ڿ�
	 * @param replace ��ü�� ���ڿ�
	 * @return
	 */
	public static String replaceAll(String src, String findStr, String replace)
	{
		StringBuffer sb = new StringBuffer();
		int j = 0;
		for ( int i = src.indexOf(findStr, j); i != -1; i = src.indexOf(findStr, j)) {
			sb.append(src.substring(j, i));
			sb.append(replace);
			j = i + findStr.length();
		}

		if (j < src.length()) {
			sb.append(src.substring(j));
		}
		return sb.toString();
	}
	
	/**
	 * ���� ���ڿ��� �����ڷ� �߶� String �迭�� ��ȯ�Ѵ�.
	 * @param src ���� ���ڿ�
	 * @param delimiter ������
	 * @return
	 */
	public static ArrayList<String> split(String src, String delimiter)
	{
		return split(src, delimiter, false);
	}
	
	/**
	 * ���� ���ڿ��� �����ڷ� �߶� String �迭�� ��ȯ�Ѵ�.
	 * @param src ���� ���ڿ�
	 * @param delimiter ������
	 * @param nullable null(��) ����
	 * @return
	 */
	public static ArrayList<String> split(String src, String delimiter, boolean nullable)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		if(src == null || delimiter == null) {
			return list;
		}
		
		int startPos = 0, curPos = 0;
		String temp = "";
		while((curPos = src.indexOf(delimiter, startPos)) >= 0)
		{
			temp = src.substring(startPos, curPos);
			if(!temp.equals("") || nullable) {
				list.add(temp);
			}
			startPos = curPos + delimiter.length();
		}
		if(startPos < src.length())
		{
			list.add(src.substring(startPos, src.length()));
		}else if(nullable){
			list.add("");
		}
		
		return list;
	}
	
	/**
	 * src ���ڿ��� ������ ���̷� �߶� String ����Ʈ�� ��ȯ�Ѵ�.
	 * @param src ���� ���ڿ�
	 * @param length �ڸ� ����
	 * @return
	 */
	public static ArrayList<String> split(String src, int length)
	{
		ArrayList<String> array = new ArrayList<String>();
		
		int startPos = 0;
		while(src.length() > startPos + length) {
			array.add(src.substring(startPos, startPos + length));
			startPos += length;
		}
		if(src.length() > startPos) {
			array.add(src.substring(startPos));
		}
		
		return array;
	}
	
	/**
	 * src ���ڿ��� ������ ���̷� �߶� String ����Ʈ�� ��ȯ�Ѵ�.
	 * @param src ���� ���ڿ�
	 * @param length �ڸ� ���� �迭
	 * @return
	 */
	public static ArrayList<String> split(String src, int[] length)
	{
		return split(src, length, false);
	}
	
	/**
	 * src ���ڿ��� ������ ���̷� �߶� String ����Ʈ�� ��ȯ�Ѵ�.
	 * @param src ���� ���ڿ�
	 * @param length �ڸ� ���� �迭
	 * @param repeat �ڸ� ���� �迭�� �ݺ��ؼ� �ڸ� ������ ����
	 * @return
	 */
	public static ArrayList<String> split(String src, int[] length, boolean repeat)
	{
		ArrayList<String> array = new ArrayList<String>();
		int index = 0;
		int startPos = 0;
		while(src.length() > startPos + length[index]) {
			array.add(src.substring(startPos, startPos + length[index]));
			startPos += length[index];
			index++;
			if(index >= length.length) {
				if(repeat) index = 0;
				else break;
			}
		}
		if(src.length() > startPos) {
			array.add(src.substring(startPos));
		}
		
		return array;
	}
	
}
