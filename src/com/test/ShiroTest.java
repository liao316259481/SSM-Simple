package com.test;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class ShiroTest {

	@Test
	public void test() {
		//获取配置文件
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//安全管理器 （通过配置文件创建）
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//全局安全工具（吧安全管理器设置进去）
		SecurityUtils.setSecurityManager(securityManager);
		//获取当前的会话(全局获取)
		Subject subject = SecurityUtils.getSubject();
		
		//首先判断用户是否登录(授权)
		if(!subject.isAuthenticated()){
			//通过用户名和密码令牌登录
			UsernamePasswordToken upt = new UsernamePasswordToken("root", "123123");
			//
			upt.setRememberMe(true);
			try {//捕捉登录异常
				
				//执行登录
				subject.login(upt);
				
			} catch (UnknownAccountException e) {//没有账户
				System.err.println("用户名不存在");  
			}catch (IncorrectCredentialsException e) {//不正确的凭证
				System.err.println("密码错误");
			}
			
			
			
		}
		
		//应用程序用户（Subject）的任何标志属性。“标志属性”可以是任何对你应用程序有意义的东西——用户名，姓，名，社会安全号码，用户 ID 等
		System.out.println(subject.getPrincipal());//当前登录的用户名(身份)
		System.out.println(subject.hasRole("user"));//判断当前用户是否拥有此角色
		System.out.println(subject.hasRole("admin"));//判断当前用户是否拥有此角色
		
		Session session = subject.getSession();
		session.setAttribute("name", subject.getPrincipal());
		String  attribute = (String) session.getAttribute("name");
		System.out.println(attribute);
		
		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录  

		subject.logout();//登出
		System.out.println(subject.getPrincipal());//当前登录的用户名

	}

}
