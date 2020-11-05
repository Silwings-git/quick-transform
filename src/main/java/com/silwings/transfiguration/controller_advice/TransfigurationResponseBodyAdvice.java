package com.silwings.transfiguration.controller_advice;

import com.silwings.transfiguration.annotation.Transfiguration;
import com.silwings.transfiguration.processor.DesensitizationProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;

/**
 * @ClassName TransfigurationResponseBodyAdvice
 * @Description 响应体增强, 用于脱敏
 * @Author 崔益翔
 * @Date 2020/10/11 10:47
 * @Version V1.0
 **/
@ControllerAdvice
public class TransfigurationResponseBodyAdvice implements ResponseBodyAdvice {

    private DesensitizationProcessor desensitizationProcessor;

    @Autowired
    public void setDesensitizationProcessor(DesensitizationProcessor desensitizationProcessor) {
        this.desensitizationProcessor = desensitizationProcessor;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        System.out.println("TransfigurationResponseBodyAdvice");
        return null != returnType.getParameterType().getAnnotation(Transfiguration.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        System.out.println("body = " + body);
        return desensitizationProcessor.desensitization(body);
    }
}
