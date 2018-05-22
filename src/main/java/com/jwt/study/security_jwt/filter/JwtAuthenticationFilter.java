package com.jwt.study.security_jwt.filter;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends GenericFilterBean {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = ((HttpServletRequest)request).getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            chain.doFilter(request,response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(((HttpServletRequest)request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token!=null){
            String user = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token.replace("Bearer ",""))
                    .getBody()
                    .getSubject();
            if(user!=null){
                return new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());
            }
            return null;
        }
        return null;
    }


}
