package com.yc.ioc.core;
/*
@auther yc
@time 2018/11/1
 */
public class YcBean {
    private Object object;
    private Boolean isinjection;

    YcBean() {
        isinjection = false;
    }

    Object getObject() {

        return object;
    }

    void setObject(Object object) {
        this.object = object;
    }

    Boolean getisInjection() {

        return isinjection;
    }

    void setisInjection(Boolean isinjection) {
        this.isinjection = isinjection;
    }

}
