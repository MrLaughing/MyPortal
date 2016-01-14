package com.zai360.portal.test.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zai360.portal.test.interceptor.ResponseInfo;

public class WriteJson {
	public static void writeResult(ResponseInfo result){
		HttpServletResponse response=ServletActionContext.getResponse();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();// Gson处理Date格式
		String resultJson = gson.toJson(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {  
	        out = response.getWriter();  
	        out.append(resultJson);
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {
	        	out.flush();
	            out.close();  
	        }  
	    }
	}
}
