package com.lionel.student_jpa.controller.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.lionel.student_jpa.model.Student;
import com.lionel.student_jpa.service.StudentService;
import com.lionel.student_jpa.utils.Generator;

@Controller
public class StudentController {

	@Autowired
	StudentService studentDAO;
    
    @GetMapping( value = "/students" )
	public String getStudentsPage( HttpServletRequest req , ModelMap model   )
	{
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		String course = req.getParameter("course");
		
		List<Student> foundStudents = new ArrayList<>();
		
		// if( id == null && name == null && course == null )
		// {
		// 	foundStudents = studentDAO.find();			
		// }
		// else
		// {
		// 	Student student = new Student();
			
		// 	if( !id.equals("") && name.equals("") && course.equals(""))
		// 	{
		// 		student.setId("%"+id+"%");
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else if( id.equals("") && !name.equals("") && course.equals(""))
		// 	{
		// 		student.setName("%"+name+"%");
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else if( id.equals("") && name.equals("") && !course.equals(""))
		// 	{
		// 		student.setCourse("%"+course+"%");
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else if( !id.equals("") && !name.equals("") && course.equals(""))
		// 	{
		// 		student.setId( "%"+id+"%");
		// 		student.setName("%"+name+"%");
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else if( !id.equals("") && name.equals("") && !course.equals(""))
		// 	{
		// 		student.setId("%"+id+"%");
		// 		student.setCourse( "%"+course+"%" );
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else if( id.equals("") && !name.equals("") && !course.equals(""))
		// 	{
		// 		student.setName( "%"+name+"%");
		// 		student.setCourse( "%"+course+"%" );
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else if( !id.equals("") && !name.equals("") && !course.equals(""))
		// 	{
		// 		student.setId( "%"+id+"%");
		// 		student.setName( "%"+name+"%" );
		// 		student.setCourse( "%"+course+"%" );
		// 		foundStudents = studentDAO.findBy( student );
		// 	}
			
		// 	else
		// 	{
		// 		foundStudents = studentDAO.find();
		// 	}
		// }
		
		model.addAttribute( "student", new Student());
		// CourseService.setAttendCoursesToStudents( studentDAO, foundStudents );
		model.addAttribute( "students", foundStudents );
		
		return "STU003";
		
	}
	
	@GetMapping( value = "/student/new" )
	public ModelAndView getStudentCreatePage( ModelMap model )
	{
		// model.addAttribute( "courses", courseDAO.find() ); 
		
		Student student = new Student();
		// student.setId( Generator.generateId( studentDAO.getMaxId() , "STU" ));
		
		return new ModelAndView( "STU001" , "student" , student );
	}
	
	@PostMapping( value = "/student/new" )
	public String postStudentCreate(@ModelAttribute("student") @Validated Student student , BindingResult bind , ModelMap model  )
	{

		if( bind.hasErrors() )
		{
			// model.addAttribute( "courses", courseDAO.find() ); 
			model.addAttribute( "student" , student );
			return "STU001";
		}
		
		// int status1 = studentDAO.save( student );
		int status2 = 0;
		
		// if( student.getCourses().size() > 0 )
		// {
		// 	status2  = studentDAO.attendCourses( student.getId() , student.getCourses() );
		// }
		
		// if( status1 + status2  == 0 )
		// {
		// 	model.addAttribute( "error", "Something went wrong!");
		// 	model.addAttribute( "student", student );
		// 	return "STU001";
		// }
		
		return "redirect:/students?msg=Successfully Registered!";
	}
	
	
	@GetMapping( value = "/students/{id}" )
	public String getStudentDetailPage( @PathVariable("id") String id , ModelMap model )
	{
		// Student student = studentDAO.findById( id );
		
	// 	if( student != null  )
	// 	{
	// 			ArrayList<String> courses = new ArrayList<>();
				
	// 			for( StudentAttendCourse attend : studentDAO.findAttendCoursesById( id ) )
	// 			{
	// 				courses.add( attend.getCourseId() );
	// 			}
	// 			student.setCourses( courses );
			
	// 		model.addAttribute( "student" , student  );
	// 	}
	// 	else
	// 	{
	// 		return "redirect:/users?msg=Something went wrong!";
	// 	}
		
	// 	model.addAttribute( "courses", courseDAO.find() ); 
		
	// 	return "STU002";
	// }
	
	// @GetMapping( value = "/students/{id}/update" )
	// public String getStuentUpdatePage( @PathVariable("id") String id , ModelMap model )
	// {
	// 	Student student = studentDAO.findById( id );
		
	// 	if( student != null )
	// 	{
	// 		ArrayList<String> courses = new ArrayList<>();
				
	// 		for( StudentAttendCourse attend : studentDAO.findAttendCoursesById( id ) )
	// 		{
	// 			courses.add( attend.getCourseId() );
	// 		}
	// 		student.setCourses( courses );
				
	// 		model.addAttribute( "student" , student  );
	// 	}
	// 	else
	// 	{
	// 		return "redirect:/users?msg=Something went wrong!";
	// 	}
		
		
		// model.addAttribute( "courses", courseDAO.find() ); 
		
		return "STU002-01";
	}
	
	
	@PostMapping(  value = "/students/{id}/update" )
	public String postStudentUpdatePage( @PathVariable("id") String id , @ModelAttribute("student") @Validated Student student , BindingResult bind  , ModelMap model )
	{
		// if( bind.hasErrors() )
		// {
		// 	model.addAttribute( "courses", courseDAO.find() ); 
		// 	model.addAttribute( "student", student );
		// 	return "STU002-01";
		// }
		
		// studentDAO.deleteAttendCourses( id );
		
		// int status1 = studentDAO.update( student );
		// int status2 = 0;
	
		
		// if( student.getCourses().size() > 0 )
		// {
		// 	status2  = studentDAO.attendCourses( student.getId() , student.getCourses() );
		// }
		
	    // if( status1 + status2 == 0 )
	    // {
	    // 	model.addAttribute( "courses", courseDAO.find() ); 
	    // 	model.addAttribute( "error", "Something went wrong!");
	    // 	model.addAttribute( "student", student );
	    // 	return "STU002-01";
	    // }
	    
	    return "redirect:/students?msg=Successfully Updated!";
	
	}
	
	@GetMapping( value = "/students/{id}/delete" )
	public String getStudentDelete( @PathVariable("id") String id , ModelMap model )
	{
		// int status = studentDAO.deleteOne( id ) + studentDAO.deleteAttendCourses( id );
		
		// if( status  == 0 )
		// {
		// 	model.addAttribute( "error", "Something went wrong!");
		// 	return "STU003";
		// }
		
		return "redirect:/students?msg=Successfully Deleted!";
	
	}

}
