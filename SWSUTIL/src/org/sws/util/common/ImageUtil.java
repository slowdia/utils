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
	 * �ҽ� url�� ĸ���Ͽ� �̹����� �����Ѵ�.
	 * @param srcUrl �ҽ� url
	 * @param start_x ĸ�� ���� x��ǥ
	 * @param start_y ĸ�� ���� y��ǥ
	 * @param width ĸ�� ����
	 * @param height ĸ�� ����
	 * @param destFile ���� ���ϸ�
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
	 * �ҽ� url �̹����� �ٿ�ε��Ͽ� ������ ���� �̸����� �����Ѵ�.
	 * @param srcUrl �ҽ� url
	 * @param destDir ���� ����
	 * @throws IOException
	 */
	public static void download(String srcUrl, String destDir) throws IOException
	{
		download(srcUrl, destDir, FileUtil.getName(srcUrl));
	}
	
	/**
	 * �ҽ� url �̹����� �ٿ�ε��Ͽ� ������ ���� �̸����� �����Ѵ�.
	 * @param srcUrl �ҽ� url
	 * @param destDir ���� ����
	 * @param fileName ���� ���� ��
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
	 * �̹��� ũ�⸦ �����Ѵ�.
	 * @param srcFile �ҽ� ���� ��
	 * @param destFile ���� �� ���� ��
	 * @param nWidth ���� ���� ũ��
	 * @param nHight ���� ���� ũ��
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
	 * �𼭸��� ���� ������ ĥ�Ѵ�.
	 * @param srcFile �ҽ� ���� ��
	 * @param color ����
	 * @param nBorder �� ����
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
	 * �𼭸��� ���� ������ �ձ۰� ó�� �Ѵ�.
	 * @param srcFile �ҽ� ���� ��
	 * @param color ����
	 * @param nBorder �� ����
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
	 * �𼭸��� ������ �ִ´�.
	 * @param srcFile �ҽ� ���� ��
	 * @param color ����
	 * @param nBorder �� ����
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
