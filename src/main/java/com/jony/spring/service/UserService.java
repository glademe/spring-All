package com.jony.spring.service;

import com.jony.spring.BeanNameAware;
import com.jony.spring.InitializingBean;
import com.jony.spring.annotation.Autowired;
import com.jony.spring.annotation.Component;
import com.jony.spring.annotation.Scope;
import com.jony.spring.annotation.Value;

/**
 * @author :Jooye
 * @date : 2023-11-05 17:53
 * @Describe: 类的描述信息
 */
@Component("userService")
@Scope("singleton")
public class UserService implements InitializingBean, BaseService, BeanNameAware {

    @Autowired
    private OrderService orderService;


    @Value("hi")
    public String hi;

    private String beanName;


    @Override
    public void test() {
        System.out.println("orderService = " + orderService + "======================" + hi + "======================" + beanName);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("UserService.afterPropertiesSet");
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
