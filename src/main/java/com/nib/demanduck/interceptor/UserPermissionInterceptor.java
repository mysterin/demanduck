package com.nib.demanduck.interceptor;

import com.alibaba.fastjson2.JSON;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 20:46
 */
@Slf4j
public class UserPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Token");
        if (StringUtils.isBlank(token)) {
            errorResponse(response, Response.error(ErrorCode.USER_NOT_LOGIN));
            return false;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // TODO

        }
        return true;
    }

    public void errorResponse(HttpServletResponse response, Response res) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json");
        try {
            PrintWriter writer = response.getWriter();
            writer.print(JSON.toJSONString(res));
        } catch (IOException e) {
            log.error("response error: {}", res, e);
        }
    }
}
