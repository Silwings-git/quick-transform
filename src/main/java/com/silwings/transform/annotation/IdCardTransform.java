package com.silwings.transform.annotation;

import com.silwings.transform.enums.BackupsEnum;
import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.strategy.specific.IdCardTransformStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.silwings.transform.enums.BackupsEnum.FOLLOW;

/**
 * @ClassName IdCardTransform
 * @Description 身份证
 * @Author 崔益翔
 * @Date 2020/11/7 21:40
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD})
@DataTransform(strategy = IdCardTransformStrategy.class)
public @interface IdCardTransform {
    /**
     * 是否执行脱敏,默认是
     */
    @AliasFor(annotation = DataTransform.class)
    boolean execute() default true;

    /**
     * @return 是否开启数据备份
     */
    @AliasFor(annotation = DataTransform.class)
    BackupsEnum backups() default FOLLOW;

    /**
     * 策略类class
     */
    @AliasFor(annotation = DataTransform.class)
    Class<? extends TransformStrategy> strategy() default IdCardTransformStrategy.class;
}
