package org.sws.util.http;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.sws.util.common.FileUtil;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

public class MultiPartUpload {

	public static int sm_iSeed = 0;
	private MultipartRequest multiPart = null;
	
	/**
	 * 파일을 업로드 한다.
	 * @param request HttpServletRequest
	 * @param saveDirectory 업로드 파일 저장 폴더
	 * @param maxPostSize 업로드 파일 최대 사이즈
	 * @throws Exception
	 */
	public MultiPartUpload(HttpServletRequest request
							, String saveDirectory
							, int maxPostSize
			) throws Exception
	{
		this(request, saveDirectory, maxPostSize, null, null);
	}
	
	/**
	 * 파일을 업로드 한다.
	 * @param request HttpServletRequest
	 * @param saveDirectory 업로드 파일 저장 폴더
	 * @param maxPostSize 업로드 파일 최대 사이즈
	 * @param encoding 파일 인코딩 캐릭터 셋
	 * @throws Exception
	 */
	public MultiPartUpload(HttpServletRequest request
							, String saveDirectory
							, int maxPostSize
							, String encoding
			) throws Exception
	{
		this(request, saveDirectory, maxPostSize, encoding, null);
	}
	
	/**
	 * 파일을 업로드 한다.
	 * @param request HttpServletRequest
	 * @param saveDirectory 업로드 파일 저장 폴더
	 * @param maxPostSize 업로드 파일 최대 사이즈
	 * @param allowExts 업로드 허용 확장자 - null 이면 체크 안함
	 * @throws Exception
	 */
	public MultiPartUpload(HttpServletRequest request
							, String saveDirectory
							, int maxPostSize
							, ArrayList<String> allowExts
			) throws Exception
	{
		this(request, saveDirectory, maxPostSize, null, allowExts);
	}
	
	/**
	 * 파일을 업로드 한다.
	 * @param request HttpServletRequest
	 * @param saveDirectory 업로드 파일 저장 폴더
	 * @param maxPostSize 업로드 파일 최대 사이즈
	 * @param encoding 파일 인코딩 캐릭터 셋
	 * @param allowExts 업로드 허용 확장자 - null 이면 체크 안함
	 * @throws Exception
	 */
	public MultiPartUpload(HttpServletRequest request
							, String saveDirectory
							, int maxPostSize
							, String encoding
							, ArrayList<String> allowExts
			) throws Exception
	{
    	saveDirectory = FileUtil.getAbsolutePath(saveDirectory);
		File dir = new File(saveDirectory);
		if(!dir.exists()) dir.mkdirs();
		
		multiPart = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new FileRenamePolicy() {
				public File rename(File f){
					// 업로드 폴더에 동일한 파일명의 파일이 있을 경우 index를 추가하여 파일명 변경하기
	  				try {
	  					int index = 1;
	  					File file = f;
	  					boolean isNew = true;
	  					while ( file.exists() ) {
	  						String fileName = file.getName();
	  						String ext = FileUtil.getExtension(fileName);
	  						String baseName = FileUtil.getBaseName(fileName);
	  						if (!isNew) {
	  							try {
	  		  						int pos = baseName.lastIndexOf("_");
		  							index = Integer.parseInt(baseName.substring(pos+1, baseName.length())) + 1;
		  							baseName = baseName.substring(0, pos);
	  							}catch(Exception e) {}
	  						}
	  						isNew = false;
	  						fileName = baseName + "_" + index;
	  						if(ext.length() > 0) {
	  							fileName += "." + ext;
	  						}
	  						file = new File(file.getParent(), fileName);
	  					}	
	  					return file;
	  				}
	  				catch (Exception e) {
	  					e.printStackTrace();
	  					return f;
	  				}
	  			}
	  		}
	  	);

		// 허용 확장자 검증
		if(allowExts != null) {
			boolean delete = false;
			File file = null;
			Enumeration<?> enums = getFileNames();
			while (enums.hasMoreElements()) {
				file = multiPart.getFile((String)enums.nextElement());
				if(allowExts.contains(FileUtil.getExtension(file))) {
					delete = true;
				}
			}
			// 허용되지 않은 확장자 포함된 경우 전체 파일 삭제
			if(delete) {
				enums = getFileNames();
				while (enums.hasMoreElements()) {
					multiPart.getFile((String)enums.nextElement()).delete();
				}
				throw new Exception("Contains files that are not allowed.");
			}
		}
	}
	
	/**
	 * Returns the names of all the parameters as an Enumeration of 
	 * Strings.  It returns an empty Enumeration if there are no parameters.
	 *
	 * @return the names of all the parameters as an Enumeration of Strings.
	 */
	public Enumeration<?> getParameterNames() {
	  return multiPart.getParameterNames();
	}

	/**
	 * Returns the names of all the uploaded files as an Enumeration of 
	 * Strings.  It returns an empty Enumeration if there are no file input 
	 * fields on the form.  Any file input field that's left empty will result 
	 * in a FilePart with null contents.  Each file name is the name specified
	 * by the form, not by the user.
	 *
	 * @return the names of all the uploaded files as an Enumeration of Strings.
	 */
	public Enumeration<?> getFileNames() {
	  return multiPart.getFileNames();
	}

	/**
	 * Returns the value of the named parameter as a String, or null if 
	 * the parameter was not sent or was sent without a value.  The value 
	 * is guaranteed to be in its normal, decoded form.  If the parameter 
	 * has multiple values, only the last one is returned (for backward 
	 * compatibility).  For parameters with multiple values, it's possible
	 * the last "value" may be null.
	 *
	 * @param name the parameter name.
	 * @return the parameter value.
	 */
	public String getParameter(String name) {
		return multiPart.getParameter(name);
	}

	/**
	 * Returns the values of the named parameter as a String array, or null if 
	 * the parameter was not sent.  The array has one entry for each parameter 
	 * field sent.  If any field was sent without a value that entry is stored 
	 * in the array as a null.  The values are guaranteed to be in their 
	 * normal, decoded form.  A single value is returned as a one-element array.
	 *
	 * @param name the parameter name.
	 * @return the parameter values.
	 */
	public String[] getParameterValues(String name) {
		return multiPart.getParameterValues(name);
	}

	/**
	 * Returns the filesystem name of the specified file, or null if the 
	 * file was not included in the upload.  A filesystem name is the name 
	 * specified by the user.  It is also the name under which the file is 
	 * actually saved.
	 *
	 * @param name the html page's file parameter name.
	 * @return the filesystem name of the file.
	 */
	public String getFilesystemName(String name) {
		return multiPart.getFilesystemName(name);
	}

	/**
	 * Returns the original filesystem name of the specified file (before any
	 * renaming policy was applied), or null if the file was not included in 
	 * the upload.  A filesystem name is the name specified by the user.
	 *
	 * @param name the html page's file parameter name.
	 * @return the original file name of the file.
	 */
	public String getOriginalFileName(String name) {
		return multiPart.getOriginalFileName(name);
	}

	/**
	 * Returns the content type of the specified file (as supplied by the 
	 * client browser), or null if the file was not included in the upload.
	 *
	 * @param name the html page's file parameter name.
	 * @return the content type of the file.
	 */
	public String getContentType(String name) {
		return multiPart.getContentType(name);
	}

	/**
	 * Returns a File object for the specified file saved on the server's 
	 * filesystem, or null if the file was not included in the upload.
	 *
	 * @param name the html page's file parameter name.
	 * @return a File object for the named file.
	 */
	public File getFile(String name) {
		return multiPart.getFile(name);
	}  
	
	/**
	 * Unique key 생성 로직
	 * DATETIME(yyyyMMddHHmmssSSS;17) + IncNum(000~999;3)
	 * @return
	 */
	synchronized public static String getUnikey()
	{
		Object[] objs = { new Integer((MultiPartUpload.sm_iSeed++) % 1000)};
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date()) + new MessageFormat("{0,number,000}").format(objs);
	}
	
}
