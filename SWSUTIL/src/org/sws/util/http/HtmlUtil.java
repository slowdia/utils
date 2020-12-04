package org.sws.util.http;

import org.sws.util.common.StringUtil;

public class HtmlUtil {

	
	/**
	 * XSS 보안 위험이 있는 태그를 주석처리한다.<br>
	 * 화이트 리스트로 관리할수가 없기 때문에 블랙 리스트로 처리한다.
	 * @param str
	 * @return
	 */
	public static String remarkXSS(String str)
	{
    	String[] blackList = {"script", "javascript", "iframe", "object", "embed", "applet"};
    	int nPos = 0;
    	for(String tag : blackList) {
	    	while((nPos = str.toLowerCase().indexOf("<"+tag)) >= 0){
				str = str.substring(0,nPos) + "<!--"+tag+str.substring(nPos+7);
				nPos = str.toLowerCase().indexOf("</"+tag+">");
				if(nPos >= 0) str = str.substring(0,nPos) + "</"+tag+" -->"+str.substring(nPos+9);
	    	}
    	}
    	return str;
	}
	
	/**
	 * XSS 보안 위험이 있는 태그를 제거한다.<br>
	 * 화이트 리스트로 관리할수가 없기 때문에 블랙 리스트로 처리한다.
	 * @param src
	 * @return
	 */
	public static String removeXSS(String src)
	{
    	String[] blackList = {"script", "javascript", "iframe", "object", "embed", "applet"};
    	for(String tag : blackList) {
    		StringUtil.removeAll(src, "<"+tag, "</"+tag+">");
    	}
    	return src;
	}
}
