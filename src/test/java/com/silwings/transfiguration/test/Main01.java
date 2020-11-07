package com.silwings.transfiguration.test;

import com.silwings.transfiguration.annotation.DataDesensitization;
import com.silwings.transfiguration.annotation.MyComponent;
import com.silwings.transfiguration.annotation.MyResponseBody;
import com.silwings.transfiguration.annotation.MyRestController;
import com.silwings.transfiguration.bean.User;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

/**
 * @ClassName Main01
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/5 23:22
 * @Version V1.0
 **/
public class Main01 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        demo4();
    }

    private static void demo4() throws NoSuchFieldException, IllegalAccessException {
        User user = new User();
        for (Field field : User.class.getDeclaredFields()) {
            MyRestController mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, MyRestController.class);
            System.out.println("mergedAnnotation = " + mergedAnnotation);

            field.setAccessible(true);
            Object o = field.get(user);
            System.out.println("o = " + o);
        }
    }

    private static void demo3() {
        MyRestController mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(User.class, MyRestController.class);
//        System.out.println("mergedAnnotation = " + mergedAnnotation.name());
        Set<MyRestController> allMergedAnnotations = AnnotatedElementUtils.findAllMergedAnnotations(User.class, MyRestController.class);
        Set<MyComponent> myComponents = AnnotatedElementUtils.findAllMergedAnnotations(User.class, MyComponent.class);
        for (MyRestController annotation : allMergedAnnotations) {
            System.out.println(annotation.name());
        }
        for (MyComponent myComponent : myComponents) {
            System.out.println(myComponent.value());
        }

    }

    private static void demo2() {
        User user = new User();
        MyRestController annotation = AnnotationUtils.findAnnotation(User.class, MyRestController.class);
//        System.out.println(annotation.name() + annotation.value());
//        MyComponent mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(annotation.getClass(), MyComponent.class);
        DataDesensitization mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(user.getClass(), DataDesensitization.class);
        MyResponseBody myResponseBody = AnnotatedElementUtils.findMergedAnnotation(user.getClass(), MyResponseBody.class);
        if (null == mergedAnnotation) {
            System.out.println("null");
        } else {
            System.out.println(mergedAnnotation);
        }
        System.out.println("myResponseBody = " + myResponseBody.name());

    }

    private static void demo1() {
        User user = new User();
        MyComponent[] annotationsByType = User.class.getAnnotationsByType(MyComponent.class);
        MyRestController[] annotationsByType2 = User.class.getAnnotationsByType(MyRestController.class);
        System.out.println(Arrays.toString(annotationsByType));
        System.out.println("annotationsByType2 = " + Arrays.toString(annotationsByType2));
        MyComponent mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(User.class, MyComponent.class);
        if (null == mergedAnnotation) {
            System.out.println("没找到");
        } else {
            System.out.println(mergedAnnotation.value());
        }
    }
}
