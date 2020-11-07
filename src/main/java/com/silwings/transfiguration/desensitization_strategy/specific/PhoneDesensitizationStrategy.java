package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;


/**
 * @ClassName PhoneDesensitizationStrategy
 * @Description 手机号脱敏策略
 * @Author 崔益翔
 * @Date 2020/10/11 11:19
 * @Version V1.0
 **/
public class PhoneDesensitizationStrategy implements DesensitizationStrategy<String> {

    /**
     * description: 对手机号进行脱敏
     * 该方法不会对手机号的格式进行校验
     * 该方法会将手机号的第4-8位使用'*'替换
     * version: 1.0
     * date: 2020/11/7 13:11
     * author: 崔益翔
     *
     * @param phone 原手机号
     * @return java.lang.String 新手机号
     */
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
