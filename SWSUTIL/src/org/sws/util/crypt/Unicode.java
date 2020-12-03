package org.sws.util.crypt;

public class Unicode {

	/**
	 * ���ڸ� �����ڵ�� ���ڵ��Ѵ�.
	 * @param	���ڿ�
	 * @return   ����� �����ڵ�
	 */
	public static String encode(String src)
	{
		StringBuffer sb = new StringBuffer();
		String temp = null;
		for( int i=0 ; i < src.length() ; i++){
			temp = Integer.toHexString( src.charAt(i) );
			sb.append("\\u").append((temp.length()==4 ? temp : "00" + temp));
		}
		return sb.toString();
	}

	/**
	 * �����ڵ带 ���ڷ� ���ڵ��Ѵ�.
	 * @param	�����ڵ�
	 * @return   ����� ���ڿ�
	 */
	public static String decode(String src)
	{
		StringBuffer sb = new StringBuffer();
		for( int i= src.indexOf("\\u"); i > -1; i = src.indexOf("\\u") ){
			sb.append(src.substring(0, i));
			sb.append(String.valueOf((char)Integer.parseInt(src.substring( i + 2, i + 6 ), 16)));
			src = src.substring(i + 6);
		}
		sb.append( src );
		return sb.toString();
	}
	
}
