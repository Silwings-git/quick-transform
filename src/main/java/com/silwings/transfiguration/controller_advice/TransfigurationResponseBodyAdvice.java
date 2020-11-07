package com.silwings.transfiguration.controller_advice;

import com.silwings.transfiguration.annotation.Transfiguration;
import com.silwings.transfiguration.processor.DesensitizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * @ClassName TransfigurationResponseBodyAdvice
 * @Description 响应体增强, 用于判断返回实例是否需要脱敏
 * @Author 崔益翔
 * @Date 2020/10/11 10:47
 * @Version V1.0
 **/
@ControllerAdvice
public class TransfigurationResponseBodyAdvice implements ResponseBodyAdvice {

    private DesensitizationManager desensitizationManager;

    @Autowired
    public void setDesensitizationManager(DesensitizationManager desensitizationManager) {
        this.desensitizationManager = desensitizationManager;
    }

    /**
     * description: 判断响应实例是否包含Transfiguration注解
     * version: 1.0
     * date: 2020/11/7 13:09
     * author: 崔益翔
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return boolean true:需要进行脱敏处理
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        System.out.println("TransfigurationResponseBodyAdvice");
        return null != returnType.getParameterType().getAnnotation(Transfiguration.class);
    }

    /**
     * description: 将需要脱敏的对象交由处理器处理
     * version: 1.0
     * date: 2020/11/7 13:10
     * author: 崔益翔
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return java.lang.Object 响应数据
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        System.out.println("body = " + body);
        return desensitizationManager.desensitization(body);
    }
}
