package com.silwings.transform.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author huyi.zhang
 */
public class BeanHelper {

    /**
     * 拷贝对象
     * @param source 源对象
     * @param target 目标对象的类型
     * @param <T> 目标的泛型裂隙
     * @return 目标类型的对象
     */
    public static <T> T copyProperties(Object source, Class<T> target){
        try {
            T t = target.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (InstantiationException e) {
            throw new RuntimeException(target.getName() + "无法被实例化，可能是一个接口或抽象类");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(target.getName() + "无法被实例化，构造函数无法访问");
        }
    }

    public static <T> List<T> copyWithCollection(List<?> sourceList, Class<T> target){
        return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toList());
    }

    public static <T> Set<T> copyWithCollection(Set<?> sourceList, Class<T> target){
        return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toSet());
    }
}
