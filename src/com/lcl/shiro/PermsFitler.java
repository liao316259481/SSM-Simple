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
 * ��Ȩ����
 * @author lcl
 *
 * 2018��3��18��
 */
public class PermsFitler extends AuthorizationFilter{
	/**
	 * Ȩ����֤����
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		HttpServletRequest req = (HttpServletRequest) request;	
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if(session==null){
			 return subject.isPermitted(req.getServletPath());//session==null ֻ�ж��Ƿ�����Ȩ��
		}
		//��Ȼ����Ҫ�ж�Ȩ�޲��㻹�Ǳ�ǿ���˳�
		return subject.isPermitted(req.getServletPath()) & session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) == null& session.getAttribute(Constants.SESSION_REPLACE_LOGOUT_KEY) == null;
	}
	
	@Override
   protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
//			
		if (subject.getPrincipal() == null) {//�ж��Ƿ��¼
			saveRequestAndRedirectToLogin(request, response);
		}else if(session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) != null){//�ж��ǲ��Ǳ�ǿ���˳�
			
			try {
				getSubject(request, response).logout();//ǿ���˳�
				ShiroSession.delSession(session);
			} catch (Exception e) {/*ignore exception*/}
			String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
			WebUtils.issueRedirect(request, response, loginUrl);//�˳�����ת��¼ҳ��
		}else if(session.getAttribute(Constants.SESSION_REPLACE_LOGOUT_KEY) != null){//�ж��ǲ��Ǳ�ǿ���˳�
			
			try {
				getSubject(request, response).logout();//ǿ���˳�
				ShiroSession.delSession(session);
			} catch (Exception e) {/*ignore exception*/}
			String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=2";
			WebUtils.issueRedirect(request, response, loginUrl);//�˳�����ת��¼ҳ��
		}else{//�ж��Ƿ���Ȩ��
			WebUtils.issueRedirect(request, response, getUnauthorizedUrl());//��ת��Ȩ��ҳ��
		}
		return false;
    }
}
