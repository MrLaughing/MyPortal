package com.zai360.portal.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.service.ExportService;
import com.zai360.portal.test.util.DynamicQuery;
import com.zai360.portal.test.util.QueryEnum;
import com.zai360.portal.test.util.Sql4RealLength;
import com.zai360.portal.test.util.SqlUtil;

/**
 * 通过唯一标识serialVersionUID 分配各查询导出
 * 
 * @author report
 *
 */

@Controller
public class ExportAction extends ActionSupport {
	@Autowired
	ExportService exportService;

	public void distribute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		for(QueryEnum query:QueryEnum.values()){
			if(request.getParameter("serialVersionUID").equals(query.getId())){
				/******************/
				if(Sql4RealLength.ifDynamic(query)){//判断是否为动态sql
					int realLength=exportService.getLength(Sql4RealLength.getRealLength(query));
					if(query.getLength()!=realLength){
						query.setLength(realLength);//首先修改length传递到sqlUtil中进行比较
						query=DynamicQuery.getDynamicQuery(query);//然后根据length对columns和titles,widths修改
					}
				}
				/******************/
				exportService.export(query, SqlUtil.getSql(query));
			}
		}	
	}
	
}
