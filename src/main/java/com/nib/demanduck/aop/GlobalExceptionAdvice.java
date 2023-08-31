package com.nib.demanduck.aop;

import com.nib.demanduck.api.response.LoginUserData;
import com.nib.demanduck.api.response.Response;
import com.nib.demanduck.exception.ErrorCode;
import com.nib.demanduck.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @author linxiaobin
 * @Description 全局异常处理
 * @date 2023/8/28 17:59
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(BindException.class)
    public Response bindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        FieldError fieldError = fieldErrors.stream().findFirst().get();
        String msg = fieldError.getDefaultMessage();
        Response response = Response.error(ErrorCode.INVALID_PARAM, msg);
        return response;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        ConstraintViolation<?> constraintViolation = constraintViolations.stream().findFirst().get();
        String msg = constraintViolation.getMessage();
        Response response = Response.error(ErrorCode.INVALID_PARAM, msg);
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Response illegalArgumentException(IllegalArgumentException e) {
        log.info("参数异常", e);
        return Response.error(ErrorCode.INVALID_PARAM);
    }

    @ExceptionHandler(ServiceException.class)
    public Response serviceException(ServiceException e) {
        log.info("服务异常", e);
        return Response.error(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public Response exception(Exception e) {
        log.error("系统异常", e);
        return Response.error(ErrorCode.SYSTEM_ERROR);
    }
}
