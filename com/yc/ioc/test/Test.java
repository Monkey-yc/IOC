package com.yc.ioc.test;

import com.yc.ioc.core.BeanDepot;
import com.yc.ioc.core.BeanFactory;
import com.yc.ioc.model.ClassA;
import com.yc.ioc.model.ClassB;
import com.yc.ioc.student.StudentAction;
import com.yc.ioc.student.StudentModel;

public class Test {
    public static void main(String[] args) {
        BeanFactory.scanPackage("com.yc.ioc.student");

        StudentAction studentAction = (StudentAction) BeanDepot.getBean(StudentAction.class);
        StudentModel student = studentAction.getStudent("12138");

        System.out.println(student);

        //依赖循环测试
        // ClassA classA = (ClassA) BeanDepot.getBean(ClassA.class);
        // System.out.println(classA.toString());

    }
}
