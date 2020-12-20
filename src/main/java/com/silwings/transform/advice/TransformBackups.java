package com.silwings.transform.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * @ClassName TransformBackups
 * @Description
 * @Author 崔益翔
 * @Date 2020/12/19 15:17
 * @Version V1.0
 **/
public class TransformBackups {
    private static final Logger LOG = LoggerFactory.getLogger(TransformBackups.class);

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
    protected static <T> T setBackup(T res, T src) {
        HashMap<Object, Object> transformBackupMap = TB.get();
//        需要初始化HashMap
        if (null == transformBackupMap) {
            transformBackupMap = new HashMap<>(16);
            TB.set(transformBackupMap);
        }
        T oldValue = (T) transformBackupMap.put(res, src);
//        可能存在覆盖的情况
        if (null != oldValue) {
            LOG.warn("quick-transform的原始备份数据 key= " + res + " : value= " + oldValue + " 已被新数据覆盖.新数据value= " + src);
        }
        return oldValue;
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
     * description: 清除全部备份数据
     * version: 1.0
     * date: 2020/12/19 15:35
     * author: 崔益翔
     *
     * @param
     * @return void
     */
    public static void removeAllBackup() {
        TB.remove();
    }

    /**
     * description: 清除指定备份数据
     * version: 1.0
     * date: 2020/12/20 12:15
     * author: 崔益翔
     *
     * @param res 数据转换后的对象
     * @return T
     */
    public static <T> T removeBackup(T res) {
        HashMap<Object, Object> transformBackupMap = TB.get();
        if (null == transformBackupMap) {
            return null;
        }
        return (T) transformBackupMap.remove(res);
    }

}
