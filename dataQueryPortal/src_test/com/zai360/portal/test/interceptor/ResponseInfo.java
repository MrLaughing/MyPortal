package com.zai360.portal.test.interceptor;

public class ResponseInfo {
	private String success = "1";//成功标识
	private String msg="";//信息
	private Object object;//额外返回对象
	private String errorCode="0";//错误码

	public ResponseInfo() {
		super();
	}
	
	public ResponseInfo(String success, String msg, Object object,
			String errorCode) {
		super();
		this.success = success;
		this.msg = msg;
		this.object = object;
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ResponseInfo [success=" + success + ", msg=" + msg
				+ ", object=" + object + ", errorCode=" + errorCode + "]";
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
