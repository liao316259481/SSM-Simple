package com.lcl.shiro;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.lcl.util.Constants;

/**
 * UserȨ�޿���
 * 
 * @author lcl
 *
 *         2018��3��14��
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SessionDAO sessionDAO;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();// ��ȡ�û�����

		/**
		 * �����û��������ݿ��ѯ��Ȩ�� �����ý���ɫ�ļ��Ϻ�Ȩ�޵ļ���
		 */

		// ��ɫ�ļ���
		Set<String> roles = new HashSet<String>();

		roles.add(userName);

		// Ȩ�޵ļ���

		Set<String> permissions = new HashSet<String>();
		permissions.add("/");
		permissions.add("/index");
		permissions.add("/getUser");
		if (userName.equals("admin")) {
			permissions.add("/json/**");
		}

		/**
		 * Ȩ�� ��ɫ��ӽ�Ȩ����֤
		 */

		System.out.println("user url--" + permissions);

		System.out.println("user��ɫ+++" + roles);

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();// ��Ȩ
		authorizationInfo.addRoles(roles);// ���ý�ɫ
		authorizationInfo.addStringPermissions(permissions);// ����Ȩ��
		
	

		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String userName = (String) token.getPrincipal();// ��ȡ�û���

		// ͨ���û�����ѯ�û�(ͨ�����ݿ��ѯname�Ƿ����)
		if (!userName.equals("admin") && !userName.equals("user"))
			throw new UnknownAccountException();// �������׳��������쳣
/****************/
		// ����session
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// ��ȡ��ǰ�ѵ�¼���û�session�б�
		for (Session session : sessions) {
			// ������û���ǰ��¼ʱ�����session
			if (userName.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
//				sessionManager.getSessionDAO().delete(session);
				session.setAttribute(Constants.SESSION_REPLACE_LOGOUT_KEY, Boolean.TRUE);
			}
		}
		
/****************/
		
//		Session currentSession = null;
//
//		Collection<Session> sessions = sessionDAO.getActiveSessions();
//
//		for(Session session:sessions){
//
//			if(userName.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)))) {
//	
//				session.setTimeout(0);//����session����ʧЧ,�������߳�ϵͳ
//	
//				break;
//	
//			}
//
//		}
		
/**********************/
		/**
		 * ����AuthenticatingRealmʹ��CredentialsMathcher��������ƥ�� ��������˼Ҳ��ÿ����ڴ��жϻ��Զ���ʵ��
		 * principal ��� credentials ƾ֤
		 */
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, "123123", getName());

		// SimplePrincipalCollection principals = new
		// SimplePrincipalCollection(principal,getName());
		// clearCachedAuthorizationInfo(principals);
		return info;
	}

}
