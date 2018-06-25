package com.lcl.controller;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lcl.entity.User;
import com.lcl.exception.IsNanException;
import com.lcl.service.AdminMapperService;
import com.lcl.util.Constants;
import com.lcl.util.ShiroSession;



@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private SessionDAO sessionDAO;
	
	private static final transient Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Resource
	AdminMapperService adminMapperService;
	
	/**
	 * ����
	 * ע��redis redisTemplate ������
	 * 
	 */
	@Resource
	private RedisTemplate<String, Object> redisTemplate;//redis
	
	@RequestMapping("")
	public String index(Model model){
		
		redisTemplate.opsForValue().set("holle", "yes");
		
		List<User> queryAll = adminMapperService.queryAll();
		
		model.addAttribute("users",queryAll);
		
		return "index";
	}
	@CacheEvict("index1")
	@RequestMapping("index")
	public String index1(Model model){
		redisTemplate.opsForValue().set("holle", "no");
		redisTemplate.opsForValue().set("aop", "����12312",30);
		redisTemplate.expire("holle", 10, TimeUnit.SECONDS);//ʧЧ
		redisTemplate.convertAndSend("topic123","hello there!");//����
		List<User> queryAll = adminMapperService.queryAll();
		
		model.addAttribute("users",queryAll);
		
		return "index";
	}
	
    @RequestMapping("delete/{sessionId}")  
    public String forceLogout(@PathVariable("sessionId") String sessionId) {  
    	//�Լ���װ�ĸ���sessionid ��ȡsession
    	Session session = ShiroSession.getSession(sessionId);
    	if(session != null) {  
            session.setAttribute(  //��ǿ���˳���session ����һ������ʶ��
                Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);  
        }  
		return "redirect:/getUser";
    }  
	
	@RequestMapping("json")
	@ResponseBody
	public List<User> index2(){
		redisTemplate.opsForValue().set("aop123", "����das",30);
		List<User> queryAll = adminMapperService.queryAll();
		
		return queryAll;
	}
	/**
	 * result���
	 * @param num
	 * @param model
	 * @return
	 * @throws IsNanException
	 */
	@RequestMapping("json/{num:\\d+}")
	@ResponseBody
	public PageInfo<User> Lindex3(@PathVariable String num,Model model) throws IsNanException{

		PageInfo<User> queryAllpage = adminMapperService.queryAllpage(num);
		
		return queryAllpage;
	}
	
	@RequestMapping("getUser")
	public String innerMain(Model model){
		
		Object object = redisTemplate.opsForValue().get("holle");
		Object object1 = redisTemplate.opsForValue().get("aop");
		System.out.println("redis : holle="+object);
		System.out.println("redis : aop="+object1);
		//��ȡ��ǰ���е�session
		Collection<Session> sessions = ShiroSession.getSessions();
		model.addAttribute("sessions", sessions);
		model.addAttribute("sesessionCount", sessions.size());  
		return "getUser" ;
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest req,Model model){
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated())
			return "redirect:index";
		if("1".equals(req.getParameter("forceLogout"))){
			model.addAttribute("info", "���ѱ�ǿ���˳�");
		}else if("2".equals(req.getParameter("forceLogout"))){
			model.addAttribute("info", "���˺��������ص��¼");
		}
		return "login/index";
	}
	@RequestMapping("loginout")
	public String loginout(){
		Subject subject = SecurityUtils.getSubject();
		log.info("�û���["+subject.getPrincipal()+"]�˳��ɹ�");
		subject.logout();
		return "redirect:json/1";
	}
	
	@RequestMapping("logindo")
	public String onlogin(String username,String password,Model model){
		
		System.out.println(username+"--"+password);
		
		
		
//		return "index";
		
		//��ȡ��ǰ�ĻỰ(ȫ�ֻ�ȡ)
		Subject subject = SecurityUtils.getSubject();
		
		boolean  sub= true; 
		
		//�����ж��û��Ƿ��¼(��Ȩ)
		if(!subject.isAuthenticated()){
			//ͨ���û������������Ƶ�¼
			UsernamePasswordToken upt = new UsernamePasswordToken(username, password);
			//
//			upt.setRememberMe(true);
			try {//��׽��¼�쳣
				
				//ִ�е�¼
				subject.login(upt);
				
			} catch (UnknownAccountException e) {//û���˻�
				System.err.println("�û���������"); 
				
				sub = false;
			}catch (IncorrectCredentialsException e) {//����ȷ��ƾ֤
				System.err.println("�������");
				model.addAttribute("info", "�������");
				sub = false;
			}
			
			
			
		}
		
		
		if(sub){
			log.info("�û���["+subject.getPrincipal()+"]��½�ɹ�");
			return "redirect:index";
		}else{
			log.info(subject.getPrincipal()+"�û��������ڻ��������");
			model.addAttribute("info", "�û��������ڻ��������");
			return "login/index";
		}
		
		
	}
	
}
