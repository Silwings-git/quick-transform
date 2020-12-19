package com.silwings.transform.processor.specific;

import com.silwings.transform.annotation.DataTransform;
import com.silwings.transform.annotation.MethodTransform;
import com.silwings.transform.container.TransformStrategyContainer;
import com.silwings.transform.processor.TransformManager;
import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.handler.TransformHandler;
import com.silwings.transform.utils.BeanHelper;
import com.silwings.transform.utils.ReflectUtil;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @ClassName DataTransformManager
 * @Description 脱敏管理器
 * 1.负责检查实体类属性的注解信息,如果发现指定注解,标明该属性需要脱敏操作
 * 2.根据注解的属性信息从`脱敏策略容器`获取脱敏策略对象
 * 3.将具体策略对象与待脱敏属性的值交给脱敏执行器
 * 4.用从脱敏执行器获取到的新值覆盖待脱敏属性的旧值
 * @Author 崔益翔
 * @Date 2020/10/11 11:51
 * @Version V1.0
 **/
public class DataTransformManager implements TransformManager {

    private TransformStrategyContainer transformStrategyContainer;
    private TransformHandler transformHandler;
    private Boolean isOpen = true;

    public DataTransformManager(TransformStrategyContainer transformStrategyContainer, TransformHandler transformHandler) {
        Objects.requireNonNull(transformStrategyContainer, "策略容器不可为空");
        Objects.requireNonNull(transformHandler, "脱敏执行器不可为空");
        this.transformStrategyContainer = transformStrategyContainer;
        this.transformHandler = transformHandler;
    }

    /**
     * description: 用来处理被添加了@Transfiguration注解的实体类中属性值的方法.通过遍历
     * 实体类的所有属性,那到注解信息.再通过注解中声明的策略类去查找相关的策略实例,对数据
     * 进行脱敏处理.处理完成后将原对象中的值进行替换
     * version: 1.0
     * date: 2020/11/7 21:04
     * author: 崔益翔
     *
     * @param body
     * @return java.lang.Object
     */
    @Override
    public Object transformOtherType(Object body) {
        Objects.requireNonNull(body, "body must be not null !");
//        此处拿到对象,应该遍历对象所有变量的注解,并使用注解名称获取transformStrategyContainer中对应的策略进行数据转换
        List<Field> allField = ReflectUtil.getFieldByCurrentAndSuper(body.getClass());
        if (null != allField && allField.size() > 0) {
//            获取到字段信息,进行注解遍历
            for (int i = 0; i < allField.size(); i++) {
                Field field = allField.get(i);
                DataTransform mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, DataTransform.class);
//                如果不存在注解或者execute值为false说明无需进行处理
                if (null == mergedAnnotation || !mergedAnnotation.execute()) {
                    continue;
                }
                TransformStrategy strategy = transformStrategyContainer.getStrategy(mergedAnnotation.strategy());
                Objects.requireNonNull(strategy, mergedAnnotation.strategy().getName() + " 实例未找到,请检查是否已添加到Spring容器");
                try {
//                   设置可访问私有
                    field.setAccessible(true);
                    Object execute = executeAndBackUpData(field.get(body), strategy);
                    field.set(body, execute);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return body;
    }

    /**
     * description: 该方法通过提供的注解实例中的信息,查找相关的策略实例,对待处理数据进行脱敏处理
     * 并将新值返回
     * version: 1.0
     * date: 2020/11/7 21:07
     * author: 崔益翔
     *
     * @param body          待处理数据
     * @param dataTransform 与待处理数据对应的注解实例
     * @return java.lang.Object
     */
    @Override
    public Object transformBasicType(Object body, DataTransform dataTransform) {
        Objects.requireNonNull(dataTransform, "DataTransform 不可未空");
        if (null != body && dataTransform.execute()) {
            TransformStrategy strategy = transformStrategyContainer.getStrategy(dataTransform.strategy());
            Objects.requireNonNull(strategy, dataTransform.strategy().getName() + " 实例未找到,请检查是否已添加到Spring容器");
            body = executeAndBackUpData(body, strategy);
        }
        return body;
    }

    /**
     * description: 该方法通过提供的注解实例中的信息,查找相关的策略实例,对待处理数据进行脱敏处理
     * 并将新值返回
     * version: 1.0
     * date: 2020/11/7 21:07
     * author: 崔益翔
     *
     * @param body            待处理数据
     * @param methodTransform 与待处理数据对应的注解实例
     * @return java.lang.Object
     */
    @Override
    public Object transformBasicType(Object body, MethodTransform methodTransform) {
        Objects.requireNonNull(methodTransform, "MethodTransform 不可未空");
        if (null != body && methodTransform.execute()) {
            TransformStrategy strategy = transformStrategyContainer.getStrategy(methodTransform.strategy());
            Objects.requireNonNull(strategy, methodTransform.strategy().getName() + " 实例未找到,请检查是否已添加到Spring容器");
            body = executeAndBackUpData(body, strategy);
        }
        return body;
    }

    /**
     * description: 执行数据转换并进行原始数据的备份
     * 1.目前仅支持对实体类类型的数据进行数据备份
     * 2.只有当用户开启了数据备份功能并且body对象不为空才进行数据备份
     * 3.当备份数据为空时不会将数据存储到备份容器中
     * version: 1.0
     * date: 2020/12/19 15:55
     * author: 崔益翔
     *
     * @param body
     * @param strategy
     * @return java.lang.Object
     */
    private Object executeAndBackUpData(Object body, TransformStrategy strategy) {
        Object backUp = null;
//            判断body的类型，进行数据拷贝
        if (isOpen && null != body) {
//            判断是否是基本数据类型(含包装类)或String类型
            if (ReflectUtil.isCommonOrWrapOrString(body)) {
//            基本数据类型或String直接赋值即可
                backUp = body;
            } else if (body instanceof Collections) {
//            暂时不支持容器类数据，不进行备份
            } else if (body instanceof Map) {
//            暂时不支持容器类数据，不进行备份
            } else {
//            目前只支持实体类类型
                backUp = BeanHelper.copyProperties(body, body.getClass());
            }
        }
        Object res = transformHandler.execute(body, strategy);
        if (null != backUp) {
            TransformBackups.setBackup(res, backUp);
        }
        return res;
    }

}
