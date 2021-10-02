package com.ddangnmarket.ddangmarkgetbackend.interceptor;

import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false);

       if(session == null || session.getAttribute(SessionConst.LOGIN_ACCOUNT) == null){
            log.info("미인증 사용자 요청");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            response.getWriter().write("미인증된 사용자 요청입니다.");
            return false;
        }
        return true;
    }
}
