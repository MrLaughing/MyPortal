package com.zai360.portal.test.vo;
/**
 * 存储下拉栏下各页面名称、路径和唯一标示
 * @author report
 *
 */
public class Path {
	private String path;//页面路径
	private String name;//下拉名称
	private String serialVersionUID;//唯一标示
	
	
	public Path() {
		super();
	}
	public Path(String path, String name, String serialVersionUID) {
		super();
		this.path = path;
		this.name = name;
		this.serialVersionUID = serialVersionUID;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerialVersionUID() {
		return serialVersionUID;
	}
	public void setSerialVersionUID(String serialVersionUID) {
		this.serialVersionUID = serialVersionUID;
	}
	@Override
	public String toString() {
		return "Path [path=" + path + ", name=" + name + ", serialVersionUID="
				+ serialVersionUID + "]";
	}
	
}
