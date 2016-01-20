package com.zai360.portal.test.interceptor;

import java.util.Map;

/**
 * URL和请求参数
 * @author Laughing_Lz
 * @date 2016年1月13日
 */
public class RequestInfo {
	/******request请求信息*******/
	private String host;//请求地址IP
	private int port;//请求地址端口
	private String method;//请求方式 GET/POST
	private String url1;//请求路径
	private String url2;//请求二级路径（表名）
	/*******请求参数*********/
	private String pageSize;//每页条数
	private String pageNumber;//当前页码
	private String pageIndex;//起始记录数
	private String totalNumber;//总条数
	private String totalPage;//总页数
//	/********可选请求参数********/
//	private String datetime_min;//时间段min
//	private String datetime_max;//时间段max
//	private String datetime_begin;//时间起点
//	private String sycode;//基站code
//	private String order_status;//订单状态
//	private String source_type;//终端机型
//	private String phoneno;//手机号
//	private String deposit_type;//收入类型
//	private String depositcredit;//收入金额
//	private String couponcode;//优惠券码
//	private String recyclemancode;//再生侠code
//	private String refereecode;//地推人员code
	private Map<String,Object> parameters;//包含？后的请求参数
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl1() {
		return url1;
	}
	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	public String getUrl2() {
		return url2;
	}
	public void setUrl2(String url2) {
		this.url2 = url2;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(String totalNumber) {
		this.totalNumber = totalNumber;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
}