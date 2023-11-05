package com.jony.spring;

/**
 * @author :Jooye
 * @date : 2023-11-05 21:34
 * @Describe: 类的描述信息
 */
public interface BeanPostProcessor {


    default Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }


}
