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
	 * URL�� ���� ������(html �ҽ��ڵ�)�� �����´�.
	 * @param srcUrl http �Ǵ� https url
	 * @return html �ҽ��ڵ�
	 * @throws Exception
	 */
	public static String getContents(String srcUrl) throws Exception
	{
		return getContents(srcUrl, "UTF-8");
	}

	/**
	 * URL�� ���� ������(html �ҽ��ڵ�)�� �����´�.
	 * @param srcUrl http �Ǵ� https url
	 * @param charset ĳ���� ��
	 * @return html �ҽ��ڵ�
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
	 * ������ ���� ������ �ٿ�ε� �޴´�.
	 * @param url �ٿ�ε��� ���� URL
	 * @param save_full_Name ���� ��� ���� ���ϸ�
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
	* ��Ű�� �����Ѵ�.�����
	*
	* @param response HttpServletResponse
	* @param name ��Ű�� �̸�
	* @param value ��Ű�� ��
	* @param expiry ��ȿ�� �ð�(��)
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
	 * ������ Ű���� ���� ��Ű�� ���� ���� �´�
	 * @param request HttpServletRequest
	 * @param cookieName ������ ��Ű�� �̸�
	 * @return ��Ű�� ��
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
	 * �Ķ���� ���ڿ��� �Ľ��Ͽ� ��ƼƼ�� ��ȯ�Ѵ�.
	 * @param param �Ķ���� ���ڿ�
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
	 * ��ƼƼ�� ���� �Ķ���� �������� �����Ͽ� ��ȯ�Ѵ�.
	 * @param param �Ķ���� ��ƼƼ
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
	 * ���ڿ��� utf-8 ĳ���� ������ url encoding �Ѵ�
	 * @param value ���ڵ��� ���ڿ�
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String value) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(value, "utf-8");
	}
	
	/**
	 * ���ڿ��� ĳ���� ������ url encoding �Ѵ�
	 * @param value ���ڵ��� ���ڿ�
	 * @param charset ���ڵ� ĳ���� ��
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String value, String charset) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(value, charset);
	}
	
	/**
	 * ���ڿ��� utf-8 ĳ���� ������ url decoding �Ѵ�
	 * @param value ���ڵ��� ���ڿ�
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String value) throws UnsupportedEncodingException
	{
		return URLDecoder.decode(value, "utf-8");
	}
	
	/**
	 * ���ڿ��� ĳ���� ������ url decoding �Ѵ�
	 * @param value ���ڵ��� ���ڿ�
	 * @param charset ���ڵ� ĳ���� ��
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String value, String charset) throws UnsupportedEncodingException
	{
		return URLDecoder.decode(value, charset);
	}
	
	/**
	 * HttpServletRequest�� ContentType�� multipart/form-data ���� Ȯ��
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
