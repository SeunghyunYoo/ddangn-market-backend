package com.ddangnmarket.ddangmarkgetbackend.interceptor;

import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author SeunghyunYoo
 */
@Slf4j
@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if(request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if(session == null || session.getAttribute(SessionConst.LOGIN_ACCOUNT) == null){
                ServletServerHttpResponse servletResponse = (ServletServerHttpResponse) response;

                HttpServletResponse httpServletResponse = servletResponse.getServletResponse();
                log.info("미인증 사용자 요청");
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write("미인증된 사용자 요청입니다.");
                return false;
            }
            attributes.put(SessionConst.LOGIN_ACCOUNT, session.getAttribute(SessionConst.LOGIN_ACCOUNT));
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
