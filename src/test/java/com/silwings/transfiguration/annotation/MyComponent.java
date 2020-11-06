package com.silwings.transfiguration.annotation;

import java.lang.annotation.*;

/**
 * @ClassName MyComponent
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/5 23:14
 * @Version V1.0
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {
    String value() default "";
}
