package com.silwings.transfiguration.config;

import com.silwings.transfiguration.controller_advice.TransfigurationResponseBodyAdvice;
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
    @Bean
    public TransfigurationResponseBodyAdvice transfigurationResponseBodyAdvice() {
        return new TransfigurationResponseBodyAdvice();
    }

    @Bean
    public DesensitizationProcessor desensitizationProcessor() {
        return new DataDesensitizationProcessor();
    }
}
