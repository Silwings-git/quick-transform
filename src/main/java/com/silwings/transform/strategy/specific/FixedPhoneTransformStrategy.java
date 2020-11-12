package com.silwings.transform.strategy.specific;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.properties.TransformProperties;

/**
 * @ClassName FixedPhoneTransformStrategy
 * @Description 固定电话脱敏策略类
 * @Author 崔益翔
 * @Date 2020/11/7 22:46
 * @Version V1.0
 **/
public class FixedPhoneTransformStrategy implements TransformStrategy<String> {

    private TransformProperties transformProperties;

    public FixedPhoneTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
    }

    /**
     * description: 固定电话展示后四位
     * version: 1.0
     * date: 2020/11/7 22:47
     * author: 崔益翔
     *
     * @param fixedPhone
     * @return java.lang.String
     */
    @Override
    public String transform(String fixedPhone) {
        String newFixedPhone = fixedPhone;
        if (null != fixedPhone && fixedPhone.length() >= 4) {
            StringBuffer buffer = new StringBuffer(fixedPhone);
            buffer.replace(0, fixedPhone.length() - 4, transformProperties.getReplaceSymbol(fixedPhone.length() - 4));
            newFixedPhone = buffer.toString();
        }
        return newFixedPhone;
    }
}
