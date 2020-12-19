package com.silwings.transform.advice;

import com.silwings.transform.enums.BackupsEnum;
import com.silwings.transform.utils.BeanHelper;
import com.silwings.transform.utils.ReflectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

/**
 * @ClassName BackupsAdvice
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/12/19 17:49
 * @Version V1.0
 **/
@Aspect
public class BackupsAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(BackupsAdvice.class);
    private boolean isOpen = true;

    @Pointcut("@annotation(com.silwings.transform.annotation.backup.Backups)")
    public void backupsPointCut() {
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
     * @param jp
     * @return java.lang.Object
     */
    @Around("backupsPointCut()")
    public Object dataConversion(ProceedingJoinPoint jp) throws Throwable {
        Object[] args = jp.getArgs();
//        如果没有方法参数说明注解使用错误了,为了不影响代码正常运行,直接过滤
        if (null == args || args.length == 0) {
            LOG.error("数据转换前数据校验失败,已取消备份");
            return jp.proceed();
        }
//        被@Backups注解标记的类的方法的第一个参数为需要被加强的数据
        Object body = args[0];
        Object backupObj = args[1];
        if (null == backupObj || !(backupObj instanceof BackupsEnum)) {
//            如果参数2位空或不是BackupsEnum类型,说明存在错误,为了不影响代码正常运行,直接过滤
            LOG.error("数据转换前数据校验失败,已取消备份");
            return jp.proceed();
        }
//        将注解的初始化标识设置为
        boolean openBackupsAnno = isOpen;
        BackupsEnum backupsEnum = (BackupsEnum) backupObj;
        openBackupsAnno = null == backupsEnum.getValue() ? isOpen : backupsEnum.getValue();

        Object backups = null;
//            判断body的类型，进行数据拷贝
        if (openBackupsAnno && null != body) {
//            判断是否是基本数据类型(含包装类)或String类型
            if (ReflectUtil.isCommonOrWrapOrString(body)) {
//            基本数据类型或String直接赋值即可
                backups = body;
            } else if (body instanceof Collections) {
//            暂时不支持容器类数据，不进行备份
            } else if (body instanceof Map) {
//            暂时不支持容器类数据，不进行备份
            } else {
//            目前只支持实体类类型
                backups = BeanHelper.copyProperties(body, body.getClass());
            }
        }
        // 调用切点方法
        Object res = jp.proceed();
        if (null != backups) {
            TransformBackups.setBackup(res, backups);
        }
        return res;
    }

}
