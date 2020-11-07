package com.silwings.transfiguration.processor.specific;

import com.silwings.transfiguration.annotation.DataDesensitization;
import com.silwings.transfiguration.annotation.MethodDesensitization;
import com.silwings.transfiguration.container.DesensitizationStrategyContainer;
import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.handler.DesensitizationHandler;
import com.silwings.transfiguration.processor.DesensitizationManager;
import com.silwings.transfiguration.utils.ReflectUtil;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName DataDesensitizationManager
 * @Description 脱敏管理器
 * 1.负责检查实体类属性的注解信息,如果发现指定注解,标明该属性需要脱敏操作
 * 2.根据注解的属性信息从`脱敏策略容器`获取脱敏策略对象
 * 3.将具体策略对象与待脱敏属性的值交给脱敏执行器
 * 4.用从脱敏执行器获取到的新值覆盖待脱敏属性的旧值
 * @Author 崔益翔
 * @Date 2020/10/11 11:51
 * @Version V1.0
 **/
public class DataDesensitizationManager implements DesensitizationManager {

    private DesensitizationStrategyContainer desensitizationStrategyContainer;
    private DesensitizationHandler desensitizationHandler;

    public DataDesensitizationManager(DesensitizationStrategyContainer desensitizationStrategyContainer, DesensitizationHandler desensitizationHandler) {
        Objects.requireNonNull(desensitizationStrategyContainer, "策略容器不可为空");
        Objects.requireNonNull(desensitizationHandler, "脱敏执行器不可为空");
        this.desensitizationStrategyContainer = desensitizationStrategyContainer;
        this.desensitizationHandler = desensitizationHandler;
    }

    /**
     * description: 该方法为了方便调用而创建,通过判断被处理的数据是不是基本数据类型或者字符串类型来判断调用哪一个方法
     * version: 1.0
     * date: 2020/11/7 21:03
     * author: 崔益翔
     *
     * @param body                被处理数据
     * @param dataDesensitization 标注的注解
     * @return java.lang.Object
     */
    @Override
    public Object desensitization(Object body, DataDesensitization dataDesensitization) {
        if (null == body) {
            return body;
        }
        Object result = null;
        if (ReflectUtil.isCommonOrWrapOrString(body)) {
//            是基本数据类型
            result = desensitizationBasicType(body, dataDesensitization);
        } else {
//            非基本数据类型
            result = desensitizationOtherType(body);
        }
        return result;
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
    public Object desensitizationOtherType(Object body) {
        Objects.requireNonNull(body, "body must be not null !");
//        此处拿到对象,应该遍历对象所有变量的注解,并使用注解名称获取desensitizationStrategyMap中对应的脱敏策略进行数据转换
        List<Field> allField = ReflectUtil.getFieldByCurrentAndSuper(body.getClass());
        if (null != allField && allField.size() > 0) {
//            获取到字段信息,进行注解遍历
            for (int i = 0; i < allField.size(); i++) {
                Field field = allField.get(i);
                DataDesensitization mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(field, DataDesensitization.class);
//                如果不存在注解或者execute值为false说明无需进行处理
                if (null == mergedAnnotation || !mergedAnnotation.execute()) {
                    continue;
                }
                DesensitizationStrategy strategy = desensitizationStrategyContainer.getStrategy(mergedAnnotation.strategy());
                Objects.requireNonNull(strategy, mergedAnnotation.strategy().getName() + " 实例未找到,请检查是否已添加到Spring容器");
                try {
//                   设置可访问私有
                    field.setAccessible(true);
                    Object execute = desensitizationHandler.execute(field.get(body), strategy);
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
     * @param body 待处理数据
     * @param dataDesensitization 与待处理数据对应的注解实例
     * @return java.lang.Object
     */
    @Override
    public Object desensitizationBasicType(Object body, DataDesensitization dataDesensitization) {
        Objects.requireNonNull(dataDesensitization, "DataDesensitization不可未空");
        if (null != body && dataDesensitization.execute()) {
            DesensitizationStrategy strategy = desensitizationStrategyContainer.getStrategy(dataDesensitization.strategy());
            Objects.requireNonNull(strategy, dataDesensitization.strategy().getName() + " 实例未找到,请检查是否已添加到Spring容器");
            body = desensitizationHandler.execute(body, strategy);
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
     * @param body 待处理数据
     * @param methodDesensitization 与待处理数据对应的注解实例
     * @return java.lang.Object
     */
    @Override
    public Object desensitizationBasicType(Object body, MethodDesensitization methodDesensitization) {
        Objects.requireNonNull(methodDesensitization, "MethodDesensitization不可未空");
        if (null != body && methodDesensitization.execute()) {
            DesensitizationStrategy strategy = desensitizationStrategyContainer.getStrategy(methodDesensitization.strategy());
            Objects.requireNonNull(strategy, methodDesensitization.strategy().getName() + " 实例未找到,请检查是否已添加到Spring容器");
            body = desensitizationHandler.execute(body, strategy);
        }
        return body;
    }

}
