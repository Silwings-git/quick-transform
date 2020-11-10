package com.silwings.transfiguration.config;

import com.silwings.transfiguration.advice.DesensitizationAdvice;
import com.silwings.transfiguration.container.DesensitizationStrategyContainer;
import com.silwings.transfiguration.handler.DesensitizationHandler;
import com.silwings.transfiguration.handler.specific.DesensitizationHandlerImpl;
import com.silwings.transfiguration.processor.DesensitizationManager;
import com.silwings.transfiguration.processor.specific.DataDesensitizationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName TransfigurationConfig
 * @Description 项目配置类
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
     * description: 初始化脱敏管理器
     * version: 1.0
     * date: 2020/11/7 20:59
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.processor.DesensitizationManager
     */
    @Bean
    public DesensitizationManager desensitizationManager() {
        return new DataDesensitizationManager(desensitizationStrategyContainer, desensitizationHandler());
    }

    /**
     * description: 初始化脱敏处理程序
     * version: 1.0
     * date: 2020/11/7 20:59
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.handler.DesensitizationHandler
     */
    @Bean
    public DesensitizationHandler desensitizationHandler() {
        return new DesensitizationHandlerImpl();
    }

    /**
     * description: 初始化本组件最核心的AOP操作类
     * version: 1.0
     * date: 2020/11/7 21:00
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.advice.DesensitizationAdvice
     */
    @Bean
    public DesensitizationAdvice desensitizationAdvice() {
        return new DesensitizationAdvice(desensitizationManager());
    }


}
