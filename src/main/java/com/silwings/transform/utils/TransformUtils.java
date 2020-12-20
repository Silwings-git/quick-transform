package com.silwings.transform.utils;

import com.silwings.transform.advice.TransformBackups;

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

    /**
     * description: 清除全部备份数据
     * version: 1.0
     * date: 2020/12/20 12:18
     * author: 崔益翔
     *
     * @param
     * @return void
     */
    public static void removeAllBackup() {
        TransformBackups.removeAllBackup();
    }

    /**
     * description: 清除指定备份数据
     * version: 1.0
     * date: 2020/12/20 12:19
     * author: 崔益翔
     *
     * @param res 数据转换后的对象
     * @return T
     */
    public static <T> T removeBackup(T res) {
        return TransformBackups.removeBackup(res);
    }

}
