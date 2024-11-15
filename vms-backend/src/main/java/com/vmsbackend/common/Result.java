package com.vmsbackend.common;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Long total;
    private Object data;

    public static Result fail(){
        return result(400, "失败", 0L, null);
    }

    public static Result succeed(){
        return result(200, "成功", 0L, null);
    }

    public static Result succeed( Object _data){
        return result(200, "成功", 0L, _data);
    }

    public static Result succeed(Long _total, Object _data){
        return result(200, "成功", _total, _data);
    }

    public static Result result(Integer _code, String _msg, Long _total, Object _data){
        Result result = new Result();
        result.setCode(_code);
        result.setMsg(_msg);
        result.setTotal(_total);
        result.setData(_data);
        return result;
    }
}
