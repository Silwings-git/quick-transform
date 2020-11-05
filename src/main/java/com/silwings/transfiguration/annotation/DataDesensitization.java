package com.silwings.transfiguration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName DataDesensitization
 * @Description 标记属性或方法返回值需要脱敏
 * @Author 崔益翔
 * @Date 2020/10/11 11:12
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD})
public @interface DataDesensitization {
    String desensitizationStrategyName();
}
