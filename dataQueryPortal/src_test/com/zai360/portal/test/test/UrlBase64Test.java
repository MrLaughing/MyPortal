package com.zai360.portal.test.test;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

 
/** 
 * 封装Base64的工具类 
 *  
 */  
class UrlBase64Coder {  
    public final static String ENCODING = "UTF-8";  
  
    // 加密  
    public static String encoded(String data) throws UnsupportedEncodingException {  
//        byte[] b = Base64.encodeBase64URLSafe(data.getBytes(ENCODING));  
        byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
        return new String(b, ENCODING);  
    }  
  
  
    // 解密  
    public static String decode(String data) throws UnsupportedEncodingException {  
        byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));  
        return new String(b, ENCODING);  
    }  
}  
  
/** 
 * 测试类 
 */  
public class UrlBase64Test {  
    public static void main(String[] args) throws UnsupportedEncodingException {  
//        String str = "username=100000&password=e10adc3949ba59abbe56e057f20f883e";  
//        String str = "create_date_min=2015-01-04&create_date_max=2016-01-21&pageSize=10&pageNumber=1";  
        String str = "username_type=100000,100081&pageSize=10&pageNumber=1";  
        // 加密该字符串  
        String encodedString = UrlBase64Coder.encoded(str);  
        System.out.println(encodedString);  
        // 解密该字符串  
        String decodedString = UrlBase64Coder.decode(encodedString);  
        System.out.println(decodedString);  
    }  
}  