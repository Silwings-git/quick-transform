package com.silwings.transform.advice;

import com.silwings.transform.annotation.DataTransform;
import com.silwings.transform.annotation.MethodTransform;
import com.silwings.transform.annotation.Transform;
import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.processor.TransformManager;
import com.silwings.transform.utils.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

import static com.silwings.transform.enums.BackupsEnum.FOLLOW;

/**
 * @ClassName TransformAdvice
 * @Description 使用AOP对数据进行设定好的脱敏操作
 * @Author 崔益翔
 * @Date 2020/11/7 15:50
 * @Version V1.0
 **/
@Aspect
public class TransformAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(TransformAdvice.class);

    private TransformManager transformManager;

    public TransformAdvice(TransformManager transformManager) {
        this.transformManager = transformManager;
    }

    @Pointcut("@annotation(com.silwings.transform.annotation.MethodTransform)" +
            "|| @annotation(com.silwings.transform.annotation.NameTransform)" +
            "|| @annotation(com.silwings.transform.annotation.PhoneTransform)" +
            "|| @annotation(com.silwings.transform.annotation.PasswordTransform)" +
            "|| @annotation(com.silwings.transform.annotation.IdCardTransform)" +
            "|| @annotation(com.silwings.transform.annotation.BankCardTransform)" +
            "|| @annotation(com.silwings.transform.annotation.FixedPhoneTransform)" +
            "|| @annotation(com.silwings.transform.annotation.EmailTransform)" +
            "|| @annotation(com.silwings.transform.annotation.DataTransform)")
    public void transformPointCut() {
    }

    @Around("transformPointCut()")
    public Object dataConversion(ProceedingJoinPoint jp) throws Throwable {
//        如果要对返回单数据做处理需要如下判断
        /*
            优先级定义:
            1.如果在方法上添加了@MethodTransform却没有指定策略类时,将判断返回值是否包含@Transform注解
                1.1 包含:按照属性字段上的注解进行数据处理
                1.2 不包含:不进行任何操作
            2.如果在方法上添加了@MethodTransform并且指定策略类,或者添加了其他@DataTransform的语义化注解
                2.1 无论返回值上有无添加 @Transform注解,将按照方法上的注解进行数据处理
                2.2 如果遇到类型转换异常,将交由开发者手动处理
         */
        // 调用切点方法
        Object result = jp.proceed();
        if (null == result) {
            return result;
        }
//      获取方法对象
        Method method = getMethod(jp);
        if (null == method) {
            LOG.error("获取方法信息失败,跳过数据处理");
            return result;
        }
        MethodTransform methodTransform = AnnotatedElementUtils.findMergedAnnotation(method, MethodTransform.class);
        if (null == methodTransform) {
//            没有添加@MethodTransform注解时,说明一定是指明使用策略的注解,使用用户定义的具体策略进行数据处理
            DataTransform dataTransform = AnnotatedElementUtils.findMergedAnnotation(method, DataTransform.class);
            result = transformManager.transformBasicType(result, dataTransform.backups(), dataTransform);
        } else if (!methodTransform.strategy().getName().equals(TransformStrategy.class.getName())) {
//            如果添加的是@MethodTransform注解,分两种情况,1是指定了具体策略,2是没有指定.如果没有指定使用返回值类中相关的注解策略
//            指定了策略
            result = transformManager.transformBasicType(result, methodTransform.backups(), methodTransform);
        } else {
//            未指定策略
            Transform transform = AnnotatedElementUtils.findMergedAnnotation(result.getClass(), Transform.class);
            if (null != transform) {
//                方法上的备份设置优先级高于实体类的
                result = transformManager.transformOtherType(result, FOLLOW.equals(methodTransform.backups()) ? transform.backups() : methodTransform.backups());
            } else {
                LOG.error("方法 " + method.getName() + " 上虽声明了需要数据处理但并未指定处理策略");
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
