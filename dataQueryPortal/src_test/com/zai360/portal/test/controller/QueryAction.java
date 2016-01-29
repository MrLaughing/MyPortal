package com.zai360.portal.test.controller;

import java.io.UnsupportedEncodingException;
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
import com.zai360.portal.test.interceptor.ErrorMsgException;
import com.zai360.portal.test.interceptor.RequestInfo;
import com.zai360.portal.test.interceptor.ResponseInfo;
import com.zai360.portal.test.service.FindService;
import com.zai360.portal.test.service.WriteService;
import com.zai360.portal.test.util.Page;
import com.zai360.portal.test.util.UrlUtil;
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
	@Autowired
	private WriteService writeService;
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
		RequestInfo requestInfo = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='polan' AND TABLE_NAME='"
					+ActionContext.getContext().getName()+ "' ;");
			String havetable = this.writeService.havetable(sql);//首先判断请求表是否存在
			if (havetable == null) {
				throw new ErrorMsgException("查询表不存在");
			}
			requestInfo = getRequestInfo(request);//获取请求参数
			//根据url2和参数进行查询
			String havacolumnsql1="SELECT COLUMN_NAME FROM information_schema.columns "
					+ " WHERE table_name='"+requestInfo.getUrl2()+"' ";
			StringBuffer querycountsql = new StringBuffer();
			querycountsql.append("SELECT COUNT(*) FROM "+requestInfo.getUrl2()+" a WHERE 1=1 ");//查询总条数
			StringBuffer querysql = new StringBuffer();
			querysql.append("SELECT * FROM "+requestInfo.getUrl2()+" a WHERE 1=1 ");//查询结果
			Map<String,String[]> parametersmap = requestInfo.getParameters();	
			for(String key:parametersmap.keySet()){
				if(key.endsWith("_min")||key.endsWith("_max")){//判断查询参数是否含有日期间隔
					String realkey=key.substring(0, key.length()-4);
					StringBuffer havacolumnsql=new StringBuffer(havacolumnsql1+" AND column_name='"+realkey+"' ");
					if(!this.findService.havecolumn(havacolumnsql).isEmpty()){//判断表中是否含有该参数
						if(parametersmap.keySet().contains(realkey+"_max")&&parametersmap.keySet().contains(realkey+"_min")){//min和max不缺失
							String[] realkeyminvalue=(String[]) parametersmap.get((realkey+"_min"));
							String[] realkeymaxvalue=(String[]) parametersmap.get((realkey+"_max"));
							if(!"".equals(realkeyminvalue[0])&&realkeyminvalue!=null&&!"".equals(realkeymaxvalue[0])&&realkeymaxvalue!=null){
								if(key.endsWith("_min")){//为了只append一次sql
									querycountsql.append(" AND a.`"+realkey+"` BETWEEN '"+ realkeyminvalue[0]+"' AND '"+realkeymaxvalue[0]+"' ");
									querysql.append(" AND a.`"+realkey+"` BETWEEN '"+ realkeyminvalue[0]+"' AND '"+realkeymaxvalue[0]+"' ");
								}
							}else{
								throw new ErrorMsgException("查询参数错误");
							}
						}else{
							throw new ErrorMsgException("查询参数缺失");
						}
					}else{
						throw new ErrorMsgException("查询表中不包含参数列");
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
					}else{
						throw new ErrorMsgException("查询表中不包含参数列");
					}
				}else if("pageNumber".equals(key)||"pageSize".equals(key)||"index".equals(key)){
					//do nothing
				}else{
					StringBuffer havacolumnsql=new StringBuffer(havacolumnsql1+" AND column_name='"+key+"' ");
					if(this.findService.havecolumn(havacolumnsql)!=null){
						String[] keyvalue=(String[]) parametersmap.get(key);
						if(keyvalue!=null&&!"".equals(keyvalue[0])){
							querycountsql.append("AND a.`"+key+"` = '"+keyvalue[0]+"'");
							querysql.append("AND a.`"+key+"` = '"+keyvalue[0]+"'");
						}
					}else{
						throw new ErrorMsgException("查询表中不包含参数列");
					}
				}
			};
			if(requestInfo.getIndex()!=0){//单条查询
				int querypageNumber =requestInfo.getIndex();// 当前页，页码
				int querypageSize = 1;// 每页记录数,固定为1
				int querypageIndex=(querypageNumber - 1) * querypageSize;//记录起始数=index-1
				querysql.append(" LIMIT " + querypageIndex + "," + querypageSize);// 拼接分页limit
				System.out.println(querycountsql);
				System.out.println(querysql);
				this.page = this.findService.findPage(querycountsql, "common.query", querysql, querypageNumber, querypageSize);
				if(page.getTotalNumber()!=0&&requestInfo.getIndex()>page.getTotalNumber()){//在有返回数据的情况下
					throw new ErrorMsgException("index参数错误");
				}
			}else{//分页查询
				int querypageNumber =requestInfo.getPageNumber();// 当前页，页码
				int querypageSize = requestInfo.getPageSize();// 每页记录数
				int querypageIndex;//记录起始数
				if(querypageNumber<=0){
					throw new ErrorMsgException("pageNumber参数错误");
				}else if(querypageSize<=0){
					throw new ErrorMsgException("pageSize参数错误");
				}else{
					querypageIndex=(querypageNumber - 1) * querypageSize;
				};
				querysql.append(" LIMIT " + querypageIndex + "," + querypageSize);// 拼接分页limit
				System.out.println(querycountsql);
				System.out.println(querysql);
				this.page = this.findService.findPage(querycountsql, "common.query", querysql, querypageNumber, querypageSize);
			}
			
		} catch(UnsupportedEncodingException e){
			e.printStackTrace();
			responseInfo.setSuccess("0");
			responseInfo.setMsg("只支持utf-8编码");
			responseInfo.setErrorCode(ErrorCode.URL_UNSUPPORT_ENCODE);
			this.setResult(responseInfo);
			return "ajax";
		} catch (ErrorMsgException e) {
			e.printStackTrace();
			responseInfo.setSuccess("0");
			responseInfo.setMsg(e.getMessage());
			if("查询表不存在".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_EMPTY_TABLE);
			}else if(e.getMessage().contains("pageNumber")){
				responseInfo.setErrorCode(ErrorCode.PARAM_MISS_PAGENUMBER);
			}else if(e.getMessage().contains("pageSize")){
				responseInfo.setErrorCode(ErrorCode.PARAM_MISS_PAGESIZE);
			}else if(e.getMessage().contains("index")){
				responseInfo.setErrorCode(ErrorCode.PARAM_MISS_INDEX);
			}else if("查询参数错误".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_INCORRECT_OTHER);
			}else if("查询参数缺失".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_MISS_OTHER);
			}else if("查询表中不包含参数列".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_EMPTY_PARAMETER);
			}else if("index参数错误".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_INCORRECT_INDEX);
			}else if("pageNumber参数错误".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_INCORRECT_PAGENUMBER);
			}else if("pageSize参数错误".equals(e.getMessage())){
				responseInfo.setErrorCode(ErrorCode.PARAM_INCORRECT_PAGESIZE);
			}
			this.setResult(responseInfo);
			return "ajax";
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String contents = gson.toJson(page.getContents());
		List<HashMap<String, Object>> contentmap = gson.fromJson(contents,
				new TypeToken<List<HashMap<String, Object>>>() {
				}.getType());
		page.setContents(contentmap);// 处理日期格式带T问题
		responseInfo.setResult(page);
		this.setResult(responseInfo);
		return "ajax";
	}
	/**
	 * 获取请求参数
	 * @param request
	 * @return
	 * @throws ErrorMsgException 
	 * @throws UnsupportedEncodingException 
	 */
	public static RequestInfo getRequestInfo(HttpServletRequest request) throws ErrorMsgException, UnsupportedEncodingException {
		RequestInfo requestInfo=new RequestInfo();
		requestInfo.setHost(request.getRemoteAddr());
		requestInfo.setPort(request.getRemotePort());
		requestInfo.setMethod(request.getMethod());
		requestInfo.setUrl1(request.getServletPath());// /query/dtxx
		requestInfo.setUrl2(ActionContext.getContext().getName());// 即为表名
//		Map<String,String[]> parametersmap = request.getParameterMap();
		Map<String,String[]> parametersmap = UrlUtil.decodeurl(request);
		if(parametersmap.containsKey("pageSize")){
			String[] pageSize=parametersmap.get("pageSize");
			if(!"".equals(pageSize[0])&&pageSize!=null){
				requestInfo.setPageSize(Integer.parseInt(pageSize[0]));
			}else{
				throw new ErrorMsgException("pageSize参数值为空");
			}
		}
		if(parametersmap.containsKey("pageNumber")){
			String[] pageNumber=parametersmap.get("pageNumber");
			if(!"".equals(pageNumber[0])&&pageNumber!=null){
			requestInfo.setPageNumber(Integer.parseInt(pageNumber[0]));
			}else{
				throw new ErrorMsgException("pageNumber参数值为空");
			}
		}
		if(parametersmap.containsKey("index")){
			String[] index=parametersmap.get("index");
			if(!"".equals(index[0])&&index!=null){
				requestInfo.setIndex(Integer.parseInt(index[0]));
			}else{
				throw new ErrorMsgException("index参数值为空");
			}
		}
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
