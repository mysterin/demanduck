package com.nib.demanduck.interceptor;

import com.alibaba.fastjson2.JSON;
import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.entity.User;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.service.UserRoleService;
import com.nib.demanduck.service.UserService;
import com.nib.demanduck.util.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class UserPermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Token");
        String companyIdStr = request.getHeader("Company-Id");
        String projectIdStr = request.getHeader("Project-Id");
        Long companyId = StringUtils.isBlank(companyIdStr) ? null : Long.valueOf(companyIdStr);
        Long projectId = StringUtils.isBlank(projectIdStr) ? null : Long.valueOf(projectIdStr);
        if (StringUtils.isBlank(token)) {
            errorResponse(response, ErrorCode.USER_NOT_LOGIN);
            return false;
        }
        User user = userService.getByToken(token);
        if (user == null) {
            errorResponse(response, ErrorCode.USER_NOT_LOGIN);
            return false;
        }

        // 用户权限
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            UserPermission userPermission = handlerMethod.getMethodAnnotation(UserPermission.class);
            if (userPermission != null) {
                if (!userRoleService.hasPermission(companyId, projectId, user.getId(), userPermission.value())) {
                    errorResponse(response, ErrorCode.USER_PERMISSION_ERROR);
                    return false;
                }
            }
        }

        // 设置线程变量 userId
        ThreadLocalUtils.setUserId(user.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清除线程变量 userId
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
