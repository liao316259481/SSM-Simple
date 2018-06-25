package com.lcl.util;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

public class ShiroSession {
	/**
	 * 获取session管理
	 * @return
	 */
	public static DefaultWebSessionManager getSessionManager(){
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		return sessionManager;
	}
	
	/**
	 * 获取sessions集合
	 * @return
	 */
	public static Collection<Session> getSessions(){
		/*获取session*/
		DefaultWebSessionManager sessionManager = getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// 获取当前已登录的用户session列表
		return sessions;
	}
	/**
	 * 获取指定session
	 * @return
	 */
	public static Session getSession(String s){
		DefaultWebSessionManager sessionManager = getSessionManager();
		return sessionManager.getSessionDAO().readSession(s);
	}
	/**
	 * 删除指定session
	 * @return
	 */
	public static void  delSession(String s){
		DefaultWebSessionManager sessionManager = getSessionManager();
		Session readSession = sessionManager.getSessionDAO().readSession(s);
		 sessionManager.getSessionDAO().delete(readSession);;
	}
	/**
	 * 删除指定session
	 * @return
	 */
	public static void  delSession(Session s){
		DefaultWebSessionManager sessionManager = getSessionManager();
		sessionManager.getSessionDAO().delete(s);;
	}

}
