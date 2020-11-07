package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;

import java.lang.reflect.Method;

/**
 * @ClassName PhoneDesensitizationStrategy
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/10/11 11:19
 * @Version V1.0
 **/
public class PhoneDesensitizationStrategy implements DesensitizationStrategy<String> {

    @Override
    public String desensitization(String phone) {
        if (null == phone || phone.length() == 0) {
            return phone;
        }
        char[] chars = phone.toCharArray();
//        只对手机号做简单的校验,这里只负责脱敏,不负责手机号的正确性
        if (chars.length == 11) {
            for (int i = 3; i < chars.length - 3; i++) {
                chars[i] = '*';
            }
        } else {
//            如果数据不符合要求全部屏蔽
            for (int i = 0; i < chars.length; i++) {
                chars[i] = '*';
            }
        }
        return String.valueOf(chars);
    }
}
