package com.zai360.portal.test.interceptor;

import java.util.HashMap;

import com.zai360.portal.test.util.Page;

public class ResponseInfo {
	private String success="1";//成功标识，默认为1：成功
	private String msg="成功";//信息，默认成功
	private String errorCode="0";//错误码，默认为0：无错误
	private Page<HashMap<String, Object>> result;//返回结果
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Page<HashMap<String, Object>> getResult() {
		return result;
	}
	public void setResult(Page<HashMap<String, Object>> result) {
		this.result = result;
	}
	
}
