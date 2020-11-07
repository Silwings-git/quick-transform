package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.PhoneDesensitizationStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName PhoneDesensitization
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 14:05
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
@DataDesensitization(strategy = PhoneDesensitizationStrategy.class)
public @interface PhoneDesensitization {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataDesensitization.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataDesensitization.class)
    Class<? extends DesensitizationStrategy> strategy() default PhoneDesensitizationStrategy.class;
}
