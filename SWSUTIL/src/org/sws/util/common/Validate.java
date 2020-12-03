package org.sws.util.common;

import java.util.regex.Pattern;

public class Validate {
	
	/**
	 * 알파멧과 숫자로 이루어져 있는지 확인한다.
	 * @param text 문자열
	 * @return 알파멧과 숫자만 포함되었으면 true, 그렇지 않으면 false
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
     * 이메일 주소의 유효성을 검사한다
     * @param email
     * @return
     */
	public static boolean isEmail(String email)
	{
		if(email == null) return false;
		return Pattern.matches("([0-9a-zA-Z]+[-_\\.0-9a-zA-Z]+?[0-9a-zA-Z]*\\@[0-9a-zA-Z]+[-\\.0-9a-zA-Z]+?[0-9a-zA-Z]*\\.[a-zA-Z]{2,3}$)", email.trim());
	}
	
}
