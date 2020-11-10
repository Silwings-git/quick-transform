package com.silwings.transform.strategy.specific;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.properties.TransformProperties;

/**
 * @ClassName NameTransformStrategy
 * @Description 名称脱敏策略
 * 默认规则为仅显示名称第一个字,剩余使用"*"替换
 * @Author 崔益翔
 * @Date 2020/11/7 13:23
 * @Version V1.0
 **/
public class NameTransformStrategy implements TransformStrategy<String> {

    private TransformProperties transformProperties;

    public NameTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
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
    public String transform(String name) {
        String newName = name;
        if (null != name) {
            int length = name.length();
            if (length > 0) {
                StringBuffer stringBuffer = new StringBuffer(length);
                stringBuffer.append(name.subSequence(0, 1));
                stringBuffer.append(transformProperties.getReplaceSymbol(length-1));
                newName = stringBuffer.toString();
            }
        }
        return newName;
    }
}
