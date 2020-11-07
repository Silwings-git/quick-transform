package com.silwings.transfiguration.advice;

import com.silwings.transfiguration.annotation.DataDesensitization;
import com.silwings.transfiguration.annotation.MethodDesensitization;
import com.silwings.transfiguration.annotation.Transfiguration;
import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.processor.DesensitizationManager;
import com.silwings.transfiguration.utils.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * @ClassName DesensitizationAdvice
 * @Description 使用AOP对数据进行设定好的脱敏操作
 * @Author 崔益翔
 * @Date 2020/11/7 15:50
 * @Version V1.0
 **/
@Aspect
public class DesensitizationAdvice {
    private static final Logger log = LoggerFactory.getLogger(DesensitizationAdvice.class);

    private DesensitizationManager desensitizationManager;

    public DesensitizationAdvice(DesensitizationManager desensitizationManager) {
        this.desensitizationManager = desensitizationManager;
    }

    @Pointcut("@annotation(com.silwings.transfiguration.annotation.MethodDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.NameDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.PhoneDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.PasswordDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.IdCardDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.BankCardDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.FixedPhoneDesensitization)" +
            "|| @annotation(com.silwings.transfiguration.annotation.DataDesensitization)")
    public void desensitizationPointCut() {
    }

    @Around("desensitizationPointCut()")
    public Object handleExceptionLog(ProceedingJoinPoint jp) throws Throwable {
//        如果要对返回单数据做处理需要如下判断
        /*
            1.需要使用就近原则,也就是如果类上有注解就使用类上的策略,如果类上没有注解,再考虑当前被切的方法上的注解
            2.拿到方法的返回值直接获取class去判断即可
            3.通过方法名和参数类型获取实际执行的方法,在获取方法上的注解
         */
        // 调用切点方法
        Object result = jp.proceed();
        if (null == result) {
            return result;
        }
        Class<?> resultClass = result.getClass();
        Transfiguration mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(resultClass, Transfiguration.class);
        if (null != mergedAnnotation) {
//              如果不存在Transfiguration注解说明
//              1.该对象未标记为需要脱敏
//              2.该返回值不是用户自定义类型
            result = desensitizationManager.desensitizationOtherType(result);
        } else {
            if (!ReflectUtil.isCommonOrWrapOrString(result)) {
                log.error("返回值:" + result.getClass().getName() + "既不是基本数据类型/字符串类型也不是添加了@Transfiguration注解的实体类类型,不支持脱敏");
            } else {
//                方法层级的
                Method method = getMethod(jp);
//              方法上存在注解,需要判断结果是什么类型,如果是基本数据类型,就需要调用,不是就不进行任何处理
                MethodDesensitization methodDesensitization = AnnotatedElementUtils.findMergedAnnotation(method, MethodDesensitization.class);
                if (null == methodDesensitization) {
                    DataDesensitization dataDesensitization = AnnotatedElementUtils.findMergedAnnotation(method, DataDesensitization.class);
                    result = desensitizationManager.desensitizationBasicType(result, dataDesensitization);
                } else if (!methodDesensitization.strategy().getName().equals(DesensitizationStrategy.class.getName())) {
                    result = desensitizationManager.desensitizationBasicType(result, methodDesensitization);
                } else {
                    log.error("未指定脱敏规则，不进行数据处理");
                }
            }
        }
        return result;
    }

    /**
     * description: 获取ProceedingJoinPoint的真实执行的方法对象
     * version: 1.0
     * date: 2020/11/7 18:53
     * author: 崔益翔
     *
     * @param jp
     * @return java.lang.reflect.Method
     */
    private Method getMethod(ProceedingJoinPoint jp) {
        //拦截的实体类
        Object target = jp.getTarget();
        //拦截的方法名称
        String methodName = jp.getSignature().getName();
        //拦截的方法参数
        Object[] args = jp.getArgs();
        //拦截的放参数类型
        Class[] parameterTypes = ((MethodSignature) jp.getSignature()).getMethod().getParameterTypes();

        Method m = null;
        try {
            //通过反射获得拦截的method
            m = target.getClass().getMethod(methodName, parameterTypes);
            //如果是桥则要获得实际拦截的method
            if (m.isBridge()) {
                for (int i = 0; i < args.length; i++) {
                    //获得泛型类型
                    Class genClazz = ReflectUtil.getSuperClassGenricType(target.getClass());
                    //根据实际参数类型替换parameterType中的类型
                    if (args[i].getClass().isAssignableFrom(genClazz)) {
                        parameterTypes[i] = genClazz;
                    }
                }
                //获得parameterType参数类型的方法
                m = target.getClass().getMethod(methodName, parameterTypes);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return m;
    }
}
