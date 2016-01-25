package com.zai360.portal.test.oauth2;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.zai360.portal.test.interceptor.ResponseInfo;

public class AuthorizeAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private ResponseInfo result;

	public String authorize() throws OAuthSystemException,
			OAuthProblemException, URISyntaxException {

		 try {
		// 构建OAuth 授权请求
		HttpServletRequest request = ServletActionContext.getRequest();
		OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
		oauthRequest.getClientSecret();
		OAuthTokenRequest oaTokenRequest=new OAuthTokenRequest(request);
		oaTokenRequest.getClientSecret();
		 OAuthAccessResourceRequest oaResourceRequest = new OAuthAccessResourceRequest(request, ParameterStyle.QUERY);
		oaResourceRequest.getAccessToken();
		oaTokenRequest.getClientSecret();
		System.out.println(oauthRequest.getClientId());
		System.out.println();
		// 检查传入的客户端id是否正确
		if (!"c1ebe466-1cdc-4bd3-ab69-77c3561b9dee".equals(oauthRequest
				.getClientId())) {
			OAuthResponse response = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.setError(OAuthError.TokenResponse.INVALID_CLIENT)
					.setErrorDescription("客户端验证失败，如错误的client_id/client_secret")
					.buildJSONMessage();
			return "error";
		}
		Subject subject = SecurityUtils.getSubject();
		// 如果用户没有登录，跳转到登陆页面
		if (!subject.isAuthenticated()) {
			if (!login(subject, request)) {// 登录失败时跳转到登陆页面
				// laughing:此处为登录失败时跳转到登陆页面
				return "error";
			}
		}
		 String username = (String)subject.getPrincipal();
		 //生成授权码
		 String authorizationCode = null;
		 //responseType目前仅支持CODE，另外还有TOKEN
		 String responseType =
		 oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
		 if (responseType.equals(ResponseType.CODE.toString())) {
		 OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new
		 MD5Generator());
		 authorizationCode = oauthIssuerImpl.authorizationCode();//laughing：生成唯一的授权码？
//		 oAuthService.addAuthCode(authorizationCode, username);
		 //laughing：将授权码给予用户|客户端？
		 }
		
		 //进行OAuth响应构建
		 OAuthASResponse.OAuthAuthorizationResponseBuilder builder =
		 OAuthASResponse.authorizationResponse(request,
		 HttpServletResponse.SC_FOUND);
		 //设置授权码
		 builder.setCode(authorizationCode);//d80f55ebc4cea1bafc415aab9d8f8c97
		 //得到到客户端重定向地址
		 String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
		 //构建响应
		 final OAuthResponse response =
		 builder.location(redirectURI).buildQueryMessage();
		
		 //根据OAuthResponse返回ResponseEntity响应
		 HttpHeaders headers = new HttpHeaders();
		 headers.setLocation(new URI(response.getLocationUri()));//{Location=[http://localhost:9080/chapter17-client/oauth2-login?code=d80f55ebc4cea1bafc415aab9d8f8c97]}
//		 return new ResponseEntity(headers,
//		 HttpStatus.valueOf(response.getResponseStatus()));
		 return "success";
		 } catch (OAuthProblemException e) {
			//出错处理
            String redirectUri = e.getRedirectUri();
            if (OAuthUtils.isEmpty(redirectUri)) {
                //告诉客户端没有传入redirectUri直接报错
                new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
                return "error";
            }

            //返回错误消息（如?error=）
            final OAuthResponse response =
                    OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND)
                            .error(e).location(redirectUri).buildQueryMessage();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(new URI(response.getLocationUri()));
            new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
            return "error";
		 }
//			 ResponseInfo responseInfo = new ResponseInfo();
//			 responseInfo.setMsg("oauth2");
//			 this.setResult(responseInfo);
//			 return "ajax";
	}

	private boolean login(Subject subject, HttpServletRequest request) {
		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);

		try {
			subject.login(token);
			return true;
		} catch (Exception e) {
			request.setAttribute("error", "登录失败:" + e.getClass().getName());
			return false;
		}
	}

	/******************************/
	public ResponseInfo getResult() {
		return result;
	}

	public void setResult(ResponseInfo result) {
		this.result = result;
	}

}
