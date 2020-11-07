package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.properties.DesensitizationProperties;

/**
 * @ClassName NameDesensitizationStrategy
 * @Description 名称脱敏策略
 * 默认规则为仅显示名称第一个字,剩余使用"*"替换
 * @Author 崔益翔
 * @Date 2020/11/7 13:23
 * @Version V1.0
 **/
public class NameDesensitizationStrategy implements DesensitizationStrategy<String> {

    private DesensitizationProperties desensitizationProperties;

    public NameDesensitizationStrategy(DesensitizationProperties desensitizationProperties) {
        this.desensitizationProperties = desensitizationProperties;
    }

    /**
     * description: 将名称字符串的首个字符以外的所有字符替换为配置文件中指定的replaceSymbol
     * version: 1.0
     * date: 2020/11/7 21:02
     * author: 崔益翔
     * @param name
     * @return java.lang.String
     */
    @Override
    public String desensitization(String name) {
        String newName = name;
        if (null != name) {
            int length = name.length();
            if (length > 0) {
                StringBuffer stringBuffer = new StringBuffer(length);
                stringBuffer.append(name.subSequence(0, 1));
                for (int i = 1; i < length; i++) {
                    stringBuffer.append(desensitizationProperties.getReplaceSymbol());
                }
                newName = stringBuffer.toString();
            }
        }
        return newName;
    }
}
