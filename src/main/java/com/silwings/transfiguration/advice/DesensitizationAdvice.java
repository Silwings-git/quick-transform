package com.silwings.transfiguration.advice;

import com.silwings.transfiguration.annotation.DataDesensitization;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @ClassName DesensitizationAdvice
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/11/7 15:50
 * @Version V1.0
 **/
@Aspect
public class DesensitizationAdvice {

    @Pointcut("@annotation(com.silwings.transfiguration.annotation.DataDesensitization)")
    public void desensitizationPointCut() {

    }

    @Around("desensitizationPointCut()&&@annotation(dataDesensitization)")
    public Object handleExceptionLog(ProceedingJoinPoint jp, DataDesensitization dataDesensitization) throws Throwable {
        // 调用切点方法
        Object result = jp.proceed();
        System.out.println("advice= " + result);
        Object target = jp.getTarget();
        System.out.println("target = " + target);
        System.out.println("dataDesensitization = " + dataDesensitization);
        return result;
    }
}
