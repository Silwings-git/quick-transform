package com.silwings.transfiguration.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @ClassName MyRestController
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/5 23:19
 * @Version V1.0
 **/
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@MyController
@MyResponseBody
public @interface MyRestController {
    @AliasFor(annotation = MyComponent.class)
    String value() default "";

    @AliasFor(annotation = MyResponseBody.class)
    String name() default "";
}
