package com.yc.ioc.model;

import com.yc.ioc.annotation.Autowired;
import com.yc.ioc.annotation.Component;

@Component
public class ClassB {
    @Autowired
    private ClassC classC;

    public ClassB() {
    }

    public ClassC getClassC() {
        return classC;
    }

    public void setClassC(ClassC classC) {
        this.classC = classC;
    }

    @Override
    public String toString() {
        return "ClassB:" + classC;
    }
}
