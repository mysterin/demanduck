package com.nib.demanduck.aop;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.api.request.BaseCompanyRequest;
import com.nib.demanduck.api.request.BaseProjectRequest;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.constant.UserRoleEnum;
import com.nib.demanduck.entity.Project;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.ProjectService;
import com.nib.demanduck.service.UserRoleService;
import com.nib.demanduck.util.ThreadLocalUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    private UserRoleService userRoleService;
    @Autowired
    private ProjectService projectService;

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
        Long companyId = null;
        Long projectId = null;
        if (Objects.nonNull(args0) && args0 instanceof BaseCompanyRequest) {
            BaseCompanyRequest baseCompanyRequest = (BaseCompanyRequest) args0;
            companyId = baseCompanyRequest.getCompanyId();
        }
        if (Objects.nonNull(args0) && args0 instanceof BaseProjectRequest) {
            BaseProjectRequest baseProjectRequest = (BaseProjectRequest) args0;
            projectId = baseProjectRequest.getProjectId();

            if (Objects.isNull(companyId) && Objects.nonNull(projectId)) {
                Project project = projectService.getById(projectId);
                companyId = Objects.nonNull(project) ? project.getCompanyId() : null;
                baseProjectRequest.setCompanyId(companyId);
                log.debug("设置companyId={}", companyId);
            }
        }
        if (!userRoleService.hasPermission(companyId, projectId, userId, userPermission.value())) {
            throw new ServiceException(ErrorCode.USER_PERMISSION_ERROR);
        }
    }

}
