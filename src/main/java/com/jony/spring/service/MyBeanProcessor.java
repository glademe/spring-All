package com.jony.spring.service;

import com.jony.spring.BeanPostProcessor;
import com.jony.spring.annotation.Component;
import com.jony.spring.annotation.Scope;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author :Jooye
 * @date : 2023-11-05 21:36
 * @Describe: 类的描述信息
 */
@Component
@Scope("singleton")
public class MyBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (beanName.equals("userService")) {
            Object proxyInstance = Proxy.newProxyInstance(MyBeanProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("切面逻辑MyBeanProcessor.invoke");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }

        return bean;
    }
}
