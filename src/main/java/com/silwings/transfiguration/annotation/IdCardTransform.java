package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.transform_strategy.specific.IdCardTransformStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName IdCardTransform
 * @Description 身份证
 * @Author 崔益翔
 * @Date 2020/11/7 21:40
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataTransform(strategy = IdCardTransformStrategy.class)
public @interface IdCardTransform {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataTransform.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataTransform.class)
    Class<? extends TransformStrategy> strategy() default IdCardTransformStrategy.class;
}
