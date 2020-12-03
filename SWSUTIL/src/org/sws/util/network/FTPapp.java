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
 * FTP ���ε� ��ƿ
 * @author ago
 *
 */
public class FTPapp
{

	static Logger logger = Logger.getLogger(FTPapp.class);
	
	/**
	 * ���� ������ �����ϰ� ���ӵ� Ŀ�ؼ��� �����Ѵ�.
	 * @param remoteIp ���� IP
	 * @param port ���� ��Ʈ
	 * @param userId ����� ���̵�
	 * @param password ����� �н�����
	 * @param encoding ���ڵ� ĳ���� ��
	 * @param defTimeout default Ÿ�Ӿƿ�
	 * @param soTimeout 
	 * @return FTP Ŀ�ؼ� Ŭ���̾�Ʈ
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
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//�̹������� ������
		
		return ftpClient;
	}
	
	/**
	 * ���� ���� ���丮 �� ��� ������ ���� ���丮�� �ٿ�ε� �޴´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localPath ���� ������ ���丮
	 * @param remoteFile ���� ���� ���� ���� ���
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
	 * BINARY_FILE_TYPE���� ���� ���� ������ ���� ���丮�� �ٿ�ε� �޴´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localPath ���� ������ ���丮
	 * @return
	 * @throws IOException
	 */
	public void downloadFile(FTPClient ftpClient, String localPath, String remoteFile) throws IOException
	{
		getFile(ftpClient, localPath, remoteFile, FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * ���� ���� ������ ���� ���丮�� �ٿ�ε� �޴´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localPath ���� ������ ���丮
	 * @param remoteFile ���� ���� ���� ���� ���
	 * @return
	 * @throws IOException
	 */
	public void downloadFile(FTPClient ftpClient, String localPath, String remoteFile, int fileType) throws IOException
	{
		getFile(ftpClient, localPath, remoteFile, fileType);
	}
	
	/**
	 * BINARY_FILE_TYPE���� ���� ���� ������ ���� ���丮�� �ٿ�ε� �޴´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localPath ���� ������ ���丮
	 * @param remoteFile ���� ���� ���� ���� ���
	 * @return
	 * @throws IOException
	 */
	public void getFile(FTPClient ftpClient, String localPath, String remoteFile) throws IOException
	{
		getFile(ftpClient, localPath, remoteFile, FTP.BINARY_FILE_TYPE);
	}
	
	/**
	 * ���� ���� ������ ���� ���丮�� �ٿ�ε� �޴´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localPath ���� ������ ���丮
	 * @param remoteFile ���� ���� ���� ���� ���
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
	 * BINARY_FILE_TYPE���� ���� ������ ������ �����ϰ� ������ ���ε� �Ѵ�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localFile ���� ���� ���� ���
	 * @param remoteFile ���� ������ ���� ���丮 ���� ���� ���
	 * @param remotePath ���� ���� �� �⺻ ���丮(���� ���丮)
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath) throws IOException
	{
		return putFile(ftpClient, localFile, remoteFile, remotePath, FTP.BINARY_FILE_TYPE);
	}

	/**
	 * ���� ������ ������ �����ϰ� ������ ���ε� �Ѵ�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localFile ���� ���� ���� ���
	 * @param remoteFile ���� ������ ���� ���丮 ���� ���� ���
	 * @param remotePath ���� ���� �� �⺻ ���丮(���� ���丮)
	 * @param fileType FTP file type
	 * @return
	 * @throws IOException
	 */
	public boolean uploadFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath, int fileType) throws IOException
	{
		return putFile(ftpClient, localFile, remoteFile, remotePath, fileType);
	}

	/**
	 * BINARY_FILE_TYPE���� ���� ������ ������ �����ϰ� ������ ���ε� �Ѵ�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localFile ���� ���� ���� ���
	 * @param remoteFile ���� ������ ���� ���丮 ���� ���� ���
	 * @return
	 * @throws IOException
	 */
	public boolean putFile(FTPClient ftpClient, String localFile, String remoteFile, String remotePath) throws IOException
	{
		return putFile(ftpClient, localFile, remoteFile, remotePath, FTP.BINARY_FILE_TYPE);
	}

	/**
	 * ���� ������ ������ �����ϰ� ������ ���ε� �Ѵ�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param localFile ���� ���� ���� ���
	 * @param remoteFile ���� ������ ���� ���丮 ���� ���� ���
	 * @param remotePath ���� ���� �� �⺻ ���丮(���� ���丮)
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
	 * �α׾ƿ� �� ������ ���´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @throws IOException
	 */
	public void close(FTPClient ftpClient) throws IOException
	{
		disconnect(ftpClient);
	}
	
	/**
	 * �α׾ƿ� �� ������ ���´�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
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
	 * ���� ������ ������ �����Ѵ�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param remoteFile ������ ���� ���� �� ����
	 * @return
	 * @throws IOException
	 */
	public boolean removeFile(FTPClient ftpClient, String remoteFile) throws IOException
	{
		return deleteFile(ftpClient, remoteFile);
	}
	
	/**
	 * ���� ������ ������ �����Ѵ�.
	 * @param ftpClient FTP Ŀ�ؼ� Ŭ���̾�Ʈ
	 * @param remoteFile ������ ���� ���� �� ����
	 * @return
	 * @throws IOException
	 */
	public boolean deleteFile(FTPClient ftpClient, String remoteFile) throws IOException
	{
		return ftpClient.deleteFile(remoteFile);
	}
}

