package com.nib.demanduck.aop;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.RoleService;
import com.nib.demanduck.util.ReflectUtils;
import com.nib.demanduck.util.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/30 14:17
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Autowired
    private RoleService roleService;

    @Around("@annotation(userPermission)")
    public Object around(ProceedingJoinPoint joinPoint, UserPermission userPermission) throws Throwable {
        Object result = null;
        // 权限校验
        checkUserPermission(joinPoint, userPermission);

        result = joinPoint.proceed();
        return result;
    }

    private void checkUserPermission(ProceedingJoinPoint joinPoint, UserPermission userPermission) throws ServiceException {
        Object[] args = joinPoint.getArgs();
        Object args0 = args.length > 0 ? args[0] : null;
        Long userId = ThreadLocalUtils.getUserId();
        Long companyId = ReflectUtils.getLongFieldValue(args0, "companyId");
        if (!roleService.hasPermission(companyId, userId, userPermission.value())) {
            throw new ServiceException(ErrorCode.USER_PERMISSION_ERROR);
        }
    }

}
