package com.lcl.util;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

public class ShiroSession {
	/**
	 * ��ȡsession����
	 * @return
	 */
	public static DefaultWebSessionManager getSessionManager(){
		DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
		DefaultWebSessionManager sessionManager = (DefaultWebSessionManager) securityManager.getSessionManager();
		return sessionManager;
	}
	
	/**
	 * ��ȡsessions����
	 * @return
	 */
	public static Collection<Session> getSessions(){
		/*��ȡsession*/
		DefaultWebSessionManager sessionManager = getSessionManager();
		Collection<Session> sessions = sessionManager.getSessionDAO().getActiveSessions();// ��ȡ��ǰ�ѵ�¼���û�session�б�
		return sessions;
	}
	/**
	 * ��ȡָ��session
	 * @return
	 */
	public static Session getSession(String s){
		DefaultWebSessionManager sessionManager = getSessionManager();
		return sessionManager.getSessionDAO().readSession(s);
	}
	/**
	 * ɾ��ָ��session
	 * @return
	 */
	public static void  delSession(String s){
		DefaultWebSessionManager sessionManager = getSessionManager();
		Session readSession = sessionManager.getSessionDAO().readSession(s);
		 sessionManager.getSessionDAO().delete(readSession);;
	}
	/**
	 * ɾ��ָ��session
	 * @return
	 */
	public static void  delSession(Session s){
		DefaultWebSessionManager sessionManager = getSessionManager();
		sessionManager.getSessionDAO().delete(s);;
	}

}
