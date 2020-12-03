package org.sws.util.common;

import java.text.DecimalFormat;

public class FormatUtil {
	
	/**
	 * ������ ��� �ڸ����� ���߱� ���Ͽ� �տ� Ư�� ���ڷ� ä���� String���� ����
	 * @param num ���� ����
	 * @param minLen �ּ� ����
	 * @param prefix ä�� ����
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
	 * ���ڿ��� �������� ��ŭ�� �����ְ�
	 * �� ���̿� �ʰ��Ǵ� ���ڿ��� ��� "..."�� ���ٿ� ������.
	 * @param src �ҽ� ���ڿ�
	 * @param limit ���� ����
	 * @return
	 */
	public static String fixLength(String src, int limit) {
		return fixLength(src, limit, "...");
	}
	
	/**
	 * ���ڿ��� �������� ��ŭ�� �����ְ�
	 * �� ���̿� �ʰ��Ǵ� ���ڿ��� ��� Ư�����ڸ� ���ٿ� ������.
	 * @param src �ҽ� ���ڿ�
	 * @param limit ���� ����
	 * @param postfix ������ ���ڿ�.
	 * @return
	 */
	public static String fixLength(String src, int limit, String postfix) {	
		char[] charArray = src.toCharArray();
		
		if (limit >= charArray.length)
			return src;
		return new String(charArray, 0, limit).concat(postfix);
	}
	
	/**
	 * ��ȭ ǥ��
	 * @param cur �ݾ�
	 * @param format ex> "###,##0"
	 * @return
	 */
	public static String getCurrency (String cur, String format) {
		return new DecimalFormat(format).format(Double.valueOf(cur).doubleValue());
	}
	
	/**
	 * ��ȭ ǥ��
	 * @param cur �ݾ�
	 * @param format ex> "###,##0"
	 * @return
	 */
	public static String getCurrency (int cur, String format) {
		return new DecimalFormat(format).format(cur);
	}
	
	/**
	 * ��ȭ ǥ��
	 * @param cur �ݾ�
	 * @param format ex> "###,##0"
	 * @return
	 */
	public static String getCurrency (double cur, String format) {
		return new DecimalFormat(format).format(cur);
	}
	
	/**
	 * ��ȭ ǥ��
	 * @param cur �ݾ�
	 * @param format ex> "###,##0"
	 * @return
	 */
	public static String getCurrency (long cur, String format) {
		return new DecimalFormat(format).format(cur);
	}
}
