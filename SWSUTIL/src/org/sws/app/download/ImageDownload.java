package org.sws.app.download;

import java.io.File;
import java.util.ArrayList;

import org.sws.util.common.FileUtil;
import org.sws.util.common.StringUtil;
import org.sws.util.http.HttpUtil;

public class ImageDownload {

	public static void main(String args[]) {
		if(args.length < 3 || args[0].equalsIgnoreCase("help")) {
			System.out.println("회차, 페이지URL, 저장 폴더를 순서대로 입력하세요.");
			return;
		}
		ImageDownload.getOnepiec(args[0], args[1], args.length > 2 ? args[2]:null);
	}
	
	public static void getOnepiec(String posting_num, String srcUrl, String save_path) {
		String abs_path = save_path;
		if(abs_path == null || abs_path.trim().equals("")) {
			abs_path = new File("").getAbsolutePath();
		}
		String temp_path = abs_path + File.separatorChar + posting_num;
		new File(temp_path).mkdirs();
		
		try {
			String contents = HttpUtil.getContents(srcUrl);
			ArrayList<String> list = StringUtil.getWrappedText(contents, "data-orig-src=\"", "\"");
			list.addAll(StringUtil.getWrappedText(contents, "<img src=\"", "\""));
			int num = 1;
			String ext = "";
			String save_full_Name = "";
			ArrayList<String> file_list = new ArrayList<String>();
			String old_url = "";
			for(String url : list) {
				if(url.equals(old_url) || !(url.toLowerCase().startsWith("http"))
						 || !(url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".gif") || url.toLowerCase().endsWith(".png"))) {
					continue;
				}
				System.out.println(url);
				ext = url.substring(url.lastIndexOf("."));
				save_full_Name = temp_path + File.separatorChar + posting_num + "_" + (num<100?"0":"") + (num<10?"0":"") + num++ + ext;
				if(file_list.contains(save_full_Name)) {
					continue;
				}
				if(HttpUtil.getHttpFile(url, save_full_Name)) {
					file_list.add(save_full_Name);
					old_url = url;
				}
			}
			String[] files = new String[file_list.size()];
			file_list.toArray(files);
			System.out.println(abs_path + File.separatorChar + "원피스 "+posting_num+"권.zip");
			FileUtil.compress(files, abs_path + File.separatorChar + "원피스 "+posting_num+"권.zip");
			for(String s : files) {
				new File(s).delete();
			}
			new File(temp_path).delete();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
