package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.transform_strategy.specific.FixedPhoneTransformStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName FixedPhoneTransform
 * @Description 固定电话
 * @Author 崔益翔
 * @Date 2020/11/7 22:45
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataTransform(strategy = FixedPhoneTransformStrategy.class)
public @interface FixedPhoneTransform {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataTransform.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataTransform.class)
    Class<? extends TransformStrategy> strategy() default FixedPhoneTransformStrategy.class;
}
