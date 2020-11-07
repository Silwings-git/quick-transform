package com.silwings.transfiguration.config;

import com.silwings.transfiguration.container.DesensitizationStrategyContainer;
import com.silwings.transfiguration.controller_advice.TransfigurationResponseBodyAdvice;
import com.silwings.transfiguration.desensitization_strategy.DesensitizationStrategy;
import com.silwings.transfiguration.desensitization_strategy.specific.PhoneDesensitizationStrategy;
import com.silwings.transfiguration.handler.DesensitizationHandler;
import com.silwings.transfiguration.handler.specific.DesensitizationHandlerImpl;
import com.silwings.transfiguration.processor.DesensitizationProcessor;
import com.silwings.transfiguration.processor.specific.DataDesensitizationProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @ClassName DemoConfig
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/10/11 10:55
 * @Version V1.0
 **/
@Configuration
public class TransfigurationConfig implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * description: 响应体增强
     * version: 1.0
     * date: 2020/11/5 21:10
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.controller_advice.TransfigurationResponseBodyAdvice
     */
    @Bean
    public TransfigurationResponseBodyAdvice transfigurationResponseBodyAdvice() {
        return new TransfigurationResponseBodyAdvice();
    }

    @Bean
    public DesensitizationProcessor desensitizationProcessor() {
        return new DataDesensitizationProcessor(applicationContext.getBean("desensitizationStrategyContainer", DesensitizationStrategyContainer.class)
                , desensitizationHandler());
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

    @Bean
    public PhoneDesensitizationStrategy phoneDesensitizationStrategy() {
        return new PhoneDesensitizationStrategy();
    }

    @Bean
    public DesensitizationHandler desensitizationHandler() {
        return new DesensitizationHandlerImpl();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
