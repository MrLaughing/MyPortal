package com.zai360.portal.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


/**
 * String工具类
 * 
 * @author admin
 * 
 */
public class StringUtil {

	/**
	 * 字符串不为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotEmpty(String str){
		return ((str != null) && (str.trim().length() > 0));
	}
	
	/**
	 * 字符串为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.trim().length() == 0));
	}
	
	/**
	 * 生成随机字符串
	 * @param length 生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) {
	    String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer(); 
	    for (int i = 0; i < length; i++) { 
	        int number = random.nextInt(base.length()); 
	        sb.append(base.charAt(number)); 
	    } 
	    return sb.toString(); 
	 }
	
	public static void logResult(String sWord) {
    	String fileName = "/usr/local/tomcat/logs/wxpay.txt";
        FileWriter writer = null;
        try {
        	writer = new FileWriter(fileName,true);
            writer.write(sWord);
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
