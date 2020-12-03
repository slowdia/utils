package org.sws.util.common;

import java.util.regex.Pattern;

public class Validate {
	
	/**
	 * ���ĸ�� ���ڷ� �̷���� �ִ��� Ȯ���Ѵ�.
	 * @param text ���ڿ�
	 * @return ���ĸ�� ���ڸ� ���ԵǾ����� true, �׷��� ������ false
	 */
	public final static boolean isDigit(String str)
	{
        if (str == null)
            return false;
        int size = str.length();
        for(int i = 0 ; i < size ; i++) {
            if(!Character.isDigit(str.charAt(i)))
                return false;
        }
        return true;
    }

    /**
     * �̸��� �ּ��� ��ȿ���� �˻��Ѵ�
     * @param email
     * @return
     */
	public static boolean isEmail(String email)
	{
		if(email == null) return false;
		return Pattern.matches("([0-9a-zA-Z]+[-_\\.0-9a-zA-Z]+?[0-9a-zA-Z]*\\@[0-9a-zA-Z]+[-\\.0-9a-zA-Z]+?[0-9a-zA-Z]*\\.[a-zA-Z]{2,3}$)", email.trim());
	}
	
}
