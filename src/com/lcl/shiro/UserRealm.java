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
 * User权限控制
 * 
 * @author lcl
 *
 *         2018年3月14日
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private SessionDAO sessionDAO;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();// 获取用户民名

		/**
		 * 根据用户名从数据库查询出权限 在设置进角色的集合和权限的集合
		 */

		// 角色的集合
		Set<String> roles = new HashSet<String>();

		roles.add(userName);

		// 权限的集合

		Set<String> permissions = new HashSet<String>();
		permissions.add("/");
		permissions.add("/index");
		permissions.add("/getUser");
		if (userName.equals("admin")) {
			permissions.add("/json/**");
		}

		/**
		 * 权限 角色添加进权限验证
		 */

		System.out.println("user url--" + permissions);

		System.out.println("user角色+++" + roles);

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();// 授权
		authorizationInfo.addRoles(roles);// 设置角色
		authorizationInfo.addStringPermissions(permissions);// 设置权限
		
	

		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String userName = (String) token.getPrincipal();// 获取用户名

		// 通过用户名查询用户(通过数据库查询name是否存在)
		if (!userName.equals("admin") && !userName.equals("user"))
			throw new UnknownAccountException();// 不存在抛出不存在异常
/****************/
		// 处理session
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// 获取当前已登录的用户session列表
		for (Session session : sessions) {
			// 清除该用户以前登录时保存的session
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
//				session.setTimeout(0);//设置session立即失效,即将其踢出系统
//	
//				break;
//	
//			}
//
//		}
		
/**********************/
		/**
		 * 交给AuthenticatingRealm使用CredentialsMathcher进行密码匹配 如果觉得人家不好可以在此判断或自定义实现
		 * principal 身份 credentials 凭证
		 */
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, "123123", getName());

		// SimplePrincipalCollection principals = new
		// SimplePrincipalCollection(principal,getName());
		// clearCachedAuthorizationInfo(principals);
		return info;
	}

}
