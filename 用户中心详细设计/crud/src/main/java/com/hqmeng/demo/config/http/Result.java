package com.hqmeng.demo.config.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    //是否成功
    private Boolean success;
    //状态码
    private Integer code;
    //提示信息
    private String msg;
    //数据
    private T data;
    //自定义异常返回的结果
    public static Result bizError(BizException be){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(be.getErrorCode());
        result.setMsg(be.getErrorMsg());
        result.setData(null);
        return result;
    }
    //其他异常处理方法返回的结果
    public static Result otherError(Exception e){
        Result result = new Result();
        result.setMsg(e.getMessage());
        result.setCode(500);
        result.setSuccess(false);
        result.setData(null);
        return result;
    }

    public static Result success(Object body){
        Result result = new Result();
        result.setMsg("success");
        result.setCode(200);
        result.setSuccess(false);
        result.setData(body);
        return result;
    }
}
