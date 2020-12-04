package org.sws.util.http;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sws.util.entity.Entity;

public class HttpUtil {
	
	public static void main(String[] args) {
		try {
			System.out.println(getContents("https://daum.net"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * URL로 부터 컨텐츠(html 소스코드)를 가져온다.
	 * @param srcUrl http 또는 https url
	 * @return html 소스코드
	 * @throws Exception
	 */
	public static String getContents(String srcUrl) throws Exception
	{
		return getContents(srcUrl, "UTF-8");
	}

	/**
	 * URL로 부터 컨텐츠(html 소스코드)를 가져온다.
	 * @param srcUrl http 또는 https url
	 * @param charset 캐릭터 셋
	 * @return html 소스코드
	 * @throws Exception
	 */
	public static String getContents(String srcUrl, String charset) throws Exception
	{
		if (!(srcUrl.toLowerCase()).startsWith("http://") && !(srcUrl.toLowerCase()).startsWith("https://")) {
			srcUrl = "http://" + srcUrl;
		}
		
		HttpURLConnection httpConn = (HttpURLConnection)new URL(srcUrl).openConnection();
		httpConn.setConnectTimeout(3000);
		StringBuffer sb = new StringBuffer();
		if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader reader = null;
			if(charset == null || charset.equals("")) {
				reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			}else {
				reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), charset));
			}
			String line = null;
			while((line = reader.readLine()) != null){
				sb.append(line + "\r\n");
			}
		}else{
			sb.append("Failed to fetch content.");
		}
		return sb.toString().trim();
	}
	
	/**
	 * 웹으로 부터 파일을 다운로드 받는다.
	 * @param url 다운로드할 파일 URL
	 * @param save_full_Name 저장 경로 포함 파일명
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static boolean getHttpFile(String url, String save_full_Name) throws MalformedURLException, IOException
	{
		HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
		conn.setConnectTimeout(3000);
		conn.setReadTimeout(3000);
		conn.setUseCaches(true);
		conn.setRequestMethod("GET");

		conn.setRequestProperty("Connection", "Keep-Alive");
		conn.setRequestProperty("Content-Type", "image/png");
		
		conn.connect();
		
		int response = conn.getResponseCode();
		if( response == HttpURLConnection.HTTP_OK ){
			InputStream is = conn.getInputStream();
			FileOutputStream outputStream = new FileOutputStream(save_full_Name);
		 
			if ("gzip".equals(conn.getContentEncoding())){
				is = new GZIPInputStream(is);
			}
			 
			// opens an output stream to save into file
			int bytesRead = -1;
			byte[] buffer = new byte[2048];
			while ((bytesRead = is.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			is.close();
			outputStream.close();
			return true;
		}else
			return false;
	}
	
	/**
	* 쿠키를 생성한다.만든다
	*
	* @param response HttpServletResponse
	* @param name 쿠키의 이름
	* @param value 쿠키의 값
	* @param expiry 유효할 시간(분)
	*/
	public static void setCookie(HttpServletResponse response, String name, String value, int expiry) throws Exception
	{
		value = java.net.URLEncoder.encode(value, "euc-kr");
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(60 * expiry);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 지정된 키값에 대한 쿠키의 값을 꺼내 온다
	 * @param request HttpServletRequest
	 * @param cookieName 가져올 쿠키의 이름
	 * @return 쿠키의 값
	 */
	public static String getCookie(HttpServletRequest request, String cookieName) throws Exception
	{
		Cookie[] cookies = request.getCookies();
		if(cookies == null) return "";
		String value = "";
		for(int i = 0; i < cookies.length; i++) {
			if(cookieName.equals(cookies[i].getName())) {
				value = URLDecoder.decode(cookies[i].getValue(), "euc-kr");
				break;
			}
		}
		return value;
	}
	
	/**
	 * 파라메터 문자열을 파싱하여 엔티티로 반환한다.
	 * @param param 파라메터 문자열
	 * @return
	 */
	public static Entity parseParameters(String param)
	{
		Entity entity = new Entity();
		StringTokenizer token = new StringTokenizer(param, "&");
		String s = "";
		int pos = 0;
		while(token.hasMoreTokens()) {
			s = token.nextToken();
			if((pos = s.indexOf("=")) > 0) {
				entity.setValue(s.substring(0, pos), pos + 1);
			}
		}
		return entity;
	}
	
	/**
	 * 엔티티의 값을 파라메터 라인으로 생성하여 반환한다.
	 * @param param 파라메터 엔티티
	 * @return
	 */
	public static String toParameters(Entity param)
	{
		StringBuffer sb = new StringBuffer();
		ArrayList<String> list = param.getKeys();
		for(String key : list) {
			if(sb.length() > 0) { sb.append("&"); }
			sb.append(key).append("=").append(param.getString(key));
		}
		return sb.toString();
	}
	
	/**
	 * 문자열을 utf-8 캐릭터 셋으로 url encoding 한다
	 * @param value 인코딩할 문자열
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String value) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(value, "utf-8");
	}
	
	/**
	 * 문자열을 캐릭터 셋으로 url encoding 한다
	 * @param value 인코딩할 문자열
	 * @param charset 인코딩 캐릭터 셋
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String value, String charset) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(value, charset);
	}
	
	/**
	 * 문자열을 utf-8 캐릭터 셋으로 url decoding 한다
	 * @param value 디코딩할 문자열
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String value) throws UnsupportedEncodingException
	{
		return URLDecoder.decode(value, "utf-8");
	}
	
	/**
	 * 문자열을 캐릭터 셋으로 url decoding 한다
	 * @param value 디코딩할 문자열
	 * @param charset 디코딩 캐릭터 셋
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String value, String charset) throws UnsupportedEncodingException
	{
		return URLDecoder.decode(value, charset);
	}
	
	/**
	 * HttpServletRequest의 ContentType이 multipart/form-data 인지 확인
	 * @param req
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest req)
	{
		String type = req.getContentType();
		if (type == null
			|| !type.toLowerCase().startsWith("multipart/form-data")) {
			return false;
		}
		return true;
	}
	
}
