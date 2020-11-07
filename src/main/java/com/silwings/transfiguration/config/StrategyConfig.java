package com.silwings.transfiguration.config;

import com.silwings.transfiguration.container.DesensitizationStrategyContainer;
import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.IdCardDesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.NameDesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.PasswordDesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.PhoneDesensitizationStrategy;
import com.silwings.transfiguration.properties.DesensitizationProperties;
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
@EnableConfigurationProperties({DesensitizationProperties.class})
public class StrategyConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private DesensitizationProperties desensitizationProperties;

    @Bean
    public PhoneDesensitizationStrategy phoneDesensitizationStrategy() {
        return new PhoneDesensitizationStrategy(desensitizationProperties);
    }

    @Bean
    public NameDesensitizationStrategy nameDesensitizationStrategy() {
        return new NameDesensitizationStrategy(desensitizationProperties);
    }

    @Bean
    public PasswordDesensitizationStrategy passwordDesensitizationStrategy() {
        return new PasswordDesensitizationStrategy(desensitizationProperties);
    }

    @Bean
    public IdCardDesensitizationStrategy idCardDesensitizationStrategy() {
        return new IdCardDesensitizationStrategy(desensitizationProperties);
    }

    @Bean
    public DesensitizationStrategyContainer desensitizationStrategyContainer() {
        Map<String, DesensitizationStrategy> beansOfType = applicationContext.getBeansOfType(DesensitizationStrategy.class);
        DesensitizationStrategyContainer container = DesensitizationStrategyContainer.getInstance();
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
