package com.college.student.event.impl;

import com.college.student.event.IEvent;
import com.college.student.pojo.Student;

public class AddStudentEvent implements IEvent {
    private Object source;
    private Student student;
    public AddStudentEvent(Object source,Student student) {
        this.student = student;
        this.source = source;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
