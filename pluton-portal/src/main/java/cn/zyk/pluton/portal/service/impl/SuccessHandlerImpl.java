package cn.zyk.pluton.portal.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class SuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.debug("登录成功");
        httpServletResponse.setCharacterEncoding("UTF-8"); //设置输出信息的编码格式
        UserDetails user= (UserDetails) authentication.getPrincipal(); //获取登录用户的主体
        System.out.println(user); //这里打印测试下是不是我们要的信息
        PrintWriter wirter = httpServletResponse.getWriter();
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ObjectMapper om=new ObjectMapper();
        String success= om.writeValueAsString("success");
        wirter.print(success);
    }
}
