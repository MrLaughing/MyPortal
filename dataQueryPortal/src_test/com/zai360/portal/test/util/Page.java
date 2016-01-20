package com.zai360.portal.test.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zai360.portal.test.vo.ColumnInfo;

/**
 * 分页
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class Page<T> implements Serializable {
	/** 内容 */
	private  List<T> content = new ArrayList<T>();
	/**
	 * 页码
	 */
	private int pageNumber;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 起始记录数
	 */
	private int pageIndex;
	/**
	 * 总条数
	 */
	private int totalNumber;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 获取内容
	 * 
	 * @return 内容
	 */
	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
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
		return "Page [content=" + content + ", pageNumber=" + pageNumber
				+ ", pageSize=" + pageSize + ", pageIndex=" + pageIndex
				+ ", totalNumber=" + totalNumber + ", totalPage=" + totalPage
				+ "]";
	}

}