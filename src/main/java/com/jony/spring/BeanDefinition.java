package com.jony.spring;

/**
 * @author :Jooye
 * @date : 2023-11-05 19:39
 * @Describe: 类的描述信息
 */
public class BeanDefinition {
    private String beanName;

    private Class beanClass;

    private String scope;


    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
