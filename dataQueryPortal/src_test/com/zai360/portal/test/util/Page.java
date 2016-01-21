package com.zai360.portal.test.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class Page<T> implements Serializable {
	/** 内容 */
	private List<T> contents = new ArrayList<T>();
	/**
	 * 页码
	 */
	private int pageNumber;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总条数
	 */
	private int totalNumber=0;
	/**
	 * 总页数
	 */
	private int totalPage=0;

	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<T> getContents() {
		return contents;
	}

	public void setContents(List<T> contents) {
		this.contents = contents;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "Page [contents=" + contents + ", pageNumber=" + pageNumber
				+ ", pageSize=" + pageSize + ", totalNumber=" + totalNumber
				+ ", totalPage=" + totalPage + "]";
	}
	
}