package org.sws.util.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlInputFilter {

	protected static final boolean ALWAYS_MAKE_TAGS = true;
	
	protected static final boolean STRIP_COMMENTS = true;
	
	protected static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
	
	
	protected Map<String,List<String>> vAllowed;
	
	/** 태그 개수 */
	protected Map<String,Integer> vTagCounts;
	
	/** 담지 않는 태그 */
	protected String[] vNoClosingTags;
	
	/** 자체로 담는 태그 */
	protected String[] vSelfClosingTags;
	
	/** 별도로 담는 태그 */
	protected String[] vNeedClosingTags;
	
	/** 허용된 속성태그 */
	protected String[] vProtocolAtts;
	
	/** 허용된 프로토콜 태그 */
	protected String[] vAllowedProtocols;
	
	/** 허용된 엔티티 */
	protected String[] vAllowedEntities;
	
	/** 내용이 없으면 삭제할 태그 */
	protected String[] vRemoveBlanks;
		
	protected boolean vDebug;
	
	/**
	 * 기본 생성자
	 */
	public HtmlInputFilter(){
		this(false);
	}

	/**
	 * 생성자
	 * @param debug 디버깅 할 것인지 여부
	 */
	public HtmlInputFilter(boolean debug) {
		vDebug = debug;
		
		vAllowed = new HashMap<String,List<String>>();
		vTagCounts = new HashMap<String,Integer>();
		
		ArrayList<String> a_atts = new ArrayList<String>();
		a_atts.add( "href" );
		a_atts.add( "target" );
		vAllowed.put( "a", a_atts );
		
		ArrayList<String> img_atts = new ArrayList<String>();
		img_atts.add( "src" );
		img_atts.add( "width" );
		img_atts.add( "height" );
		img_atts.add( "alt" );
		vAllowed.put( "img", img_atts );
		
		ArrayList<String> p_atts = new ArrayList<String>();
		p_atts.add( "align" );
		vAllowed.put( "p", p_atts );
		
		ArrayList<String> font_atts = new ArrayList<String>();
		font_atts.add( "color" );
		font_atts.add( "size" );
		font_atts.add( "face" );
		font_atts.add( "style" );
		vAllowed.put( "font", font_atts );
		
		ArrayList<String> iframe_atts = new ArrayList<String>();
		iframe_atts.add( "height" );
		iframe_atts.add( "width" );
		iframe_atts.add( "src" );
		iframe_atts.add( "allowfullscreen" );
		iframe_atts.add( "frameborder" );
		vAllowed.put( "iframe", iframe_atts );
		
		ArrayList<String> no_atts = new ArrayList<String>();
		vAllowed.put( "b", no_atts );
		vAllowed.put( "strong", no_atts );
		vAllowed.put( "i", no_atts );
		vAllowed.put( "em", no_atts );
		vAllowed.put( "u", no_atts );
		vAllowed.put( "br", no_atts );

		vNoClosingTags = new String[] { "br" };
		vSelfClosingTags = new String[] { "img" };
		vNeedClosingTags = new String[] { "a", "b", "strong", "i", "em", "u", "p", "font", "iframe" };
		vAllowedProtocols = new String[] { "http", "mailto", "BACKGROUND-COLOR", "https" }; // no ftp.
		vProtocolAtts = new String[] { "src", "href", "align", "color", "size", "face", "style", "frameborder", "allowfullscreen", "height", "width" };
		vRemoveBlanks = new String[] { "a", "b", "strong", "i", "em", "u", "p", "font" };
		vAllowedEntities = new String[] { "amp", "gt", "lt", "quot", "nbsp" };
	}
	
	/**
	 * 리셋
	 */
	protected void reset(){
		vTagCounts = new HashMap<String,Integer>();
	}
	/**
	 * 디버깅 메시지 프린트
	 * @param msg
	 */
	protected void debug( String msg ){
		if (vDebug){
			System.out.println( msg );
		}
	}
	
	/**
	 * 숫자를 String으로 변경
	 * @param decimal
	 * @ return
	 */
	public static String chr( int decimal ){
		return String.valueOf( (char) decimal );
	}
	
	/**
	 * 태그로 사용되는 특수문자 치환
	 * @param s
	 * @return
	 */
	
	public static String htmlSpecialChars( String s ){
		s = s.replaceAll( "&", "&amp;" );
		s = s.replaceAll( "\"", "&quot;" );
		s = s.replaceAll( "<", "&lt;" );
		s = s.replaceAll( ">", "&gt;" );
		return s;
	}
	
	public synchronized String filter( String input ){
		reset();
		String s = input;
		
		debug( "				INPUT: " + input );
		
		s = escapeComments(s);
		debug( "	 escapeComments: " + s );
		
		s = balanceHTML(s);
		debug( "		balanceHTML: " + s );
		
		s = checkTags(s);
		debug( "			checkTags: " + s );
		
		s = processRemoveBlanks(s);
		debug( "processRemoveBlanks: " + s );
		
		s = validateEntities(s);
		debug( "	validateEntites: " + s );
		
		return s;
	}
	
	/**
	 * 주석 구문 처리
	 * @param s
	 * @return
	 */
	protected String escapeComments( String s ){
		Pattern p = Pattern.compile( "<!--(.*?)-->", Pattern.DOTALL );
		Matcher m = p.matcher( s );
		StringBuffer buf = new StringBuffer();
		if (m.find()){
			String match = m.group( 1 ); //(.*?)
			m.appendReplacement( buf, "<!--" + htmlSpecialChars( match ) + "-->" );
		}
		m.appendTail( buf );

		return buf.toString();
	}
	
	/**
	 * 태그 오류 수정???
	 * @param s
	 * @return
	 */
	protected String balanceHTML( String s ){
		if (ALWAYS_MAKE_TAGS) {
			s = regexReplace("^>", "", s);
			s = regexReplace("<([^>]*?)(?=<|$)", "<$1>", s);
			s = regexReplace("(^|>)([^<]*?)(?=>)", "$1<$2", s);
		}else{
			s = regexReplace("<([^>]*?)(?=<|$)", "&lt;$1", s);
			s = regexReplace("(^|>)([^<]*?)(?=>)", "$1$2&gt;<", s);
			s = s.replaceAll("<>", "");
		}
		return s;
	}
	
	/**
	 * 허용되지 않은 태그 삭제
	 * @param s
	 * @return
	 */
	protected String checkTags( String s ){	
		Pattern p = Pattern.compile( "<(.*?)>", Pattern.DOTALL );
		Matcher m = p.matcher( s );
		
		StringBuffer buf = new StringBuffer();
		while (m.find()){
			String replaceStr = m.group( 1 );
			replaceStr = processTag( replaceStr );
			m.appendReplacement(buf, replaceStr);
		}
		m.appendTail(buf);
		
		s = buf.toString();
		
		for( String key : vTagCounts.keySet()){
			for(int ii=0; ii<vTagCounts.get(key); ii++){
				s += "</" + key + ">";
			}
		}
		return s;
	}
	
	/**
	 * 비어있는 태그 제거
	 * @param s
	 * @return
	 */
	protected String processRemoveBlanks( String s ){
		for( String tag : vRemoveBlanks ){
			s = regexReplace( "<" + tag + "(\\s[^>]*)?></" + tag + ">", "", s );
			s = regexReplace( "<" + tag + "(\\s[^>]*)?/>", "", s );
		}
		return s;
	}
	
	/**
	 * regex_pattern 패턴에 해당하는 스트링을 replacement 스트링으로 변경
	 * @param regex_pattern
	 * @param replacement
	 * @param s
	 * @return
	 */
	protected String regexReplace( String regex_pattern, String replacement, String s ){
		Pattern p = Pattern.compile( regex_pattern );
		Matcher m = p.matcher( s );
		return m.replaceAll( replacement );
	}
	
	/**
	 * 체크하여 허용되지 않은 태그/속성/프로토콜 삭제
	 * @param s
	 * @return
	 */
	protected String processTag( String s ){	
		// ending tags
		Pattern p = Pattern.compile( "^/([a-z0-9]+)", REGEX_FLAGS_SI );
		Matcher m = p.matcher( s );
		if (m.find()) {
			String name = m.group(1).toLowerCase();
			if (vAllowed.containsKey( name )) {
				if (!inArray(name, vSelfClosingTags) && !inArray(name, vNoClosingTags)) {
					if (vTagCounts.containsKey( name )) {
						vTagCounts.put(name, vTagCounts.get(name)-1);
					return "</" + name + ">";
					}
				}
			}
		}
		
		// starting tags
		p = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
		m = p.matcher( s );
		if (m.find()) {
			String name = m.group(1).toLowerCase();
			String body = m.group(2);
			String ending = m.group(3);
			
			if (vAllowed.containsKey( name )) {
				String params = "";
				
				//본래는 '히+ㅎ'까지 체크해야 하나 euc-kr은 '힝'까지만 가능
				Pattern p2 = Pattern.compile("([a-z0-9가-힝]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
				Pattern p3 = Pattern.compile("([a-z0-9가-힝]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
				Matcher m2 = p2.matcher( body );
				Matcher m3 = p3.matcher( body );
				List<String> paramNames = new ArrayList<String>();
				List<String> paramValues = new ArrayList<String>();
				while (m2.find()) {
					paramNames.add(m2.group(1)); //([a-z0-9가-힝]+)
					paramValues.add(m2.group(3)); //(.*?)
				}
				while (m3.find()) {
					paramNames.add(m3.group(1)); //([a-z0-9가-힝]+)
					paramValues.add(m3.group(3)); //([^\"\\s']+)
				}
				
				String paramName, paramValue;
				for( int ii=0; ii<paramNames.size(); ii++ ) {
					paramName = paramNames.get(ii).toLowerCase();
					paramValue = paramValues.get(ii);
					
					if (vAllowed.get( name ).contains( paramName )) {
						if (inArray( paramName, vProtocolAtts )) {
							paramValue = processParamProtocol( paramValue );
						}
						params += " " + paramName + "=\"" + paramValue + "\"";
					}
				}
				
				if (inArray( name, vSelfClosingTags )) {
					ending = " /";
				}
				
				if (inArray( name, vNoClosingTags ) || inArray( name, vNeedClosingTags )) {
					ending = "";
				}
				
				if (ending == null || ending.length() < 1) {
					if (!inArray(name, vNoClosingTags)) {
						if (vTagCounts.containsKey( name )) {
							vTagCounts.put( name, vTagCounts.get(name)+1 );
						} else {
							vTagCounts.put( name, 1 );
						}
					}
				} else {
					ending = " /";
				}
				return "<" + name + params + ending + ">";
			} else {
				return "";
			}
		}
		
		// comments
		p = Pattern.compile( "^!--(.*)--$", REGEX_FLAGS_SI );
		m = p.matcher( s );
		if (m.find()) {
			String comment = m.group();
			if (STRIP_COMMENTS) {
				return "";
			} else {
				return "<" + comment + ">"; 
			}
		}
		
		return "";
	}
	
	/**
	 * 허용된 프로토콜 외에는 제거
	 * @param s
	 * @return
	 */
	 protected String processParamProtocol( String s ){
		s = decodeEntities( s );
		Pattern p = Pattern.compile( "^([^:]+):", REGEX_FLAGS_SI );
		Matcher m = p.matcher( s );
		if (m.find()) {
			String protocol = m.group(1);
			if (!inArray( protocol, vAllowedProtocols ) && !inArray( protocol.toUpperCase(), vAllowedProtocols )) {
				s = "#" + s.substring( protocol.length()+1, s.length() );
				if (s.startsWith("#//")) s = "#" + s.substring( 3, s.length() );
			}
		}
		return s;
	}
	
	/**
	 * 엔티티 디코드
	 * @param s
	 * @return
	 */
	protected String decodeEntities( String s ){
		StringBuffer buf = new StringBuffer();
		
		Pattern p = Pattern.compile( "&#(\\d+);?" );
		Matcher m = p.matcher( s );
		while (m.find()) {
			String match = m.group( 1 );
			int decimal = Integer.decode( match ).intValue();
			m.appendReplacement( buf, chr( decimal ) );
		}
		m.appendTail( buf );
		s = buf.toString();
		
		buf = new StringBuffer();
		p = Pattern.compile( "&#x([0-9a-f]+);?");
		m = p.matcher( s );
		while (m.find()) {
			String match = m.group( 1 );
			int decimal = Integer.decode( match ).intValue();
			m.appendReplacement( buf, chr( decimal ) );
		}
		m.appendTail( buf );
		s = buf.toString();
		
		buf = new StringBuffer();
		p = Pattern.compile( "%([0-9a-f]{2});?");
		m = p.matcher( s );
		while (m.find()) {
			String match = m.group( 1 );
			int decimal = Integer.decode( match ).intValue();
			m.appendReplacement( buf, chr( decimal ) );
		}
		m.appendTail( buf );
		s = buf.toString();
		
		s = validateEntities( s );
		return s;
	}

	/**
	 * 엔티티 검증
	 * @param s
	 * @return
	 */
	protected String validateEntities( String s )
	{
		// validate entities throughout the string
		StringBuffer buf = new StringBuffer();
		Pattern p = Pattern.compile( "&([^&;]*)(?=(;|&|$))" );
		Matcher m = p.matcher( s );
		if (m.find()) {
			String entity_nm = m.group( 1 ); //([^&;]*) 
			String terminator = m.group( 2 ); //(?=(;|&|$))
			m.appendReplacement(buf, checkEntity(entity_nm, terminator));
		}
		m.appendTail(buf);
		while(buf.indexOf("$") > -1){
			buf.replace(buf.indexOf("$"), buf.indexOf("$")+1, "&#36;");
			
		}
		s = buf.toString();
		/*if (m.find()) {
			String one = m.group( 1 ); //([^&;]*) 
			String two = m.group( 2 ); //(?=(;|&|$))
			s = checkEntity( one, two );
		}*/
		
		// validate quotes outside of tags
		p = Pattern.compile( "(>|^)([^<]+?)(<|$)", Pattern.DOTALL );
		m = p.matcher( s );
		buf = new StringBuffer();
		if (m.find()) {
			String one = m.group( 1 ); //(>|^) 
			String two = m.group( 2 ); //([^<]+?) 
			String three = m.group( 3 ); //(<|$) 
			m.appendReplacement( buf, one + two.replaceAll( "\"", "&quot;" ) + three);
		}
		m.appendTail( buf );
		s = buf.toString();
		
		return s;
	}
	
	/**
	 * 허용된 엔티티인지 확인하여 허용되지 않은 엔티티는 ; 를 &amp; 로 변경
	 * @param preamble
	 * @param term
	 * @return
	 */
	protected String checkEntity( String entity_nm, String terminator ){
		if (!terminator.equals(";")) {
			return "&amp;" + entity_nm;
		}
		
		if ( inArray(entity_nm, vAllowedEntities) ) {
			return "&" + entity_nm;
		}
		
		/*if ( isValidEntity( entity_nm ) ) {
			return "&" + entity_nm;
		}*/
		
		return "&amp;" + entity_nm;
	}
	
	/**
	 * 배열에 포함되었는지 여부 확인
	 * @param s
	 * @param array
	 * @return
	 */
	private boolean inArray( String s, String[] array ){
		for (String item : array){
			if (item != null && item.equals(s)){
				return true;
			}
		}
		return false;
	}
	
	
}
