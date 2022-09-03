package com.lionel.student_jpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    
    @GetMapping( value = "/" )
	public String getRoot()
	{
		return "MNU001";
	}
	
	@GetMapping( value = "/home" )
	public String getHomePage()
	{
		return "MNU001";
	}

}
