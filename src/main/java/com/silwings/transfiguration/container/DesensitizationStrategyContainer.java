package com.silwings.transfiguration.container;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;

import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName DesensitizationStrategyContainer
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 10:06
 * @Version V1.0
 **/
public class DesensitizationStrategyContainer {

    private static DesensitizationStrategyContainer instance = new DesensitizationStrategyContainer();

    private DesensitizationStrategyContainer() {
    }

    public static DesensitizationStrategyContainer getInstance() {
        return instance;
    }

    private final HashMap<String, DesensitizationStrategy> container = new HashMap(16);

    public DesensitizationStrategyContainer addStrategy(DesensitizationStrategy strategy) {
        Objects.requireNonNull(strategy, "禁止向脱敏策略容器中添加空策略");
        String key = strategy.getClass().getName();
        if (null != container.put(key, strategy)) {
            throw new RuntimeException("脱敏策略:" + key + "重复");
        }
        System.out.println(key + "添加到容器成功");
        return this;
    }

    public DesensitizationStrategy getStrategy(Class<? extends DesensitizationStrategy> strategyClass) {
        Objects.requireNonNull(strategyClass, "获取脱敏策略时未指定策略类型");
        return container.get(strategyClass.getName());
    }

}
