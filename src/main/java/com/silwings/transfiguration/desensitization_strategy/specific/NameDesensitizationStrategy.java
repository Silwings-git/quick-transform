package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;

/**
 * @ClassName NameDesensitizationStrategy
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 13:23
 * @Version V1.0
 **/
public class NameDesensitizationStrategy implements DesensitizationStrategy<String> {
    @Override
    public String desensitization(String name) {
        String newName = name;
        if (null != name) {
            int length = name.length();
            if (length > 0) {
                StringBuffer stringBuffer = new StringBuffer(length);
                stringBuffer.append(name.subSequence(0, 1));
                for (int i = 1; i < length; i++) {
                    stringBuffer.append('*');
                }
                newName = stringBuffer.toString();
            }
        }
        return newName;
    }
}
