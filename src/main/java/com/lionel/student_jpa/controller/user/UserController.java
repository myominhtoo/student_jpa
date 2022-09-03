package com.lionel.student_jpa.controller.user;

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
import org.springframework.web.servlet.ModelAndView;

import com.lionel.student_jpa.model.User;
import com.lionel.student_jpa.service.UserService;
import com.lionel.student_jpa.utils.Generator;

@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping( value = "/login" )
	public ModelAndView getLoginPage()
	{
		return new ModelAndView( "LGN001" , "user" , new User() );
	}
	
	@PostMapping( value = "/login" )
	public String postLoginPage( @ModelAttribute("user") User user , ModelMap model , HttpServletRequest req  )
	{
		if( user.getId().equals("") || user.getPassword().equals(""))
		{
			model.addAttribute( "error", "Input must not be empty!");
			model.addAttribute("user", user);
			return "LGN001";
		}
		
		User savedUser = userService.findById( user.getId() );
		boolean isOk = false;

		if( savedUser != null )
		{
			if( savedUser.getPassword().equals(user.getPassword()))
			{
				isOk = true;
			}
		}
		
		if( !isOk )
		{
			model.addAttribute( "error" , "Please check data again!");
			model.addAttribute( "user", user );
			return "LGN001";
		}
		
		req.getSession().setAttribute( "authUser", savedUser );
		return "MNU001";
	}
	
	@GetMapping( value  = "/logout" )
	public String getLogout( HttpServletRequest req , ModelMap model  )
	{
		HttpSession session = req.getSession(false);
		
		session.removeAttribute("authUser");
		session.invalidate();
		
		return "redirect:/login?msg=Successfully Logout!";
	}
	
	
	@GetMapping( value = "/users" )
	public String getUsersPage( ModelMap model , HttpServletRequest req )
	{
		
		String id = req.getParameter("id");
		String name = req.getParameter("name");
		
		User user = new User();
		
		// if( id != null && !id.equals("") && name.equals(""))
		// {
		// 	user.setId( "%"+id+"%" );
		// 	model.addAttribute( "users", userDAO.findBy( user ) );
		// }
		
		// else if( name != null && !name.equals("") && id.equals("") )
		// {
		// 	user.setName( "%"+name+"%" );
		// 	model.addAttribute( "users", userDAO.findBy( user ));
		// }
		
		// else if( name != null && id != null && !id.equals("") && !name.equals(""))
		// {
		// 	user.setId( "%"+id+"%" );
		// 	user.setName( "%"+name+"%" );
		// 	model.addAttribute("users" , userDAO.findBy( user ));
		// }
		
		// else
		// {
		// 	model.addAttribute( "users", userDAO.find() );
		// }

		model.addAttribute( "users" , userService.findAll() );
	
		model.addAttribute( "user" ,  new User() );
		
		return "USR003";
	}
	
	@GetMapping( value = "/user/new" )
	public ModelAndView getUserCreatePage()
	{
		return new ModelAndView( "USR001" , "user" , new User() );
	}
	
	@PostMapping( value = "/user/new" )
	public String postUserCreate(@ModelAttribute("user") @Validated User user , BindingResult bind , ModelMap model  )
	{
		if( bind.hasErrors() )
		{
			model.addAttribute( "user" , user );
			return "USR001";
		}
		
		if( !user.getPassword().equals(user.getConfirmPassword()))
		{
			model.addAttribute( "error", "Confirm password must be equal with password!");
			model.addAttribute( "user" , user );
			return "USR001";
		}
		
		
		if( userService.isEmailDuplicate( user.getEmail() ) )
		{
			model.addAttribute( "error", " Email must be unique!");
			model.addAttribute( "user" , user );
			return "USR001";
		}
	
		user.setId( Generator.generateId( userService.getMaxid() , "USR" ));
			
		if( !userService.save( user ) )
		{
			model.addAttribute( "error" , "Something went wrong!");
			model.addAttribute( "user" , user );
			return "USR001";
		}
		
		model.addAttribute( "msg", "Successfully Registered!");
		model.addAttribute( "user", new User());
		return "USR001";
	}
	
	@GetMapping( value = "/users/{id}/delete" )
	public String getDeleteUser( @PathVariable("id") String id , ModelMap model )
	{
		if( !userService.deleteOne( id ) )
		{
			model.addAttribute( "error" , "Something went wrong!" );
			return "USR003";
		}
		
		return "redirect:/users?msg=Successfully Deleted!";
	}
	
	@GetMapping( value = "/users/{id}/update" )
	public String getUserUpdatePage( @PathVariable("id") String id , ModelMap model )
	{
		
		User savedUser = userService.findById( id );

		System.out.println(savedUser);
		
		if( savedUser == null ) return "USR003?msg=Something went wrong!";
		
		model.addAttribute( "user" , savedUser );
		
		return "USR002";
		
	}
	
	@PostMapping( value = "/users/{id}/update" )
	public String postUserUpdate(@PathVariable("id") String id , @ModelAttribute("user") @Validated User user , BindingResult bind , ModelMap model , HttpServletRequest req )
	{
		if( bind.hasErrors() )
		{
			model.addAttribute( "user", user);
			return "USR002";
		}
		
		if( !user.getPassword().equals(user.getConfirmPassword()))
		{
			model.addAttribute( "error", "Confirm password must be equal with password!");
			model.addAttribute( "user" , user );
			return "USR002";
		}

		User savedUser = userService.findByEmail( user.getEmail() );
		
		if( savedUser != null && !savedUser.getId().equals(user.getId()))
		{
			model.addAttribute( "error", "Email must be unique!");
			model.addAttribute( "user" , user );
			return "USR002";
		}
	
		
		if( !userService.updateOne( user ) )
		{
			model.addAttribute( "error" , "Something went wrong!");
			model.addAttribute( "user" , user );
			return "USR002";
		}
		
		// User authUser = (User) req.getSession().getAttribute("authUser");
		
		// if( user.getId().equals(authUser.getId()))
		// {
		// 	req.getSession().setAttribute( "authUser", user );
		// }
		
		return "redirect:/users?msg=Successfully Updated!";
			
	}

}
