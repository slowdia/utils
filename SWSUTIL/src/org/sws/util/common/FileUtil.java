package org.sws.util.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	
	/** 1 KB */
	public static final int ONE_KB = 1024;

	/** 1 MB. */
	public static final int ONE_MB = ONE_KB * ONE_KB;

	/** 1 GB */
	public static final int ONE_GB = ONE_KB * ONE_MB;

	/** 1 TB */
	public static final long ONE_TB = (long)ONE_KB * ONE_GB;
	
	/**
	 * 폴더 내 모든 파일과 하위 폴더을 삭제한다.
	 * @param dir 내용을 삭제할 폴더
	 * @return 디렉토리 내 모든 파일을 삭제하였으면 true, 삭제하지 못하였거나 일부만 삭제하였으면 false
	 */
	public static boolean clean(String dir) throws Exception
	{
		return clean(new File(dir), true);
	}
	
	/**
	 * 폴더 내 모든 파일과 하위 폴더을 삭제한다.
	 * @param dir 내용을 삭제할 폴더
	 * @return 디렉토리 내 모든 파일을 삭제하였으면 true, 삭제하지 못하였거나 일부만 삭제하였으면 false
	 */
	public static boolean clean(File dir) throws Exception
	{
		return clean(dir, true);
	}
	
	/**
	 * 폴더 내 모든 파일을 삭제한다.
	 * @param dir 내용을 삭제할 폴더
	 * @param incSubDir 하위 디렉터리 포함 삭제 여부
	 * @return 디렉토리 내 모든 파일을 삭제하였으면 true, 삭제하지 못하였거나 일부만 삭제하였으면 false
	 */
	public static boolean clean(String dir, boolean incSubDir) throws Exception
	{
		return clean(new File(dir), incSubDir);
	}
	
	/**
	 * 폴더 내 모든 파일을 삭제한다.
	 * @param dir 내용을 삭제할 폴더
	 * @param incSubDir 하위 디렉터리 포함 삭제 여부
	 * @return 디렉토리 내 모든 파일을 삭제하였으면 true, 삭제하지 못하였거나 일부만 삭제하였으면 false
	 */
	public static boolean clean(File dir, boolean incSubDir) throws Exception
	{
		if(!dir.isDirectory()) {
			throw new Exception("The path is not a directory.");
		}
		
		boolean b = true;
		File files[] = dir.listFiles();
		for (int i = 0 ; i < files.length ; i++) {
			if (files[i].isDirectory()) {
				if(incSubDir) {
					if(clean(files[i], incSubDir)) { // 서브 디렉토리 내 파일 삭제
						b = false;
					}
					if(files[i].delete()) { // 디렉토리 삭제
						b = false;
					}
				}
			}else {
				if(files[i].delete()) { // 파일 삭제
					b = false;
				}
			}
		}
		
		return b;
	}

	/**
	 * 디렉토리 내 파일리스트를 압축하여 저장한다.
	 * @param srcDir 원본 파일이 있는 디렉토리
	 * @param targetFile 타겟 파일명
	 * @throws Exception
	 */
	public static void compress(File srcDir, String targetFile) throws Exception
	{
		compress(srcDir, targetFile, true);
	}

	/**
	 * 디렉토리 내 파일리스트를 압축하여 저장한다.
	 * @param srcDir 원본 파일이 있는 디렉토리
	 * @param targetFile 타겟 파일명
	 * @param incSubDir 하위 디렉터리 포함 여부
	 * @throws Exception
	 */
	public static void compress(File srcDir, String targetFile, boolean incSubDir) throws Exception
	{
		byte[] buf = new byte[4096];
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));
		String[] files = listFiles(srcDir, incSubDir);
		for (int i=0; i<files.length; i++) {
			FileInputStream in = new FileInputStream(files[i]);
			
			String fileName = files[i].substring(srcDir.getAbsolutePath().length());
			if(fileName.startsWith(File.separator)) {
				fileName = srcDir.getName() + fileName;
			}else {
				fileName = srcDir.getName() + File.separator + fileName;
			}
			ZipEntry ze = new ZipEntry(fileName);
			out.putNextEntry(ze);
			  
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			  
			out.closeEntry();
			in.close();
		}
			  
		out.close();
	}
	
	/**
	 * 파일리스트를 압축하여 저장한다.
	 * @param files 원본 파일 리스트
	 * @param targetFile 타겟 파일명
	 * @throws Exception
	 */
	public static void compress(File[] files, String targetFile) throws Exception
	{
		byte[] buf = new byte[4096];

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));

		for (int i=0; i<files.length; i++) {
			FileInputStream in = new FileInputStream(files[i]);
			String fileName = files[i].getName();
					
			ZipEntry ze = new ZipEntry(fileName);
			out.putNextEntry(ze);
			  
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			  
			out.closeEntry();
			in.close();
		}
			  
		out.close();
	}
	
	/**
	 * 파일리스트를 압축하여 저장한다.
	 * @param files 원본 파일 리스트
	 * @param targetFile 타겟 파일명
	 * @throws Exception
	 */
	public static void compress(String[] files, String targetFile) throws Exception
	{
		byte[] buf = new byte[4096];

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(targetFile));

		for (int i=0; i<files.length; i++) {
			FileInputStream in = new FileInputStream(files[i]);
			String fileName = Paths.get(files[i]).getFileName().toString();
			ZipEntry ze = new ZipEntry(fileName);
			out.putNextEntry(ze);
			  
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			  
			out.closeEntry();
			in.close();
		}
			  
		out.close();
	}
	
	/**
	 * 동일 폴더에 파일명 뒤에 _bak를 붙여서 백업한다.<br>
	 * 백업 파일이 존재 할 경우 _0~ 숫자를 추가로 붙인다.
	 * @param pathName
	 * @throws IOException
	 */
	public static void backupFile(String pathName) throws IOException {
		if(!new File(pathName).isFile()) {
			throw new IOException("There is no file or it's not file. " + pathName);
		}
		int idx = 0;
		String backupPathName = pathName.substring(0, pathName.lastIndexOf(".")) + "_bak";
		String ext = pathName.substring(pathName.lastIndexOf("."));
		while(new File(backupPathName + ext).exists()) {
			backupPathName = backupPathName + "_" + idx++;
		}
		FileUtil.copyFile(pathName, backupPathName + ext);
	}
	
	/**
	 * 파일을 복사 한다.
	 * @param pathName 복사 할 원본 파일 절대 경로
	 * @param newPathName 복사 될 파일의 정대 경로
	 * @throws IOException
	 */
	public static void copyFile(String pathName, String newPathName) throws IOException {
		if(!new File(pathName).isFile()) {
			throw new IOException("There is no file. " + pathName);
		}
		FileInputStream fis = new FileInputStream(pathName);
		FileOutputStream fos = new FileOutputStream(newPathName);
	   
		int data = 0;
		while((data=fis.read())!=-1) {
			fos.write(data);
		}
		fis.close();
		fos.close();
	}
	
	/**
	 * 압축 해제
	 * @param zipData 압축 파일 데이터 바이트 배열
	 * @param targetDir 압축 해제 타겟 폴더
	 * @return
	 * @throws Exception
	 */
	public static void extract(byte[] zipData, String targetDir) throws Exception
	{
		FileOutputStream fos = null;

		ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipData));
		ZipEntry entry;

		while((entry = zis.getNextEntry()) != null) {

			try { Thread.sleep(100); } catch(InterruptedException e) {}

			File entryFile = new File(new StringBuffer(targetDir)
					.append(File.separator)
					.append(entry.getName().replace('/', File.separatorChar))
					.toString());

			if( entry.isDirectory() ) {
				if( !entryFile.exists() ) {
					entryFile.mkdirs();
				}
				continue;
			}else {
				if( entryFile.getParent() != null ) {
					File parent = new File(entryFile.getParent());
					if( !parent.exists() ) {
						parent.mkdirs();
					}
				}
			}
			
			if(entry.getMethod() == ZipEntry.STORED) {
			} else if(entry.getMethod() == ZipEntry.DEFLATED) {
			} else {
				continue;
			}

			fos = new FileOutputStream(entryFile);
			byte buf[] = new byte[1024];

			for( int cnt; (cnt = zis.read(buf)) != -1; ) {
				fos.write(buf, 0, cnt);
			}

			if( fos != null ) {
				fos.close();
				fos = null;
			}
		}
		if(zis != null) { zis.close(); zis = null; }
	}

	/**
	 * 압축 해제
	 * @param zipData 압축 파일 데이터 바이트 배열
	 * @param targetDir 압축 해제 타겟 폴더
	 * @return
	 * @throws Exception
	 */
	public static void extract(String zipFile, String targetDir) throws Exception
	{
		extract(new File(zipFile), targetDir);
	}

	/**
	 * 압축 해제
	 * @param zipData 압축 파일 데이터 바이트 배열
	 * @param targetDir 압축 해제 타겟 폴더
	 * @return
	 * @throws Exception
	 */
	public static void extract(File zipFile, String targetDir) throws Exception
	{
		if(targetDir.endsWith(File.separator))
			targetDir.substring(0, targetDir.length()-1);
		
		FileOutputStream fos = null;
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry entry;

		while((entry = zis.getNextEntry()) != null) {

			try { Thread.sleep(100); } catch(InterruptedException e) {}

			File entryFile = new File(new StringBuffer(targetDir)
					.append(File.separator)
					.append(entry.getName().replace('/', File.separatorChar))
					.toString());

			if( entry.isDirectory() ) {
				if( !entryFile.exists() ) {
					entryFile.mkdirs();
				}
				continue;
			}else {
				if( entryFile.getParent() != null ) {
					File parent = new File(entryFile.getParent());
					if( !parent.exists() ) {
						parent.mkdirs();
					}
				}
			}
			
			if(entry.getMethod() == ZipEntry.STORED) {
			} else if(entry.getMethod() == ZipEntry.DEFLATED) {
			} else {
				continue;
			}

			fos = new FileOutputStream(entryFile);
			byte buf[] = new byte[1024];

			for( int cnt; (cnt = zis.read(buf)) != -1; ) {
				fos.write(buf, 0, cnt);
			}

			if( fos != null ) {
				fos.close();
				fos = null;
			}
		}
		if(zis != null) { zis.close(); zis = null; }
	}
	
	/**
	 * 파일 경로에 포함된 상대 경로를 제거한다.<br>
	 * c:\downlad/../..\aaa.txt => c:\downlad\aaa.txt<br>
	 * /downlad/../..\aaa.txt => /downlad/aaa.txt
	 * @param file
	 * @return
	 */
	public static String getAbsolutePath(String file) {
		if(file.indexOf("/") > -1 || file.indexOf("\\") > -1) {
			file = StringUtil.replaceAll(file, "\\", File.separator);
			file = StringUtil.replaceAll(file, "/", File.separator);
		}
		for(;file.indexOf("."+File.separator) > -1;) {
			file = StringUtil.replaceAll(file, "."+File.separator, File.separator);
		}
		for(;file.indexOf(File.separator+File.separator) > -1;) {
			file = StringUtil.replaceAll(file, File.separator+File.separator, File.separator);
		}
		return file;
	}
	
	/**
	 * 경로와 확장자를 제외한 순수 파일 이름 추출
	 * @param file
	 * @return
	 */
	public static String getBaseName(String file) {
		String name = getName(file);
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(0, dotPos) : file;
	}
	
	/**
	 * 경로와 확장자를 제외한 순수 파일 이름 추출
	 * @param file
	 * @return
	 */
	public static String getBaseName(File file) {
		String name = file.getName();
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(0, dotPos) : name;
	}
	
	/**
	 * 파일의 확장자 추출
	 * @param file
	 * @return
	 */
	public static String getExtension(String file) {
		String name = getName(file);
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(dotPos+1) : "";
	}
	
	/**
	 * 파일의 확장자 추출
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		String name = file.getName();
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(dotPos+1) : "";
	}
	
	/**
	 * 경로를 제거한 파일 명만을 추출한다.
	 * @param file 확장자 포함 파일명
	 * @return
	 */
	public static String getName(String file) {
		int i = file.lastIndexOf(File.separator);
		return (i >= 0 ? file.substring(i + 1) : file);
	}
	
	/**
	 * 파일이 포함된 디렉토리 경로를 추출한다.
	 * @param file
	 * @return
	 */
	public static String getParent(String file) {
		for(;file.endsWith(File.separator);) {
			file = file.substring(0, file.length()-1);
		}
		int i = file.lastIndexOf(File.separator);
		return (i >= 0 ? file.substring(0, i) : "");
	}
	
	/**
	 * 파일 사이즈를 얻는다.
	 * @param file
	 * @return
	 */
	public static int getSize(File file)
	{
		return (new Long(file.length())).intValue();
	}
	
	/**
	 * 파일 사이즈를 TB/GB/MB/KB/Bytes 단위로 표현하여 반환
	 * @param file
	 * @param decimalPoint 소수점 표현 자리수 - 최대 3
	 * @return
	 */
	public static String getSizeFormat(File file, int decimalPoint)
	{
		int size = (new Long(file.length())).intValue();
		int decimal = 1;
		switch (decimalPoint) {
		case(0) :
			decimal = 1; break;
		case(1) :
			decimal = 10; break;
		case(2) :
			decimal = 100; break;
		case(3) :
			decimal = 1000; break;
		default :
			decimal = 1000;
		}
		String displaySize;
		long longSize = 0l;
		if ((new Float(size / ONE_TB)).intValue() > 0) {
			longSize = size * decimal / ONE_TB;
			size = new Float(longSize).intValue();
			if(decimalPoint > 0) {
				longSize = (long)size / decimal;
				displaySize = String.valueOf(longSize) + " TB";
			}else {
				displaySize = String.valueOf(size) + " TB";
			}
		}
		else if (size / ONE_GB > 0) {
			longSize = size * decimal / ONE_GB;
			size = new Float(longSize).intValue();
			if(decimalPoint > 0) {
				longSize = (long)size / decimal;
				displaySize = String.valueOf(longSize) + " GB";
			}else {
				displaySize = String.valueOf(size) + " GB";
			}
		}
		else if (size / ONE_MB > 0) {
			longSize = size * decimal / ONE_MB;
			size = new Float(longSize).intValue();
			if(decimalPoint > 0) {
				longSize = (long)size / decimal;
				displaySize = String.valueOf(longSize) + " MB";
			}else {
				displaySize = String.valueOf(size) + " MB";
			}
		}
		else if (size / ONE_KB > 0) {
			longSize = size * decimal / ONE_KB;
			size = new Float(longSize).intValue();
			if(decimalPoint > 0) {
				longSize = (long)size / decimal;
				displaySize = String.valueOf(longSize) + " KB";
			}else {
				displaySize = String.valueOf(size) + " KB";
			}
		}
		else {
			displaySize = String.valueOf(size) + " Bytes";
		}
		return displaySize;
	}
	
	/**
	 * 단위 사이즈를 bytes 사이즈로 변환한다.
	 * @param srcSize 단위 형식 사이즈 (ex> 2TB/3GB/120MB)
	 * @return
	 */
	public static long getSize(String srcSize)
	{
		long size = 0l;
		srcSize = StringUtil.replaceAll(srcSize.trim().toUpperCase(),"," ,"");
		
		if (srcSize.endsWith("T") || srcSize.endsWith("TB")) {
			size = ONE_TB * Integer.parseInt(srcSize.substring(0,srcSize.indexOf("T")).trim());
		}
		else if (srcSize.endsWith("G") || srcSize.endsWith("GB")) {
			size = ONE_GB * Integer.parseInt(srcSize.substring(0,srcSize.indexOf("G")).trim());
		}
		else if (srcSize.endsWith("M") || srcSize.endsWith("MB")) {
			size = ONE_MB * Integer.parseInt(srcSize.substring(0,srcSize.indexOf("M")).trim());
		}
		else if (srcSize.endsWith("K") || srcSize.endsWith("KB")) {
			size = ONE_KB * Integer.parseInt(srcSize.substring(0,srcSize.indexOf("K")).trim());
		}
		else {
			if(srcSize.indexOf("B") > 0) {
				srcSize = srcSize.substring(0,srcSize.indexOf("B")).trim();
			}
			size = Integer.parseInt(srcSize);
		}

		return size;
	}
	
	/**
	 * path가 파일인지 여부를 확인한다.
	 *
	 * @param path 파일의 경로
	 * @return 파일인지의 여부
	 */
	public static boolean isFile(String path) {

		boolean b = false;
		if (path != null) {
			File f = new File(path);
			b = f.isFile();
		}
		return b;
	}
	
	/**
	 * path가 폴더인지 여부를 확인한다.
	 *
	 * @param path 파일의 경로
	 * @return 디렉토리인지의 여부
	 */
	public static boolean isDirectory(String path) {

		boolean b = false;
		if (path != null) {
			File file = new File(path);
			b = file.isDirectory();
		}
		return b;
	}

	/**
	 * 폴더 내 하위 폴더 포함 파일 리스트 조회
	 * @param srcDir 소스 폴더
	 * @return
	 */
	public static String[] listFiles(File srcDir) {
		return listFiles(srcDir, true);
	}
	
	/**
	 * 폴더 내 파일 리스트 조회
	 * @param srcDir 소스 폴더
	 * @param incSubDir 하위 폴더 포함 여부
	 * @return
	 */
	public static String[] listFiles(File srcDir, boolean incSubDir) {
		ArrayList<String> fileList = new ArrayList<String>();
		File[] files = srcDir.listFiles();
		for(File f: files) {
			if(f.exists()) {
				if(f.isDirectory()) {
					String[] list = listFiles(f, incSubDir);
					for(String s:list) {
						fileList.add(s);
					}
				}else {
					fileList.add(f.getAbsolutePath());
				}
			}
		}
		
		String[] array = new String[fileList.size()];
		fileList.toArray(array);
		return array;
	}
	
	/**
	 * 파일을 읽어 String 으로 반환한다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(String file) throws Exception
	{
		return read(new File(file), null);
	}
	
	/**
	 * 파일을 읽어 String 으로 반환한다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(File file) throws Exception
	{
		return read(file, null);
	}
	
	/**
	 * 파일을 읽어 String 으로 반환한다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(String file, String charset) throws Exception
	{
		return read(new File(file), charset);
	}
	
	/**
	 * 파일을 읽어 String 으로 반환한다.
	 * @param file
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String read(File file, String charset) throws Exception
	{
		StringBuffer sb = new StringBuffer();
		FileInputStream in = null;
		BufferedReader br = null;
		try{
			in = new FileInputStream(file);

			if(charset == null || charset.equals("")) {
				br = new BufferedReader(new InputStreamReader(in));
			}else {
				br = new BufferedReader(new InputStreamReader(in, charset));
			}
			String line;
			while ( (line = br.readLine()) != null )  {
				sb.append(line + "\n");
			}
		}catch(Exception e){
			throw e;
		}finally{
			try{if(in != null) in.close();} catch(Exception ee){}
			try{if(br != null) br.close();} catch(Exception ee){}
		}

		return sb.toString();
	}
	
	/**
	 * 파일을 읽어 바이트 배열로 반환한다.
	 * @param file 읽을 파일
	 * @return
	 * @throws Exception
	 */
	public static byte[] readToByte(String file) throws Exception
	{
		return readToByte(new File(file));
	}
	
	/**
	 * 파일을 읽어 바이트 배열로 반환한다.
	 * @param file 읽을 파일
	 * @return
	 * @throws Exception
	 */
	public static byte[] readToByte(File file) throws Exception 
	{
		InputStream is = null;
		byte[] bytes = null;
		try{
			is = new FileInputStream(file);
			
			long length = file.length();
			if (length > Integer.MAX_VALUE) {
				throw new Exception("The file is too large to be read as a byte array. " + file.getAbsolutePath());
			}
			bytes = new byte[(int)length];
			
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0){
				offset += numRead;
			}
		
			if (offset < bytes.length){
				throw new IOException("Could not completely read file. " + file.getAbsolutePath());
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			try{if(is != null) is.close();}catch(Exception ee){}
		}
		return bytes;
	}
	
	/**
	 * 데이터를 파일로 쓴다.
	 * @param file 파일 명
	 * @param data 파일 내용
	 * @throws Exception
	 */
	public static void write(String file, String data) throws Exception
	{
		write(new File(file), data.getBytes());
	}
	
	/**
	 * 데이터를 파일로 쓴다.
	 * @param file 파일 명
	 * @param data 파일 내용
	 * @param charset 캐릭터 셋
	 * @throws Exception
	 */
	public static void write(String file, String data, String charset) throws Exception
	{
		write(new File(file), data.getBytes(charset));
	}
	
	/**
	 * 데이터를 파일로 쓴다.
	 * @param file 파일 명
	 * @param data 파일 내용
	 * @throws Exception
	 */
	public static void write(String file, byte[] data) throws Exception
	{
		write(new File(file), data);
	}
	
	/**
	 * 데이터를 파일로 쓴다.
	 * @param file 파일 명
	 * @param data 파일 내용
	 * @throws Exception
	 */
	public static void write(File file, String data) throws Exception
	{
		write(file, data.getBytes());
	}
	
	/**
	 * 데이터를 파일로 쓴다.
	 * @param file 파일 명
	 * @param data 파일 내용
	 * @param charset 캐릭터 셋
	 * @throws Exception
	 */
	public static void write(File file, String data, String charset) throws Exception
	{
		write(file, data.getBytes(charset));
	}
	
	/**
	 * 데이터를 파일로 쓴다.
	 * @param file 파일 명
	 * @param data 파일 내용
	 * @throws Exception
	 */
	public static void write(File file, byte[] data) throws Exception
	{
		FileOutputStream out = null;
		try{
			out = new FileOutputStream(file);
			out.write(data);
		}catch(Exception e){
			throw e;
		}finally{
			if(out != null) out.close();
		}
	}
	
	public static void main(String args[]) {
		try {
			//String fileName = Paths.get("D:/BACKUP/baba_sword.d2s").getFileName().toString();
			//System.out.println(fileName);
			//File f = new File("D:/BACKUP/baba_sword.d2s");
			//fileName = f.getName();
			//System.out.println(fileName);
			
			//FileUtil.compress(new File("D:/BACKUP/"), "D:/backup.zip");
			
			//System.out.println(FileUtil.getSizeFormat(new File("D:/backup.zip"), 1));
			
			//FileUtil.clean(new File("D:/BACKUP/"), true);
			

			System.out.println(FileUtil.getAbsolutePath("c:\\downlad/../..\\.......////.......\\\\aaa.txt"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
