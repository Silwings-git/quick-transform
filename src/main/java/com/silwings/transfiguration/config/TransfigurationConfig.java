package com.silwings.transfiguration.config;

import com.silwings.transfiguration.advice.TransformAdvice;
import com.silwings.transfiguration.container.TransformStrategyContainer;
import com.silwings.transfiguration.handler.TransformHandler;
import com.silwings.transfiguration.handler.specific.TransformHandlerImpl;
import com.silwings.transfiguration.processor.TransformManager;
import com.silwings.transfiguration.processor.specific.DataTransformManager;
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

    private TransformStrategyContainer transformStrategyContainer;

    public TransfigurationConfig(TransformStrategyContainer transformStrategyContainer) {
        this.transformStrategyContainer = transformStrategyContainer;
    }

    /**
     * description: 初始化脱敏管理器
     * version: 1.0
     * date: 2020/11/7 20:59
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.processor.TransformManager
     */
    @Bean
    public TransformManager transformManager() {
        return new DataTransformManager(transformStrategyContainer, transformHandler());
    }

    /**
     * description: 初始化脱敏处理程序
     * version: 1.0
     * date: 2020/11/7 20:59
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.handler.TransformHandler
     */
    @Bean
    public TransformHandler transformHandler() {
        return new TransformHandlerImpl();
    }

    /**
     * description: 初始化本组件最核心的AOP操作类
     * version: 1.0
     * date: 2020/11/7 21:00
     * author: 崔益翔
     *
     * @param
     * @return com.silwings.transfiguration.advice.TransformAdvice
     */
    @Bean
    public TransformAdvice transformAdvice() {
        return new TransformAdvice(transformManager());
    }


}
