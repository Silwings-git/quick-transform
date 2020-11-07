package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.properties.DesensitizationProperties;

/**
 * @ClassName IdCardDesensitizationStrategy
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 21:41
 * @Version V1.0
 **/
public class IdCardDesensitizationStrategy implements DesensitizationStrategy<String> {

    private DesensitizationProperties desensitizationProperties;

    public IdCardDesensitizationStrategy(DesensitizationProperties desensitizationProperties) {
        this.desensitizationProperties = desensitizationProperties;
    }

    /**
     * description: 隐藏出生日期
     * version: 1.0
     * date: 2020/11/7 21:43
     * author: 崔益翔
     *
     * @param idCardNum 身份证号码
     * @return java.lang.Object
     */
    @Override
    public String desensitization(String idCardNum) {
        String newIdCardNum = idCardNum;
        if (null != idCardNum) {
            StringBuffer buffer = new StringBuffer(idCardNum);
            buffer.replace(6, 14, desensitizationProperties.getReplaceSymbol(8));
            newIdCardNum = buffer.toString();
        }
        return newIdCardNum;
    }
}
