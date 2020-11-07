package com.silwings.transfiguration.bean;

/**
 * @ClassName Stu
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 17:18
 * @Version V1.0
 **/
public class Stu extends User implements A<String> {
    @Override
    public void show(String o) {
        System.out.println("o = " + o);
    }
}
