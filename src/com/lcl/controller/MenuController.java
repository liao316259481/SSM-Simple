package com.lcl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("menu")
public class MenuController {

	@RequestMapping("add")
//	@ResponseBody
	public String addMenu(Model model){
		return "main/index";
	}
	
}
