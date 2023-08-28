package com.nib.demanduck.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author linxiaobin
 * @Description 服务异常
 * @date 2023/8/28 17:59
 */
@Getter
@Setter
public class ServiceException extends Exception {
    private ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
    }
}
