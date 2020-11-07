package com.silwings.transfiguration.handler.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.handler.DesensitizationHandler;

import java.util.Objects;

/**
 * @ClassName DesensitizationHandlerImpl
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 10:50
 * @Version V1.0
 **/
public class DesensitizationHandlerImpl implements DesensitizationHandler {
    @Override
    public <T> T execute(T source, DesensitizationStrategy strategy) {
        return (T)strategy.desensitization(source);
    }
}
