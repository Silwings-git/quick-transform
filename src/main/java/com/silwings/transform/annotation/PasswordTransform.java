package com.silwings.transform.annotation;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.strategy.specific.PasswordTransformStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName PasswordTransform
 * @Description 标记为密码脱敏
 * @Author 崔益翔
 * @Date 2020/11/7 21:36
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataTransform(strategy = PasswordTransformStrategy.class)
public @interface PasswordTransform {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataTransform.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataTransform.class)
    Class<? extends TransformStrategy> strategy() default PasswordTransformStrategy.class;
}
