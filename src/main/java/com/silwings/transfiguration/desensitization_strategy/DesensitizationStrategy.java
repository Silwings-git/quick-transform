package com.silwings.transfiguration.desensitization_strategy;

/**
 * @ClassName DesensitizationStrategy
 * @Description 脱敏策略抽象, 其子类必须实现desensitization方法
 * @Author 崔益翔
 * @Date 2020/10/11 11:15
 * @Version V1.0
 **/
public interface DesensitizationStrategy<T> {
    /**
     * description: 真正执行数据处理的方法,该方法返回的值将替换原先的值
     * version: 1.0
     * date: 2020/11/7 13:13
     * author: 崔益翔
     *
     * @param t 原先的值
     * @return T 脱敏后的值
     */
    T desensitization(T t);
}
