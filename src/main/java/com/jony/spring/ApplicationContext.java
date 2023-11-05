package com.jony.spring;

import com.jony.spring.annotation.Autowired;
import com.jony.spring.annotation.Component;
import com.jony.spring.annotation.ComponentScan;
import com.jony.spring.annotation.Scope;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author :Jooye
 * @date : 2023-11-05 17:54
 * @Describe: 类的描述信息
 */
@SuppressWarnings("all")
public class ApplicationContext {

    Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    Map<String, Object> singleObjects = new HashMap<>();

    private Class targetCLass;

    private String scanPath;

    /**
     * 构造方法
     *
     * @param clazz
     */
    public ApplicationContext(Class clazz) {
        this.targetCLass = clazz;
        //扫描
        scan();

        Set<Map.Entry<String, BeanDefinition>> entries = beanDefinitionMap.entrySet();
        for (Map.Entry<String, BeanDefinition> entry : entries) {
            String key = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            //单例bean
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(key, beanDefinition);
                singleObjects.put(key, bean);
            }
        }
    }

    private Object createBean(String key, BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        Object instance;
        try {
            //无参构造创建对象
            instance = beanClass.getConstructor().newInstance();
            //依赖注入 DI
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    //属性
                    field.setAccessible(true);
                    //反射设置值
                    field.set(instance, getBean(field.getName()));
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void scan() {
        //扫描
        if (targetCLass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotation = (ComponentScan) targetCLass.getAnnotation(ComponentScan.class);
            //注解中的属性
            scanPath = componentScanAnnotation.value();
            //扫描该路径下的类是否包含@Component注解
            String replacePath = scanPath.replace(".", "\\");
            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(replacePath);
            File file = new File("D:\\后端\\代码\\spring-All\\target\\classes\\com\\jony\\spring\\service");
            if (file.isDirectory()) {
                for (File listFile : file.listFiles()) {
                    String absolutePath = listFile.getAbsolutePath();
                    absolutePath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class")).toString();
                    absolutePath = absolutePath.replace("\\", ".");
                    //加载字节码文件
                    Class<?> aClass = null;
                    try {
                        aClass = classLoader.loadClass(absolutePath);
                        boolean hasComponent = aClass.isAnnotationPresent(Component.class);
                        boolean hasScope = aClass.isAnnotationPresent(Scope.class);
                        if (hasComponent) {
                            Component componentAnnotation = aClass.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if ("".equals(beanName)) {
                                beanName = Introspector.decapitalize(aClass.getSimpleName());
                            }
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setBeanClass(aClass);
                            if (hasScope) {
                                Scope scopeAnnotation = aClass.getAnnotation(Scope.class);
                                String scopeValue = scopeAnnotation.value();
                                //单例
                                if (scopeValue.equals("singleton")) {
                                    beanDefinition.setScope("singleton");
                                } else if (scopeValue.equals("prototype")) {
                                    beanDefinition.setScope("prototype");
                                }
                            }
                            beanDefinition.setBeanName(beanName);
                            //存储BeanDefinition
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }


                }
            }
        }
    }


    /**
     * 从Ioc容器获取Bean
     */
    public Object getBean(String beanName) {
        if (!beanDefinitionMap.containsKey(beanName)) {
            throw new RuntimeException("name " + beanName + " not found ,please check Bean " + beanName + "");
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition.getScope().equals("singleton")) {
            Object singleBean = singleObjects.get(beanName);
            if (singleBean == null) {
                singleBean = createBean(beanName, beanDefinition);
                singleObjects.put(beanName, beanDefinition);
            }
            return singleBean;
        } else if (beanDefinition.getScope().equals("prototype")) {
            Object bean = createBean(beanName, beanDefinition);
            return bean;
        }
        return null;
    }
}
