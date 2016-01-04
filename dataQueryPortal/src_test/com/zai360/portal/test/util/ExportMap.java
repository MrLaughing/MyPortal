package com.zai360.portal.test.util;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

/**
 * list<T>为map数据
 * 
 * @author report
 * @param <T>
 */

public class ExportMap {
	public static void export(List<HashMap<String, Object>> list, String[] columns,
			String filename, String[] titles, String sheetName, Integer[] widths)
			throws Exception {
		/**
		 * 获取list<T>中T的属性和值
		 */
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		int length;
		String[] properties = null;
		if (list.size() != 0) {
			length = list.get(0).size();
			properties = new String[length];
		}	ExportExcel exportExcel = new ExportExcel(properties, titles, list,
				columns);// list即data
		exportExcel.sheetName = sheetName;
		exportExcel.widths = widths;
		exportExcel.doExportExcel(request, response, filename);
	}
}
