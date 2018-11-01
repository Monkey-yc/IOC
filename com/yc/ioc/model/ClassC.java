package com.yc.ioc.model;

import com.yc.ioc.annotation.Autowired;
import com.yc.ioc.annotation.Component;

@Component
public class ClassC {
    @Autowired
    private ClassA classA;

    public ClassC() {
    }

    public ClassA getClassA() {
        return classA;
    }

    public void setClassA(ClassA classA) {
        this.classA = classA;
    }

    @Override
    public String toString() {
        return "ClassC:";
    }
}
