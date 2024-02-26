package com.college.student.event.impl;

import com.college.student.event.IEvent;
import com.college.student.pojo.Student;

import java.util.List;

public class GetAllStudentEvent implements IEvent {
    private List<Student> studentList;
    private Object source;

    public GetAllStudentEvent(Object source, List<Student> studentList) {
        this.source = source;
        this.studentList = studentList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
