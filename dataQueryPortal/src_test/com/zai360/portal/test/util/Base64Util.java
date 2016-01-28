package com.zai360.portal.test.util;
import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64工具类 
 * @author Laughing_Lz
 * @date 2016年1月27日
 */
public class Base64Util {  
    public final static String ENCODING = "UTF-8";  
  
    // 加密  
    public static String encoded(String data) throws UnsupportedEncodingException {  
        byte[] b = Base64.encodeBase64URLSafe(data.getBytes(ENCODING)); 
        return new String(b, ENCODING);  
    }  
  
  
    // 解密  
    public static String decode(String data) throws UnsupportedEncodingException {  
        byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));  
        return new String(b, ENCODING);  
    }  
}   