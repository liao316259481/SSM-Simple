package com.lcl.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lcl.entity.User;

public interface AdminMapperService {

	List<User> queryAll();
	PageInfo<User> queryAllpage(String num);
}
