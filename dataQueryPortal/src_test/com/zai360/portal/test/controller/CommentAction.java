package com.zai360.portal.test.controller;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
/**
 * 意见反馈
 * 发送邮件至xx邮箱
 * @author report
 *
 */
@Controller
public class CommentAction {
	
	public String sendMessage() throws AddressException, MessagingException, UnsupportedEncodingException{
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");//发送邮件协议
		properties.setProperty("mail.smtp.auth", "true");//需要验证
		// properties.setProperty("mail.debug", "true");//设置debug模式 后台输出邮件发送的过程
		Session session = Session.getInstance(properties);
		session.setDebug(false);//debug模式
		//邮件信息
		Message messgae = new MimeMessage(session);
		messgae.setFrom(new InternetAddress("liuzhen@zai360.com"));//设置发送人
		HttpServletRequest request = ServletActionContext.getRequest();
		String comment=request.getParameter("comment");//获取反馈意见
		messgae.setText(comment);//发送邮件
//		messgae.setContent(comment, "text/html;charset=UTF-8");;// 发送邮件 ??丢失<>内容
		messgae.setSubject("反馈意见");//设置邮件主题
		//发送邮件
		Transport tran = session.getTransport();
		tran.connect("smtp.qq.com", 25, "liuzhen@zai360.com", "Liuzhenzai360");//连接到邮箱服务器 发送人
		tran.sendMessage(messgae, new Address[]{ new InternetAddress("liuzhen@zai360.com")});//设置邮件接收人
		tran.close();
		return "success";
	}
}
