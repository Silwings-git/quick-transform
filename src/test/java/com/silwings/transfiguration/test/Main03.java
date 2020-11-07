package com.silwings.transfiguration.test;

import com.silwings.transfiguration.bean.Stu;
import com.silwings.transfiguration.bean.User;
import com.silwings.transfiguration.utils.ReflectUtil;

/**
 * @ClassName Main03
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 17:16
 * @Version V1.0
 **/
public class Main03 {
    public static void main(String[] args) throws NoSuchMethodException {
        Class type = ReflectUtil.getSuperClassGenricType(Stu.class.getDeclaredMethods()[0].getClass(), 0);
        System.out.println("type = " + type);
    }
}
