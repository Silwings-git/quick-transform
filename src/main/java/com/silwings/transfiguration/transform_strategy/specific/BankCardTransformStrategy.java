package com.silwings.transfiguration.transform_strategy.specific;

import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.properties.TransformProperties;

/**
 * @ClassName BankCardTransformStrategy
 * @Description 银行卡脱敏
 * @Author 崔益翔
 * @Date 2020/11/7 22:24
 * @Version V1.0
 **/
public class BankCardTransformStrategy implements TransformStrategy<String> {

    private TransformProperties transformProperties;

    public BankCardTransformStrategy(TransformProperties transformProperties) {
        this.transformProperties = transformProperties;
    }

    /**
     * description: 显示前 6 位 + *(实际位数) + 后 4 位，如：622575******1496
     * version: 1.0
     * date: 2020/11/7 22:27
     * author: 崔益翔
     *
     * @param bankCard
     * @return java.lang.String
     */
    @Override
    public String transform(String bankCard) {
        String newBankCard = bankCard;
        if (null != bankCard) {
            StringBuffer buffer = new StringBuffer(bankCard);
            int lastIndex = bankCard.length() - 4;
            buffer.replace(6, lastIndex, transformProperties.getReplaceSymbol(lastIndex - 6));
            newBankCard = buffer.toString();
        }
        return newBankCard;
    }
}
