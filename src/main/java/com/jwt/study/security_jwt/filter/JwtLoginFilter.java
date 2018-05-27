package com.jwt.study.security_jwt.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jwt.study.security_jwt.entity.MyUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    /*@Autowired
    public JwtLoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }*/


    /**
     * 接收并解析用户凭证
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
        try {
//            MyUser user = new ObjectMapper().readValue(request.getInputStream(), MyUser.class);
            MyUser user = JSON.parseObject(request.getInputStream(), MyUser.class);
           /* String username = request.getParameter("username");
            String password = request.getParameter("password");
*/
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            /*username,
                            password,*/
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((User)authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+ 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512,"MyJwtSecret")
                .compact();

        //写入Header
        //response.addHeader("Authorization","Bearer "+token);

        //写入Body
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String,Object> map = new HashMap<>();
        map.put("token","Bearer "+token);
        response.getOutputStream().println(JSON.toJSONString(map));
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Map<String,Object> map = new HashMap<>();
        map.put("status","500");
        map.put("message","Internal Server Error!!!");
        map.put("result",null);
        response.getOutputStream().println(JSON.toJSONString(map));
    }
}
