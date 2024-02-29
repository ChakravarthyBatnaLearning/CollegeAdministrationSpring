package com.college.student.event.impl;

import com.college.student.event.IEvent;
import com.college.student.pojo.Student;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

public class GetStudentEvent extends ApplicationEvent implements IEvent {
    private final Student student;
    private final Object source;

    public GetStudentEvent(Object source, Student student) {
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
