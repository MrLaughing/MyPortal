package com.zai360.portal.test.vo;
/**
 * easyui表格动态表头
 * @author report
 *
 */
public class ColumnInfo {
    private String field;//列字段名称
    private String title;//列标题文本
    private int width;//列的宽度
    private String align;//指明如何对齐列数据。可以使用的值有：'left','right','center'
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	@Override
	public String toString() {
		return "ColumnInfo [field=" + field + ", title=" + title + ", width="
				+ width + ", align=" + align + "]";
	}
    
}
