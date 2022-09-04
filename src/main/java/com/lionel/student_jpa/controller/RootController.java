package com.lionel.student_jpa.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lionel.student_jpa.constants.Const;
import com.lionel.student_jpa.utils.Auth;

@Controller
public class RootController {
    
    @GetMapping( value = "/" )
	public String getRoot( HttpSession session )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		return "MNU001";
	}
	
	@GetMapping( value = "/home" )
	public String getHomePage( HttpSession session )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}
		
		return "MNU001";
	}

}
