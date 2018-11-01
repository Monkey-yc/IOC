package com.yc.ioc.core;

import com.yc.ioc.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
/*
@auther yc
@time 2018/10/30
建立存储Bean的一个仓库，在程序加载时将所有加@Bean注解的方法均初始化。
以Map存储类名及其对应的生成对象，在加载时遇到带有@Autowried注解的成员时自动注入
若Map中没有需要的Bean，则会调用BeanFactory类及时生成并存储到Map。
 */
public class BeanDepot {
    private static final Map<String, YcBean> beanMap = new HashMap<>();

    BeanDepot() {
    }

    private static void injection(Class<?> klass, Object object) {
        Field[] fields = klass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                return;
            }
            //表示该类已被注入过;
            beanMap.get(klass.getName()).setisInjection(true);

            Class<?> fieldType = field.getType();
            YcBean fieldTypeBean = beanMap.get(fieldType.getName());
            Object fieldobject = fieldTypeBean.getObject();
            //如果成员的类类型需要注入，即成员的类型在被加载时发现他的类中需要注入另一个
            //成员，其才能被成功加载。同理，在加载其成员的类的成员时，成员的成员也需要注入成员
            // 故此处应使用递归处理。
            if(!fieldTypeBean.getisInjection()) {
                injection(fieldType, fieldobject);
            }
            field.setAccessible(true);
            try {
                field.set(object, fieldobject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static Object getBean(Class<?> klass) {
        YcBean ycBean = beanMap.get(klass.getName());
        if(!ycBean.getisInjection()) {
            injection(klass, ycBean.getObject());
        }

        return ycBean.getObject();
    }

    YcBean getYcBean(Class<?> klass) {
        return beanMap.get(klass.getName());
    }

    void addBeanMap(Class<?> klass, Object object) throws Exception {
        String className = klass.getName();
        YcBean ycBean = beanMap.get(className);
        if(ycBean != null) {
            throw new Exception(className + "已存在！");
        }
        ycBean = new YcBean();
        ycBean.setObject(object);
        beanMap.put(className, ycBean);
    }

    void removeBeanMap(String className) {
        beanMap.remove(className);
    }
}
