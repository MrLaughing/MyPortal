package com.zai360.portal.test.interceptor;

import java.util.HashMap;
import java.util.List;

import com.zai360.portal.test.vo.ColumnInfo;

public class ResponseInfo {
	private String success="1";//成功标识，默认为1
	private String msg="成功";//信息，默认成功
	private Object object;//额外返回对象
	private String errorCode="0";//错误码，默认为0
	private String total;//查询总条数
	private String totalPage;//查询总页数
	private List<HashMap<String, Object>> rows;//查询结果
	private List<ColumnInfo> columns;//动态表头
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
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public List<HashMap<String, Object>> getRows() {
		return rows;
	}
	public void setRows(List<HashMap<String, Object>> rows) {
		this.rows = rows;
	}
	public List<ColumnInfo> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnInfo> columns) {
		this.columns = columns;
	}
	@Override
	public String toString() {
		return "ResponseInfo [success=" + success + ", msg=" + msg
				+ ", object=" + object + ", errorCode=" + errorCode
				+ ", total=" + total + ", totalPage=" + totalPage + ", rows="
				+ rows + ", columns=" + columns + "]";
	}
	public ResponseInfo() {
		super();
	}
	public ResponseInfo(String success, String msg, Object object,
			String errorCode, String total, String totalPage,
			List<HashMap<String, Object>> rows, List<ColumnInfo> columns) {
		super();
		this.success = success;
		this.msg = msg;
		this.object = object;
		this.errorCode = errorCode;
		this.total = total;
		this.totalPage = totalPage;
		this.rows = rows;
		this.columns = columns;
	}
	
}
