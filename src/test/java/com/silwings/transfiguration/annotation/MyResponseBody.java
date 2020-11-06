package com.silwings.transfiguration.annotation;

import java.lang.annotation.*;

/**
 * @ClassName MyResponseBody
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/5 23:17
 * @Version V1.0
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyResponseBody {
    String name() default "";
}
