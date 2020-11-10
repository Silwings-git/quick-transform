package com.silwings.transfiguration.container;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Objects;

/**
 * @ClassName TransformStrategyContainer
 * @Description 脱敏策略容器
 * 维护一个HashMap<String, TransformStrategy>,项目启动时会获取Spring容器中所有的TransformStrategy实现类实例put到HashMap中
 * key: 策略类的全限定名
 * value: 策略类实例
 * @Author 崔益翔
 * @Date 2020/11/7 10:06
 * @Version V1.0
 **/
public class TransformStrategyContainer {
    private static final Logger log = LoggerFactory.getLogger(TransformStrategyContainer.class);

    private static TransformStrategyContainer instance = new TransformStrategyContainer();

    private TransformStrategyContainer() {
    }

    public static TransformStrategyContainer getInstance() {
        return instance;
    }

    private final HashMap<String, TransformStrategy> container = new HashMap(16);

    /**
     * description: 向容器添加一个策略类实例
     * version: 1.0
     * date: 2020/11/7 13:07
     * author: 崔益翔
     *
     * @param strategy 要添加的实例
     * @return com.silwings.transfiguration.container.TransformStrategyContainer
     */
    public TransformStrategyContainer addStrategy(TransformStrategy strategy) {
        Objects.requireNonNull(strategy, "禁止向脱敏策略容器中添加空策略");
//        防止动态代理生成策略,导致获取到的name与原class的name不同
        String key = strategy.getClass().getName().split("$")[0];
        if (null != container.put(key, strategy)) {
            throw new RuntimeException("脱敏策略:" + key + "重复");
        }
        log.info(key + "添加到策略容器成功");
        return this;
    }

    /**
     * description: 根据策略类class对象获取容器中对应的实例
     * version: 1.0
     * date: 2020/11/7 13:08
     * author: 崔益翔
     *
     * @param strategyClass 策略类class对象
     * @return com.silwings.transfiguration.transform_strategy.TransformStrategy
     */
    public TransformStrategy getStrategy(Class<? extends TransformStrategy> strategyClass) {
        Objects.requireNonNull(strategyClass, "获取脱敏策略时未指定策略类型");
        return container.get(strategyClass.getName());
    }

}
