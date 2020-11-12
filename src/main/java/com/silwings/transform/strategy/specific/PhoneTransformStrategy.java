package com.silwings.transform.strategy.specific;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.properties.TransformProperties;


/**
 * @ClassName PhoneTransformStrategy
 * @Description 手机号脱敏策略
 * @Author 崔益翔
 * @Date 2020/10/11 11:19
 * @Version V1.0
 **/
public class PhoneTransformStrategy implements TransformStrategy<String> {
    private TransformProperties transformProperties;

    public PhoneTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
    }

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
    public String transform(String phone) {
        if (null == phone || phone.length() == 0) {
            return phone;
        }
        StringBuffer buffer = new StringBuffer(phone);
        buffer.replace(3, 8, transformProperties.getReplaceSymbol(5));
        return buffer.toString();
    }
}
