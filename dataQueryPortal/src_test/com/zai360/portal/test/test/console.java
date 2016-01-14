package com.zai360.portal.test.test;

import javax.xml.ws.Endpoint;

public class console {
	public static void main(String[] args) {
		String address="http://192.168.1.102:8899/Webservice";
		Endpoint.publish(address, new WebserviceImpl());
	}
}
