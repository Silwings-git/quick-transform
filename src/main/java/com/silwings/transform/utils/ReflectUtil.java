package com.silwings.transform.utils;


import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName ReflectUtil
 * @Description 反射工具类
 * @Author 崔益翔
 * @Date 2020/8/23 16:03
 * @Version V1.0
 **/
public class ReflectUtil {

    /**
     * description: 获取属性值.该方法通过获取get方法的方法对象来获取对象中的属性值
     * version: 1.0
     * date: 2020/8/23 16:31
     * author: 崔益翔
     *
     * @param src       源数据,将从该对象中获取属性值
     * @param fieldName 要获取的属性值的属性名称
     * @return T
     */
    public static <T> T getFieldValue(Object src, String fieldName) throws RuntimeException {
        try {
            Method[] m = src.getClass().getMethods();
            for (int i = 0; i < m.length; i++) {
                if (("get" + fieldName).toLowerCase().equals(m[i].getName().toLowerCase())) {
                    return (T) m[i].invoke(src);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * description: 根据属性，拿到set方法，并把值set到对象中
     * version: 1.0
     * date: 2020/8/25 8:11
     * author: 崔益翔
     *
     * @param obj       操作的对象实例
     * @param clazz     obj的类型
     * @param fieldName 需要赋值的字段名
     * @param value     值
     * @return void
     */
    public static void setValue(Object obj, Class<?> clazz, String fieldName, Object value) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        fieldName = removeLine(fieldName);
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

        Field field = getFieldByCurrentAndSuper(clazz, fieldName);
        if (null == field) {
            throw new NoSuchFieldException("未在" + clazz.getName() + "及其父类找到" + fieldName + "属性。");
        }
        Class<?> type = field.getType();
//            判断字段类型与输入的value类型是否一致
        boolean typeMatching = verifyValueType(type, value);
        if (!typeMatching) {
            throw new ClassCastException("参数与字段类型不匹配");
        }
        Method method = getMethodByCurrentAndSuper(clazz, methodName, new Class[]{type});
        if (null == method) {
            throw new NoSuchMethodException("未找到" + methodName + "(" + type.getName() + " " + fieldName + " )方法");
        }
        method.invoke(obj, new Object[]{value});
    }

    /**
     * description:
     * 判断输入的参数是否与指定类型匹配
     * 如果type不是基本数据类型,value值允许为null
     * version: 1.0
     * date: 2020/8/25 7:47
     * author: 崔益翔
     *
     * @param type
     * @param value
     * @return boolean
     */
    private static boolean verifyValueType(Class<?> type, Object value) {
//        value为空的情况
        if (null == value && !isCommonDataType(type)) {
            return true;
        } else if (null == value && isCommonDataType(type)) {
            return false;
        }

//        value不为空的情况
        if (isCommonDataType(type)) {
//            Class 对象表示一个基本类型时调用isInstance方法总是返回 false。所以需要使用包装类来比较
            type = getCommonOrWarp(type);
        }

        return type.isInstance(value);
    }

    /**
     * description: 从当前以及父类中获取字段
     * version: 1.0
     * date: 2020/8/25 0:07
     * author: 崔益翔
     *
     * @param clazz     属性所在类
     * @param fieldName 属性（字段）名
     * @return java.lang.reflect.Field
     */
    private static Field getFieldByCurrentAndSuper(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            if (!clazz.equals(Object.class)) {
                return getFieldByCurrentAndSuper(clazz.getSuperclass(), fieldName);
            }
        }
        return field;
    }


    /**
     * description: 根据名称获取字段对象
     * version: 1.0
     * date: 2020/9/6 10:12
     * author: 崔益翔
     *
     * @param src
     * @param fieldName
     * @return java.lang.reflect.Field
     */
    public static Field getFieldByName(Object src, String fieldName) throws NoSuchFieldException {
        return src.getClass().getField(fieldName);
    }

    /**
     * description: 从当前以及父类中获取全部字段
     * version: 1.0
     * date: 2020/8/25 0:07
     * author: 崔益翔
     *
     * @param clazz 属性所在类
     * @return java.lang.reflect.Field
     */
    public static List<Field> getFieldByCurrentAndSuper(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        getFieldByCurrentAndSuper(clazz, fields);
        return fields;
    }

    /**
     * description: 从当前以及父类中获取全部字段
     * version: 1.0
     * date: 2020/8/25 0:07
     * author: 崔益翔
     *
     * @param clazz 属性所在类
     * @return java.lang.reflect.Field
     */
    private static List<Field> getFieldByCurrentAndSuper(Class<?> clazz, List<Field> fields) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            fields.add(declaredField);
        }
        if (!clazz.equals(Object.class)) {
            return getFieldByCurrentAndSuper(clazz.getSuperclass(), fields);
        }
        return fields;
    }

    /**
     * description: 从当前以及父类中获取全部字段.并按照父类字段在前的顺序排序
     * version: 1.0
     * date: 2020/8/25 0:07
     * author: 崔益翔
     *
     * @param clazz 属性所在类
     * @return java.lang.reflect.Field
     */
    public static List<Field> getFieldSortBySuperFront(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Stack<Class> fieldStack = new Stack<>();
        getFieldSortBySuperFront(clazz, fields, fieldStack);
        return fields;
    }

    /**
     * description: 从当前以及父类中获取全部字段.并按照父类字段在前的顺序排序
     * version: 1.0
     * date: 2020/9/6 0:19
     * author: 崔益翔
     *
     * @param clazz
     * @param fields
     * @param fieldStack
     * @return void
     */
    private static void getFieldSortBySuperFront(Class<?> clazz, List<Field> fields, Stack<Class> fieldStack) {
        if (!clazz.equals(Object.class)) {
            fieldStack.push(clazz);
            getFieldSortBySuperFront(clazz.getSuperclass(), fields, fieldStack);
        } else {
            Iterator<Class> stackIterator = fieldStack.iterator();
            while (stackIterator.hasNext()) {
                Class elementClass = fieldStack.pop();
                Field[] declaredFields = elementClass.getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    fields.add(declaredField);
                }
            }
        }
    }

    /**
     * description: 从当前或父类获取指定的方法对象
     * version: 1.0
     * date: 2020/8/24 23:03
     * author: 崔益翔
     *
     * @param src        要获取的方法所在的类
     * @param methodName 要获取的方法的方法名称
     * @param paramTypes 要获取的方法的方法参数类型列表
     * @return java.lang.reflect.Method
     */
    private static Method getMethodByCurrentAndSuper(Class<?> src, String methodName, Class<?>[] paramTypes) {
        Method method = null;
        try {
            method = src.getDeclaredMethod(methodName, paramTypes);
//            取消访问限制检查
            method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            method = getMethod(src, methodName, paramTypes);
        }
        if (method == null && !src.equals(Object.class)) {
            return getMethod(src.getSuperclass(), methodName, paramTypes);
        }
        return method;
    }


    /**
     * description: 获取当前类的方法
     * version: 1.0
     * date: 2020/8/24 23:42
     * author: 崔益翔
     *
     * @param src        方法所在类
     * @param methodName 方法名称
     * @param paramTypes 方法参数类型
     * @return java.lang.reflect.Method
     */
    private static Method getMethod(Class<?> src, String methodName, Class<?>[] paramTypes) {
        Method resultMethod = null;
//        获取该class的所有方法
        Method[] methods = src.getDeclaredMethods();

        if (null == methods || methods.length == 0) {
            return null;
        }

        methodFor:
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            int paramLength = parameterTypes == null ? 0 : parameterTypes.length;
//            如果方法名相同，参数数量相同，进行重载判断
            if (method.getName().equals(methodName) && paramLength == paramTypes.length) {
//              判断重载
                if (parameterTypes.length == 0) {
//                  没有参数不存在重载,说明就是该方法
//                  取消访问限制检查
                    method.setAccessible(true);
                    resultMethod = method;
                    break;
                }
//                如果出现类型不匹配，说明该方法不匹配，应当进入下一个循环
                paramFor:
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> paramType = paramTypes[i];
                    boolean equals = paramType.equals(parameterTypes[i]);
                    if (equals) {
//                        类型相同,判断下一个
                        continue;
                    }
//                        不相同时判断是否是包装类
                    boolean commonOrWrap = isCommonOrWrap(paramType);
                    if (commonOrWrap) {
//                        是包装类或基本数据类型，判断另一种类型
                        paramType = getCommonOrWarp(paramType);
                        boolean twoEquals = paramType.equals(parameterTypes[i]);
                        if (!twoEquals) {
//                            还不相同说明不是该方法
                            continue methodFor;
                        }
                    }
                }
//              取消访问限制检查
                method.setAccessible(true);
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }

    /**
     * description: 获取指定基本数据类型的包装类或者指定包装类的基本数据类型
     * version: 1.0
     * date: 2020/8/24 23:52
     * author: 崔益翔
     *
     * @param paramType 只能是基本数据类型或包装类Class对象，否则返回null
     * @return java.lang.Class<?>
     */
    private static Class<?> getCommonOrWarp(Class<?> paramType) {

        if (null == paramType) {
            return null;
        }

        if (!isCommonOrWrap(paramType)) {
            return null;
        }

        Class resultClass = null;

        if (paramType.equals(byte.class) || paramType.equals(Byte.class)) {
            resultClass = paramType.equals(byte.class) ? Byte.class : byte.class;

        } else if (paramType.equals(short.class) || paramType.equals(Short.class)) {
            resultClass = paramType.equals(short.class) ? Short.class : short.class;

        } else if (paramType.equals(int.class) || paramType.equals(Integer.class)) {
            resultClass = paramType.equals(int.class) ? Integer.class : int.class;

        } else if (paramType.equals(long.class) || paramType.equals(Long.class)) {
            resultClass = paramType.equals(long.class) ? Long.class : long.class;

        } else if (paramType.equals(float.class) || paramType.equals(Float.class)) {
            resultClass = paramType.equals(float.class) ? Float.class : float.class;

        } else if (paramType.equals(double.class) || paramType.equals(Double.class)) {
            resultClass = paramType.equals(double.class) ? Double.class : double.class;

        } else if (paramType.equals(boolean.class) || paramType.equals(Boolean.class)) {
            resultClass = paramType.equals(boolean.class) ? Boolean.class : boolean.class;

        } else if (paramType.equals(char.class) || paramType.equals(Character.class)) {
            resultClass = paramType.equals(char.class) ? Character.class : char.class;
        }
        return resultClass;
    }

    /**
     * 判断是否是基础数据类型，即 int,double,long等类似格式
     */
    /**
     * description: 判断是否是基本数据类型
     * version: 1.0
     * date: 2020/8/24 23:53
     * author: 崔益翔
     *
     * @param clazz 需要校验的Class对象
     * @return boolean 是返回true
     */
    private static boolean isCommonDataType(Class clazz) {
        return clazz.isPrimitive();
    }

    /**
     * description: 判断是否是基础数据类型的包装类型
     * version: 1.0
     * date: 2020/8/24 23:54
     * author: 崔益翔
     *
     * @param clazz 需要校验的Class对象
     * @return boolean 是返回true
     */
    private static boolean isWrapClass(Class clazz) {
        try {
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * description: 是否是基本数据类型或包装类型
     * version: 1.0
     * date: 2020/8/24 23:55
     * author: 崔益翔
     *
     * @param clazz 需要校验的Class对象
     * @return boolean 是返回true
     */
    public static boolean isCommonOrWrap(Class clazz) {
        return isCommonDataType(clazz) || isWrapClass(clazz);
    }

    /**
     * description: 判断是否是基本数据类型或String类型
     * version: 1.0
     * date: 2020/12/19 16:43
     * author: 崔益翔
     * @param obj
     * @return boolean
     */
    public static boolean isCommonOrWrapOrString(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class<?> clazz = obj.getClass();
        return isCommonDataType(clazz) || isWrapClass(clazz);
    }

    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }


    /**
     * 处理字符串  如：  abc_dex ---> abcDex
     *
     * @param str
     * @return
     */
    public static String removeLine(String str) {
        if (null != str && str.contains("_")) {
            int i = str.indexOf("_");
            char ch = str.charAt(i + 1);
            char newCh = (ch + "").substring(0, 1).toUpperCase().toCharArray()[0];
            String newStr = str.replace(str.charAt(i + 1), newCh);
            String newStr2 = newStr.replace("_", "");
            return newStr2;
        }
        return str;
    }

    public static <E> List<Field> getFieldByAnnotation(Class clazz, Class<? extends Annotation> annotation) {
        Objects.requireNonNull(clazz);
        List<Field> resultFieldList = new ArrayList<>();
        List<Field> fieldByCurrentAndSuper = getFieldByCurrentAndSuper(clazz);
        for (Field field : fieldByCurrentAndSuper) {
            E e = (E) field.getAnnotation(annotation);
            if (null != e) {
                resultFieldList.add(field);
            }
        }
        return resultFieldList;
    }

    /**
     * description: 获取超类的通用类型
     * version: 1.0
     * date: 2020/11/7 17:21
     * author: 崔益翔
     *
     * @param aClass
     * @param index
     * @return java.lang.Class
     */
    public static Class getSuperClassGenricType(Class<?> aClass, int index) {
        Type genType = aClass.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class) params[index];

    }

    public static Class getSuperClassGenricType(Class<?> aClass) {
        return getSuperClassGenricType(aClass, 0);
    }
}


