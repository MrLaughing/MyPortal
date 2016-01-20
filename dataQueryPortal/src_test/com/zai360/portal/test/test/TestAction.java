package com.zai360.portal.test.test;

import com.zai360.portal.test.interceptor.ResponseInfo;




public class TestAction {
	public void testforresult() {
//		ResponseInfo result = new ResponseInfo(true, "成功", null,"");
//		WriteJson.writeResult(result);
	}
	public static void main(String[] args) {
//		String time_min="datetime_min";
//		String time_max="datetime_max";
//		System.out.println(time_min.substring(0, time_min.length()-4));
		StringBuffer str1=new StringBuffer("aaaa");
		StringBuffer str2=str1;
		str2.append("bbbb");
		System.out.println(str1+"///"+str2);
		
	}
}
