package com.silwings.transfiguration.transform_strategy.specific;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.properties.TransformProperties;

/**
 * @ClassName EmailTransformStrategy
 * @Description 邮箱脱敏策略类
 * @Author 崔益翔
 * @Date 2020/11/7 22:54
 * @Version V1.0
 **/
public class EmailTransformStrategy implements TransformStrategy<String> {
    private static final String AT = "@";
    private TransformProperties transformProperties;

    public EmailTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
    }

    /**
     * description: 展示首个字符和@符号后面的值
     * version: 1.0
     * date: 2020/11/7 22:58
     * author: 崔益翔
     *
     * @param email
     * @return java.lang.String
     */
    @Override
    public String transform(String email) {
        String newEmail = email;
        if (null != email && email.contains(AT)) {
            int index = email.indexOf(AT);
            if (index > 0) {
                StringBuffer buffer = new StringBuffer(email);
                buffer.replace(1, index, transformProperties.getReplaceSymbol(index - 1));
                newEmail = buffer.toString();
            }
        }
        return newEmail;
    }
}
