package org.sws.util.http;

import org.sws.util.common.StringUtil;

public class HtmlUtil {

	
	/**
	 * XSS ���� ������ �ִ� �±׸� �ּ�ó���Ѵ�.<br>
	 * ȭ��Ʈ ����Ʈ�� �����Ҽ��� ���� ������ �� ����Ʈ�� ó���Ѵ�.
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
	 * XSS ���� ������ �ִ� �±׸� �����Ѵ�.<br>
	 * ȭ��Ʈ ����Ʈ�� �����Ҽ��� ���� ������ �� ����Ʈ�� ó���Ѵ�.
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
