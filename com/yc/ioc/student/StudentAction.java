package com.yc.ioc.student;

import com.yc.ioc.annotation.Autowired;
import com.yc.ioc.annotation.Component;

@Component
public class StudentAction {
    @Autowired
    private StudentDao dao;

    public StudentAction() {
    }

    public StudentModel getStudent(String id) {
        StudentModel stu = dao.getStuById(id);

        return stu;
    }


}
