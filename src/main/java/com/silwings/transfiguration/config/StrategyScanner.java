package com.silwings.transfiguration.config;

import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName StrategyScanner
 * @Description 自动扫描暂时不使用,让用户使用Spring注解注入即可
 * @Author 崔益翔
 * @Date 2020/11/7 11:26
 * @Version V1.0
 **/
public class StrategyScanner implements BeanDefinitionRegistryPostProcessor, ResourceLoaderAware, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private MetadataReaderFactory metadataReaderFactory;

    private ResourcePatternResolver resourcePatternResolver;


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 获取启动类所在包
        List<String> packages = AutoConfigurationPackages.get(applicationContext);
        // 开始扫描包，获取字节码
        Set<Class<?>> beanClazzSet = scannerPackages(packages.get(0));
        for (Class beanClazz : beanClazzSet) {
            // 判断是否是DesensitizationStrategy
            if (isNotDesensitizationStrategy(beanClazz)) {
                continue;
            }
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(beanClazz);
            //这里采用的是byType方式注入，类似的还有byName等
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            String simpleName = beanClazz.getSimpleName();
            simpleName = simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
            registry.registerBeanDefinition(simpleName, builder.getBeanDefinition());
        }
    }

    private boolean isNotDesensitizationStrategy(Class beanClazz) {
        return beanClazz.isInterface() || beanClazz.getInterfaces().length <= 0 || beanClazz.getInterfaces()[0] != DesensitizationStrategy.class;
    }


    /**
     * 根据包路径获取包及子包下的所有类
     *
     * @param basePackage basePackage
     * @return Set<Class < ?>> Set<Class<?>>
     */
    private Set<Class<?>> scannerPackages(String basePackage) {
        Set<Class<?>> set = new LinkedHashSet<>();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePackage) + '/' + DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    Class<?> clazz;
                    try {
                        clazz = Class.forName(className);
                        set.add(clazz);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    private String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(
                this.applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        this.metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
