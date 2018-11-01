package com.yc.ioc.student;

public class StudentDao {
    public StudentDao() {
    }

    public StudentModel getStuById(String id) {
        StudentModel stu = new StudentModel();
        stu.setId("12138");
        stu.setName("张三");
        stu.setPasswd("123456");

        return stu;
    }
}
