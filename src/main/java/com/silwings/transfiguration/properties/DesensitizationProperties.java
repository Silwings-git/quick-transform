package com.silwings.transfiguration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName DesensitizationProperties
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 13:36
 * @Version V1.0
 **/
@ConfigurationProperties(prefix = "desensitization")
public class DesensitizationProperties {
    private boolean openResponseBodyTransition= false;

    private String replaceSymbol = "*";

    public Character getReplaceSymbol() {
        return replaceSymbol.charAt(0);
    }

    public void setReplaceSymbol(String replaceSymbol) {
        this.replaceSymbol = replaceSymbol;
    }

    public boolean isOpenResponseBodyTransition() {
        return openResponseBodyTransition;
    }

    public void setOpenResponseBodyTransition(boolean openResponseBodyTransition) {
        this.openResponseBodyTransition = openResponseBodyTransition;
    }
}
