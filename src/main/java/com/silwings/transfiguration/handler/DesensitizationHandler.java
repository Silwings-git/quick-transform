package com.silwings.transfiguration.handler;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;

/**
 * @ClassName DesensitizationHandler
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 10:49
 * @Version V1.0
 **/
public interface DesensitizationHandler {

    <T> T execute(T source, DesensitizationStrategy strategy);
}
