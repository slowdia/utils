package org.sws.util.common;

public class CharsetUtil {
	
	/**
	 * ���ڿ��� UTF-8 BOM(Byte Order Mark) ���� �� ���ڿ� ��ȯ
	 * @param src �ҽ� ���ڿ�
	 * @return ������ ���ڿ�
	 */
	public static String removeBom(String src) {
		String line = src;
		
		try {
			byte[] bom = new byte[]{(byte) 0xEF, (byte)0xBB, (byte)0xBF};
			byte[] lineByte = src.getBytes("UTF-8");
			if(lineByte[0] == bom[0] && lineByte[1] == bom[1] && lineByte[2] == bom[2]){
				line = new String(lineByte, 3, lineByte.length - 3, "UTF-8");
			}
			if(line.startsWith(new String(bom))){
				line = StringUtil.replace(line, new String(bom), "");
			}
		} catch (Exception e) {}
		
		return line;
	}
	
}
