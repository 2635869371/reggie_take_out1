package com.itheima.reggie.common;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */

/**
 * 基于ThreadLocal封装工具类,用于保存和获取当前登录用户Id
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
