package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;

/**
 * @ClassName PhoneDesensitizationStrategy
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/10/11 11:19
 * @Version V1.0
 **/
public class PhoneDesensitizationStrategy implements DesensitizationStrategy<String> {

    @Override
    public boolean apply(Object obj) {
        return true;
    }

    @Override
    public String desensitization(String phone) {
        char[] chars = phone.toCharArray();
        if (chars.length == 11) {
            for (int i = 3; i < chars.length - 3; i++) {
                chars[i] = '*';
            }
        }else {

        }
        return String.valueOf(chars);
    }
}
