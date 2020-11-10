package com.silwings.transform.handler.specific;

import com.silwings.transform.strategy.TransformStrategy;
import com.silwings.transform.handler.TransformHandler;


/**
 * @ClassName TransformHandlerImpl
 * @Description 该类负责对脱敏策略进行调度
 * @Author 崔益翔
 * @Date 2020/11/7 10:50
 * @Version V1.0
 **/
public class TransformHandlerImpl implements TransformHandler {
    /**
     * description: 使用strategy对数据进行处理
     * version: 1.0
     * date: 2020/11/7 13:17
     * author: 崔益翔
     *
     * @param source 待处理数据
     * @param strategy 脱敏策略
     * @return T 处理后的值
     */
    @Override
    public <T> T execute(T source, TransformStrategy strategy) {
        return (T) strategy.transform(source);
    }
}
