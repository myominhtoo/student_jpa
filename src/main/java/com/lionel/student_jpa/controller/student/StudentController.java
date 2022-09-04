package com.lionel.student_jpa.controller.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.lionel.student_jpa.constants.Const;
import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.service.CourseService;
import com.lionel.student_jpa.service.StudentService;
import com.lionel.student_jpa.utils.Auth;
import com.lionel.student_jpa.utils.Generator;

@Controller
public class StudentController {

	@Autowired
	StudentService studentService;

	@Autowired
	CourseService courseService;
    
    @GetMapping( value = "/students" )
	public String getStudentsPage( HttpSession session ,  HttpServletRequest req , ModelMap model   )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}
		
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String course = req.getParameter("course");
		
		List<Student> foundStudents = new ArrayList<>();
		
		if( id == null && name == null && course == null )
		{
			foundStudents = studentService.findAll();	
		}
		else
		{
			// Student student = new Student();
			
			if( !id.equals("") && name.equals("") && course.equals(""))
			{
				foundStudents = studentService.findWithId("%"+id+"%");
			}
			
			else if( id.equals("") && !name.equals("") && course.equals(""))
			{
				foundStudents = studentService.findWithName("%"+name+"%");
			}
			
			else if( id.equals("") && name.equals("") && !course.equals(""))
			{
				foundStudents = studentService.findWithCourse("%"+course+"%");
			}
			
			else if( !id.equals("") && !name.equals("") && course.equals(""))
			{
				foundStudents = studentService.findwithIdAndName( "%"+id+"%", "%"+name+"%" );
			}
			
			else if( !id.equals("") && name.equals("") && !course.equals(""))
			{
				foundStudents = studentService.findWithIdAndCourse( "%"+id+"%", "%"+course+"%");
			}
			
			else if( id.equals("") && !name.equals("") && !course.equals(""))
			{
				foundStudents = studentService.findWithNameAndCourse( "%"+name+"%", "%"+course+"%");
			}
			
			else if( !id.equals("") && !name.equals("") && !course.equals(""))
			{
				foundStudents = studentService.findWithIdAndNameAndCourse( "%"+id+"%", "%"+name+"%", "%"+course+"%");
			}
			
			else
			{
				foundStudents = studentService.findAll();
			}
		}
		
		model.addAttribute( "student", new Student());
		model.addAttribute( "students", foundStudents );
		
		return "STU003";
		
	}
	
	@GetMapping( value = "/students/new" )
	public String getStudentCreatePage( HttpSession session ,  ModelMap model )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		model.addAttribute( "courses", courseService.findAll() ); 
		
		Student student = new Student();
		student.setId( Generator.generateId( studentService.getMaxId() , "STU" ));
		
		model.addAttribute("student", student);

		return "STU001";
	}
	
	@PostMapping( value = "/students/new" )
	public String postStudentCreate( HttpSession session ,  @ModelAttribute("student") @Validated Student student , BindingResult bind , ModelMap model  )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		if( bind.hasErrors() )
		{
			model.addAttribute( "courses", courseService.findAll() ); 
			model.addAttribute( "student" , student );
			return "STU001";
		}
		
		if( !studentService.save( student ))
		{
			model.addAttribute( "error", "Something went wrong!");
			model.addAttribute( "student", student );
			return "STU001";
		}
		
		return "redirect:/students?msg=Successfully Registered!";
	}
	
	
	@GetMapping( value = "/students/{id}" )
	public String getStudentDetailPage( HttpSession session , @PathVariable("id") String id , ModelMap model )
	{

		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		Student student = studentService.findById( id );
		
		if( student == null )
		{
			return "redirect:/users?msg=Something went wrong!";
		}

		model.addAttribute( "student", student);
		model.addAttribute( "courses", courseService.findAll() ); 
		
		return "STU002";
	}
	
	@GetMapping( value = "/students/{id}/update" )
	public String getStuentUpdatePage( HttpSession session , @PathVariable("id") String id , ModelMap model )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		Student student = studentService.findById( id );
		
		if( student == null )
		{
			return "redirect:/users?msg=Something went wrong!";			
		}

		model.addAttribute( "student" , student );
		model.addAttribute( "courses", courseService.findAll() ); 
		
		return "STU002-01";
	}
	
	
	@PostMapping(  value = "/students/{id}/update" )
	public String postStudentUpdatePage( HttpSession session ,  @PathVariable("id") String id , @ModelAttribute("student") @Validated Student student , BindingResult bind  , ModelMap model )
	{
		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}

		if( bind.hasErrors() )
		{
			model.addAttribute( "courses", courseService.findAll() ); 
			model.addAttribute( "student", student );
			return "STU002-01";
		}
		
	    if( !studentService.updateOne( student) )
	    {
	    	model.addAttribute( "courses", courseService.findAll() ); 
	    	model.addAttribute( "error", "Something went wrong!");
	    	model.addAttribute( "student", student );
	    	return "STU002-01";
	    }
	    
	    return "redirect:/students?msg=Successfully Updated!";
	
	}
	
	@GetMapping( value = "/students/{id}/delete" )
	public String getStudentDelete( HttpSession session ,  @PathVariable("id") String id , ModelMap model )
	{

		if( !Auth.check(session, "authUser") ){
			return Const.REDIRECT;
		}
		
		if( !studentService.deleteOne( id ) )
		{
			model.addAttribute( "error", "Something went wrong!");
			return "STU003";
		}
		
		return "redirect:/students?msg=Successfully Deleted!";
	
	}

}
