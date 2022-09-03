package com.lionel.student_jpa.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lionel.student_jpa.model.Course;
import com.lionel.student_jpa.service.CourseService;

@Controller
public class CourseController {

	@Autowired 
	CourseService courseDAO;
    
    @GetMapping( value = "/course/new" )
	public ModelAndView getCreateCoursePage()
	{
		return new ModelAndView( "BUD003" , "course" , new Course() );
	}
	
	@PostMapping( value = "/course/new" )
	public String postCreateCourse(@ModelAttribute("course") @Validated Course course , BindingResult bind , ModelMap model )
	{
		if( bind.hasErrors() )
		{
			model.addAttribute( "course", course );
			return "BUD003";
		}
		
		// boolean isDuplicate = courseDAO.findById(course.getId()) == null ? false : true;
		
		// if( isDuplicate )
		// {
		// 	model.addAttribute( "error", "Duplicate Course Id!");
		// 	model.addAttribute( "course", course );
		// 	return "BUD003";
		// }
		
		// int status = courseDAO.save( course );
		
		// if( status == 0 )
		// {
		// 	model.addAttribute( "error" , "Something went wrong!" );
		// 	model.addAttribute( "course" , course );
		// 	return "BUD003";
		// }
		
		model.addAttribute( "msg", "Successfully Registered!" );
		model.addAttribute( "course", new Course());
		
		return "BUD003";
	}

}
