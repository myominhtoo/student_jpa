package com.lionel.student_jpa.controller.course;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lionel.student_jpa.constants.Const;
import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.service.CourseService;
import com.lionel.student_jpa.utils.Auth;

@Controller
public class CourseController {

	@Autowired 
	CourseService courseService;
    
    @GetMapping( value = "/courses/new" )
	public String getCreateCoursePage( HttpSession session , ModelMap model  )
	{
		
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		model.addAttribute("course", new Course() );

		return "BUD003";
	}
	
	@PostMapping( value = "/courses/new" )
	public String postCreateCourse( HttpSession session , @ModelAttribute("course") @Validated Course course , BindingResult bind , ModelMap model )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		if( bind.hasErrors() )
		{
			model.addAttribute( "course", course );
			return "BUD003";
		}
	
		
		if( courseService.isDuplicate( course.getId()) )
		{
			model.addAttribute( "error", "Duplicate Course Id!");
			model.addAttribute( "course", course );
			return "BUD003";
		}
	
		
		if( !courseService.save( course ))
		{
			model.addAttribute( "error" , "Something went wrong!" );
			model.addAttribute( "course" , course );
			return "BUD003";
		}
		
		model.addAttribute( "msg", "Successfully Registered!" );
		model.addAttribute( "course", new Course());
		
		return "BUD003";
	}

}
