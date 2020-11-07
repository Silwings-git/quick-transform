package com.silwings.transfiguration.annotation;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.NameDesensitizationStrategy;
import org.springframework.stereotype.Controller;

import java.lang.annotation.*;

/**
 * @ClassName MethodDesensitization
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 18:20
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface MethodDesensitization {
    /**
     * 是否执行脱敏,默认是
     */
    boolean execute() default true;

    /**
     * 策略类class,如果返回值是
     */
    Class<? extends DesensitizationStrategy> strategy() default DesensitizationStrategy.class;


    Class<? extends Annotation> strategyAnno() default DataDesensitization.class;

}
