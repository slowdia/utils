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
	 * �Էµ� ���ڿ� inputstream�� ���ڵ��Ͽ� outputstream�� �Է��Ѵ�.
	 * @param inputstream ���ڵ� �� inputstream
	 * @param outputstream ���ڵ��� ���ڿ��� ����� outputstream
	 * @param lineLength �ٳ����� ���� ����
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
			if(lineLength != 0 && lineLength <= i) {	// �������� ���������� ���ִ� ��..
				outputstream.write(13);
				outputstream.write(10);
				i = 0;
			}
		}
	}

	/**
	 * �Էµ� ����Ʈ�迭 ���ڿ��� ���ڵ��Ͽ� ����Ʈ�迭�� ��ȯ�Ѵ�.
	 * @param src ���ڵ� ��
	 * @return ���ڵ� ��
	 * @throws IOException
	 */
	public static byte[] encode(byte src[]) throws IOException
	{
		return encode(src, 0);
	}

	/**
	 * �Էµ� ����Ʈ�迭 ���ڿ��� ���ڵ��Ͽ� ����Ʈ�迭�� ��ȯ�Ѵ�.
	 * @param src ���ڵ� ��
	 * @return ���ڵ� ��
	 * @param lineLength �ٳ����� ���� ����
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
	 * �Էµ� ���ڵ� ���� ���ڵ��Ͽ� ��ȯ�Ѵ�.
	 * @param data ���ڵ� ���ڿ�
	 * @return ���ڵ� ���ڿ�
	 */
	public static String encode(String src) throws IOException
	{
		return encode(src, 0);
	}

	/**
	 * �Էµ� ���ڵ� ���� ���ڵ��Ͽ� ��ȯ�Ѵ�.
	 * @param data ���ڵ� ���ڿ�
	 * @param lineLength �ٳ����� ���� ����
	 * @return ���ڵ� ���ڿ�
	 */
	public static String encode(String src, int lineLength) throws IOException
	{
		byte[] encData = null;
		encData = encode(src.getBytes(), lineLength);
		return new String(encData);
	}

	/**
	 * �Էµ� ���ڵ� ���� ���ڵ��Ͽ� ��ȯ�Ѵ�.
	 * @param src ���ڵ� �� ���ڿ�
	 * @param charset ���ڿ��� ����Ʈ�迭�� ��Ȱ�Ҷ� ����� ĳ���� ��
	 * @return ���ڵ��� ���ڿ�
	 */
	public static String encode(String src, String charset) throws IOException
	{
		return encode(src, charset, 0);
	}

	/**
	 * �Էµ� ���ڵ� ���� ���ڵ��Ͽ� ��ȯ�Ѵ�.
	 * @param src ���ڵ� �� ���ڿ�
	 * @param charset ���ڿ��� ����Ʈ�迭�� ��Ȱ�Ҷ� ����� ĳ���� ��
	 * @param lineLength �ٳ����� ���� ����
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
	 * 3����Ʈ ���ڸ� 4����Ʈ ���ڵ��� ����Ʈ �迭�� ��ȯ�Ͽ� �Է�<br>
	 * �� �ϳ��� ���ڵ��Ѵ�.
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
	 * base64 ����Ʈ ���� ����<br>
	 * �ٳ����̳� ��ĭ ������ ���ִ°���
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
	 * 4����Ʈ ���ڵ� ����Ʈ �迭�� 3����Ʈ ���ڵ� ����Ʈ �迭�� ��ȯ�ϰ� ��ȯ�� ����� ���̸� ����<br>
	 * �� �ϳ��� ���ڵ��Ѵ�.
	 * @param src
	 * @param dest
	 * @return ���ڵ��� �迭 ����
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
	 * �Էµ� ��Ʈ���� ���ڵ� �Ͽ� ��� ��Ʈ������ �����͸� ������.
	 * @param inputstream ���ڵ��� ���
	 * @param outputstream ���ڵ� ����� ��ȯ�� ���
	 * @throws IOException ���ڵ��� �������� ��쿡 ���ܸ� ��ȯ
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
	 * base64 ���ڵ� ����Ʈ �迭�� ���ڵ��Ͽ� ����Ʈ �迭�� ��ȯ�Ѵ�.
	 * @param src base64 ���ڵ� �� ����Ʈ �迭
	 * @return ���ڵ� �� ����Ʈ �迭
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
	 * �Էµ� ������ ��Ʈ���� ����Ʈ �迭�� ��ȯ�Ͽ� base64 ���ڵ��� �� �� ĳ���� ���� �����Ͽ� ��Ʈ������ ��ȯ�Ѵ�.
	 * @param src base64 ���ڵ��� ���ڿ�
	 * @param charset ������ String�� ����� ���ڼ�
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
	 * �Էµ� ������ ��Ʈ���� ����Ʈ �迭�� ��ȯ�Ͽ� base64 ���ڵ��� �� �� ĳ���� ���� �����Ͽ� ��Ʈ������ ��ȯ�Ѵ�.
	 * @param data base64���ڵ��� ���ڿ�
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
