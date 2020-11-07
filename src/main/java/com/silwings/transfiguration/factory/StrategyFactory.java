//package com.silwings.transfiguration.factory;
//
//import com.silwings.transfiguration.desensitization_strategy.specific.PhoneDesensitizationStrategy;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.FactoryBean;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Proxy;
//
///**
// * @ClassName StrategyFactory
// * @Description TODO
// * @Author 崔益翔
// * @Date 2020/11/7 11:37
// * @Version V1.0
// **/
//public class StrategyFactory<T> implements FactoryBean<T> {
//    private static final Logger log = LoggerFactory.getLogger(StrategyFactory.class);
//
//    private Class<T> interfaceType;
//
//    public StrategyFactory(Class<T> interfaceType) {
//        log.info("StrategyFactory init ...");
//        this.interfaceType = interfaceType;
//    }
//
//    @Override
//    public T getObject() throws Exception {
//        log.info("RepositoryBean proxy init ...");
//        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType},
//                (InvocationHandler) interfaceType.newInstance());
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return interfaceType;
//    }
//}
