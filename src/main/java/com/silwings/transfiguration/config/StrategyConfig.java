package com.silwings.transfiguration.config;

import com.silwings.transfiguration.container.TransformStrategyContainer;
import com.silwings.transfiguration.transform_strategy.TransformStrategy;
import com.silwings.transfiguration.transform_strategy.specific.*;
import com.silwings.transfiguration.properties.TransformProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @ClassName StrategyConfig
 * @Description 策略配置类
 * @Author 崔益翔
 * @Date 2020/11/7 13:41
 * @Version V1.0
 **/
@Configuration
@EnableConfigurationProperties({TransformProperties.class})
public class StrategyConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private TransformProperties transformProperties;

    @Bean
    public PhoneTransformStrategy phoneTransformStrategy() {
        return new PhoneTransformStrategy(transformProperties);
    }

    @Bean
    public NameTransformStrategy nameTransformStrategy() {
        return new NameTransformStrategy(transformProperties);
    }

    @Bean
    public PasswordTransformStrategy passwordTransformStrategy() {
        return new PasswordTransformStrategy(transformProperties);
    }

    @Bean
    public IdCardTransformStrategy idCardTransformStrategy() {
        return new IdCardTransformStrategy(transformProperties);
    }

    @Bean
    public BankCardTransformStrategy bankCardTransformStrategy() {
        return new BankCardTransformStrategy(transformProperties);
    }

    @Bean
    public FixedPhoneTransformStrategy fixedPhoneTransformStrategy() {
        return new FixedPhoneTransformStrategy(transformProperties);
    }

    @Bean
    public EmailTransformStrategy emailTransformStrategy() {
        return new EmailTransformStrategy(transformProperties);
    }

    @Bean
    public TransformStrategyContainer transformStrategyContainer() {
        Map<String, TransformStrategy> beansOfType = applicationContext.getBeansOfType(TransformStrategy.class);
        TransformStrategyContainer container = TransformStrategyContainer.getInstance();
        if (null != beansOfType && beansOfType.keySet().size() > 0) {
            for (String key : beansOfType.keySet()) {
                container.addStrategy(beansOfType.get(key));
            }
        }
        return container;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
