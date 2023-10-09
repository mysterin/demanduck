package com.nib.demanduck.interceptor;

import com.alibaba.fastjson2.JSON;
import com.nib.demanduck.response.Response;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.service.UserService;
import com.nib.demanduck.util.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author linxiaobin
 * @Description 登录拦截器
 * @date 2023/8/28 20:46
 */
@Slf4j
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Token");
        if (StringUtils.isBlank(token)) {
            errorResponse(response, ErrorCode.USER_NOT_LOGIN);
            return false;
        }
        User user = userService.getByToken(token);
        if (user == null) {
            errorResponse(response, ErrorCode.USER_NOT_LOGIN);
            return false;
        }

        // 设置线程变量
        ThreadLocalUtils.setUserId(user.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除线程变量
        ThreadLocalUtils.removeUserId();
    }

    public void errorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(Response.error(errorCode)));
        } catch (IOException e) {
            log.error("response error: {}", errorCode, e);
        }
    }
}
