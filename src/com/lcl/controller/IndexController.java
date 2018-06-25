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
	 * 测试
	 * 注入redis redisTemplate 存数据
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
		redisTemplate.opsForValue().set("aop", "切面12312",30);
		redisTemplate.expire("holle", 10, TimeUnit.SECONDS);//失效
		redisTemplate.convertAndSend("topic123","hello there!");//发布
		List<User> queryAll = adminMapperService.queryAll();
		
		model.addAttribute("users",queryAll);
		
		return "index";
	}
	
    @RequestMapping("delete/{sessionId}")  
    public String forceLogout(@PathVariable("sessionId") String sessionId) {  
    	//自己封装的根据sessionid 获取session
    	Session session = ShiroSession.getSession(sessionId);
    	if(session != null) {  
            session.setAttribute(  //给强制退出的session 增加一个属性识别
                Constants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);  
        }  
		return "redirect:/getUser";
    }  
	
	@RequestMapping("json")
	@ResponseBody
	public List<User> index2(){
		redisTemplate.opsForValue().set("aop123", "切面das",30);
		List<User> queryAll = adminMapperService.queryAll();
		
		return queryAll;
	}
	/**
	 * result风格
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
		//获取当前所有的session
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
			model.addAttribute("info", "你已被强制退出");
		}else if("2".equals(req.getParameter("forceLogout"))){
			model.addAttribute("info", "该账号在其它地点登录");
		}
		return "login/index";
	}
	@RequestMapping("loginout")
	public String loginout(){
		Subject subject = SecurityUtils.getSubject();
		log.info("用户：["+subject.getPrincipal()+"]退出成功");
		subject.logout();
		return "redirect:json/1";
	}
	
	@RequestMapping("logindo")
	public String onlogin(String username,String password,Model model){
		
		System.out.println(username+"--"+password);
		
		
		
//		return "index";
		
		//获取当前的会话(全局获取)
		Subject subject = SecurityUtils.getSubject();
		
		boolean  sub= true; 
		
		//首先判断用户是否登录(授权)
		if(!subject.isAuthenticated()){
			//通过用户名和密码令牌登录
			UsernamePasswordToken upt = new UsernamePasswordToken(username, password);
			//
//			upt.setRememberMe(true);
			try {//捕捉登录异常
				
				//执行登录
				subject.login(upt);
				
			} catch (UnknownAccountException e) {//没有账户
				System.err.println("用户名不存在"); 
				
				sub = false;
			}catch (IncorrectCredentialsException e) {//不正确的凭证
				System.err.println("密码错误");
				model.addAttribute("info", "密码错误");
				sub = false;
			}
			
			
			
		}
		
		
		if(sub){
			log.info("用户：["+subject.getPrincipal()+"]登陆成功");
			return "redirect:index";
		}else{
			log.info(subject.getPrincipal()+"用户名不存在或密码错误");
			model.addAttribute("info", "用户名不存在或密码错误");
			return "login/index";
		}
		
		
	}
	
}
