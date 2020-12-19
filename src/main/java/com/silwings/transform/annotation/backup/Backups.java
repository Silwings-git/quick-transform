package com.silwings.transform.annotation.backup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName Backups
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/12/19 17:48
 * @Version V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Backups {
}
