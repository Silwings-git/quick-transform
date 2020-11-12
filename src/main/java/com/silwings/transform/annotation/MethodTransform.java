package com.silwings.transform.annotation;

import com.silwings.transform.strategy.TransformStrategy;

import java.lang.annotation.*;

/**
 * @ClassName MethodTransform
 * @Description 标记方法返回值需要进行数据处理
 * 优先级定义:
 * 1.如果在方法上添加了@MethodTransform却没有指定策略类时,将判断返回值是否包含@Transform注解
 * 1.1 包含:按照属性字段上的注解进行数据处理
 * 1.2 不包含:不进行任何操作
 * 2.如果在方法上添加了@MethodTransform并且指定策略类,或者添加了其他@DataTransform的语义化注解
 * 2.1 无论返回值上有无添加 @Transform注解,将按照方法上的注解进行数据处理
 * 2.2 如果遇到类型转换异常,将交由开发者手动处理
 * @Author 崔益翔
 * @Date 2020/11/7 18:20
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface MethodTransform {
    /**
     * 是否执行脱敏,默认是
     */
    boolean execute() default true;

    /**
     * 策略类class,如果返回值是
     */
    Class<? extends TransformStrategy> strategy() default TransformStrategy.class;

}
