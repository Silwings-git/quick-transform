package com.silwings.transform.annotation;

import com.silwings.transform.enums.BackupsEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.silwings.transform.enums.BackupsEnum.FOLLOW;

/**
 * @ClassName Transfiguration
 * @Description 标记实体类中存在需要脱敏的属性或方法
 * @Author 崔益翔
 * @Date 2020/10/11 10:50
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Transform {
    /**
     * @return 是否开启数据备份
     */
    BackupsEnum backups() default FOLLOW;
}
