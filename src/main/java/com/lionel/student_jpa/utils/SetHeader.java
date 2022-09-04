package com.lionel.student_jpa.utils;

import javax.servlet.http.HttpServletResponse;

public class SetHeader {
    
    public static void make( HttpServletResponse res ){

        res.setHeader("Cache-Control", "no-cache , no-store , must-revalidate");
        res.setHeader( "Pragma", "no-cache" );
        res.setHeader("Expires", "0");

    }

}
