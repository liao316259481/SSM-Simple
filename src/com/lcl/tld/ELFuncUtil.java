package com.lcl.tld;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.lcl.util.Constants;

public class ELFuncUtil {  
    /** 
     * EL�����������������ַ��� 
     *  
     * @param str1 
     * @param str2 
     * @return 
     */  
    public static String append(String str1, String str2) {  
        return str1 + str2;  
    }  
    /**
     * �Ƿ񱻏��ƌ���
     * @param session
     * @return
     */
    public static boolean isForceLogout(Session session){
		return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY)!=null;
    	
    }
    /**
     * �ж�session�Ƿ��¼
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