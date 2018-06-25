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
		//��ȡ�����ļ�
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//��ȫ������ ��ͨ�������ļ�������
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		//ȫ�ְ�ȫ���ߣ��ɰ�ȫ���������ý�ȥ��
		SecurityUtils.setSecurityManager(securityManager);
		//��ȡ��ǰ�ĻỰ(ȫ�ֻ�ȡ)
		Subject subject = SecurityUtils.getSubject();
		
		//�����ж��û��Ƿ��¼(��Ȩ)
		if(!subject.isAuthenticated()){
			//ͨ���û������������Ƶ�¼
			UsernamePasswordToken upt = new UsernamePasswordToken("root", "123123");
			//
			upt.setRememberMe(true);
			try {//��׽��¼�쳣
				
				//ִ�е�¼
				subject.login(upt);
				
			} catch (UnknownAccountException e) {//û���˻�
				System.err.println("�û���������");  
			}catch (IncorrectCredentialsException e) {//����ȷ��ƾ֤
				System.err.println("�������");
			}
			
			
			
		}
		
		//Ӧ�ó����û���Subject�����κα�־���ԡ�����־���ԡ��������κζ���Ӧ�ó���������Ķ��������û������գ�������ᰲȫ���룬�û� ID ��
		System.out.println(subject.getPrincipal());//��ǰ��¼���û���(���)
		System.out.println(subject.hasRole("user"));//�жϵ�ǰ�û��Ƿ�ӵ�д˽�ɫ
		System.out.println(subject.hasRole("admin"));//�жϵ�ǰ�û��Ƿ�ӵ�д˽�ɫ
		
		Session session = subject.getSession();
		session.setAttribute("name", subject.getPrincipal());
		String  attribute = (String) session.getAttribute("name");
		System.out.println(attribute);
		
		Assert.assertEquals(true, subject.isAuthenticated()); //�����û��Ѿ���¼  

		subject.logout();//�ǳ�
		System.out.println(subject.getPrincipal());//��ǰ��¼���û���

	}

}
