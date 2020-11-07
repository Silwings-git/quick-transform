package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;

import java.lang.annotation.*;

/**
 * @ClassName MethodDesensitization
 * @Description 标记方法返回值需要进行脱敏
 * 1.如果标记的方法返回值是一个标记有@Transfiguration注解的类的实例,将会按照该
 *   对象的属性上添加的@DataDesensitization及其他语义化注解声明的策略进行处理,
 *   1.1 如果返回类上声明了与类上注解不同的脱敏策略,以类上的为准.
 *   1.2 如果返回类上没有声明@Transfiguration注解将不会进行任何操作
 * 2.如果标记的方法返回的是一个基本数据类型或者字符串,将按照该注解定义的strat-
 *   -egy进行处理
 * @Author 崔益翔
 * @Date 2020/11/7 18:20
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface MethodDesensitization {
    /**
     * 是否执行脱敏,默认是
     */
    boolean execute() default true;

    /**
     * 策略类class,如果返回值是
     */
    Class<? extends DesensitizationStrategy> strategy() default DesensitizationStrategy.class;

}
