package com.yc.ioc.core;

import com.yc.ioc.annotation.Bean;
import com.yc.ioc.annotation.Component;
import yc_util.core.ScannerPackage;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/*
@auther yc
@time 2018/10/30
该类的作用就是生成对象的一个工厂，通过包扫描，扫描带有@Component注解的类，
自动给其生成对象，并存储到BeanDepot类的仓库;
因为在实际操作中，类都是封装好的，你不可能去添加注解生成Bean，
因此需要写一些带有@Bean注解的方法，通过反射执行带有@Bean注解的方法生成Bean
存储到BeanDepot类的仓库中以备注入。
 */
public class BeanFactory {
    public static void scanPackage(String packageName) {
        BeanDepot beanDepot = new BeanDepot();

        new ScannerPackage() {
            @Override
            public void dealClass(Class<?> klass) {
                if (!klass.isAnnotationPresent(Component.class)) {
                    return;
                }

                try {
                    createBean(beanDepot, klass, null);
                    Object object = beanDepot.getYcBean(klass).getObject();
                    Method[] methods = klass.getMethods();
                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(Bean.class)) {
                            continue;
                        }
                        invokeBeanMethod(beanDepot, method, object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.scanPackage(packageName);
    }

    //反射带有@Bean注解的方法，并且创建Bean存储到BeanDepot仓库。因为要返回新生成的相对应的类的对象，其返回值不能为空。
    private static void invokeBeanMethod(BeanDepot beanDepot, Method method, Object object) throws Exception {
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(void.class)) {
            return;
        }

        Parameter[] parameters = method.getParameters();
        if(parameters.length <= 0) {
            Object result = method.invoke(object);
            createBean(beanDepot, returnType, result);
        } else {
            Object result = invokeParaBeanMethod(object, method, beanDepot, parameters);
            createBean(beanDepot, returnType, result);
        }
    }

    //反射带有参数的Bean方法，返回执行产生的对象。
    private static Object invokeParaBeanMethod(Object object,
                   Method method, BeanDepot beanDepot, Parameter[] parameters) throws Exception {
        int paraCount = parameters.length;
        Object[] args = new Object[paraCount];
        int index;
        for(index = 0; index < paraCount; index++) {
            Parameter parameter = parameters[index];
            Object argObject = beanDepot.getYcBean(parameter.getClass()).getObject();
            if(argObject != null) {
                args[index] = argObject;
            }
        }

        return method.invoke(object, args);
    }

    //将产生的Object存储进Beandepot仓库
    private static void createBean(BeanDepot beanDepot, Class<?> klass, Object object)
            throws Exception{
        if (object != null) {
            beanDepot.addBeanMap(klass, object);
        } else {
            beanDepot.addBeanMap(klass, klass.newInstance());
        }
    }
}
