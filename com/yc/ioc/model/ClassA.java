package com.yc.ioc.model;

import com.yc.ioc.annotation.Autowired;
import com.yc.ioc.annotation.Component;

@Component
public class ClassA {
    @Autowired
    private ClassB classB;

    public ClassA() {
    }

    public ClassB getClassB() {
        return classB;
    }

    public void setClassB(ClassB classB) {
        this.classB = classB;
    }

    @Override
    public String toString() {
        return "ClassA:" + classB;
    }
}
