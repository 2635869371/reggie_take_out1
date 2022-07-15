package com.itheima.reggie.common;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */

/**
 * 自定义业务异常类
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
