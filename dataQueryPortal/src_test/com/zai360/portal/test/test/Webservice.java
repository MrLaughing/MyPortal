package com.zai360.portal.test.test;

import java.io.File;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="偶也")
public interface Webservice {
	@WebMethod
	String sayHello(@WebParam(name="excel") File excel);
	@WebResult
	String result();
}
