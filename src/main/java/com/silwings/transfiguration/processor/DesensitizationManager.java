package com.silwings.transfiguration.processor;

import com.silwings.transfiguration.annotation.DataDesensitization;
import com.silwings.transfiguration.annotation.MethodDesensitization;

/**
 * @ClassName DesensitizationManager
 * @Description 脱敏管理器抽象
 * @Author 崔益翔
 * @Date 2020/10/11 11:51
 * @Version V1.0
 **/
public interface DesensitizationManager {
    /**
     * description: desensitization
     * version: 1.0
     * date: 2020/11/5 21:37
     * author: 崔益翔
     *
     * @param body
     * @param dataDesensitization
     * @return java.lang.Object
     */
    Object desensitization(Object body, MethodDesensitization dataDesensitization);

    Object desensitizationOtherType(Object body);

    Object desensitizationBasicType(Object body, MethodDesensitization dataDesensitization);


}
