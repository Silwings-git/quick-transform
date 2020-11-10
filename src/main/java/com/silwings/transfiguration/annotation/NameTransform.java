package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.transform_strategy.specific.NameTransformStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName NameTransform
 * @Description 标记按照名称脱敏规则进行脱敏
 * 默认的名称脱敏规则是仅显示姓名第一个字,后续的字使用"*"替换
 * @Author 崔益翔
 * @Date 2020/11/7 13:55
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataTransform(strategy = NameTransformStrategy.class)
public @interface NameTransform {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataTransform.class)
    boolean execute() default true;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataTransform.class)
    Class<? extends TransformStrategy> strategy() default NameTransformStrategy.class;
}
