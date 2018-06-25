package com.lcl.resolver;

import java.io.IOException;
import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcl.dto.FormData;
import com.lcl.exception.IsNanException;


public class FileErrorsResolver extends AbstractHandlerExceptionResolver {//AbstractHandlerExceptionResolver

	private static final ModelAndView VIEW_ENPTY = new ModelAndView();

	private ObjectMapper obj = new ObjectMapper();
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest req, HttpServletResponse resp, Object o,
			Exception e) {
		try {
			if (e instanceof CannotCreateTransactionException) {
				resp.setContentType("application/json;charset=utf-8");
				resp.getWriter().write(obj.writeValueAsString(new FormData(false, "未连接数据库!")));
				return VIEW_ENPTY;
			}
			if (e instanceof IsNanException) {
				resp.setContentType("application/json;charset=utf-8");
				resp.getWriter().write(obj.writeValueAsString(new FormData(false, "输入字符不对!")));
				return VIEW_ENPTY;
			}
			if (e instanceof UnknownAccountException) {
				resp.setContentType("application/json;charset=utf-8");
				resp.getWriter().write(obj.writeValueAsString(new FormData(false, "用户名不存在!")));
				return VIEW_ENPTY;
			}
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
