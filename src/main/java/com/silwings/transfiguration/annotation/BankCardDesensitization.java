package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.BankCardDesensitizationStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName BankCardDesensitization
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 22:23
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataDesensitization(strategy = BankCardDesensitizationStrategy.class)
public @interface BankCardDesensitization {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataDesensitization.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataDesensitization.class)
    Class<? extends DesensitizationStrategy> strategy() default BankCardDesensitizationStrategy.class;
}
