package com.lcl.mapper;

import org.apache.ibatis.annotations.Param;

import com.lcl.entity.User;

public interface LoginMapper {
	
	User checkLogin(@Param(value = "username") String username,@Param(value = "password") String password);
	
}
