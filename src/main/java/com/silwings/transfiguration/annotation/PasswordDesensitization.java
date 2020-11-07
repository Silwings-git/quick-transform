package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.PasswordDesensitizationStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName PasswordDesensitization
 * @Description 标记为密码脱敏
 * @Author 崔益翔
 * @Date 2020/11/7 21:36
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataDesensitization(strategy = PasswordDesensitizationStrategy.class)
public @interface PasswordDesensitization {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataDesensitization.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataDesensitization.class)
    Class<? extends DesensitizationStrategy> strategy() default PasswordDesensitizationStrategy.class;
}
