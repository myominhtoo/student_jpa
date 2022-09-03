package com.lionel.student_jpa.utils;

import javax.servlet.http.HttpSession;

public class Auth {
    
    public static String check( HttpSession session , String targetKey ){

        String target = null;

        if( session.getAttribute(targetKey) == null ){
            target = "redirect:/";
        }

        return target;
        
    }

}
