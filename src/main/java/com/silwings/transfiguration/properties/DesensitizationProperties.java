package com.silwings.transfiguration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName DesensitizationProperties
 * @Description 自定义配置类
 * @Author 崔益翔
 * @Date 2020/11/7 13:36
 * @Version V1.0
 **/
@ConfigurationProperties(prefix = "desensitization")
public class DesensitizationProperties {

    /**
     * 是否开启响应体增强脱敏
     * 1.在关闭状态下,实体类中添加了脱敏相关注解后并不会生效,必须在将该类作为返回
     * 值返回的方法上添加相关注解(如@MethodDesensitization等)后才会生效.
     * 2.开启响应体增强脱敏后,上述的方法上无需添加任何注解,当被添加相关注解的类实
     * 例被作为向客户端返回的结果返回时,会在响应体增强中对其进行数据脱敏
     * 3.无需担心方法上与响应体增强中提示添加注解发生冲突,遵循就近原则
     */
    private boolean openResponseBodyTransition= false;

    /**
     * 设置用来替换原值的字符
     * 虽然此处支持设置超过长度1的字符串,但是实际使用时暂时仅支持该属性的首个字符
     * 如设置值为"!@#",实际生效为'!'
     */
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
