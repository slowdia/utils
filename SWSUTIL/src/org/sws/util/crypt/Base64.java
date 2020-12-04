package org.sws.util.crypt;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Base64 Encoding/Decoding utillity
 * @author ago
 *
 */
public class Base64
{
	
	/**
	 * 입력된 문자열 inputstream을 인코딩하여 outputstream에 입력한다.
	 * @param inputstream 인코딩 할 inputstream
	 * @param outputstream 인코딩된 문자열을 출력할 outputstream
	 * @param lineLength 줄내림이 들어가는 길이
	 * @throws IOException
	 */
	public static void encode(InputStream inputstream, OutputStream outputstream, int lineLength)
		throws IOException
	{
		byte src[] = new byte[3];
		byte desc[] = new byte[4];
		int i = 0;

		while(inputstream.available() != 0) {
			int j = inputstream.read(src);
			encodeBlock(src, j, desc);
			outputstream.write(desc);
			i += 4;
			if(lineLength != 0 && lineLength <= i) {	// 뉴라인을 강제적으로 해주는 것..
				outputstream.write(13);
				outputstream.write(10);
				i = 0;
			}
		}
	}

	/**
	 * 입력된 바이트배열 문자열을 인코드하여 바이트배열로 반환한다.
	 * @param src 디코딩 값
	 * @return 인코딩 값
	 * @throws IOException
	 */
	public static byte[] encode(byte src[]) throws IOException
	{
		return encode(src, 0);
	}

	/**
	 * 입력된 바이트배열 문자열을 인코드하여 바이트배열로 반환한다.
	 * @param src 디코딩 값
	 * @return 인코딩 값
	 * @param lineLength 줄내림이 들어가는 길이
	 * @throws IOException
	 */
	public static byte[] encode(byte src[], int lineLength) throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		encode(((InputStream)(new ByteArrayInputStream(src))), ((OutputStream)(bos)), lineLength);
		bos.close();
		return bos.toByteArray();
	}

	/**
	 * 입력된 디코딩 값을 인코딩하여 반환한다.
	 * @param data 디코딩 문자열
	 * @return 인코딩 문자열
	 */
	public static String encode(String src) throws IOException
	{
		return encode(src, 0);
	}

	/**
	 * 입력된 디코딩 값을 인코딩하여 반환한다.
	 * @param data 디코딩 문자열
	 * @param lineLength 줄내림이 들어가는 길이
	 * @return 인코딩 문자열
	 */
	public static String encode(String src, int lineLength) throws IOException
	{
		byte[] encData = null;
		encData = encode(src.getBytes(), lineLength);
		return new String(encData);
	}

	/**
	 * 입력된 디코딩 값을 인코딩하여 반환한다.
	 * @param src 디코딩 할 문자열
	 * @param charset 문자열을 바이트배열로 변활할때 사용할 캐릭터 셋
	 * @return 인코딩된 문자열
	 */
	public static String encode(String src, String charset) throws IOException
	{
		return encode(src, charset, 0);
	}

	/**
	 * 입력된 디코딩 값을 인코딩하여 반환한다.
	 * @param src 디코딩 할 문자열
	 * @param charset 문자열을 바이트배열로 변활할때 사용할 캐릭터 셋
	 * @param lineLength 줄내림이 들어가는 길이
	 * @return
	 * @throws IOException
	 */
	public static String encode(String src, String charset, int lineLength) throws IOException
	{
		byte[] encData = null;
		String retVal = null;
		encData = encode(src.getBytes(charset), lineLength);
		retVal = new String(encData);
		return retVal;
	}

	/**
	 * 3바이트 문자를 4바이트 인코딩된 바이트 배열로 변환하여 입력<br>
	 * 블럭 하나를 인코딩한다.
	 * @param src
	 * @param i
	 * @param desc
	 */
	private static final void encodeBlock(byte src[], int i, byte desc[])
	{
		if(desc == null) {
			desc = new byte[4];
		}

		for(int j = 3; i < j; ) {
			src[--j] = 0;
		}

		desc[0] = (byte)((src[0] & 0xfc) >>> 2);
		desc[1] = (byte)((src[0] & 3) << 4 | (src[1] & 0xf0) >>> 4);
		desc[2] = (byte)((src[1] & 0xf) << 2 | (src[2] & 0xc0) >>> 6);
		desc[3] = (byte)(src[2] & 0x3f);

		for(int k = 0; k < 4; k++) {
			if(desc[k] < 26) {
				desc[k] = (byte)(desc[k] + 65);
			}
			else if(desc[k] < 52) {
				desc[k] = (byte)(desc[k] + 71);
			}
			else if(desc[k] < 62) {
				desc[k] = (byte)(desc[k] - 4);
			}
			else if(desc[k] == 62) {
				desc[k] = 43;
			}
			else if(desc[k] == 63) {
				desc[k] = 47;
			}
		}

		if(i < 3) {
			desc[3] = 61;
		}

		if(i < 2) {
			desc[2] = 61;
		}
	}

	/**
	 * base64 바이트 문자 검증<br>
	 * 줄내림이나 빈칸 같은거 없애는거임
	 * @param byte0
	 * @return
	 */
	private static boolean isDecodDomain(byte byte0)
	{
		if(byte0 > 122) {
			return false;
		}
		if(byte0 == 43) {
			return true;
		}
		if(byte0 < 47) {
			return false;
		}
		if(byte0 <= 57) {
			return true;
		}
		if(byte0 == 61) {
			return true;
		}
		if(byte0 < 65) {
			return false;
		}
		if(byte0 <= 90) {
			return true;
		}
		if(byte0 < 97) {
			return false;
		}

		return byte0 <= 122;
	}

	/**
	 * 4바이트 인코딩 바이트 배열을 3바이트 디코드 바이트 배열로 변환하고 변환된 결과의 길이를 리턴<br>
	 * 블럭 하나를 디코딩한다.
	 * @param src
	 * @param dest
	 * @return 디코딩된 배열 길이
	 */
	private static final int decodeBlock(byte src[], byte dest[])
	{
		byte byte0 = 3;
		int ai[] = new int[4];
		boolean flag = false;

		for(int i = 0; i < 4; i++) {
			byte byte1 = src[i];
			if(byte1 == 61) {
				byte0 = ((byte)(i != 2 ? 2 : 1));
				break;
			}
			if(byte1 > 96) {
				ai[i] = byte1 - 71;
			}
			else if(byte1 > 64) {
				ai[i] = byte1 - 65;
			}
			else if(byte1 > 47) {
				ai[i] = byte1 + 4;
			}
			else if(byte1 == 43) {
				ai[i] = 62;
			}
			else if(byte1 == 47) {
				ai[i] = 63;
			}
		}

		dest[0] = (byte)((ai[0] & 0xff) << 2 | (ai[1] & 0xff) >> 4);
		dest[1] = (byte)((ai[1] & 0xff) << 4 | (ai[2] & 0xff) >> 2);
		dest[2] = (byte)((ai[2] & 0xff) << 6 | ai[3] & 0xff);

		return byte0;
	}
	
	/**
	 * 입력된 스트림을 디코드 하여 출력 스트림으로 데이터를 전달함.
	 * @param inputstream 디코드할 대상
	 * @param outputstream 디코드 출력을 반환할 대상
	 * @throws IOException 디코딩에 실패했을 경우에 예외를 반환
	 */
	public static void decode(InputStream inputstream, OutputStream outputstream)
		throws IOException
	{
		byte src[] = new byte[4];
		byte desc[] = new byte[3];

		while(inputstream.available() != 0) {
			int i = 0;
			while(inputstream.available() != 0) {
				byte byte0 = (byte)inputstream.read();
				if(!isDecodDomain(byte0)) {
					continue;
				}
				src[i] = byte0;
				if(++i == 4) {
					break;
				}
			}

			if(i != 4 && i != 0) {
				throw new IOException("Base64 decoding fail : There is an error in inputstream : Please check the length.");
			}

			if(i != 0) {
				int j = decodeBlock(src, desc);
				outputstream.write(desc, 0, j);
			}
		}
	}

	/**
	 * base64 인코드 바이트 배열을 디코드하여 바이트 배열로 반환한다.
	 * @param src base64 인코드 된 바이트 배열
	 * @return 디코드 된 바이트 배열
	 * @throws IOException
	 */
	public static byte[] decode(byte src[]) throws IOException
	{
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		decode(((InputStream)(new ByteArrayInputStream(src))), ((OutputStream)(bytearrayoutputstream)));
		bytearrayoutputstream.close();
		return bytearrayoutputstream.toByteArray();
	}

	/**
	 * 입력된 데이터 스트링을 바이트 배열로 변환하여 base64 디코딩을 한 후 캐릭터 셋을 적용하여 스트링으로 변환한다.
	 * @param src base64 인코딩된 문자열
	 * @param charset 생성될 String에 적용될 문자셋
	 * @return
	 */
	public static String decode(String src, String charset)
	{
		byte[] decData = null;
		String retVal = null;
		try {
			decData = decode(src.getBytes());
			retVal = new String(decData, charset);
		}
		catch(IOException ex) {
			retVal = src;
		}
		return retVal;
	}

	/**
	 * 입력된 데이터 스트링을 바이트 배열로 변환하여 base64 디코딩을 한 후 캐릭터 셋을 적용하여 스트링으로 변환한다.
	 * @param data base64인코딩된 문자열
	 * @return
	 */
	public static String decode(String src)
	{
		byte[] decData = null;
		String retVal = null;
		try {
			decData = decode(src.getBytes());
			retVal = new String(decData);
		}
		catch(IOException ex) {
			retVal = src;
		}
		return retVal;
	}

}
