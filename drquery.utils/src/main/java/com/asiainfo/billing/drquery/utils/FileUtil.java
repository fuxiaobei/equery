package com.asiainfo.billing.drquery.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;

public class FileUtil {

	/**
	 * 读取文件
	 * @param path
	 * @return
	 */
	public static String readFile(String path){
		StringBuffer sb = new StringBuffer(); 
		try{
			File file = new File(path);
			//FileReader fr = new FileReader(file);
			FileInputStream in = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader (in,"UTF-8");
	        BufferedReader br = new BufferedReader(read);
	
	        String line = "";
	        
	        while((line = br.readLine()) != null){
	        	sb.append(line).append(System.getProperty("line.separator"));
	        }
		}catch(Exception e){
			throw new RuntimeException("读取文件：" + path + "失败！", e);
		}
		return sb.toString();
	}
	
	
	/**
	 * 写入文件
	 * @param filePath
	 * @param content
	 */
	public static void writeFile(String filePath, String content){
		try{
			BufferedReader br = new BufferedReader(new StringReader(content));
			File file = new File(filePath);
			FileOutputStream writerStream = new FileOutputStream(file); 
			OutputStreamWriter osw = new OutputStreamWriter(writerStream,"UTF-8");
			BufferedWriter output = new BufferedWriter(osw);
			if(!file.exists()){
				file.createNewFile();
			}
			String line = "";
			while((line = br.readLine()) != null){
				output.write(line + System.getProperty("line.separator"));
	        }
			
			output.flush();
			output.close();
		}catch(Exception e){
			throw new RuntimeException("写入文件："+ filePath +"失败！", e);
		}
	}
}
