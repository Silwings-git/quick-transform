package com.silwings.transform.annotation;

import com.silwings.transform.strategy.TransformStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName DataTransform
 * @Description 标记属性或者方法返回值需要脱敏
 * 被标记的属性值或方法返回值会被指定的strategy进行修改
 * @Author 崔益翔
 * @Date 2020/10/11 11:12
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface DataTransform {
    /**
     * 是否执行脱敏,默认是
     */
    boolean execute() default true;

    /**
     * 策略类class
     */
    Class<? extends TransformStrategy> strategy();
}
