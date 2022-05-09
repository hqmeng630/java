package com.hqmeng.demo.config.oauth.error.authorization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hqmeng.demo.config.http.BizException;
import com.hqmeng.demo.config.http.Result;

import java.io.IOException;

/**
 * @author zhanghui
 * @date 2019/6/21
 */
public class MyOauthExceptionSerializer extends StdSerializer<MyOauthException> {

    public MyOauthExceptionSerializer() {
        super(MyOauthException.class);
    }

    @Override
    public void serialize(MyOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //输出自定义字符串
        String code = value.getOAuth2ErrorCode();
        String message = value.getMessage();
        BizException bizException = new BizException(code, message);
        Result result = Result.bizError(bizException);
        jsonGenerator.writeObject(result);
    }
}
