package com.lcl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("error")
public class ErrorController {
	
	@RequestMapping("unauth")
	public String unauth(){
		return "error/unauth";
	}
	
}
