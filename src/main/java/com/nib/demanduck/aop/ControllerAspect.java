package com.nib.demanduck.aop;

import com.nib.demanduck.annotation.UserPermission;
import com.nib.demanduck.constant.EntityType;
import com.nib.demanduck.constant.RoleEnum;
import com.nib.demanduck.entity.Demand;
import com.nib.demanduck.entity.Flaw;
import com.nib.demanduck.entity.Mission;
import com.nib.demanduck.entity.Project;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import com.nib.demanduck.service.*;
import com.nib.demanduck.util.ReflectUtils;
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
    private RoleService roleService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DemandService demandService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private FlawService flawService;

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
        RoleEnum[] roleEnum = userPermission.value();
        EntityType entityType = userPermission.entityType();
        Long companyId = null;
        Long projectId = null;
        switch (entityType) {
            case NO_ENTITY:
                break;
            case COMPANY:
                companyId = getBusinessId(args0, "companyId");
                break;
            case PROJECT:
                projectId = getBusinessId(args0, "projectId");
                if (Objects.nonNull(projectId)) {
                    Project project = projectService.getById(projectId);
                    companyId = project.getCompanyId();
                }
                break;
            case DEMAND:
                Long demandId = getBusinessId(args0, "demandId");
                if (Objects.nonNull(demandId)) {
                    Demand demand = demandService.getById(demandId);
                    companyId = demand.getCompanyId();
                    projectId = demand.getProjectId();
                }
                break;
            case MISSION:
                Long missionId = getBusinessId(args0, "missionId");
                if (Objects.nonNull(missionId)) {
                    Mission mission = missionService.getById(missionId);
                    companyId = mission.getCompanyId();
                    projectId = mission.getProjectId();
                }
                break;
            case FLAW:
                Long flawId = getBusinessId(args0, "flawId");
                if (Objects.nonNull(flawId)) {
                    Flaw flaw = flawService.getById(flawId);
                    companyId = flaw.getCompanyId();
                    projectId = flaw.getProjectId();
                }
                break;
            default:
                log.error("不支持的实体类型: {}", entityType);
                throw new ServiceException(ErrorCode.SYSTEM_ERROR);

        }
        if (!roleService.hasPermission(companyId, projectId, userId, roleEnum)) {
            throw new ServiceException(ErrorCode.USER_PERMISSION_ERROR);
        }
    }

    /**
     * 获取参数中的业务 id
     * @param args0
     * @param idName
     * @return
     */
    private Long getBusinessId(Object args0, String idName) {
        return ReflectUtils.getLongFieldValue(args0, idName);
    }

}
