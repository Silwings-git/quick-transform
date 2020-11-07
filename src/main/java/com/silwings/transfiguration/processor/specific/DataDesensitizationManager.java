package com.silwings.transfiguration.processor.specific;

import com.silwings.transfiguration.annotation.DataDesensitization;
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
        this.desensitizationStrategyContainer = desensitizationStrategyContainer;
        this.desensitizationHandler = desensitizationHandler;
    }

    @Override
    public Object desensitization(Object body) {
        Objects.requireNonNull(body, "body must be not null !");
        System.out.println("body:" + body);
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
}
