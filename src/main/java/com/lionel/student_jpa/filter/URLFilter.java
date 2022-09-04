package com.lionel.student_jpa.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter({
    "/users/*",
    "/students/*",
    "/courses/*",
})
public class URLFilter implements Filter {
    
    @Override
    public void doFilter( ServletRequest req , ServletResponse res , FilterChain filterChain ){
        System.out.println("Remote Host "+req.getRemoteHost());
        System.out.println("Remote Address "+req.getRemotePort());
    }

}
