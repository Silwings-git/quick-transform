package com.silwings.transfiguration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName TransformProperties
 * @Description 自定义配置类
 * @Author 崔益翔
 * @Date 2020/11/7 13:36
 * @Version V1.0
 **/
@ConfigurationProperties(prefix = "transform")
public class TransformProperties {

    /**
     * 设置用来替换原值的字符
     * 虽然此处支持设置超过长度1的字符串,但是实际使用时暂时仅支持该属性的首个字符
     * 如设置值为"!@#",实际生效为'!'
     */
    private String replaceSymbol = "*";

    public Character getReplaceSymbol() {
        return replaceSymbol.charAt(0);
    }

    public String getReplaceSymbol(int num) {
        char charAt = replaceSymbol.charAt(0);
        StringBuffer buffer = new StringBuffer(num);
        for (int i = 0; i < num; i++) {
            buffer.append(charAt);
        }
        return buffer.toString();
    }

    public void setReplaceSymbol(String replaceSymbol) {
        this.replaceSymbol = replaceSymbol;
    }

}
