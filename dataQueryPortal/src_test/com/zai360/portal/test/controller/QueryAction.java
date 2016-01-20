package com.zai360.portal.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.interceptor.ErrorCode;
import com.zai360.portal.test.interceptor.RequestInfo;
import com.zai360.portal.test.interceptor.ResponseInfo;
import com.zai360.portal.test.service.FindService;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.QueryEnum;
import com.zai360.portal.test.util.Sql4CountUtil;
import com.zai360.portal.test.util.SqlUtil;
/**
 * 查询接口
 * 不需要columns表头信息，无dynamic方法
 * @author Laughing_Lz
 * @date 2016年1月19日
 */
public class QueryAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	@Autowired
	private FindService findService;
	private Page<HashMap<String, Object>> page;
	// 返回流对象（用于AJAX和文件下载）
	private ResponseInfo result;
	/**
	 * 查询入口
	 * @return
	 */
	public String search() {
		ResponseInfo responseInfo = new ResponseInfo();
		HttpServletRequest request = ServletActionContext.getRequest();
		RequestInfo requestInfo=getRequestInfo(request);//获取请求参数
		//根据url2和参数进行查询
		String havacolumnsql1="SELECT * FROM information_schema.columns "
				+ " WHERE table_name='"+requestInfo.getUrl2()+"' ";
		StringBuffer querycountsql = new StringBuffer();
		querycountsql.append("SELECT COUNT(*) FROM "+requestInfo.getUrl2()+" a WHERE 1=1 ");//查询总条数
		StringBuffer querysql = new StringBuffer();
		querysql.append("SELECT * FROM "+requestInfo.getUrl2()+" a WHERE 1=1 ");//查询结果
		
		Map<String,Object> parametersmap = requestInfo.getParameters();	
		for(String key:parametersmap.keySet()){
			if(key.endsWith("_min")||key.endsWith("_max")){//判断查询参数是否含有日期间隔
				String realkey=key.substring(0, key.length()-4);
				StringBuffer havacolumnsql=new StringBuffer(havacolumnsql1+" AND column_name='"+realkey+"' ");
				if(this.findService.havecolumn(havacolumnsql)!=null&&parametersmap.keySet().contains(realkey+"_max")){//判断表中是否含有该参数且包含此参数_max
					String[] realkeyminvalue=(String[]) parametersmap.get((realkey+"_min"));
					String[] realkeymaxvalue=(String[]) parametersmap.get((realkey+"_max"));
					
					querycountsql.append(" AND a.`"+realkey+"` BETWEEN '"+ realkeyminvalue[0]+"' AND '"+realkeymaxvalue[0]+"' ");
					querysql.append(" AND a.`"+realkey+"` BETWEEN '"+ realkeyminvalue[0]+"' AND '"+realkeymaxvalue[0]+"' ");
					
				}
			}else if(key.endsWith("_status")||key.endsWith("_type")){//判断是否有多选参数
				String realkey = key.endsWith("_status")?key.substring(0,key.length()-7):key.substring(0, key.length()-5);
				StringBuffer havacolumnsql=new StringBuffer(havacolumnsql1 + " AND column_name='"+realkey+"' ");
				if(this.findService.havecolumn(havacolumnsql)!=null){
					String[] keyvalues=(String[]) parametersmap.get(key);
					StringBuffer keyvalues_str=new StringBuffer();
					if (keyvalues != null) {
						if (keyvalues.length > 0 & keyvalues[0] != "") {
							for (int i = 0; i < keyvalues.length; i++) {
								if (i == keyvalues.length - 1) {
									keyvalues_str.append(keyvalues[i]);
								} else {
									keyvalues_str.append(keyvalues[i] + ",");
								}
							}
							querycountsql.append("AND a.`"+realkey+"` IN ("+keyvalues_str+")");
							querysql.append("AND a.`"+realkey+"` IN ("+keyvalues_str+")");
						}
					}
				}
			}else{
				StringBuffer havacolumnsql=new StringBuffer(havacolumnsql1+" AND column_name='"+key+"' ");
				if(this.findService.havecolumn(havacolumnsql)!=null){
					String[] keyvalue=(String[]) parametersmap.get(key);
					querycountsql.append("AND a.`"+key+"` = '"+keyvalue[0]+"'");
					querysql.append("AND a.`"+key+"` = '"+keyvalue[0]+"'");
				}
			}
			
		};

		int querypageNumber = Integer.parseInt(requestInfo.getPageNumber());// 当前页，页码
		int querypageSize = Integer.parseInt(requestInfo.getPageSize());// 每页记录数
		int querypageIndex=(querypageNumber - 1) * querypageSize;
		this.page = this.findService.findPage(querycountsql, "common.query", querysql, querypageNumber, querypageSize,querypageIndex);

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String content = gson.toJson(page.getContent());
		List<HashMap<String, Object>> contentmap = gson.fromJson(content,
				new TypeToken<List<HashMap<String, Object>>>() {
				}.getType());// 处理日期格式带T问题
		responseInfo.setSuccess("1");
		responseInfo.setTotal(String.valueOf(page.getTotalNumber()));
		responseInfo.setTotalPage(String.valueOf(page.getTotalPage()));
		responseInfo.setRows(contentmap);
		this.setResult(responseInfo);
		return "ajax";
	}
	/**
	 * 获取请求参数
	 * @param request
	 * @return
	 */
	public static RequestInfo getRequestInfo(HttpServletRequest request){
		RequestInfo requestInfo=new RequestInfo();
		requestInfo.setHost(request.getRemoteAddr());
		requestInfo.setPort(request.getRemotePort());
		requestInfo.setMethod(request.getMethod());
		requestInfo.setUrl1(request.getServletPath());// /query/dtxx
		requestInfo.setUrl2(ActionContext.getContext().getName());// 即为表名
		Map<String,Object> parametersmap=ActionContext.getContext().getParameters();
		if(parametersmap.containsKey("pageSize")){
			String[] pageSize=(String[])parametersmap.get("pageSize");
			if(!"".equals(pageSize)&&pageSize!=null){
				requestInfo.setPageSize(pageSize[0]);
			}
		};
		if(parametersmap.containsKey("pageNumber")){
			String[] pageNumber=(String[])parametersmap.get("pageNumber");
			if(!"".equals(pageNumber)&&pageNumber!=null){
			requestInfo.setPageNumber(pageNumber[0]);
			}
		};
		if(parametersmap.containsKey("pageIndex")){
			String[] pageIndex=(String[])parametersmap.get("pageIndex");
			if(!"".equals(pageIndex)&&pageIndex!=null){
			requestInfo.setPageIndex(pageIndex[0]);
			}
		};
		if(parametersmap.containsKey("totalNumber")){
			String[] totalNumber=(String[])parametersmap.get("totalNumber");
			if(!"".equals(totalNumber)&&totalNumber!=null){
			requestInfo.setTotalNumber(totalNumber[0]);
			}
		};
		if(parametersmap.containsKey("totalPage")){
			String[] totalPage=(String[])parametersmap.get("totalPage");
			if(!"".equals(totalPage)&&totalPage!=null){
			requestInfo.setTotalPage(totalPage[0]);
			}
		};
		requestInfo.setParameters(parametersmap);//存入?后的请求参数
		return requestInfo;
	}

	/****************************/
	public ResponseInfo getResult() {
		return result;
	}
	
	public void setResult(ResponseInfo result) {
		this.result = result;
	}	
}
