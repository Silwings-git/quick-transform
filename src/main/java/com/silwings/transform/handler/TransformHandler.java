package com.silwings.transform.handler;

import com.silwings.transform.strategy.TransformStrategy;

/**
 * @ClassName TransformHandler
 * @Description 该类实现类负责对脱敏策略进行调度
 * @Author 崔益翔
 * @Date 2020/11/7 10:49
 * @Version V1.0
 **/
public interface TransformHandler {

    <T> T execute(T source, TransformStrategy strategy);
}
