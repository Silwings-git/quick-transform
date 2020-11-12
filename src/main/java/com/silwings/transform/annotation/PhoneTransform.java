package com.silwings.transform.annotation;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.strategy.specific.PhoneTransformStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName PhoneTransform
 * @Description 标记属性使用手机号脱敏策略进行数据处理
 * 默认的处理规则是将手机号的4-8位使用'*'替换
 * @Author 崔益翔
 * @Date 2020/11/7 14:05
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataTransform(strategy = PhoneTransformStrategy.class)
public @interface PhoneTransform {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataTransform.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataTransform.class)
    Class<? extends TransformStrategy> strategy() default PhoneTransformStrategy.class;
}