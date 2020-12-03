package org.sws.util.common;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.text.StringEscapeUtils;

public class StringUtil extends StringEscapeUtils {
	
	/**
	 * 문자를 아스키 코드로 변환한다.
	 * @param ch 변환할 문자
	 * @return int Ascii 코드
	 */
	public static int charToAscii(char ch)
	{
		return (int)ch;
	}
	
	/**
	 * Ascii코드를 문자로 변환
	 * @param ascii 문자로 변환할 Ascii 코드
	 * @return String 문자
	 */
	public static String asciiToChar(int ascii)
	{
		return String.valueOf((char)ascii) ;		
	}
	
	/**
	 * array에 담긴 String을 coupler로 연결한다.
	 * @param array String 리스트
	 * @param coupler 연결자
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
	 * array에 담긴 String을 delimiter로 연결한다.
	 * @param array String 배열
	 * @param coupler 연결자
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
	 * 배열 내에 찾으려는 오프젝트가 포함되어있는지 확인
	 * @param array String 배열
	 * @param str 찾고자하는 문자열
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
	 * 문자열의 캐릭터 셋을 변경한다.
	 * @param src 소스 문자열
	 * @param fromCharset 현재 문자 charset
	 * @param toCharset 변경될 문자 charset
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
	 * 소스 문자열에 검색 할 문자열이 몇개가 포함되었는지 확인
	 * @param src 소스 문자열
	 * @param search 검색 문자열
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
	 * 16진수 문자열을 바이트 배열로 변환한다.
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
	 * 바이트 배열을 16진수로 변환하여 String으로 반환한다.
	 * @param src 변환 할 소스 byte[]
	 * @return 변환된 String
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
	 * 랜던 문자를 지정된 길이만큼 생성하여 리턴, 영문 소문자 + 숫자
	 * @param len 생성될 문자 길이
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
	 * 시작태그와 종료 태그로 싸여진 문자리스트를 반환한다.
	 *
	 * @param src 대상 문자열
	 * @param startTag 시작 태그
	 * @param endTag 종료 태그
	 * @return String 리스트
	 */
	public static ArrayList<String> getWrappedText(String src, String startTag, String endTag) {

		return getWrappedText(src, startTag, endTag, 0);
	}

	/**
	 * 시작태그와 종료 태그로 싸여진 문자리스트를 반환한다. 시작 태그와 종료 태그 미 포함
	 *
	 * @param src 대상 문자열
	 * @param startTag 시작 태그
	 * @param endTag 종료 태그
	 * @param startIndex 검색 시작 위치
	 * @return String 리스트
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
	 * 소스 문자열에서 시작 문자열과 종료 문자열 사이의 문자를 모두 제거 한다. 시작 문자열과 종료 문자열을 포함하여 삭제
	 * @param src 소스 문자열
	 * @param startStr 시작 문자열
	 * @param endStr 종료 문자열
	 * @return
	 */
	public static String removeAll(String src, String startStr, String endStr)
	{
		return removeAll(src, startStr, endStr, true);
	}
	
	/**
	 * 소스 문자열에서 시작 문자열과 종료 문자열 사이의 문자를 모두 제거 한다.
	 * @param src 소스 문자열
	 * @param startStr 시작 문자열
	 * @param endStr 종료 문자열
	 * @param includeSearch 시작 문자열과 종료 문자열을 포함하여 삭제할 것인지 여부
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
	 * 지정 문자열에서 처음 찾은 특정 문자를 변경함
	 * @param src 소스 문자열
	 * @param findStr 찾는 문자열
	 * @param replace 교체될 문자열
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
	 * 지정 문자열에서 특정 문자를 변경함
	 * @param src 소스 문자열
	 * @param findStr 찾는 문자열
	 * @param replace 교체될 문자열
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
	 * 원본 문자열을 구분자로 잘라 String 배열로 반환한다.
	 * @param src 원본 문자열
	 * @param delimiter 구분자
	 * @return
	 */
	public static ArrayList<String> split(String src, String delimiter)
	{
		return split(src, delimiter, false);
	}
	
	/**
	 * 원본 문자열을 구분자로 잘라 String 배열로 반환한다.
	 * @param src 원본 문자열
	 * @param delimiter 구분자
	 * @param nullable null(빈값) 포함
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
	 * src 문자열을 지정된 길이로 잘라 String 리스트로 반환한다.
	 * @param src 원본 문자열
	 * @param length 자를 길이
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
	 * src 문자열을 지정된 길이로 잘라 String 리스트로 반환한다.
	 * @param src 원본 문자열
	 * @param length 자를 길이 배열
	 * @return
	 */
	public static ArrayList<String> split(String src, int[] length)
	{
		return split(src, length, false);
	}
	
	/**
	 * src 문자열을 지정된 길이로 잘라 String 리스트로 반환한다.
	 * @param src 원본 문자열
	 * @param length 자를 길이 배열
	 * @param repeat 자를 길이 배열로 반복해서 자를 것인지 여부
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
