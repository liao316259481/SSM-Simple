package com.lcl.tld;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.lcl.util.Constants;

public class ELFuncUtil {  
    /** 
     * EL方法用于连接两个字符串 
     *  
     * @param str1 
     * @param str2 
     * @return 
     */  
    public static String append(String str1, String str2) {  
        return str1 + str2;  
    }  
    /**
     * 是否被強制對出
     * @param session
     * @return
     */
    public static boolean isForceLogout(Session session){
		return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY)!=null;
    	
    }
    /**
     * 判断session是否登录
     * @param session
     * @return
     */
    public static boolean isLogin(Session session){
    	try {
    		return (boolean) session.getAttribute(DefaultSubjectContext.AUTHENTICATED_SESSION_KEY);
		} catch (Exception e) {
			return false;
		}
    }
}  