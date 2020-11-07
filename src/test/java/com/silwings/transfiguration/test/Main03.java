package com.silwings.transfiguration.test;

import com.silwings.transfiguration.annotation.DataDesensitization;
import com.silwings.transfiguration.annotation.MethodDesensitization;
import com.silwings.transfiguration.annotation.NameDesensitization;
import com.silwings.transfiguration.annotation.Transfiguration;
import com.silwings.transfiguration.bean.Stu;
import com.silwings.transfiguration.bean.User;
import com.silwings.transfiguration.utils.ReflectUtil;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @ClassName Main03
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 17:16
 * @Version V1.0
 **/
public class Main03 {
    public static void main(String[] args) throws Exception {
        demo3();
    }

    private static void demo3() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Method[] declaredMethods = Main03.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            MethodDesensitization mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(declaredMethod, MethodDesensitization.class);
            if (null != mergedAnnotation) {
                Class<? extends Annotation> strategyAnno = mergedAnnotation.strategyAnno();
                Field[] declaredFields = strategyAnno.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    System.out.println("declaredField = " + declaredField);
                }
                Method[] declaredMethods1 = strategyAnno.getDeclaredMethods();
                for (Method method : declaredMethods1) {

                    Object handler = Proxy.getInvocationHandler(strategyAnno);
                    Field f = null;
                    try {
                        f = handler.getClass().getDeclaredField("memberValues");
                    } catch (NoSuchFieldException | SecurityException e) {
                        throw new IllegalStateException(e);
                    }

                    f.setAccessible(true);

                    Map<String, Object> memberValues;

                    try {
                        memberValues = (Map<String, Object>) f.get(handler);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }

                    Object oldValue = memberValues.get("execute");
                    System.out.println("oldValue = " + oldValue);
                    System.out.println("method = " + method);
                    Object invoke = method.invoke(strategyAnno, null);
                    System.out.println("invoke = " + invoke);
                }
            }
        }
    }

    @MethodDesensitization(strategyAnno = NameDesensitization.class)
    public static void demo2() {
        String s = "12345678";

        boolean commonOrWrap = ReflectUtil.isCommonOrWrap(s.getClass());
        boolean commonOrWrap2 = ReflectUtil.isCommonOrWrap(User.class);
        System.out.println("commonOrWrap = " + commonOrWrap);
        System.out.println("commonOrWrap2 = " + commonOrWrap2);
    }

    private static void demo1() {
        Class type = ReflectUtil.getSuperClassGenricType(Stu.class.getDeclaredMethods()[0].getClass(), 0);
        System.out.println("type = " + type);
    }
}
