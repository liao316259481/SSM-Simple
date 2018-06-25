package com.lcl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lcl.entity.User;
import com.lcl.mapper.AdminMapper;
import com.lcl.service.AdminMapperService;

@Service
public class AdminMapperServiceImpl implements AdminMapperService {

	@Resource
	AdminMapper adminMapper;
	
//	@CacheEvict("queryAll")
	@Override
	public List<User> queryAll() {
		List<User> queryAll = adminMapper.queryAll();
		return queryAll;
	}
	@CacheEvict("queryAllpage")
	@Override
	public PageInfo<User> queryAllpage(String num) {
		int Page = num == null ? 1 : Integer.parseInt(num);
		PageHelper.startPage(Page, 2);
		List<User> queryAll = adminMapper.queryAll();
		PageInfo<User> p=new PageInfo<User>(queryAll);
		System.out.println(p.getList());
		return p;
	}

}
