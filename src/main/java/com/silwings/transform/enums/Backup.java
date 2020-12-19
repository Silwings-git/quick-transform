package com.silwings.transform.enums;

/**
 * @ClassName Backup
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/12/19 17:36
 * @Version V1.0
 **/
public enum Backup {
    FOLLOW("跟随全局设置", "FOLLOW"),
    OPEN("开启数据备份", "OPEN"),
    CLOSE("关闭数据备份", "CLOSE");

    private String name;
    private String value;

    Backup(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
