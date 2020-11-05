package com.silwings.transfiguration.desensitization_strategy;

/**
 * @ClassName DesensitizationStrategy
 * @Description 脱敏策略抽象
 * @Author 崔益翔
 * @Date 2020/10/11 11:15
 * @Version V1.0
 **/
public interface DesensitizationStrategy <T>{
    boolean apply(Object obj);

    T desensitization(T t);
}
