package com.silwings.transfiguration.desensitization_strategy.specific;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.properties.DesensitizationProperties;

/**
 * @ClassName BankCardDesensitizationStrategy
 * @Description 银行卡脱敏
 * @Author 崔益翔
 * @Date 2020/11/7 22:24
 * @Version V1.0
 **/
public class BankCardDesensitizationStrategy implements DesensitizationStrategy<String> {

    private DesensitizationProperties desensitizationProperties;

    public BankCardDesensitizationStrategy(DesensitizationProperties desensitizationProperties) {
        this.desensitizationProperties = desensitizationProperties;
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
    public String desensitization(String bankCard) {
        String newBankCard = bankCard;
        if (null != bankCard) {
            StringBuffer buffer = new StringBuffer(bankCard);
            int lastIndex = bankCard.length() - 4;
            buffer.replace(6, lastIndex, desensitizationProperties.getReplaceSymbol(lastIndex - 6));
            newBankCard = buffer.toString();
        }
        return newBankCard;
    }
}
