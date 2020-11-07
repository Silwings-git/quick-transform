package com.silwings.transfiguration.config;

import com.silwings.transfiguration.container.DesensitizationStrategyContainer;
import com.silwings.transfiguration.controller_advice.TransfigurationResponseBodyAdvice;
import com.silwings.transfiguration.handler.DesensitizationHandler;
import com.silwings.transfiguration.handler.specific.DesensitizationHandlerImpl;
import com.silwings.transfiguration.processor.DesensitizationManager;
import com.silwings.transfiguration.processor.specific.DataDesensitizationManager;
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

    private DesensitizationStrategyContainer desensitizationStrategyContainer;

    public TransfigurationConfig(DesensitizationStrategyContainer desensitizationStrategyContainer) {
        this.desensitizationStrategyContainer = desensitizationStrategyContainer;
    }

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
    public DesensitizationManager desensitizationProcessor() {
        return new DataDesensitizationManager(desensitizationStrategyContainer, desensitizationHandler());
    }

    @Bean
    public DesensitizationHandler desensitizationHandler() {
        return new DesensitizationHandlerImpl();
    }

}
