package org.sws.util.mp3;

import java.io.File;

import org.sws.util.common.Validate;

public class MP3 {

	public static void main(String args[]) {
		MP3.rename("D:/MP3/���/");
	}
	
	private static void rename(String dir) {
		
		removeUnderbar(dir);
		removePostNum(dir);
	}
	
	/**
	 * �ҽ����ϸ��� ���� ���ϸ����� ����, ���� ������ �ߺ��ϰ��� �ҽ������� ����
	 * @param f
	 * @param dest
	 */
	private static void rename(File src, File dest) {
		
		try {
			if(dest.exists()) {
				src.delete();
			}else {
				src.renameTo(dest);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * ���ϸ� ����ٳ� �ι����� ������ ���ֱ�
	 * @param dir
	 */
	private static void removeUnderbar(String dir) {
		File[] files = new File(dir).listFiles();
		
		for(File f : files) {
			String fileName = f.getName().replaceAll("_", " ").replace("+", " ").replaceAll("[", " ").replaceAll("]", " ").replaceAll("-", " - ");
			while(fileName.indexOf('+') > -1){
				fileName = fileName.replace('+', ' ');
			}
			while(fileName.indexOf("  ") > -1){
				fileName = fileName.replaceAll("  ", " ");
			}
			fileName = fileName.trim();
			rename(f, new File(dir + fileName));
		}
	}
	
	/**
	 * �տ� ���� ���ΰ� ���ֱ�
	 * @param dir
	 */
	private static void removePostNum(String dir) {
		File[] files = new File(dir).listFiles();
		
		for(File f : files) {
			String fileName = f.getName();
			int idx = 0;
			char c = fileName.charAt(idx++);
			StringBuffer strNum = new StringBuffer();
			while(Validate.isDigit(c+"")) {
				strNum.append(c);
				c = fileName.charAt(idx++);
			}
			try {
				if(strNum.length() > 0) {
					int num = Integer.parseInt(strNum.toString());
					if(num > 15) {
						fileName = fileName.replaceAll(strNum.toString(), "");
						while(fileName.startsWith(".")) {
							fileName = fileName.substring(1, fileName.length());
						}
						fileName = fileName.trim();
						rename(f, new File(dir + fileName));
					}
				}
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
