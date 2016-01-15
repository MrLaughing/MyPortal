package com.zai360.portal.test.interceptor;

import java.util.HashMap;
import java.util.List;

public class ResponseInfo {
	private String success = "1";//成功标识
	private String msg="成功";//信息
	private Object object;//额外返回对象
	private String errorCode="";//错误码
	private String total="0";//查询总条数
	private List<HashMap<String, Object>> rows=null;
	public ResponseInfo() {
		super();
	}
	
	public ResponseInfo(String success, String msg, Object object,
			String errorCode, String total, List<HashMap<String, Object>> rows) {
		super();
		this.success = success;
		this.msg = msg;
		this.object = object;
		this.errorCode = errorCode;
		this.total = total;
		this.rows = rows;
	}


	@Override
	public String toString() {
		return "ResponseInfo [success=" + success + ", msg=" + msg
				+ ", object=" + object + ", errorCode=" + errorCode
				+ ", total=" + total + ", rows=" + rows + "]";
	}

	public List<HashMap<String, Object>> getRows() {
		return rows;
	}


	public void setRows(List<HashMap<String, Object>> rows) {
		this.rows = rows;
	}


	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	
}
