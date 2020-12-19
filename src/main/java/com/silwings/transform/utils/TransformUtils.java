package com.silwings.transform.utils;

import com.silwings.transform.processor.specific.TransformBackups;

/**
 * @ClassName TransformUtils
 * @Description
 * @Author 崔益翔
 * @Date 2020/12/19 16:22
 * @Version V1.0
 **/
public class TransformUtils {
    /**
     * description: 使用当前数据获取备份数据
     * version: 1.0
     * date: 2020/12/19 16:24
     * author: 崔益翔
     *
     * @param res 当前数据
     * @return T 当前数据最初的备份数据
     */
    public static <T> T getBackup(T res) {
        return TransformBackups.getBackup(res);
    }
}
