package com.yc.ioc.student;

import com.yc.ioc.annotation.Bean;
import com.yc.ioc.annotation.Component;

@Component
public class StudentConfig {
    public StudentConfig() {
    }

    @Bean
    public StudentModel getStuModel() {
        return new StudentModel();
    }

    @Bean
    public StudentDao getStuDao() {
        return new StudentDao();
    }
}
