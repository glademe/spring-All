package com.jony.spring.test;

import com.jony.spring.ApplicationContext;
import com.jony.spring.config.AppConfig;
import com.jony.spring.service.UserService;
import org.junit.Test;

/**
 * @author :Jooye
 * @date : 2023-11-05 17:57
 * @Describe: 类的描述信息
 */
public class ApiTest {


    @Test
    public void test() {
        //创建非懒加载的单例Bean
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);

        UserService userService = (UserService) applicationContext.getBean("userService");
        UserService userService1 = (UserService) applicationContext.getBean("userService");
        UserService userService2 = (UserService) applicationContext.getBean("userService");
        UserService userService3 = (UserService) applicationContext.getBean("userService");


        System.out.println(userService);
        System.out.println(userService1);
        System.out.println(userService2);
        System.out.println(userService3);

    }
}
