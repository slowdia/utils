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
	 * ���� �� ��� ���ϰ� ���� ������ �����Ѵ�.
	 * @param dir ������ ������ ����
	 * @return ���丮 �� ��� ������ �����Ͽ����� true, �������� ���Ͽ��ų� �Ϻθ� �����Ͽ����� false
	 */
	public static boolean clean(String dir) throws Exception
	{
		return clean(new File(dir), true);
	}
	
	/**
	 * ���� �� ��� ���ϰ� ���� ������ �����Ѵ�.
	 * @param dir ������ ������ ����
	 * @return ���丮 �� ��� ������ �����Ͽ����� true, �������� ���Ͽ��ų� �Ϻθ� �����Ͽ����� false
	 */
	public static boolean clean(File dir) throws Exception
	{
		return clean(dir, true);
	}
	
	/**
	 * ���� �� ��� ������ �����Ѵ�.
	 * @param dir ������ ������ ����
	 * @param incSubDir ���� ���͸� ���� ���� ����
	 * @return ���丮 �� ��� ������ �����Ͽ����� true, �������� ���Ͽ��ų� �Ϻθ� �����Ͽ����� false
	 */
	public static boolean clean(String dir, boolean incSubDir) throws Exception
	{
		return clean(new File(dir), incSubDir);
	}
	
	/**
	 * ���� �� ��� ������ �����Ѵ�.
	 * @param dir ������ ������ ����
	 * @param incSubDir ���� ���͸� ���� ���� ����
	 * @return ���丮 �� ��� ������ �����Ͽ����� true, �������� ���Ͽ��ų� �Ϻθ� �����Ͽ����� false
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
					if(clean(files[i], incSubDir)) { // ���� ���丮 �� ���� ����
						b = false;
					}
					if(files[i].delete()) { // ���丮 ����
						b = false;
					}
				}
			}else {
				if(files[i].delete()) { // ���� ����
					b = false;
				}
			}
		}
		
		return b;
	}

	/**
	 * ���丮 �� ���ϸ���Ʈ�� �����Ͽ� �����Ѵ�.
	 * @param srcDir ���� ������ �ִ� ���丮
	 * @param targetFile Ÿ�� ���ϸ�
	 * @throws Exception
	 */
	public static void compress(File srcDir, String targetFile) throws Exception
	{
		compress(srcDir, targetFile, true);
	}

	/**
	 * ���丮 �� ���ϸ���Ʈ�� �����Ͽ� �����Ѵ�.
	 * @param srcDir ���� ������ �ִ� ���丮
	 * @param targetFile Ÿ�� ���ϸ�
	 * @param incSubDir ���� ���͸� ���� ����
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
	 * ���ϸ���Ʈ�� �����Ͽ� �����Ѵ�.
	 * @param files ���� ���� ����Ʈ
	 * @param targetFile Ÿ�� ���ϸ�
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
	 * ���ϸ���Ʈ�� �����Ͽ� �����Ѵ�.
	 * @param files ���� ���� ����Ʈ
	 * @param targetFile Ÿ�� ���ϸ�
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
	 * ���� ������ ���ϸ� �ڿ� _bak�� �ٿ��� ����Ѵ�.<br>
	 * ��� ������ ���� �� ��� _0~ ���ڸ� �߰��� ���δ�.
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
	 * ������ ���� �Ѵ�.
	 * @param pathName ���� �� ���� ���� ���� ���
	 * @param newPathName ���� �� ������ ���� ���
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
	 * ���� ����
	 * @param zipData ���� ���� ������ ����Ʈ �迭
	 * @param targetDir ���� ���� Ÿ�� ����
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
	 * ���� ����
	 * @param zipData ���� ���� ������ ����Ʈ �迭
	 * @param targetDir ���� ���� Ÿ�� ����
	 * @return
	 * @throws Exception
	 */
	public static void extract(String zipFile, String targetDir) throws Exception
	{
		extract(new File(zipFile), targetDir);
	}

	/**
	 * ���� ����
	 * @param zipData ���� ���� ������ ����Ʈ �迭
	 * @param targetDir ���� ���� Ÿ�� ����
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
	 * ���� ��ο� ���Ե� ��� ��θ� �����Ѵ�.<br>
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
	 * ��ο� Ȯ���ڸ� ������ ���� ���� �̸� ����
	 * @param file
	 * @return
	 */
	public static String getBaseName(String file) {
		String name = getName(file);
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(0, dotPos) : file;
	}
	
	/**
	 * ��ο� Ȯ���ڸ� ������ ���� ���� �̸� ����
	 * @param file
	 * @return
	 */
	public static String getBaseName(File file) {
		String name = file.getName();
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(0, dotPos) : name;
	}
	
	/**
	 * ������ Ȯ���� ����
	 * @param file
	 * @return
	 */
	public static String getExtension(String file) {
		String name = getName(file);
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(dotPos+1) : "";
	}
	
	/**
	 * ������ Ȯ���� ����
	 * @param file
	 * @return
	 */
	public static String getExtension(File file) {
		String name = file.getName();
		int dotPos = name.lastIndexOf('.');
		return dotPos > -1 ? name.substring(dotPos+1) : "";
	}
	
	/**
	 * ��θ� ������ ���� ���� �����Ѵ�.
	 * @param file Ȯ���� ���� ���ϸ�
	 * @return
	 */
	public static String getName(String file) {
		int i = file.lastIndexOf(File.separator);
		return (i >= 0 ? file.substring(i + 1) : file);
	}
	
	/**
	 * ������ ���Ե� ���丮 ��θ� �����Ѵ�.
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
	 * ���� ����� ��´�.
	 * @param file
	 * @return
	 */
	public static int getSize(File file)
	{
		return (new Long(file.length())).intValue();
	}
	
	/**
	 * ���� ����� TB/GB/MB/KB/Bytes ������ ǥ���Ͽ� ��ȯ
	 * @param file
	 * @param decimalPoint �Ҽ��� ǥ�� �ڸ��� - �ִ� 3
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
	 * ���� ����� bytes ������� ��ȯ�Ѵ�.
	 * @param srcSize ���� ���� ������ (ex> 2TB/3GB/120MB)
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
	 * path�� �������� ���θ� Ȯ���Ѵ�.
	 *
	 * @param path ������ ���
	 * @return ���������� ����
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
	 * path�� �������� ���θ� Ȯ���Ѵ�.
	 *
	 * @param path ������ ���
	 * @return ���丮������ ����
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
	 * ���� �� ���� ���� ���� ���� ����Ʈ ��ȸ
	 * @param srcDir �ҽ� ����
	 * @return
	 */
	public static String[] listFiles(File srcDir) {
		return listFiles(srcDir, true);
	}
	
	/**
	 * ���� �� ���� ����Ʈ ��ȸ
	 * @param srcDir �ҽ� ����
	 * @param incSubDir ���� ���� ���� ����
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
	 * ������ �о� String ���� ��ȯ�Ѵ�.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(String file) throws Exception
	{
		return read(new File(file), null);
	}
	
	/**
	 * ������ �о� String ���� ��ȯ�Ѵ�.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(File file) throws Exception
	{
		return read(file, null);
	}
	
	/**
	 * ������ �о� String ���� ��ȯ�Ѵ�.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String read(String file, String charset) throws Exception
	{
		return read(new File(file), charset);
	}
	
	/**
	 * ������ �о� String ���� ��ȯ�Ѵ�.
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
	 * ������ �о� ����Ʈ �迭�� ��ȯ�Ѵ�.
	 * @param file ���� ����
	 * @return
	 * @throws Exception
	 */
	public static byte[] readToByte(String file) throws Exception
	{
		return readToByte(new File(file));
	}
	
	/**
	 * ������ �о� ����Ʈ �迭�� ��ȯ�Ѵ�.
	 * @param file ���� ����
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
	 * �����͸� ���Ϸ� ����.
	 * @param file ���� ��
	 * @param data ���� ����
	 * @throws Exception
	 */
	public static void write(String file, String data) throws Exception
	{
		write(new File(file), data.getBytes());
	}
	
	/**
	 * �����͸� ���Ϸ� ����.
	 * @param file ���� ��
	 * @param data ���� ����
	 * @param charset ĳ���� ��
	 * @throws Exception
	 */
	public static void write(String file, String data, String charset) throws Exception
	{
		write(new File(file), data.getBytes(charset));
	}
	
	/**
	 * �����͸� ���Ϸ� ����.
	 * @param file ���� ��
	 * @param data ���� ����
	 * @throws Exception
	 */
	public static void write(String file, byte[] data) throws Exception
	{
		write(new File(file), data);
	}
	
	/**
	 * �����͸� ���Ϸ� ����.
	 * @param file ���� ��
	 * @param data ���� ����
	 * @throws Exception
	 */
	public static void write(File file, String data) throws Exception
	{
		write(file, data.getBytes());
	}
	
	/**
	 * �����͸� ���Ϸ� ����.
	 * @param file ���� ��
	 * @param data ���� ����
	 * @param charset ĳ���� ��
	 * @throws Exception
	 */
	public static void write(File file, String data, String charset) throws Exception
	{
		write(file, data.getBytes(charset));
	}
	
	/**
	 * �����͸� ���Ϸ� ����.
	 * @param file ���� ��
	 * @param data ���� ����
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
