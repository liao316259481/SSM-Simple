package com.lcl.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.lcl.util.Constants;
import com.lcl.util.ShiroSession;

/**
 * 授权过滤
 * @author lcl
 *
 * 2018年3月18日
 */
public class PermsFitler extends AuthorizationFilter{
	/**
	 * 权限验证过滤
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		HttpServletRequest req = (HttpServletRequest) request;	
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if(session==null){
			 return subject.isPermitted(req.getServletPath());//session==null 只判断是否满足权限
		}
		//不然都需要判断权限不足还是被强制退出
		return subject.isPermitted(req.getServletPath()) & session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) == null& session.getAttribute(Constants.SESSION_REPLACE_LOGOUT_KEY) == null;
	}
	
	@Override
   protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
//			
		if (subject.getPrincipal() == null) {//判断是否登录
			saveRequestAndRedirectToLogin(request, response);
		}else if(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) != null){//判断是不是被强制退出
			
			try {
				getSubject(request, response).logout();//强制退出
				ShiroSession.delSession(session);
			} catch (Exception e) {/*ignore exception*/}
			String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
			WebUtils.issueRedirect(request, response, loginUrl);//退出并跳转登录页面
		}else if(session.getAttribute(Constants.SESSION_REPLACE_LOGOUT_KEY) != null){//判断是不是被强制退出
			
			try {
				getSubject(request, response).logout();//强制退出
				ShiroSession.delSession(session);
			} catch (Exception e) {/*ignore exception*/}
			String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=2";
			WebUtils.issueRedirect(request, response, loginUrl);//退出并跳转登录页面
		}else{//判断是否有权限
			WebUtils.issueRedirect(request, response, getUnauthorizedUrl());//跳转无权限页面
		}
		return false;
    }
}
