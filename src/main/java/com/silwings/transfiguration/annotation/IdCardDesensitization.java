package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.IdCardDesensitizationStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName IdCardDesensitization
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 21:40
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataDesensitization(strategy = IdCardDesensitizationStrategy.class)
public @interface IdCardDesensitization {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataDesensitization.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataDesensitization.class)
    Class<? extends DesensitizationStrategy> strategy() default IdCardDesensitizationStrategy.class;
}
