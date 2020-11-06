package com.silwings.transfiguration.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @ClassName MyController
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/5 23:14
 * @Version V1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyComponent
public @interface MyController {
    @AliasFor(annotation = MyComponent.class)
    String value() default "";
}
