package com.silwings.transfiguration.bean;

import com.silwings.transfiguration.annotation.MyComponent;
import com.silwings.transfiguration.annotation.MyRestController;

/**
 * @ClassName User
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/5 23:21
 * @Version V1.0
 **/
@MyRestController(name = "User类",value = "娃哈哈")
@MyComponent(value = "aaa")
public class User {
    @MyRestController(name = "name",value = "小王")
    private String name;
    @MyRestController(name = "手机号",value = "12345678911")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
