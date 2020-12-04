package org.sws.util.common;

import java.text.DecimalFormat;

public class FormatUtil {
	
	/**
	 * 숫자의 경우 자리수를 맞추기 위하여 앞에 특정 문자로 채워서 String으로 리턴
	 * @param num 원본 숫자
	 * @param minLen 최소 길이
	 * @param prefix 채울 문자
	 * @return
	 */
	public String addPrefix(int num, int minLen, char prefix)
	{
		String s = num + "";
		for(;s.length() < minLen;) {
			s = prefix + s;
		}
		return s;
	}
	
	/**
	 * 문자열을 일정길이 만큼만 보여주고
	 * 그 길이에 초과되는 문자열일 경우 "..."를 덧붙여 보여줌.
	 * @param src 소스 문자열
	 * @param limit 제한 길이
	 * @return
	 */
	public static String cutString(String src, int limit) {
		return cutString(src, limit, "...");
	}
	
	/**
	 * 문자열을 일정길이 만큼만 보여주고
	 * 그 길이에 초과되는 문자열일 경우 특정문자를 덧붙여 보여줌.
	 * @param src 소스 문자열
	 * @param limit 제한 길이
	 * @param postfix 덧붙일 문자열.
	 * @return
	 */
	public static String cutString(String src, int limit, String suffix) {	
		char[] charArray = src.toCharArray();
		
		if (limit >= charArray.length)
			return src;
		return new String(charArray, 0, limit).concat(suffix);
	}
	
	/**
	 * 통화 표기
	 * @param cur 금액
	 * @param format ex> "###,##0.0"
	 * @return
	 */
	public static String getCurrency (String cur, String format) {
		return new DecimalFormat(format).format(Double.valueOf(cur).doubleValue());
	}
	
	/**
	 * 통화 표기
	 * @param cur 금액
	 * @param format ex> "###,##0"
	 * @return
	 */
	public static String getCurrency (int cur, String format) {
		return new DecimalFormat(format).format(cur);
	}
	
	/**
	 * 통화 표기
	 * @param cur 금액
	 * @param format ex> "###,##0.0"
	 * @return
	 */
	public static String getCurrency (double cur, String format) {
		return new DecimalFormat(format).format(cur);
	}
	
	/**
	 * 통화 표기
	 * @param cur 금액
	 * @param format ex> "###,##0.0"
	 * @return
	 */
	public static String getCurrency (long cur, String format) {
		return new DecimalFormat(format).format(cur);
	}
}
