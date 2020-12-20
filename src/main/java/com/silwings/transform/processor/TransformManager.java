package com.silwings.transform.processor;

import com.silwings.transform.annotation.DataTransform;
import com.silwings.transform.annotation.MethodTransform;
import com.silwings.transform.enums.BackupsEnum;

/**
 * @ClassName TransformManager
 * @Description 脱敏管理器抽象
 * @Author 崔益翔
 * @Date 2020/10/11 11:51
 * @Version V1.0
 **/
public interface TransformManager {

    Object transformOtherType(Object body, BackupsEnum backupsEnum);

    Object transformBasicType(Object body, BackupsEnum backupsEnum, DataTransform dataTransform);

    Object transformBasicType(Object result, BackupsEnum backupsEnum, MethodTransform methodTransform);
}
