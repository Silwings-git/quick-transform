package com.silwings.transfiguration.processor.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.processor.DesensitizationProcessor;

import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName DataDesensitizationProcessor
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/10/11 11:51
 * @Version V1.0
 **/
public class DataDesensitizationProcessor implements DesensitizationProcessor {
    private HashMap<String, DesensitizationStrategy> desensitizationStrategyMap = new HashMap<>(16);

    public HashMap<String, DesensitizationStrategy> addDesensitizationStrategy(DesensitizationStrategy desensitizationStrategy) {
        Objects.requireNonNull(desensitizationStrategy);
        desensitizationStrategyMap.put(desensitizationStrategy.getClass().getName(), desensitizationStrategy);
        return desensitizationStrategyMap;
    }

    @Override
    public Object desensitization(Object body) {
        System.out.println("body:" + body);
        return body;
    }
}
