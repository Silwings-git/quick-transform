package com.silwings.transform.strategy.specific;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.properties.TransformProperties;

/**
 * @ClassName PasswordTransformStrategy
 * @Description 密码脱敏策略类
 * @Author 崔益翔
 * @Date 2020/11/7 21:37
 * @Version V1.0
 **/
public class PasswordTransformStrategy implements TransformStrategy<String> {
    private static final String PWD = "********";
    private static final Character DEFAULT_CHAR = '*';

    private TransformProperties transformProperties;

    public PasswordTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
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
    public String transform(String password) {
        if (null == password || password.length() == 0) {
            return password;
        }
        if (DEFAULT_CHAR.equals(transformProperties)) {
            return PWD;
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(transformProperties.getReplaceSymbol(8));
            return buffer.toString();
        }
    }
}
