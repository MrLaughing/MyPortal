package com.zai360.portal.test.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.service.FindService;
import com.zai360.portal.test.util.DynamicQuery;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.QueryEnum;
import com.zai360.portal.test.util.Sql4CountUtil;
import com.zai360.portal.test.util.Sql4RealLength;
import com.zai360.portal.test.util.SqlUtil;
import com.zai360.portal.test.util.jsonUtil;
import com.zai360.portal.test.vo.ColumnInfo;

/***
 * 处理前台分页查询
 * 
 * @author report
 *
 */
@Controller
public class FindAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private FindService findService;
	private Page<HashMap<String, Object>> page;
	// 返回流对象（用于AJAX和文件下载）
	private InputStream inputStream;
	/**
	 * 修改easyui中的columns表头
	 * @return
	 */
	public String dynamic() {
		HttpServletRequest request = ServletActionContext.getRequest();
		List<ColumnInfo> columnInfoList = new ArrayList<ColumnInfo>();
		for (QueryEnum query : QueryEnum.values()) {
			if (request.getParameter("serialVersionUID").equals(query.getId())) {
				if (Sql4RealLength.ifDynamic(query)) {// 判断是否为动态sql
					int realLength = findService.getTotal(Sql4RealLength
							.getRealLength(query));
					if (query.getLength() != realLength) {
						query.setLength(realLength);// 首先修改length传递到sqlUtil中进行比较
						query = DynamicQuery.getDynamicQuery(query);// 然后根据length对columns和titles,widths修改
					}
					for (String str : query.getColumns()) {// 修改返回easyui中动态columns
						ColumnInfo columnInfo = new ColumnInfo();
						columnInfo.setTitle(str);
						columnInfo.setField(str);
						columnInfoList.add(columnInfo);
					}

				}
			}
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String columnInfo = gson.toJson(columnInfoList);
		String info = "{\"columns\":" + columnInfo + "}";// 拼接json数据
		inputStream = jsonUtil.string2stream(info);
		return "ajax";
	}
	/**
	 * easyui分页查询
	 * @return
	 */
	public String search() {
		HttpServletRequest request = ServletActionContext.getRequest();
		for (QueryEnum query : QueryEnum.values()) {
			if (request.getParameter("serialVersionUID").equals(query.getId())) {
				int pageNumber = Integer.parseInt(request.getParameter("page"));// 当前页，页码
				int pageSize = Integer.parseInt(request.getParameter("rows"));// 每页记录数
				StringBuffer countsql=Sql4CountUtil.getSql(query);
				System.out.println(countsql);
				StringBuffer sql=SqlUtil.getSql(query);
				System.out.println(sql);
				this.page = this.findService.findPage(countsql, query.getMappermethod(),
						sql, pageNumber, pageSize);
			}
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String content = gson.toJson(page.getContent());
		 String info = "{\"total\":" + page.getTotalNumber() + ",\"rows\":"
		 + content + "}";// 拼接json数据
		inputStream = jsonUtil.string2stream(info);
		return "ajax";
	}
	/****************************/

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
