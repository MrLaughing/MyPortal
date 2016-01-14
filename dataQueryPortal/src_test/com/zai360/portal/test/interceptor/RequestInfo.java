package com.zai360.portal.test.interceptor;

/**
 * URL和请求参数
 * @author Laughing_Lz
 * @date 2016年1月13日
 */
public class RequestInfo {
	/******request请求信息*******/
	private String host;//请求地址IP
	private int port;//请求地址端口
	private String requestMethod;//请求方式 GET/POST
	private String url1;//请求路径
	private String url2;//请求二级路径
	/*******固有请求参数*********/
	private String serialVersionUID;//唯一标识
	private String rows;//每页条数
	private String page;//当前页
	/********可选请求参数********/
	private String datetime_min;//时间段min
	private String datetime_max;//时间段max
	private String datetime_begin;//时间起点
	private String sycode;//基站code
	private String order_status;//订单状态
	private String source_type;//终端机型
	private String phoneno;//手机号
	private String deposit_type;//收入类型
	private String deposit_credit;//收入金额
	private String coupon_code;//优惠券码
	private String recycleman_code;//再生侠code
	private String refereecode;//地推人员code
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
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setSerialVersionUID(String serialVersionUID) {
		this.serialVersionUID = serialVersionUID;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getDatetime_min() {
		return datetime_min;
	}
	public void setDatetime_min(String datetime_min) {
		this.datetime_min = datetime_min;
	}
	public String getDatetime_max() {
		return datetime_max;
	}
	public void setDatetime_max(String datetime_max) {
		this.datetime_max = datetime_max;
	}
	public String getDatetime_begin() {
		return datetime_begin;
	}
	public void setDatetime_begin(String datetime_begin) {
		this.datetime_begin = datetime_begin;
	}
	public String getSycode() {
		return sycode;
	}
	public void setSycode(String sycode) {
		this.sycode = sycode;
	}

	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	public String getSource_type() {
		return source_type;
	}
	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getDeposit_type() {
		return deposit_type;
	}
	public void setDeposit_type(String deposit_type) {
		this.deposit_type = deposit_type;
	}
	public String getDeposit_credit() {
		return deposit_credit;
	}
	public void setDeposit_credit(String deposit_credit) {
		this.deposit_credit = deposit_credit;
	}
	public String getCoupon_code() {
		return coupon_code;
	}
	public void setCoupon_code(String coupon_code) {
		this.coupon_code = coupon_code;
	}
	public String getRecycleman_code() {
		return recycleman_code;
	}
	public void setRecycleman_code(String recycleman_code) {
		this.recycleman_code = recycleman_code;
	}

	public String getRefereecode() {
		return refereecode;
	}
	public void setRefereecode(String refereecode) {
		this.refereecode = refereecode;
	}
	public RequestInfo() {
		super();
	}
	@Override
	public String toString() {
		return "RequestInfo [host=" + host + ", port=" + port
				+ ", requestMethod=" + requestMethod + ", url1=" + url1
				+ ", url2=" + url2 + ", serialVersionUID=" + serialVersionUID
				+ ", rows=" + rows + ", page=" + page + ", datetime_min="
				+ datetime_min + ", datetime_max=" + datetime_max
				+ ", datetime_begin=" + datetime_begin + ", sycode=" + sycode
				+ ", order_status=" + order_status + ", source_type="
				+ source_type + ", phoneno=" + phoneno + ", deposit_type="
				+ deposit_type + ", deposit_credit=" + deposit_credit
				+ ", coupon_code=" + coupon_code + ", recycleman_code="
				+ recycleman_code + ", refereecode=" + refereecode + "]";
	}


}
