package com.silwings.transfiguration.config;

import com.silwings.transfiguration.container.DesensitizationStrategyContainer;
import com.silwings.transfiguration.controller_advice.TransfigurationResponseBodyAdvice;
import com.silwings.transfiguration.desensitization_strategy.specific.PhoneDesensitizationStrategy;
import com.silwings.transfiguration.handler.DesensitizationHandler;
import com.silwings.transfiguration.handler.specific.DesensitizationHandlerImpl;
import com.silwings.transfiguration.processor.DesensitizationProcessor;
import com.silwings.transfiguration.processor.specific.DataDesensitizationProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DemoConfig
 * @Description TODO
 * @Author 崔益翔
 * @Date 2020/10/11 10:55
 * @Version V1.0
 **/
@Configuration
public class TransfigurationConfig {
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
    public PhoneDesensitizationStrategy phoneDesensitizationStrategy() {
        return new PhoneDesensitizationStrategy();
    }

    @Bean
    public DesensitizationStrategyContainer desensitizationStrategyContainer() {
        return DesensitizationStrategyContainer.getInstance().addStrategy(phoneDesensitizationStrategy());
    }

    @Bean
    public DesensitizationProcessor desensitizationProcessor() {
        return new DataDesensitizationProcessor(desensitizationStrategyContainer(), desensitizationHandler());
    }

    @Bean
    public DesensitizationHandler desensitizationHandler() {
        return new DesensitizationHandlerImpl();
    }
}
