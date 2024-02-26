package com.college.student.event.impl;

import com.college.student.event.IEvent;
import com.college.student.pojo.Student;

public class GetStudentEvent implements IEvent {
    private Student student;
    private Object source;

    public GetStudentEvent(Object source, Student student) {
        this.source = source;
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public Object getSource() {
        return source;
    }
}
