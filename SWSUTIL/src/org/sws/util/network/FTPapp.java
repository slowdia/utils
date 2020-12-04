package org.sws.util.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.StringTokenizer;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.sws.util.common.FileUtil;

/**
 * FTP 업로드 유틸
 * @author ago
 *
 */
public class FTPapp
{

	static Logger logger = Logger.getLogger(FTPapp.class);
	
	/**
	 * 원격 서버에 접속하고 접속된 커넥션을 리턴한다.
	 * @param remoteIp 원격 IP
	 * @param port 접속 포트
	 * @param userId 사용자 아이디
	 * @param password 사용자 패스워드
	 * @param encoding 인코딩 캐릭터 셋
	 * @param defTimeout default 타임아웃
	 * @param soTimeout 
	 * @return FTP 커넥션 클라이언트
	 * @throws SocketException
	 * @throws IOException
	 */
	public FTPClient getConnection(String remoteIp, int port, String userId, String password, String charset, int defTimeout, int soTimeout)
			throws SocketException, IOException
	{
		FTPClient ftpClient = new FTPClient();
		ftpClient.setControlEncoding(charset);
		ftpClient.setDefaultPort(port);
		ftpClient.connect(remoteIp);

		if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
			ftpClient.disconnect();
			throw new SocketException("ftpClient.disconnect()");
		}

		ftpClient.login(userId, password);
		ftpClient.setDefaultTimeout(defTimeout);
		ftpClient.setSoTimeout(soTimeout);
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//이미지깨짐 방지위
		
		return ftpClient;
	}
	
	/**
	 * 원격 서버 디렉토리 내 모든 파일을 로컬 디렉토리에 다운로드 받는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localPath 로컬 저장할 디렉토리
	 * @param remoteFile 원격 서버 파일 절대 경로
	 * @return
	 * @throws IOException
	 */
	public void downloadFiles(FTPClient ftpClient, String localPath, String remotePath, int fileType) throws IOException
	{
		while(remotePath.endsWith("/") || remotePath.endsWith("\\")) {
			remotePath = remotePath.substring(0, remotePath.length()-1);
		}
		ftpClient.changeWorkingDirectory(remotePath);
		String[] files = ftpClient.listNames();
		for(String file : files) {
			getFile(ftpClient, localPath, remotePath + "/" + file, fileType);
		}
	}
	
	/**
	 * BINARY_FILE_TYPE으로 원격 서버 파일을 로컬 디렉토리에 다운로드 받는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localPath 로컬 저장할 디렉토리
	 * @return
	 * @throws IOException
	 */
	public void downloadFile(FTPClient ftpClient, String localPath, String remoteFile) throws IOException
	{
		getFile(ftpClient, localPath, remoteFile, FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * 원격 서버 파일을 로컬 디렉토리에 다운로드 받는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localPath 로컬 저장할 디렉토리
	 * @param remoteFile 원격 서버 파일 절대 경로
	 * @return
	 * @throws IOException
	 */
	public void downloadFile(FTPClient ftpClient, String localPath, String remoteFile, int fileType) throws IOException
	{
		getFile(ftpClient, localPath, remoteFile, fileType);
	}
	
	/**
	 * BINARY_FILE_TYPE으로 원격 서버 파일을 로컬 디렉토리에 다운로드 받는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localPath 로컬 저장할 디렉토리
	 * @param remoteFile 원격 서버 파일 절대 경로
	 * @return
	 * @throws IOException
	 */
	public void getFile(FTPClient ftpClient, String localPath, String remoteFile) throws IOException
	{
		getFile(ftpClient, localPath, remoteFile, FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * 원격 서버 파일을 로컬 디렉토리에 다운로드 받는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localPath 로컬 저장할 디렉토리
	 * @param remoteFile 원격 서버 파일 절대 경로
	 * @param fileType FTP file type
	 * @return
	 * @throws IOException
	 */
	public void getFile(FTPClient ftpClient, String localPath, String remoteFile, int fileType) throws IOException
	{
		ftpClient.setFileType(fileType);
		FileOutputStream outputstream = new FileOutputStream(new File(localPath + FileUtil.getName(remoteFile)));
		ftpClient.retrieveFile(remoteFile, outputstream);
		outputstream.close();
	}

	/**
	 * BINARY_FILE_TYPE으로 원격 서버에 폴더를 생성하고 파일을 업로드 한다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localFile 로컬 파일 절대 경로
	 * @param remoteFile 원격 폴더내 하위 디렉토리 포함 파일 경로
	 * @param remotePath 원격 서버 내 기본 디렉토리(업무 디렉토리)
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath) throws IOException
	{
		return putFile(ftpClient, localFile, remoteFile, remotePath, FTP.BINARY_FILE_TYPE);
	}

	/**
	 * 원격 서버에 폴더를 생성하고 파일을 업로드 한다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localFile 로컬 파일 절대 경로
	 * @param remoteFile 원격 폴더내 하위 디렉토리 포함 파일 경로
	 * @param remotePath 원격 서버 내 기본 디렉토리(업무 디렉토리)
	 * @param fileType FTP file type
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath, int fileType) throws IOException
	{
		return putFile(ftpClient, localFile, remoteFile, remotePath, fileType);
	}

	/**
	 * BINARY_FILE_TYPE으로 원격 서버에 폴더를 생성하고 파일을 업로드 한다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localFile 로컬 파일 절대 경로
	 * @param remoteFile 원격 폴더내 하위 디렉토리 포함 파일 경로
	 * @return
	 * @throws IOException
	 */
	public boolean putFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath) throws IOException
	{
		return putFile(ftpClient, localFile, remoteFile, remotePath, FTP.BINARY_FILE_TYPE);
	}

	/**
	 * 원격 서버에 폴더를 생성하고 파일을 업로드 한다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param localFile 로컬 파일 절대 경로
	 * @param remoteFile 원격 폴더내 하위 디렉토리 포함 파일 경로
	 * @param remotePath 원격 서버 내 기본 디렉토리(업무 디렉토리)
	 * @param fileType FTP file type
	 * @return
	 * @throws IOException
	 */
	public boolean putFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath, int fileType) throws IOException
	{
		ftpClient.setFileType(fileType);
		remoteFile = remoteFile.trim();
		localFile = localFile.trim();

		ftpClient.changeWorkingDirectory(remotePath); 
		remoteFile = remoteFile.toLowerCase().replaceAll(remotePath.toLowerCase(),"");
		String path = remoteFile.substring(remoteFile.indexOf("/"),remoteFile.lastIndexOf("/"));
		int tem_dir = 0;
		String p_temp = "";
		StringTokenizer st = new StringTokenizer(path,"/");
		String dir = "";
		while(st.hasMoreTokens()){
			dir = st.nextToken();
			if(dir.equals(".") || dir.equals("..")) {
				continue;
			}
			if(tem_dir == 0){
				p_temp = st.nextToken();
			}else{
				p_temp += "/"+st.nextToken();
			}
			ftpClient.makeDirectory(p_temp);
			tem_dir++;
		}

		return ftpClient.storeFile(remotePath.toLowerCase() + remoteFile, new FileInputStream(new File(localFile)));
	}
	
	/**
	 * 로그아웃 후 접속을 끊는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @throws IOException
	 */
	public void close(FTPClient ftpClient) throws IOException
	{
		disconnect(ftpClient);
	}
	
	/**
	 * 로그아웃 후 접속을 끊는다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @throws IOException
	 */
	public void disconnect(FTPClient ftpClient) throws IOException
	{
		if(ftpClient != null && ftpClient.isConnected()) {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			}catch(Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 원격 서버의 파일을 삭제한다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param remoteFile 삭제할 원격 서버 내 파일
	 * @return
	 * @throws IOException
	 */
	public boolean removeFile(FTPClient ftpClient, String remoteFile) throws IOException
	{
		return deleteFile(ftpClient, remoteFile);
	}
	
	/**
	 * 원격 서버의 파일을 삭제한다.
	 * @param ftpClient FTP 커넥션 클라이언트
	 * @param remoteFile 삭제할 원격 서버 내 파일
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(FTPClient ftpClient, String remoteFile) throws IOException
	{
		return ftpClient.deleteFile(remoteFile);
	}
}

