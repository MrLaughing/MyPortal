package com.zai360.portal.test.test;

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

@WebService(name="webserviceimpl",serviceName="writeservice",portName="what?")
public class WebserviceImpl implements Webservice {

	@Override
	@WebMethod(operationName="说hello")
	@WebResult(name="返回值",header=true)
	public String sayHello(@WebParam(name="参数") File excel) {
		HttpServletRequest request = ServletActionContext.getRequest();
		return "sayhello";
	}

	@Override
	@WebMethod
	public String result() {
		return "result";
	}

}
