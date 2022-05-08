package com.hqmeng.demo.config.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException{
    protected Integer errorCode;
    protected String errorMsg;

    public BizException(ErrorEnum errorEnum) {
        this.errorCode=errorEnum.getErrorCode();
        this.errorMsg=errorEnum.getErrorMsg();
    }

    public BizException(ErrorEnum errorEnum,String errorMsg) {
        this.errorCode=errorEnum.getErrorCode();
        this.errorMsg=errorMsg;
    }
}
