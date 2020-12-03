package org.sws.util.common;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.SwingUtilities;
import javax.swing.text.html.HTMLEditorKit;

public class ImageUtil {
	
	/**
	 * 소스 url을 캡쳐하여 이미지로 저장한다.
	 * @param srcUrl 소스 url
	 * @param start_x 캡쳐 시작 x좌표
	 * @param start_y 캡쳐 시작 y좌표
	 * @param width 캡쳐 넓이
	 * @param height 캡쳐 높이
	 * @param destFile 저장 파일명
	 * @throws IOException
	 */
	public static void capture(String srcUrl, int start_x, int start_y, int width, int height, String destFile) throws IOException
	{
		JEditorPane pane = new JEditorPane();
		HTMLEditorKit kit = new HTMLEditorKit();
		pane.setEditorKit(kit);
		pane.setEditable(false);
		pane.setMargin(new Insets(0,0,0,0));
		pane.setPage(srcUrl);
		BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufImage.createGraphics();
		Container container = new Container();
		SwingUtilities.paintComponent(graphics, pane, container, start_x, start_y, width, height);
		graphics.dispose();
		ImageIO.write(bufImage, FileUtil.getExtension(destFile), new File(FileUtil.getAbsolutePath(destFile)));
	}
	
	/**
	 * 소스 url 이미지를 다운로드하여 원본과 동일 이름으로 저장한다.
	 * @param srcUrl 소스 url
	 * @param destDir 저장 폴더
	 * @throws IOException
	 */
	public static void download(String srcUrl, String destDir) throws IOException
	{
		download(srcUrl, destDir, FileUtil.getName(srcUrl));
	}
	
	/**
	 * 소스 url 이미지를 다운로드하여 원본과 동일 이름으로 저장한다.
	 * @param srcUrl 소스 url
	 * @param destDir 저장 폴더
	 * @param fileName 저장 파일 명
	 * @throws IOException
	 */
	public static void download(String srcUrl, String destDir, String fileName) throws IOException
	{
		URL url = new URL(srcUrl);
		BufferedImage bufImage = ImageIO.read(url);
		File file = new File(FileUtil.getAbsolutePath(destDir), FileUtil.getName(fileName));
		ImageIO.write(bufImage, FileUtil.getExtension(srcUrl), file);
	}

	/**
	 * 이미지 크기를 변경한다.
	 * @param srcFile 소스 파일 명
	 * @param destFile 저장 될 파일 명
	 * @param nWidth 변경 가로 크기
	 * @param nHight 변경 세로 크기
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void resizeImage(String srcFile, String destFile, int nWidth, int nHight) throws IOException, InterruptedException
	{
		ImageIcon imgIcon = new ImageIcon(srcFile);
		Image imgSource = imgIcon.getImage();		
		if(nHight == 0 ) nHight = imgIcon.getIconHeight();
		if(nWidth == 0 ) nWidth = imgIcon.getIconWidth();
		Image imgTarget = imgSource.getScaledInstance(nWidth, nHight, Image.SCALE_SMOOTH); 
		int pixels[] = new int[nWidth * nHight]; 
		PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, nWidth, nHight, pixels, 0, nWidth); 
		pg.grabPixels(); 
		BufferedImage bi = new BufferedImage(nWidth, nHight, BufferedImage.TYPE_INT_RGB); 
		bi.setRGB(0, 0, nWidth, nHight, pixels, 0, nWidth); 
		ImageIO.write(bi, FileUtil.getExtension(srcFile), new File(FileUtil.getAbsolutePath(destFile))); 
	}
	
	/**
	 * 모서리를 지정 색으로 칠한다.
	 * @param srcFile 소스 파일 명
	 * @param color 색상
	 * @param nBorder 선 굵기
	 * @throws IOException
	 */
	public static void rectImage(String srcFile, Color color, int nBorder) throws IOException
	{
		File file = new File(srcFile);
		BufferedImage image = ImageIO.read(file);
		int height = image.getHeight();
		int width = image.getWidth();
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.gray);
		graphics.setColor(color);
		for(int i = 0; i < nBorder; i++){
			graphics.drawRect(0+i, 0+i, width-((i*2)+1), height-((i*2)+1));
		}
		ImageIO.write(image, FileUtil.getExtension(srcFile), file);
	}

	/**
	 * 모서리를 지정 색으로 둥글게 처리 한다.
	 * @param srcFile 소스 파일 명
	 * @param color 색상
	 * @param nBorder 선 굵기
	 * @throws IOException
	 */
	public static void roundRectImage(String srcFile, Color color, int nBorder) throws IOException
	{
		File file = new File(srcFile);
		BufferedImage image = ImageIO.read(file);
		int height = image.getHeight();
		int width = image.getWidth();
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.gray);
		graphics.setColor(color);
		for(int i = 0; i < nBorder; i++){
			graphics.drawRoundRect(0+i, 0+i, width-((i*2)+1), height-((i*2)+1), width/10, height/10);
		}
		ImageIO.write(image, FileUtil.getExtension(srcFile), file);
	}
	
	/**
	 * 모서리에 라인을 넣는다.
	 * @param srcFile 소스 파일 명
	 * @param color 색상
	 * @param nBorder 선 굵기
	 * @throws IOException
	 */
	public static void rectLineImage(String srcFile, Color color, int nBorder) throws IOException
	{
		File file = new File(srcFile);
		BufferedImage image = ImageIO.read(file);
		int height = image.getHeight();
		int width = image.getWidth();
		Graphics2D graphics = image.createGraphics();
		for(int i = 0; i < nBorder; i++){
			graphics.setColor(color);
			graphics.drawRect(0+i, 0+i, width-((i*2)+1), height-((i*2)+1));
			graphics.setColor(Color.WHITE);
			graphics.drawRect(0+i, 0+i, width-((i*2)+1), height-((i*2)+1));
			graphics.setColor(color);
			graphics.drawRect(0+i, 0+i, width-((i*2)+1), height-((i*2)+1));
		}
		ImageIO.write(image, FileUtil.getExtension(srcFile), file);
	}	

}
