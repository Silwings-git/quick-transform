package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.properties.DesensitizationProperties;

/**
 * @ClassName PasswordDesensitizationStrategy
 * @Description 密码脱敏策略类
 * @Author 崔益翔
 * @Date 2020/11/7 21:37
 * @Version V1.0
 **/
public class PasswordDesensitizationStrategy implements DesensitizationStrategy<String> {
    private static final String PWD = "********";
    private static final Character DEFAULT_CHAR = '*';

    private DesensitizationProperties desensitizationProperties;

    public PasswordDesensitizationStrategy(DesensitizationProperties desensitizationProperties) {
        this.desensitizationProperties = desensitizationProperties;
    }

    /**
     * description: 密码直接返回固定字符串
     * version: 1.0
     * date: 2020/11/7 21:42
     * author: 崔益翔
     *
     * @param password
     * @return java.lang.String
     */
    @Override
    public String desensitization(String password) {
        if (null == password || password.length() == 0) {
            return password;
        }
        if (DEFAULT_CHAR.equals(desensitizationProperties)) {
            return PWD;
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(desensitizationProperties.getReplaceSymbol(8));
            return buffer.toString();
        }
    }
}
