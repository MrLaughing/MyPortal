package com.zai360.portal.test.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
/**
 * 对url解密并返回参数map集合
 * @author Laughing_Lz
 * @date 2016年1月27日
 */
public class UrlUtil {
	public static Map<String,String[]> decodeurl(HttpServletRequest request) throws UnsupportedEncodingException{
		Map<String,String[]> newparametersmap=new HashMap<String,String[]>();//返回解密后的请求参数 
		String url = Base64Util.decode(request.getQueryString());//只能获取get方式提交的参数
		/*对url路径进行字符串切割重新组合成请求参数，写入request*/
		String[] queryparams=url.split("&");
		for(int i=0;i<queryparams.length;i++){
			String key= queryparams[i].substring(0, queryparams[i].indexOf("="));
			String[] value = queryparams[i].substring(queryparams[i].indexOf("=")+1, queryparams[i].length()).split(",");
			newparametersmap.put(key, value);
		}
		return newparametersmap;
	}
}
