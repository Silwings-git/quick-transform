package com.silwings.transfiguration.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Transfiguration
 * @Description 标记实体类中存在需要脱敏的属性或方法
 * @Author 崔益翔
 * @Date 2020/10/11 10:50
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Transfiguration {
}
