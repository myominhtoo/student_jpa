package com.lionel.student_jpa.utils;

import javax.servlet.http.HttpSession;

public class Auth {
    
    public static boolean check( HttpSession session , String targetKey ){

       boolean isAuth = true;

        if( session.getAttribute(targetKey) == null ){
           isAuth = false;
        }

        return isAuth;
        
    }

}
