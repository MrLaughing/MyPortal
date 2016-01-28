package com.zai360.portal.test.interceptor;

public class ErrorCode {
	/**************incorrect**************/
	public static String PARAM_INCORRECT_PAGESIZE="0001";//每页条数参数错误
	public static String PARAM_INCORRECT_PAGENUMBER="0002";//页码参数错误
	public static String PARAM_INCORRECT_INDEX="0003";//查询第某条记录参数错误
	public static String PARAM_INCORRECT_OTHER="0004";//查询参数错误
	/**************miss*****************/
	public static String PARAM_MISS_PAGESIZE="1001";//每页条数参数缺失
	public static String PARAM_MISS_PAGENUMBER="1002";//页码参数缺失
	public static String PARAM_MISS_INDEX="1003";//查询第某条记录参数缺失
	public static String PARAM_MISS_OTHER="1004";//查询参数缺失
	/*************empty*****************/
	public static String PARAM_EMPTY_TABLE="2001";//查询表不存在
	public static String PARAM_EMPTY_PARAMETER="2002";//查询表中不包含参数列
	/*************unsupport*****************/
	public static String URL_UNSUPPORT_ENCODE="3001";//不支持的编码格式
}
