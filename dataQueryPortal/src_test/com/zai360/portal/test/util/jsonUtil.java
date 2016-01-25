package com.zai360.portal.test.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 常量
 * @author SuperStar
 *
 */
public class jsonUtil {
	//防止表单重复提交
	public static final String TOKEN_SERVER = "bjsxt_server_token";
	public static final String TOKEN_CLIENT = "bjsxt_client_token";
	//表单注入
	public static final String FORM_2_BEAN = "form_2_bean";
	//数据字典常量
	public static final String DICTIONARY_MAP_LIST = "DICTIONARY_MAP_LIST";
	public static final String DICTIONARY_MAP = "DICTIONARY_MAP";

	//返回AJAX
	public static final String STRUTS_RESULT_AJAX = "ajax";
	public static final String STRUTS_RESULT_UPDATE = "update";
	//设置消息状态
	public static final String LOGIN_TYPE_LOGIN = "1";
	public static final String LOGIN_TYPE_LOGOUT = "2";
	public static final String LOGIN_TYPE_UPDATEPWD = "3";
	public static final String LOGIN_TYPE_ACCESS = "4";
	//随机授权码
	public static String authorizationCode="未生成授权码";
	/**
	 * 将字符串转成输入流
	 * @param inputStream
	 * @param info
	 */
	public static InputStream string2stream(String info) {
		InputStream inputStream = null;
		try {
			if (info == null) {
				//当字符串为空时，默认设置为空
				info = "";
			}
			inputStream = new ByteArrayInputStream(info.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	/**
	 * 生成n位的随机字符串
	 * 生成授权码
	 * @param n 位
	 * @return
	 */
	public static String generateRandomCode(int n){
//		String randomCode=RandomStringUtils.randomAlphanumeric(n);
//		authorizationCode=randomCode;
//		return randomCode;
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random=new Random();
	    StringBuffer sb=new StringBuffer();
	    for(int i=0;i<n;i++){
	      int number=random.nextInt(62);
	      sb.append(str.charAt(number));
	    }
	    authorizationCode=sb.toString();
	    return sb.toString();
	}

	/**
	 * 生成汉字
	 * @throws Exception
	 */
	public static String generateHz(int count) {

		String str = "";
		for (int i = 0; i < count; i++) {
			int hightPos, lowPos; // 定义高低位

			Random random = new Random();

			hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值

			lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值

			byte[] b = new byte[2];

			b[0] = (new Integer(hightPos).byteValue());

			b[1] = (new Integer(lowPos).byteValue());

			try {
				//转成中文
				str += new String(b, "GBk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 随机生成一个出生日期
	 * @return
	 */
	public static Date generateBirthday() {
		Random random = new Random();
		int year = 65 + random.nextInt(50);
		int month = random.nextInt(12);
		int date = random.nextInt(28);
		return new Date(year, month, date);
	}

	/**
	 * 在package对应的路径下找到所有的class
	 * 
	 * @param packageName
	 *            package名称
	 * @param filePath
	 *            package对应的路径
	 * @param recursive
	 *            是否查找子package
	 * @param clazzs
	 *            找到class以后存放的集合
	 */
	public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class> clazzs) {
		//获取文件的路径
		File dir = new File(filePath);
		//判断文件路径是否存在
		if (!dir.exists() || !dir.isDirectory()) {
			//路径有误（11/19修改）	
			LoggerFactory.getLogger(jsonUtil.class).error("文件路径不存在" + filePath);
			return;
		}
		//在给定的目录下找到所有的文件，并且进行条件过滤
		File[] dirFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				// 接受dir目录
				boolean acceptDir = recursive && file.isDirectory();
				// 接受class文件
				boolean acceptClass = file.getName().endsWith("class");
				// 如果他是一个文件夹或者他是一个class结尾的文件，就返回
				return acceptDir || acceptClass;
			}
		});
		//开始遍历文件夹
		for (File file : dirFiles) {
			//如果是一个文件夹
			if (file.isDirectory()) {
				//迭代查找
				findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
			} else {
				//如果是一个以class结尾的文件
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					//然后通过类加载器加载这个类
					clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
