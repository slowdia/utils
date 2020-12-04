package org.sws.solution.mp3;

import java.io.File;

import org.sws.util.common.Validate;

public class MP3 {

	public static void main(String args[]) {
		MP3.rename("D:/MP3/멜론/");
	}
	
	private static void rename(String dir) {
		
		removeUnderbar(dir);
		removePostNum(dir);
	}
	
	/**
	 * 소스파일명을 목적 파일명으로 변경, 목적 파일이 중복일경우는 소스파일을 제거
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
	 * 파일명에 언더바나 두번띄어쓰기 같은거 없애기
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
	 * 앞에 순번 붙인거 없애기
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
