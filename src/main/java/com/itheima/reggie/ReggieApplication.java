package com.itheima.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */

//lombok提供的日志功能
@Slf4j
@SpringBootApplication
@ServletComponentScan
//事务注解支持
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        //启动日志
        log.info("项目启动成功...");
    }
}
