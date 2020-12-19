package com.silwings.transform.advice;

import java.util.HashMap;

/**
 * @ClassName TransformBackups
 * @Description
 * @Author 崔益翔
 * @Date 2020/12/19 15:17
 * @Version V1.0
 **/
public class TransformBackups {
    private static final ThreadLocal<HashMap<Object, Object>> TB = new ThreadLocal<>();

    /**
     * description: 设置备份数据到ThreadLocal中
     * 如果res为空，src无论是否为空都进行存储，但因为HashMap的特性，value可能会被后来的null key所替换，使用时需要注意
     * 方法上的泛型无法确保使用该方法时res与src类型一致,所以将该方法设置为protected,防止被错误使用
     * version: 1.0
     * date: 2020/12/19 15:19
     * author: 崔益翔
     *
     * @param res 新数据
     * @param src 旧数据
     * @return void
     */
    protected static <T> void setBackup(T res, T src) {
        HashMap<Object, Object> transformBackupMap = TB.get();
//        需要初始化HashMap
        if (null == transformBackupMap) {
            transformBackupMap = new HashMap<>(16);
            TB.set(transformBackupMap);
        }
        transformBackupMap.put(res, src);
    }

    /**
     * description: 使用当前数据获取备份的数据
     * version: 1.0
     * date: 2020/12/19 15:31
     * author: 崔益翔
     *
     * @param res 当前数据
     * @return T
     */
    public static <T> T getBackup(T res) {
        HashMap<Object, Object> transformBackupMap = TB.get();
        if (null == transformBackupMap) {
            return null;
        }
        Object value = transformBackupMap.get(res);
        return null == value ? null : (T) value;
    }

    /**
     * description: 清楚数据
     * version: 1.0
     * date: 2020/12/19 15:35
     * author: 崔益翔
     *
     * @param
     * @return void
     */
    public static void removeBackup() {
        TB.remove();
    }

}
