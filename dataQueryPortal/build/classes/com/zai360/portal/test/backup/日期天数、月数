package com.zai360.portal.test.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
	/**
	 * 处理日期相差月数
	 * 转换日期起始为当月1号
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
	    SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM");
	    Date currentdate= myFormatter.parse(myFormatter.format(new Date()));
	    Date registerdate= myFormatter.parse("2015-10-20");
	    System.out.println(myFormatter.format(new Date()));
	    long  day=(currentdate.getTime()-registerdate.getTime())/(24*60*60*1000);
	    System.out.println(day);
	    long month=day/30;
	    System.out.println(month);
	    SimpleDateFormat myFormatter2 = new SimpleDateFormat("yyyy-MM-dd");
	    String data3=myFormatter2.format(registerdate);
	    System.out.println(data3);
	}
}
