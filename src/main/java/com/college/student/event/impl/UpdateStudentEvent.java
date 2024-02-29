package com.college.student.event.impl;

import com.college.student.event.IEvent;
import com.college.student.pojo.Student;
import org.springframework.context.ApplicationEvent;

public class UpdateStudentEvent extends ApplicationEvent implements IEvent {
    private Student student;
    private Object source;
    public UpdateStudentEvent(Object source, Student student) {
        super(source);
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
