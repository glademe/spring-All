package com.jony.spring.service;

import com.jony.spring.annotation.Autowired;
import com.jony.spring.annotation.Component;
import com.jony.spring.annotation.Scope;

/**
 * @author :Jooye
 * @date : 2023-11-05 17:53
 * @Describe: 类的描述信息
 */
@Component("userService")
@Scope("singleton")
public class UserService {

    @Autowired
    private OrderService orderService;


    public void test() {
        System.out.println("orderService = " + orderService);
    }
}
