package com.zai360.portal.test.interceptor;
/**
 * 自定义异常
 * 响应错误信息
 * @author Laughing_Lz
 * @date 2016年1月20日
 */
public class ErrorMsgException extends Exception {

	private static final long serialVersionUID = 1L;

	public ErrorMsgException() {
		super();
	}

	public ErrorMsgException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ErrorMsgException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorMsgException(String message) {
		super(message);
	}

	public ErrorMsgException(Throwable cause) {
		super(cause);
	}
	

	
}
