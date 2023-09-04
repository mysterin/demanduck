package com.nib.demanduck.api.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nib.demanduck.exception.ErrorCode;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author linxiaobin
 * @Description
 * @date 2023/8/28 18:09
 */
@Data
@Accessors(chain = true)
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;
    private List<T> list;
    private Long total;

    public static Response success() {
        return new Response()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg());
    }

    public static <T> Response success(T data) {
        return new Response()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg())
                .setData(data);
    }

    public static <T> Response success(List<T> data) {
        return new Response()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg())
                .setList(data);
    }

    public static <T> Response success(List<T> data, Long total) {
        return new Response()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg())
                .setList(data)
                .setTotal(total);
    }

    public static <T> Response success(IPage<T> page) {
        return new Response()
                .setCode(ErrorCode.SUCCESS.getCode())
                .setMsg(ErrorCode.SUCCESS.getMsg())
                .setList(page.getRecords())
                .setTotal(page.getTotal());
    }

    public static Response error() {
        return error(ErrorCode.SYSTEM_ERROR);
    }

    public static Response error(ErrorCode errorCode) {
        return new Response().setCode(errorCode.getCode()).setMsg(errorCode.getMsg());
    }

    public static Response error(ErrorCode errorCode, String msg) {
        return new Response().setCode(errorCode.getCode()).setMsg(msg);
    }
}
