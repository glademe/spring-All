package com.jony.spring.service;

import com.jony.spring.BeanPostProcessor;
import com.jony.spring.annotation.Component;
import com.jony.spring.annotation.Scope;
import com.jony.spring.annotation.Value;

import java.lang.reflect.Field;

/**
 * @author :Jooye
 * @date : 2023-11-05 22:11
 * @Describe: 类的描述信息
 */
@Component
@Scope("singleton")
public class ValueBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
            for (Field field :  bean.getClass().getDeclaredFields()) {
                boolean hasValueAnnotation = field.isAnnotationPresent(Value.class);
                if (hasValueAnnotation) {
                    field.setAccessible(true);
                    Value valueAnnotation = field.getAnnotation(Value.class);
                    String value = valueAnnotation.value();
                    try {
                        field.set(bean, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        return bean;
    }
}
