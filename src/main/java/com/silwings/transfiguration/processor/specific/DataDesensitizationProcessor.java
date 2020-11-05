package com.silwings.transfiguration.processor.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.processor.DesensitizationProcessor;
import com.silwings.transfiguration.utils.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
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
        Objects.requireNonNull(body, "body must be not null !");
        System.out.println("body:" + body);
//        此处拿到对象,应该遍历对象所有变量的注解,并使用注解名称获取desensitizationStrategyMap中对应的脱敏策略进行数据转换
        List<Field> allField = ReflectUtil.getFieldByCurrentAndSuper(body.getClass());
        if (null != allField && allField.size() > 0) {
//            获取到字段信息,进行注解遍历

        }
        return body;
    }
}
