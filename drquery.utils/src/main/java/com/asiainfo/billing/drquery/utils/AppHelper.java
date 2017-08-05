package com.asiainfo.billing.drquery.utils;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppHelper {

	private static Log Logger = LogFactory.getLog(AppHelper.class);

	private AppHelper() {
	}

	/**
	 * 得到web目录所在的绝对路径
	 * 
	 * @return String web目录所在的绝对路径 eg. E:/work/framework/WebRoot
	 */
	public static String getWebAppPath() {
		AppHelper util = new AppHelper();

		String osname = System.getProperty("os.name");
		String clazzFilePath = util.getClassFilePath();
		String webPath = null;
		
		if (clazzFilePath.indexOf("WEB-INF") != -1) {
			webPath = clazzFilePath.substring(0,
					clazzFilePath.indexOf("WEB-INF"));
		} 
		else if (clazzFilePath.indexOf("target") != -1) {
			webPath = clazzFilePath.substring(0,
					clazzFilePath.indexOf("target"));
		}

		if (webPath.indexOf("file:") != -1) {
			webPath = webPath.substring(webPath.indexOf("file:") + 6,
					webPath.length());
		}
		if ((osname.indexOf("ux") != -1) || (osname.indexOf("UX") != -1)) {
			webPath = "//" + webPath;
		}
		return webPath;

	}

	/**
	 * 获取classes文件的绝对路径
	 * 
	 * 
	 * @return String
	 */
	private String getClassFilePath() {
		String strClassName = getClass().getName();
		String strPackageName = "";
		if (getClass().getPackage() != null) {
			strPackageName = getClass().getPackage().getName();
		}
		String strClassFileName = "";
		if (!"".equals(strPackageName)) {
			strClassFileName = strClassName.substring(
					strPackageName.length() + 1, strClassName.length());
		} else {
			strClassFileName = strClassName;
		}
		URL url = null;
		url = getClass().getResource(strClassFileName + ".class");

		String strURL = url.getFile();
		try {
			strURL = java.net.URLDecoder.decode(strURL, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return strURL;
	}

	private String getClassPackageName() {
		String strPackageName = "";
		if (getClass().getPackage() != null) {
			strPackageName = getClass().getPackage().getName();
		}

		return strPackageName;
	}

	public static String getPackageName() {
		AppHelper util = new AppHelper();
		String strPackageName = util.getClassPackageName();
		return strPackageName;
	}

	public static String getSpringConfigFilePath() {
		String springConfigFilePath = getWebAppPath()
				+ "WEB-INF//classes//spring//applicationContext.xml";
		return springConfigFilePath;
	}

	public static String getSpringConfigFilePathForBuss() {
		String springConfigFilePath = getWebAppPath()
				+ "WEB-INF//classes//spring//applicationContext_osfc_service.xml";
		return springConfigFilePath;
	}

	public static String changISO(String data) {

		String s1 = "";
		try {
			byte[] temp = data.getBytes();
			s1 = new String(temp, "iso-8859-1");
		} catch (Exception e) {
		}
		return s1;
	}

	public static String changeGB2312(String data) {
		String s1 = "";

		try {

			byte[] temp = data.getBytes("ISO8859-1");
			s1 = new String(temp, "GB2312");
		} catch (Exception e) {
			Logger.error("catch change exception" + e);

		}
		return s1;
	}

	public static String changeGBK(String data) {
		String s1 = "";

		try {

			byte[] temp = data.getBytes("ISO8859-1");
			s1 = new String(temp, "GBK");
		} catch (Exception e) {
			Logger.error("catch change exception" + e);

		}
		return s1;
	}

}
