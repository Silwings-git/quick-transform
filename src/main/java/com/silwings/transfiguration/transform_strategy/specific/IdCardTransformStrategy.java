package com.silwings.transfiguration.transform_strategy.specific;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.properties.TransformProperties;

/**
 * @ClassName IdCardTransformStrategy
 * @Description 身份证脱敏策略类
 * @Author 崔益翔
 * @Date 2020/11/7 21:41
 * @Version V1.0
 **/
public class IdCardTransformStrategy implements TransformStrategy<String> {

    private TransformProperties transformProperties;

    public IdCardTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
    }

    /**
     * description: 隐藏出生日期及其前三位
     * version: 1.0
     * date: 2020/11/7 21:43
     * author: 崔益翔
     *
     * @param idCardNum 身份证号码
     * @return java.lang.Object
     */
    @Override
    public String transform(String idCardNum) {
        String newIdCardNum = idCardNum;
        if (null != idCardNum) {
            StringBuffer buffer = new StringBuffer(idCardNum);
            buffer.replace(3, 14, transformProperties.getReplaceSymbol(11));
            newIdCardNum = buffer.toString();
        }
        return newIdCardNum;
    }
}
