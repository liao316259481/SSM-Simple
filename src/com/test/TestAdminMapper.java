package com.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.lcl.entity.User;
import com.lcl.service.AdminMapperService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-mybatis.xml")
public class TestAdminMapper {

	@Resource
	AdminMapperService adminMapperService;
	
	@Test
	public void testone() {
		System.out.println(adminMapperService.queryAll());
	}
	
	@Test
	public void testtwo(){
		PageInfo<User> queryAllpage = adminMapperService.queryAllpage("1");
		
		System.out.println(queryAllpage);
		System.out.println(queryAllpage.getList());
	}
	
	public static void main(String[] args) {
		int i = (20*1024)/(1);
		System.out.println(i);
	}
}
