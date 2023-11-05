package com.jony.spring;

import com.jony.spring.config.AppConfig;
import com.jony.spring.service.BaseService;

/**
 * @author :Jooye
 * @date : 2023-11-05 18:28
 * @Describe: 类的描述信息
 */
public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);

        BaseService userService = (BaseService) applicationContext.getBean("userService");
        userService.test();


    }
}
