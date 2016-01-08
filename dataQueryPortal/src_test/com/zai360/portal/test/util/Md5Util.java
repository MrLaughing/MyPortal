package com.zai360.portal.test.util;

import com.ndktools.javamd5.Mademd5;

public class Md5Util {
	/**
	 * toMd5 转为32位小写
	 * @param str
	 * @return
	 */
	public static String toMd5(String str){
		Mademd5 mademd5 =new Mademd5();
		return 	mademd5.toMd5(str).toLowerCase();
	}
}
