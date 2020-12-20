package com.silwings.transform.enums;

/**
 * @ClassName BackupsEnum
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/12/19 17:36
 * @Version V1.0
 **/
public enum BackupsEnum {
    FOLLOW("跟随全局设置", null),
    OPEN("开启数据备份", true),
    CLOSE("关闭数据备份", false);

    private String name;
    private Boolean value;

    BackupsEnum(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
